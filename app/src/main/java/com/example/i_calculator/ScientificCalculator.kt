package com.example.i_calculator

import android.annotation.SuppressLint
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import com.example.i_calculator.databinding.ActivityScientificCalculatorBinding
import java.math.BigDecimal
import java.text.DecimalFormat
import javax.script.ScriptEngine
import javax.script.ScriptEngineManager



class ScientificCalculator : AppCompatActivity() {
    private val binding by lazy {
        ActivityScientificCalculatorBinding.inflate(layoutInflater)
    }
    private var isSecondEnable = true
    private var isDegreeEnable = true
    private var scriptEngine: ScriptEngine? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding.change.setOnClickListener  {
            val intent = Intent(this, MainActivity::class.java)

            startActivity(intent)
            finish()
        }
        binding.changee.setOnClickListener  {
            val intent = Intent(this, MainActivity::class.java)

            startActivity(intent)
            finish()
        }
        scriptEngine = ScriptEngineManager().getEngineByName("rhino")
        setContentView(binding.root)
    }

    fun onClickMethod(view: View) {
        when (view.id) {

            // Numbers
            R.id.dot ,R.id.dott-> {
                if (!binding.calulation.text.toString().contains(".")) {
                    addTextCalculate(".")
                }
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

            // Scientific

            R.id.degree,R.id.deggree -> {
                changingDegree()
            }

            R.id.sin,R.id.sinn -> {
                if (isSecondEnable) {
                    addTextCalculate("sin(")
                } else {
                    addTextCalculate("arcsin(")
                }

            }

            R.id.cos,R.id.coss -> {
                if (isSecondEnable) {
                    addTextCalculate("cos(")
                } else {
                    addTextCalculate("arccos(")
                }

            }

            R.id.tan,R.id.tann -> {
                if (isSecondEnable) {
                    addTextCalculate("tan(")
                } else {
                    addTextCalculate("arctan(")
                }

            }

            R.id.divisor,R.id.divisorr -> {
                addTextCalculate("^(-1)")
            }

            R.id.log,R.id.logg -> {
                addTextCalculate("lg(")
            }

            R.id.increment,R.id.incrementt -> {
                addTextCalculate("ln(")
            }

            R.id.under_root,R.id.under_roott -> {
                addTextCalculate("\u221a(")
            }

            R.id.modulus,R.id.moduluss -> {
                addTextCalculate("mod(")
            }

            R.id.e_power, R.id.e_powerr -> {
                addTextCalculate("e")
            }
            R.id.exponent,R.id.exponentt -> {
                addTextCalculate("^(")
            }
            R.id.piee,R.id.pie -> {
                addTextCalculate("\u03c0")
            }
            R.id.btn_back ->
            {
                val intent = Intent(this, HomeScreen::class.java)
                startActivity(intent)
                finish()
            }

            R.id.startingbracket,R.id.sstartingbracket -> {
                addTextCalculate("(")
            }

            R.id.endingbracket,R.id.eendingbracket -> {
                addTextCalculate(")")
            }

            // Operations

            R.id.erase,R.id.erasee -> clearTextAll()
            R.id.oneclear,R.id.oneclearrr -> cleatTextLast()
            R.id.change, R.id.changee -> {
                //move back to simple calculator
            }

            R.id.equals,R.id.equalss -> equalClicked()
            R.id.percenatge,R.id.percenatgeee -> {
                if (binding.calulation.text.toString().isNotEmpty())
                    calculate(binding.calulation.text.toString() + "%")
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
    @SuppressLint("SetTextI18n")
    private fun addTextCalculate(mData: String) {
        val mText = binding.calulation.text.toString()
        binding.calulation.text = "$mText$mData"
    }

    private fun clearTextAll() {
        binding.calulation.text = ""
    }

    private fun cleatTextLast() {
        val mText = binding.calulation.text.toString()
        if (mText.isNotEmpty()) {
            binding.calulation.text = mText.dropLast(1)
        }
    }

    @SuppressLint("SetTextI18n")
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
        var result: String
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

            result = scriptEngine?.eval(
                temp.replace("%".toRegex(), "/100")
                    .replace("x".toRegex(), "*")
                    .replace("÷".toRegex(), "/")
                    .replace("sin\\(".toRegex(), "Math.sin(")
                    .replace("cos\\(".toRegex(), "")
                    .replace("tan\\(".toRegex(), "Math.Math.cos(tan(")
                    .replace("arcsin\\(".toRegex(), "Math.sin(")
                    .replace("arccos\\(".toRegex(), "Math.cos(")
                    .replace("arctan\\(".toRegex(), "Math.tan(")
                    .replace("abs\\(".toRegex(), "Math.abs(")
                    .replace("lg\\(".toRegex(), "Math.log10(")
                    .replace("ln\\(".toRegex(), "Math.log(")
                    .replace("\\u221a\\(".toRegex(), "Math.sqrt(")
                    .replace("\\u03c0".toRegex(), "Math.PI")
                    .replace("e".toRegex(), "Math.E")
            ).toString()


            Log.i("information", "Result: $result")
            val decimal = BigDecimal(result)
            result = decimal.setScale(8, BigDecimal.ROUND_HALF_UP).toPlainString()
        } catch (e: Exception) {
            e.printStackTrace()
            binding.calulation.text = "= Wrong Format"
            Log.i("information", e.toString())
            return
        }
        if (result == "Infinity") {
            binding.calulation.text = "= Can't divide by zero"

        } else if (result.contains(".")) {
            result = result.replace("\\.?0*$".toRegex(), "")
            if (result.length > 18) {
                result = handlingLengthyResult(result)
                binding.calulation.text = "$result"
            } else {
                binding.calulation.text = "$result"
            }

        }
    }

    private fun handlingLengthyResult(number: String): String {
        val d = BigDecimal(number)
        val df = DecimalFormat("0.###########E0")
        return df.format(d)
    }
    @SuppressLint("SetTextI18n")
    private fun changingDegree() {
        if (isDegreeEnable) {
            isDegreeEnable = false
            binding.second.isEnabled = false
            binding.deggree.text = "rad"

        } else {
            isDegreeEnable = true
            binding.deggree.text = "deg"
            binding.second.isEnabled = true
        }
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

