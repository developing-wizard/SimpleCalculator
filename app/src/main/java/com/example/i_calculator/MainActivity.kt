package com.example.i_calculator


import android.annotation.SuppressLint
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import com.example.i_calculator.databinding.ActivityMainBinding
import java.math.BigDecimal
import java.text.DecimalFormat
import javax.script.ScriptEngine
import javax.script.ScriptEngineManager

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private var scriptEngine: ScriptEngine? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.changee.setOnClickListener {
            val intent = Intent(this, ScientificCalculator::class.java)
            // start your next activity
            startActivity(intent)
            finish()
        }
        binding.changeee.setOnClickListener {
            val intent = Intent(this, ScientificCalculator::class.java)
            // start your next activity
            startActivity(intent)
            finish()
        }
        scriptEngine = ScriptEngineManager().getEngineByName("rhino")

    }


    fun onClickMethod(view: View) {
        when (view.id) {

            // Numbers
            R.id.dot-> {
                if (!binding.calulation.text.toString().contains(".")) {
                    addTextCalculate(".")
                }
            }

            R.id.dot ,R.id.dott-> {
                if (!binding.calulation.text.toString().contains(".")) {
                    addTextCalculate(".")
                }
            }
            R.id.btn_back ->
            {
                val intent = Intent(this, HomeScreen::class.java)
                // start your next activity
                startActivity(intent)
                finish()
            }
            R.id.equals,R.id.equalss -> equalClicked()

            R.id.zero ,R.id.zero0-> addTextCalculate("0")
            R.id.one,R.id.onee -> addTextCalculate("1")
            R.id.two,R.id.twoo -> addTextCalculate("2")
            R.id.three,R.id.threee -> addTextCalculate("3")
            R.id.four ,R.id.fourr-> addTextCalculate("4")
            R.id.five ,R.id.fivee-> addTextCalculate("5")
            R.id.six,R.id.sixx -> addTextCalculate("6")
            R.id.seven,R.id.sevenn -> addTextCalculate("7")
            R.id.eight,R.id.eightt -> addTextCalculate("8")
            R.id.nine,R.id.ninee -> addTextCalculate("9")


            // Operations

            R.id.erase,R.id.erasee -> clearTextAll()
            R.id.btnBackClear -> cleatTextLast()

            R.id.equals,R.id.equalss -> equalClicked()
            R.id.percenatge,R.id.percenatgeee -> {
                if (binding.calulation.text.toString().isNotEmpty())
                    calculate(binding.calulation.text.toString() + "%")
            }
            R.id.btnMultiplicativeInverse,R.id.btnMultiplicativeInversee -> {
                addTextCalculate("^(-1)")
            }
            R.id.divide,R.id.dividee -> {
                addOperands("÷")
            }

            R.id.multiply,R.id.multiplyy -> {
                addOperands("x")
            }

            R.id.minus,R.id.minuss -> {
                addOperands("-")
            }

            R.id.add,R.id.addd -> {
                addOperands("+")
            }

        }
    }

    @SuppressLint("SetTextI18n")
    private fun addTextCalculate(mData: String) {
        val mText = binding.calulation.text.toString()
        binding.calulation.text = "$mText$mData"

    }

    private fun clearTextAll() {
        binding.calulation.text = ""
        binding.calulation.text = ""
    }

    private fun cleatTextLast() {
        val mText = binding.calulation.text.toString()
        if (mText.isNotEmpty()) {
            binding.calulation.text = mText.dropLast(1)
        }
    }


    private fun equalClicked() {
        if (binding.calulation.text.toString().isNotEmpty()) {
            calculate(binding.calulation.text.toString())
        }

    }
    @SuppressLint("SetTextI18n")
    private fun calculate(input: String) {

        val indexesList: List<Int>
        var tempData = ""
        val originalList = "1*($input)"

        var temp: String
        var calulation: String
        try {
            temp = originalList

            indexesList = originalList.indexesOf("^", false)


            for (index in indexesList.indices) {
                for (i in indexesList[index] - 1 downTo 0) {
                    if (!isDigit(originalList[i])) {
                        Log.i("information", "Start index: $i")
                        Log.i("information", "End index: ${indexesList[index]}")
                        Log.i("information", originalList.substring(i + 1, indexesList[index] + 2))

                        tempData = originalList.substring(i + 1, indexesList[index])
                        temp = temp.replace(
                            "${tempData}\\^\\(".toRegex(),
                            "Math.pow(${originalList.substring(i + 1, indexesList[index])},"
                        )

                        break
                    }
                }

            }

            calulation = scriptEngine?.eval(
                temp.replace("%".toRegex(), "/100")
                    .replace("x".toRegex(), "*")
                    .replace("÷".toRegex(), "/")
            ).toString()


            Log.i("information", "calulation: $calulation")
            val decimal = BigDecimal(calulation)
            calulation = decimal.setScale(8, BigDecimal.ROUND_HALF_UP).toPlainString()
        } catch (e: Exception) {
            e.printStackTrace()
            binding.calulation.text = "Wrong Format"
            Log.i("information", e.toString())
            return
        }
        if (calulation == "Infinity") {
            binding.calulation.text = "Can't divide by zero"

        } else if (calulation.contains(".")) {
            calulation = calulation.replace("\\.?0*$".toRegex(), "")
            if (calulation.length > 18) {
                calulation = handlingLengthycalulation(calulation)
                binding.calulation.text = "$calulation"
            } else {
                binding.calulation.text = "$calulation"
            }

        }
    }
    private fun handlingLengthycalulation(number: String): String {
        val d = BigDecimal(number)
        val df = DecimalFormat("0.###########E0")
        return df.format(d)
    }

    @SuppressLint("SetTextI18n")
    private fun addOperands(operands: String) {
        val mText = binding.calulation.text.toString()
        if (mText.isEmpty()) {
            addTextCalculate("0${operands}")
        } else {
            if (isOperands(mText.last().toString())) {
                binding.calulation.text = "${mText.dropLast(1)}$operands"
            } else {
                addTextCalculate(operands)
            }

        }


    }
    private fun isOperands(operands: String): Boolean {
        return operands == "+" || operands == "-" || operands == "x" || operands == "÷"
    }
    private fun String?.indexesOf(substr: String, ignoreCase: Boolean = false): List<Int> {
        return this?.let {
            val indexes = mutableListOf<Int>()
            var startIndex = 0
            while (startIndex in 0 until length) {
                val index = this.indexOf(substr, startIndex, ignoreCase)
                startIndex = if (index != -1) {
                    indexes.add(index)
                    index + substr.length
                } else {
                    index
                }
            }
            return indexes
        } ?: emptyList()
    }
    private fun isDigit(ch: Char): Boolean {
        return Character.isDigit(ch)
    }

}
