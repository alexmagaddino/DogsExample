package it.alexm.dogsexample

import android.content.Context
import android.view.View
import android.view.View.GONE
import android.view.View.VISIBLE
import android.widget.ImageView
import androidx.databinding.BindingAdapter
import androidx.swiperefreshlayout.widget.CircularProgressDrawable
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions


/**
 * Created by alexm on 04/04/2020
 */
fun <V : View> V.setVisible(isVisible: Boolean?) {
    visibility = if (isVisible == true) VISIBLE else GONE
}

fun getProgressDrawable(context: Context) = CircularProgressDrawable(context).apply {
    strokeWidth = 10f
    centerRadius = 50f
    start()
}

fun ImageView.loadImage(url: String?) {
    val options = RequestOptions()
        .placeholder(getProgressDrawable(context))
        .error(R.drawable.ic_launcher_foreground)

    Glide.with(context)
        .setDefaultRequestOptions(options)
        .load(url)
        .into(this)
}

@BindingAdapter("android:imageUrl")
fun bindLoadedImage(view: ImageView, url: String?) {
    view.loadImage(url)
}
