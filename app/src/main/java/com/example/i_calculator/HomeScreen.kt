package com.example.i_calculator


import android.app.Dialog
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.View
import android.view.Window
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import com.example.i_calculator.databinding.ActivityHomeScreenBinding
import com.example.i_calculator.dialog.BottomSheet
import kotlin.system.exitProcess


class HomeScreen : AppCompatActivity() {
     private val binding by lazy {
         ActivityHomeScreenBinding.inflate(layoutInflater)
     }
    override fun onCreate(savedInstanceState: Bundle?) {
        binding.scientificCalculations2.setBackgroundResource(R.drawable.gradient)
        binding.simpleCalculation2.setBackgroundResource(R.drawable.gradient)
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
    }

    override fun onBackPressed() {
        showDialog(this)
    }
    private fun showDialog(context: Context) {
        val dialog = Dialog(context)
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
        dialog.setCancelable(true)
        dialog.setContentView(R.layout.exit_alert)
        val yesBtn = dialog.findViewById(R.id.btn_ok) as Button
        val noBtn = dialog.findViewById(R.id.btn_cancel) as Button
        yesBtn.setOnClickListener {
            dialog.dismiss()
            exitProcess(0)
        }
        noBtn.setOnClickListener {
            dialog.dismiss()
        }

        dialog.show()
    }


    fun onClickMethod(view: View) {
        when (view.id) {

           R.id.scientificCalculations , R.id.scientificCalculations2,R.id.scientificCalculations3 ->
           {
               val intent = Intent(this, ScientificCalculator::class.java)
               startActivity(intent)

           }
             R.id.simpleCalculation2,R.id.simpleCalculation3 ->
            {
                val intent = Intent(this, MainActivity::class.java)
                startActivity(intent)

            }
            R.id.side_menu ->
            {
                val bottomSheetFragment = BottomSheet()
                bottomSheetFragment.show(supportFragmentManager, bottomSheetFragment.tag)
            }
        }
    }
}