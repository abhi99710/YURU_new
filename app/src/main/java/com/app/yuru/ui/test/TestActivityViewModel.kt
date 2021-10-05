package com.app.yuru.ui.test

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class TestActivityViewModel : ViewModel() {
    val pageLiveData = MutableLiveData<String>()
}