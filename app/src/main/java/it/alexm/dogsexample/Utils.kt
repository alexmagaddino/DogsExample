package it.alexm.dogsexample

import android.view.View
import android.view.View.GONE
import android.view.View.VISIBLE


/**
 * Created by alexm on 04/04/2020
 */
fun <V : View> V.setVisible(isVisible: Boolean?) {
    visibility = if (isVisible == true) VISIBLE else GONE
}