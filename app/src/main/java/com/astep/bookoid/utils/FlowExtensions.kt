package com.astep.bookoid.utils

import android.annotation.SuppressLint
import android.text.Editable
import android.text.TextWatcher
import android.widget.CheckBox
import android.widget.CompoundButton
import android.widget.EditText
import androidx.appcompat.widget.SearchView
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow


fun EditText.textChangedFlow(): Flow<String> =
    callbackFlow<String> {
        val textChangeListener = object : TextWatcher {
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {}
            override fun afterTextChanged(p0: Editable?) {}
            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                trySend(p0?.toString().orEmpty())
            }
        }
        this@textChangedFlow.addTextChangedListener(textChangeListener)
        awaitClose {
            this@textChangedFlow.removeTextChangedListener(textChangeListener)
        }
    }


fun CheckBox.checkedChangesFlow() =
    callbackFlow<Boolean> {
        val checkedChangeListener = CompoundButton.OnCheckedChangeListener { _, isChecked ->
            trySend(isChecked)
        }
        this@checkedChangesFlow.setOnCheckedChangeListener(checkedChangeListener)
        awaitClose {
            this@checkedChangesFlow.setOnCheckedChangeListener(null)
        }
    }


fun SearchView.textChangedFlow() =
    callbackFlow<String> {
        DebugLogger.d(msg = "new SearchView.textChangeListener")

        val textChangeListener = object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean = true

//            @SuppressLint("SetTextI18n")
            override fun onQueryTextChange(newText: String?): Boolean {
                trySend(newText.orEmpty())
                return true
            }
        }
        this@textChangedFlow.setOnQueryTextListener(textChangeListener)
        awaitClose {
            DebugLogger.d(msg = "closed SearchView.textChangeListener")
            this@textChangedFlow.setOnQueryTextListener(null)
        }
    }