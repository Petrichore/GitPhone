package com.stefanenko.gitphone.ui.base

import android.app.AlertDialog
import android.content.Context
import android.content.DialogInterface
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import dagger.android.support.AndroidSupportInjection

abstract class BaseFragment: Fragment() {

    abstract fun getLayoutId(): Int
    abstract fun setListeners()

    override fun onAttach(context: Context) {
        AndroidSupportInjection.inject(this)
        super.onAttach(context)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        showDebugLog("onCreateView")
        val layoutId = getLayoutId()
        return inflater.inflate(layoutId, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        showDebugLog("onViewCreated")
        setListeners()
    }

    fun showDebugLog(message: String){
        Log.d(":::${this.javaClass.name}", message)
    }

     protected fun showAlertDialog(
        title: String,
        message: String,
        positiveAction: (dialog: DialogInterface) -> Unit,
        negativeAction: (dialog: DialogInterface) -> Unit
    ) {
        AlertDialog.Builder(context)
            .setTitle(title)
            .setMessage(message)
            .setPositiveButton("Ok") { dialog, _ ->
                positiveAction.invoke(dialog)
            }
            .setNegativeButton("Cancel") { dialog, _ ->
                negativeAction.invoke(dialog)
            }
            .create()
            .show()
    }
}