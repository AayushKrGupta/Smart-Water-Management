package aayush.kumar.smartwatermanagement.ui.gallery

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import aayush.kumar.smartwatermanagement.databinding.FragmentGalleryBinding
import android.webkit.WebSettings
import android.webkit.WebView
import android.webkit.WebViewClient

class GalleryFragment : Fragment() {

    private var _binding: FragmentGalleryBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentGalleryBinding.inflate(inflater, container, false)
        val root: View = binding.root

        // Configure WebView
        val webView: WebView = binding.webViewGraph
        webView.settings.javaScriptEnabled = true // Enable JavaScript
        webView.settings.cacheMode = WebSettings.LOAD_DEFAULT // Load normally
        webView.webViewClient = WebViewClient() // Ensures the link opens in the WebView

        // Load the URL for the Thingspeak graph
        webView.loadUrl("https://development.asia/explainer/what-smart-water-management")

        return root
    }


    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}