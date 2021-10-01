package com.app.yuru.ui.test

import org.json.JSONArray

interface TestQuestionsListener {
    fun onNextClicked(jsonArray: JSONArray)
}