package com.gmail.appverstas.ketorecipes.ui.details

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.webkit.WebViewClient
import com.gmail.appverstas.ketorecipes.R
import com.gmail.appverstas.ketorecipes.data.models.Recipe
import kotlinx.android.synthetic.main.fragment_instructions.view.*


class InstructionsFragment : Fragment() {


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_instructions, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val args = arguments
        val myBundle: Recipe? = args?.getParcelable("recipeBundle")

        view.webview_instructions.webViewClient = WebViewClient()
        val websiteUrl = myBundle?.sourceUrl
        view.webview_instructions.loadUrl(websiteUrl ?: "")

    }
}