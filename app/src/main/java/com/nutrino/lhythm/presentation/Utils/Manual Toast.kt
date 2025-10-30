package com.nutrino.lhythm.presentation.Utils

import android.content.Context
import com.nutrino.lhythm.constants.Constants
import com.shashank.sony.fancytoastlib.FancyToast

fun showToastMessage(context: Context,text: String,type: String){
    when{
        type== Constants.TOASTSUCCESS ->{
            FancyToast.makeText(context,text.toString(),FancyToast.LENGTH_SHORT, FancyToast.SUCCESS,false).show()

        }
        type== Constants.TOASTERROR ->{
            FancyToast.makeText(context,text.toString(),FancyToast.LENGTH_SHORT, FancyToast.ERROR,false).show()
        }
        type== Constants.TOASTWARNING ->{
            FancyToast.makeText(context,text.toString(),FancyToast.LENGTH_SHORT, FancyToast.WARNING,false).show()
        }
        type== Constants.TOASTCONFUSING ->{
            FancyToast.makeText(context,text.toString(),FancyToast.LENGTH_SHORT, FancyToast.CONFUSING,false).show()
        }
    }


}