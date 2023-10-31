package com.example.project7

import android.app.AlertDialog
import android.app.Dialog
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.DialogFragment

class DeleteConfirmationDialog(private val onConfirm: () -> Unit) : DialogFragment() {

    //delete confirmation dialog
    //if user presses yes then note gets deleted.
    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        return AlertDialog.Builder(activity)
            .setMessage("Are you sure you want to delete this note?")
            .setPositiveButton("Yes") { _, _ ->
                Log.i("DeleteConfirmationDialog", "onCreateDialog: Yes")
                onConfirm.invoke()
                dismiss()
            }
            .setNegativeButton("No") { _, _ ->
                Log.i("DeleteConfirmationDialog", "onCreateDialog: No")
                dismiss()
            }
            .create()
    }
}
