package com.gmail.appverstas.ketorecipes.adapters

import android.widget.ImageView
import android.widget.TextView
import androidx.databinding.BindingAdapter
import coil.load
import com.gmail.appverstas.ketorecipes.R
import org.jsoup.Jsoup

/**
 *Veli-Matti Tikkanen, 27.8.2021
 */
class RecipesRowBinding {

    companion object {

        @BindingAdapter("setReadyInMinutes")
        @JvmStatic
        fun setReadyInMinutes(textView: TextView, minutes: Int){
            textView.text = "$minutes Min"
        }


        @BindingAdapter("loadImageFromUrl")
        @JvmStatic
        fun loadImageFromUrl(imageView: ImageView, imageUrl: String){
            imageView.load(imageUrl){
                crossfade(600)
                error(R.drawable.ic_hourglass)
            }
        }

        @BindingAdapter("parseHtml")
        @JvmStatic
        fun parseHtml(textView: TextView, description: String?){
            description?.let {
                val desc = (Jsoup.parse(it)).text()
                textView.text = desc
            }
        }

    }
}