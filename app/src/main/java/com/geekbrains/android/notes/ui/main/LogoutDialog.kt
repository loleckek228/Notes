package com.geekbrains.android.notes.ui.main

import android.app.AlertDialog
import android.app.Dialog
import android.content.DialogInterface
import android.os.Bundle
import androidx.fragment.app.DialogFragment
import com.geekbrains.android.notes.R

class LogoutDialog : DialogFragment() {

    companion object {
        val TAG = LogoutDialog::class.java.name + "TAG"
        fun createInstance() = LogoutDialog()
    }

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog = AlertDialog.Builder(context!!)
            .setTitle(getString(R.string.logout_title))
            .setMessage(getString(R.string.logout_message))
            .setPositiveButton(R.string.logout_ok) { dialog, which ->
                (activity as LogoutListener).onLogout()
            }
            .setNegativeButton(R.string.logout_cancel) { dialog, which ->
                dismiss()
            }
            .create()


    interface LogoutListener {
        fun onLogout()
    }

}
//class LogoutDialog(val title: String, val message: String, val okClick: (DialogInterface) -> Unit) : DialogFragment() {
//    companion object {
//        val TAG = LogoutDialog::class.java.name + "TAG"
//
//        fun createInstance(title: String, message: String, okClick: (DialogInterface) -> Unit) =
//                LogoutDialog(title, message, okClick)
//    }
//
//    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog = AlertDialog.Builder(context!!)
//            .setTitle(title)
//            .setMessage(message)
//            .setPositiveButton(R.string.logout_ok) { dialog, which ->
//                okClick(dialog)
//            }
//            .setNegativeButton(R.string.logout_cancel) { dialog, which ->
//                dismiss()
//            }.create()
//}