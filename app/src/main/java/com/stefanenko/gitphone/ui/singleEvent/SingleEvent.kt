package com.stefanenko.gitphone.ui.singleEvent

class SingleEvent <T>(private val content: T) {

    var isHandled = false
        private set

    fun getNotHandledContent(): T?{
        return if(isHandled){
            null
        }else{
            isHandled = true
            content
        }
    }
}