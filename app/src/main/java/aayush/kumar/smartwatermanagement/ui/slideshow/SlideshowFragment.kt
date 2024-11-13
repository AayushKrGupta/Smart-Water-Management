package aayush.kumar.smartwatermanagement.ui.slideshow

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import aayush.kumar.smartwatermanagement.databinding.FragmentSlideshowBinding
import android.webkit.WebSettings
import android.webkit.WebView
import android.webkit.WebViewClient

class SlideshowFragment : Fragment() {

    private var _binding: FragmentSlideshowBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val slideshowViewModel =
            ViewModelProvider(this).get(SlideshowViewModel::class.java)

        _binding = FragmentSlideshowBinding.inflate(inflater, container, false)
        val root: View = binding.root



        // Configure WebView
        val webView: WebView = binding.github
        webView.settings.javaScriptEnabled = true // Enable JavaScript
        webView.settings.cacheMode = WebSettings.LOAD_DEFAULT // Load normally
        webView.webViewClient = WebViewClient() // Ensures the link opens in the WebView


        webView.loadUrl("https://github.com/AayushKrGupta/Smart-Water-Management")

        return root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}