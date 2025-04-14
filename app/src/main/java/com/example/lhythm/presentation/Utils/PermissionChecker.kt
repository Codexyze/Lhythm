package com.example.lhythm.presentation.Utils

import android.content.Context
import android.content.pm.PackageManager
import android.widget.Toast
import androidx.core.content.ContextCompat

fun checkPermission(context: Context, permission: String): Boolean{
    if(ContextCompat.checkSelfPermission(context,permission)== PackageManager.PERMISSION_GRANTED){
        Toast.makeText(context, "Permission Granted", Toast.LENGTH_SHORT).show()
        return true
    }else{
        Toast.makeText(context, "Please grant Permission", Toast.LENGTH_SHORT).show()
        return false
    }

}