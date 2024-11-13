package aayush.kumar.smartwatermanagement.ui.home

import aayush.kumar.smartwatermanagement.MyData
import aayush.kumar.smartwatermanagement.R
import aayush.kumar.smartwatermanagement.databinding.FragmentHomeBinding
import aayush.kumar.watermanagement.APIInterface
import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import android.os.Build
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.TranslateAnimation
import android.webkit.WebView
import android.widget.Toast
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import androidx.fragment.app.Fragment
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class HomeFragment : Fragment() {

    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        createNotificationChannel()

        // Set up Retrofit to fetch data
        val retrofit = Retrofit.Builder()
            .baseUrl("https://api.thingspeak.com/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()
        val api = retrofit.create(APIInterface::class.java)

        val call = api.getFeedData()
        call.enqueue(object : Callback<MyData?> {
            override fun onResponse(call: Call<MyData?>, response: Response<MyData?>) {
                val data = response.body()
                if (data != null) {
                    displayData(data)
                    checkForWarnings(data)
                } else {
                    Toast.makeText(context, "No data available", Toast.LENGTH_SHORT).show()
                }
            }

            override fun onFailure(call: Call<MyData?>, t: Throwable) {
                Toast.makeText(context, "Failed to load data", Toast.LENGTH_SHORT).show()
            }
        })

        // Set up WebView toggles for each CardView
        setupWebViewToggle(binding.tdsCardView, binding.tdsWebView, "https://thingspeak.mathworks.com/channels/2716484/charts/1?bgcolor=%23ffffff&color=%23d62020&dynamic=true&results=60&type=line&update=15")
        setupWebViewToggle(binding.temperatureCardView, binding.temperatureWebView, "https://thingspeak.mathworks.com/channels/2716484/charts/2?bgcolor=%23ffffff&color=%23d62020&dynamic=true&results=60&type=line&update=15")
        setupWebViewToggle(binding.waterUsedCardView, binding.waterUsedWebView, "https://thingspeak.mathworks.com/channels/2716484/charts/3?bgcolor=%23ffffff&color=%23d62020&dynamic=true&results=60&type=line&update=15")
    }

    private fun displayData(data: MyData) {
        val avgField1 = data.feeds.takeLast(20).mapNotNull { it.field1.toDoubleOrNull() }.average()
        val avgField2 = data.feeds.takeLast(20).mapNotNull { it.field2.toDoubleOrNull() }.average()
        val avgField3 = data.feeds.takeLast(20).mapNotNull { it.field3.toDoubleOrNull() }.average()

        // Set the dynamic text values in the UI
        binding.tdsValueTextView.text = "${"%.2f".format(avgField1)} ppm"
        binding.temperatureValueTextView.text = "${"%.2f".format(avgField2)}°C"
        binding.waterUsedValueTextView.text = "${"%.2f".format(avgField3)} liters"
    }

    private fun checkForWarnings(data: MyData) {
        val lastField1 = data.feeds.lastOrNull()?.field1?.toDoubleOrNull()
        val lastField2 = data.feeds.lastOrNull()?.field2?.toDoubleOrNull()

        if (lastField1 != null && lastField1 > 150) {
            sendNotification("Warning", "Water is not safe to drink: TDS > 150")
        }
        if (lastField2 != null && lastField2 > 36) {
            sendNotification("Warning", "Water is too hot: Temperature > 36°C")
        }
    }

    private fun sendNotification(title: String, message: String) {
        val builder = NotificationCompat.Builder(requireContext(), "warning_channel")
            .setSmallIcon(R.drawable.ic_warning)
            .setContentTitle(title)
            .setContentText(message)
            .setPriority(NotificationCompat.PRIORITY_HIGH)

        NotificationManagerCompat.from(requireContext()).notify(System.currentTimeMillis().toInt(), builder.build())
    }

    private fun createNotificationChannel() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val name = "Warning Channel"
            val descriptionText = "Channel for water quality warnings"
            val importance = NotificationManager.IMPORTANCE_HIGH
            val channel = NotificationChannel("warning_channel", name, importance).apply {
                description = descriptionText
            }
            val notificationManager: NotificationManager =
                requireContext().getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
            notificationManager.createNotificationChannel(channel)
        }
    }

    private fun setupWebViewToggle(cardView: View, webView: WebView, url: String) {
        cardView.setOnClickListener {
            if (webView.visibility == View.GONE) {
                webView.loadUrl(url)
                slideDown(webView)
            } else {
                slideUp(webView)
            }
        }
    }

    private fun slideDown(view: View) {
        view.visibility = View.VISIBLE
        val animate = TranslateAnimation(0f, 0f, -view.height.toFloat(), 0f)
        animate.duration = 500
        view.startAnimation(animate)
    }

    private fun slideUp(view: View) {
        val animate = TranslateAnimation(0f, 0f, 0f, -view.height.toFloat())
        animate.duration = 500
        view.startAnimation(animate)
        view.visibility = View.GONE
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
