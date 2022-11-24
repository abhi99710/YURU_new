package com.app.yuru.ui.test

import android.content.Intent
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import com.android.volley.*
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import com.app.yuru.databinding.FragmentTestQuestionsBinding
import com.app.yuru.ui.testResult.TestResultActivity
import com.app.yuru.utility.concatArray
import com.app.yuru.utility.showToast
import dagger.hilt.android.AndroidEntryPoint
import org.json.JSONArray
import org.json.JSONException
import org.json.JSONObject
import java.util.*


@AndroidEntryPoint
class NTestQuestionsFragment : TestQuestionsFragment() {
    lateinit var jsonData : JSONArray
    private var requestQueue: RequestQueue? = null
    override fun setupView(binding: FragmentTestQuestionsBinding) {
        super.setupView(binding)
        viewModel.pageLiveData.postValue("N")

        viewModel.getQuestions("5")

        viewModel.uiState().observe(this, {

            when (it) {
                is TestViewModel.QuestionState.Loading -> {
                    baseActivity.showToast("Loading...")
                }
                is TestViewModel.QuestionState.Error -> {
                    baseActivity.showToast(it.message)
                }
                is TestViewModel.QuestionState.Success -> {
                    val testQuestionsAdapter = TestQuestionsAdapter(
                        requireActivity(),
                        getTestQuestionsListener(),
                        it.questionResponse
                    )
                    binding.rvQuestions.layoutManager = LinearLayoutManager(context)
                    binding.rvQuestions.setHasFixedSize(true)
                    binding.rvQuestions.adapter = testQuestionsAdapter
                    submitPageTitle(it.questionResponse.result.data.title)
                }
            }


        })
    }

    override fun getTestQuestionsListener(): TestQuestionsListener {
        return object : TestQuestionsListener {

            val rnds = (0..100).random()

            override fun onNextClicked(jsonArray: JSONArray) {
                jsonData = jsonArray
                activityViewModel.questions.concatArray(jsonArray)
                viewModelSubmit.submitResponse(
                    "1",
                    activityViewModel.questions.toString()
                ) // userid needed

//                jsonParse()

                viewModelSubmit.uiState().observe(this@NTestQuestionsFragment, {

                    when (it) {
                        is SubmitResponseModel.SubmitResponseSealed.Loading -> {
                            baseActivity.showToast("Loading...")
                        }
                        is SubmitResponseModel.SubmitResponseSealed.Error -> {
//                            baseActivity.showToast(it.message)
                            startActivity(Intent(baseActivity, TestResultActivity::class.java))
//                            Toast.makeText(context, it.message, Toast.LENGTH_SHORT)

                        }
                        is SubmitResponseModel.SubmitResponseSealed.Success -> {
                            baseActivity.showToast(it.questionResponse.result.message)
                            Toast.makeText(context, it.questionResponse.result.message, Toast.LENGTH_SHORT)
                           val intent = Intent(baseActivity, TestResultActivity::class.java)
//                            intent.putExtra("user-token", rnds)
                            startActivity(intent)
                        }
                    }
                })
            }
        }
    }

    private fun jsonParse() {

//        modelAnswer.answer = jsonData
//        modelAnswer.user_id = "1"
//        jsonObject.put("",modelAnswer)

//        jsonObject.put("answer",  ""+jsonData)
        val url = "https://app.whyuru.com/api/web/submitrating"
        val request = object : StringRequest(Request.Method.POST, url,  { response ->
            try {

                val jsonObject1 = JSONObject(response)
                val result = jsonObject1.getJSONObject("result")
                var msg = result.getString("message")
                Toast.makeText(context, "" + msg, Toast.LENGTH_SHORT)
                startActivity(Intent(baseActivity, TestResultActivity::class.java))

            } catch (e: JSONException) {
                e.printStackTrace()
            }
        },
            {
                object : Response.ErrorListener
                {
                    override fun onErrorResponse(volleyError: VolleyError) {
//                    progressDialog.dismiss()
                        Toast.makeText(context, volleyError.message, Toast.LENGTH_LONG).show()
                    }
                }
            }
        )
        {
            @Throws(AuthFailureError::class)
            override fun getParams(): Map<String, String> {
                val params = HashMap<String, String>()
                params.put("user_id", "1")
                params.put("answer", ""+jsonData)
                return params
            }
        }


        requestQueue = Volley.newRequestQueue(context)
        requestQueue?.add(request)
    }
}


