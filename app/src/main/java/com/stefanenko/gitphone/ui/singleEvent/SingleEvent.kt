package com.stefanenko.gitphone.ui.singleEvent

class SingleEvent<T>(private var content: T) {

    var isHandled = false
        private set

    fun handleEvent(action: (T) -> Unit) {
        if (!isHandled) {
            action.invoke(content)
            isHandled = true
        }
    }
}