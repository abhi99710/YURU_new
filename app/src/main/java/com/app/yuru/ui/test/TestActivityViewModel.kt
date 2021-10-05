package com.app.yuru.ui.test

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import org.json.JSONArray

class TestActivityViewModel : ViewModel() {
    val pageLiveData = MutableLiveData<String>()
    lateinit var questions: JSONArray
}