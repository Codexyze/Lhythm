package com.example.lhythm.presentation.Utils

import android.content.Context
import android.content.pm.PackageManager
import androidx.core.content.ContextCompat
import com.shashank.sony.fancytoastlib.FancyToast

fun checkPermission(context: Context, permission: String): Boolean{
    if(ContextCompat.checkSelfPermission(context,permission)== PackageManager.PERMISSION_GRANTED){
        FancyToast.makeText(
            context, "Permission Granted",
            FancyToast.LENGTH_LONG,
            FancyToast.SUCCESS, false
        ).show()

        return true
    }else{
        FancyToast.makeText(
            context, "Please Grant Permission",
            FancyToast.LENGTH_LONG,
            FancyToast.ERROR, false
        ).show()
        return false
    }

}