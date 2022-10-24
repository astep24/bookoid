package com.skillbox.lesson20myself

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.LayoutRes

// Обращаемся к родительской вью и просим заинфлейтить дочернюю вью
fun ViewGroup.inflate(@LayoutRes layoutRes: Int, attachToRoot: Boolean = false): View {
    // parent - это RecyclerView
    // получаем инфлейтер
    val inflater = LayoutInflater.from(this.context)
    // получаем корневую вью для элемента списка - инфлейтим разметку;
    // attachToRoot=false: это мы говорим, чтобы при инфлейте создаваемая
    // вьюшка не добавлялась в родительскую вью
    val view = inflater.inflate(layoutRes, this, attachToRoot)
    return view
}