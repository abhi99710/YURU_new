package com.app.yuru.ui.test

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import javax.inject.Inject

class TestViewModel @Inject constructor() : ViewModel() {
    val pageLiveData = MutableLiveData<String>()
}