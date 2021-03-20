package br.com.julianawl.anitime.ui

import android.app.Activity
import android.app.AlertDialog
import android.content.Context
import br.com.julianawl.anitime.R
import br.com.julianawl.anitime.ui.activity.MainActivity

class LoadingDialog {

    lateinit var dialog: AlertDialog

    fun show(context:Context){
        val builder = AlertDialog.Builder(context)
        val inflater = (context as Activity).layoutInflater
        builder.setView(inflater.inflate(R.layout.custom_loading_dialog, null))

        dialog = builder.create()
        dialog.show()
    }
     fun dismissDialog(){
         dialog.dismiss()
     }
}