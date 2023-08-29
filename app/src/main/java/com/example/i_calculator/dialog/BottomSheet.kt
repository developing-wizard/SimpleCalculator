package com.example.i_calculator.dialog


import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.i_calculator.databinding.BottomSheetBinding
import com.google.android.material.bottomsheet.BottomSheetDialogFragment

class BottomSheet: BottomSheetDialogFragment() {

    private var _binding: BottomSheetBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = BottomSheetBinding.inflate(inflater, container, false)
        return binding.root

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.rating.setOnClickListener()
        {
            startActivity(Intent(Intent.ACTION_VIEW, Uri.parse("market://details?id=com.example.i_calculator")))
            dismiss()
        }
        binding.privacypolicy.setOnClickListener()
        {
            openUrlInBrowser("https://ironcladapp.com/journal/contracts/best-privacy-policy-examples-for-gdpr/")

        }
        binding.share.setOnClickListener()
        {
            share()
        }

    }
private fun share()
{
    try {
        val shareIntent = Intent(Intent.ACTION_SEND)
        shareIntent.type = "text/plain"
        shareIntent.putExtra(Intent.EXTRA_SUBJECT, "My First Calculator")
        var shareMessage = "\nLet me recommend you this application\n\n"
        shareMessage =
            """
                    ${shareMessage + "https://play.google.com/store/apps/details?id=com.example.i_calculator.dialog"}
                    
                    
                    """.trimIndent()
        shareIntent.putExtra(Intent.EXTRA_TEXT, shareMessage)
        startActivity(Intent.createChooser(shareIntent, "choose one"))
    } catch (e: Exception) {
        //e.toString();
    }
}
    private fun openUrlInBrowser(url: String) {
        val intent = Intent(Intent.ACTION_VIEW, Uri.parse(url))
            startActivity(intent)

    }
}