package com.example.lhythm.presentation.Utils

import android.content.Context
import com.example.lhythm.constants.Constants
import com.shashank.sony.fancytoastlib.FancyToast
import org.w3c.dom.Text

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