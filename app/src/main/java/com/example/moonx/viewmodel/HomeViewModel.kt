package com.example.moonx.viewmodel

import android.annotation.SuppressLint
import android.app.DatePickerDialog
import android.content.Context
import androidx.core.content.ContentProviderCompat.requireContext
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.moonx.model.Day
import com.example.moonx.model.WeatherResponse
import com.example.moonx.repo.HomeRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Date
import java.util.Locale
import javax.inject.Inject

@HiltViewModel
class HomeViewModel  @Inject constructor(private val weatherRepository: HomeRepository) : ViewModel() {

    var observeData : MutableLiveData<WeatherResponse> = MutableLiveData()

    private val _zodiacSign = MutableLiveData<String>()
    val zodiacSign: LiveData<String> = _zodiacSign

    val selectedDate: MutableLiveData<Calendar> = MutableLiveData()
    val apiFormattedDate: MutableLiveData<String> = MutableLiveData()

    val horoscope: MutableLiveData<String> = MutableLiveData()

    private val _loadingStatus: MutableLiveData<Boolean> = MutableLiveData()
    val loadingStatus: LiveData<Boolean>
        get() = _loadingStatus


    fun generateHoroscope(category : String) {
        _loadingStatus.value = true
        val zodiacSignValue = zodiacSign.value
        val apiFormattedDateValue = apiFormattedDate.value

        if (!zodiacSignValue.isNullOrEmpty() && !apiFormattedDateValue.isNullOrEmpty()) {
            val prompt =
            "If I am a $zodiacSignValue, pretend you are a fortune teller, please generate my $category horoscope for $apiFormattedDateValue. Please try to limit it 120 words"

            viewModelScope.launch {

                val response = weatherRepository.getGptResponse(prompt, 120)
                if (response.isSuccessful) {
                    val choices = response.body()?.choices
                    if (!choices.isNullOrEmpty()) {
                        val horoscopeText = choices[0].text

                        horoscope.postValue(horoscopeText)
                        _loadingStatus.value = false
                    }
                } else {
                    horoscope.postValue("Horoscope generation failed.")
                    _loadingStatus.value = false

                }
            }
        }
    }





    fun showDatePickerDialog(context: Context,  onDateSelected: (Calendar) -> Unit) {
        val currentDate = Calendar.getInstance()
        val year = currentDate.get(Calendar.YEAR)
        val month = currentDate.get(Calendar.MONTH)
        val day = currentDate.get(Calendar.DAY_OF_MONTH)

        val datePickerDialog = DatePickerDialog(
            context,
            { _, selectedYear, selectedMonth, selectedDay ->
                val selectedCalendar = Calendar.getInstance()
                selectedCalendar.set(selectedYear, selectedMonth, selectedDay)
                selectedDate.value = selectedCalendar
                val apiFormatted = SimpleDateFormat("yyyy-M-d", Locale.getDefault()).format(selectedCalendar.time)
                apiFormattedDate.value = apiFormatted
            },
            year, month, day
        )

        val maxDate = Calendar.getInstance()
        maxDate.add(Calendar.DAY_OF_MONTH, 15)
        datePickerDialog.datePicker.maxDate = maxDate.timeInMillis

        datePickerDialog.show()
    }


    fun getData(city:String, date:String,elements : List<Day>){
        viewModelScope.launch {
            val data=weatherRepository.getData(city,date,elements)
            if(data.isSuccessful){
                observeData.postValue(data.body())
            } else {
                val errorBody = data.errorBody()?.string()
                println("HTTP Error Body: $errorBody")
            }
        }
    }


    @SuppressLint("SimpleDateFormat")
    fun calculateZodiacSign(birthDate: String) {
        val dateFormat = SimpleDateFormat("dd.MM.yyyy")
        val birthCalendar = Calendar.getInstance()
        birthCalendar.time = dateFormat.parse(birthDate) as Date

        val day = birthCalendar.get(Calendar.DAY_OF_MONTH)
        val month = birthCalendar.get(Calendar.MONTH) + 1

        val zodiacSignResult = when {
            (month == 3 && day >= 21) || (month == 4 && day <= 19) -> "Aries"
            (month == 4 && day >= 20) || (month == 5 && day <= 20) -> "Taurus"
            (month == 5 && day >= 21) || (month == 6 && day <= 20) -> "Gemini"
            (month == 6 && day >= 21) || (month == 7 && day <= 22) -> "Cancer"
            (month == 7 && day >= 23) || (month == 8 && day <= 22) -> "Leo"
            (month == 8 && day >= 23) || (month == 9 && day <= 22) -> "Virgo"
            (month == 9 && day >= 23) || (month == 10 && day <= 22) -> "Libra"
            (month == 10 && day >= 23) || (month == 11 && day <= 21) -> "Scorpio"
            (month == 11 && day >= 22) || (month == 12 && day <= 21) -> "Sagittarius"
            (month == 12 && day >= 22) || (month == 1 && day <= 19) -> "Capricorn"
            (month == 1 && day >= 20) || (month == 2 && day <= 18) -> "Aquarius"
            else -> "Pisces"
        }

        _zodiacSign.postValue(zodiacSignResult)
    }

}