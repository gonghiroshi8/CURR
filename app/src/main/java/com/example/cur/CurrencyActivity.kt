package com.example.cur

import CurrencyResponse
import ExchangeRateApi
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.EditText
import android.widget.Spinner
import android.widget.TextView
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class CurrencyActivity : AppCompatActivity() {
    private lateinit var amountEditText: EditText
    private lateinit var fromCurrencySpinner: Spinner
    private lateinit var toCurrencySpinner: Spinner
    private lateinit var swapButton: Button
    private lateinit var convertButton: Button
    private lateinit var resultTextView: TextView

    private val api: ExchangeRateApi by lazy {
        Retrofit.Builder()
            .baseUrl("https://api.exchangerate-api.com/v4/latest/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(ExchangeRateApi::class.java)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_currency)

        amountEditText = findViewById(R.id.amountEditText)
        fromCurrencySpinner = findViewById(R.id.fromCurrencySpinner)
        toCurrencySpinner = findViewById(R.id.toCurrencySpinner)
        swapButton = findViewById(R.id.swapButton)
        convertButton = findViewById(R.id.convertButton)
        resultTextView = findViewById(R.id.resultTextView)

        // สร้างรายการสกุลเงิน
        val currencies = listOf("USD", "EUR", "GBP", "JPY", "THB")

        // ตั้งค่า Adapter ให้กับ Spinner
        val adapter = ArrayAdapter(this, android.R.layout.simple_spinner_item, currencies)
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        fromCurrencySpinner.adapter = adapter
        toCurrencySpinner.adapter = adapter

        swapButton.setOnClickListener {
            swapCurrencies()
        }

        convertButton.setOnClickListener {
            convertCurrency()
        }
    }

    private fun swapCurrencies() {
        val fromPosition = fromCurrencySpinner.selectedItemPosition
        val toPosition = toCurrencySpinner.selectedItemPosition

        fromCurrencySpinner.setSelection(toPosition)
        toCurrencySpinner.setSelection(fromPosition)
    }

    private fun convertCurrency() {
        val amountString = amountEditText.text.toString()

        // ตรวจสอบว่าค่าที่ได้รับไม่ว่างเปล่า
        if (amountString.isEmpty()) {
            resultTextView.text = "Please enter an amount"
            return
        }

        try {
            val amount = amountString.toDouble()
            val fromCurrency = fromCurrencySpinner.selectedItem?.toString()
            val toCurrency = toCurrencySpinner.selectedItem?.toString()

            // ตรวจสอบว่าค่าใน Spinner ไม่เป็น null
            if (fromCurrency == null || toCurrency == null) {
                resultTextView.text = "Please select both currencies"
                return
            }

            api.getRates(fromCurrency).enqueue(object : Callback<CurrencyResponse> {
                override fun onResponse(call: Call<CurrencyResponse>, response: Response<CurrencyResponse>) {
                    if (response.isSuccessful) {
                        val rates = response.body()?.rates
                        val rate = rates?.get(toCurrency)
                        val result = rate?.let { amount * it }
                        resultTextView.text = result.toString()
                    } else {
                        resultTextView.text = "Error: ${response.message()}"
                    }
                }

                override fun onFailure(call: Call<CurrencyResponse>, t: Throwable) {
                    resultTextView.text = "Failure: ${t.message}"
                }
            })
        } catch (e: NumberFormatException) {
            resultTextView.text = "Invalid amount"
        }
    }
}
