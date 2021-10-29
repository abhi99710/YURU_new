package com.app.yuru.ui.login

import android.app.ProgressDialog
import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.ProgressBar
import android.widget.Toast
import androidx.navigation.fragment.findNavController
import com.android.volley.AuthFailureError
import com.android.volley.RequestQueue
import com.android.volley.Response
import com.android.volley.VolleyError
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import com.app.yuru.R
import com.app.yuru.coreandroid.base.BaseFragmentBinding
import com.app.yuru.databinding.FragmentLoginBinding

import com.app.yuru.ui.getStarted.GetStartedActivity
import com.app.yuru.ui.splash.Splash2
import com.app.yuru.ui.test.TestActivity
import com.app.yuru.ui.transition.AdapterMain
import com.app.yuru.ui.transition.TransitionActivity
import dagger.hilt.android.AndroidEntryPoint
import org.json.JSONException
import org.json.JSONObject

@AndroidEntryPoint
class LoginFragment : BaseFragmentBinding<FragmentLoginBinding>() {

    private var requestQueue: RequestQueue? = null
    private lateinit var progressDialog : ProgressDialog


    override val bindingInflater: (LayoutInflater, ViewGroup?, Boolean) -> FragmentLoginBinding
        get() = FragmentLoginBinding::inflate

    override fun setupView(binding: FragmentLoginBinding) {
        binding.tvSignUp.setOnClickListener {
            findNavController().navigate(R.id.action_login_to_signUp)

            progressDialog = ProgressDialog(context)
            progressDialog.setMessage("Loading...")
            progressDialog.setCancelable(false)
//            progressDialog.show()

        }
        binding.btnProceed.setOnClickListener{

            Toast.makeText(context, "Login", Toast.LENGTH_SHORT).show()
            startActivity(Intent(context, TransitionActivity::class.java))

            if(binding.edtEmail.text.toString().isNullOrEmpty()){
                Toast.makeText(context, "please enter your email", Toast.LENGTH_SHORT).show()

            }else if(binding.edtPassword.text.toString().isNullOrEmpty()){
                Toast.makeText(context, "please enter your password", Toast.LENGTH_SHORT).show()

            }else{
//                apiLogin()
            }



        }
    }

    private fun apiLogin() {
        val url  = "https://promask.com.co/yuru/api/login"

        val stringRequest = object : StringRequest(
            Method.POST, url,
            Response.Listener<String> { response ->
                try {
                    val obj = JSONObject(response)
//                    progressDialog.dismiss()
                    var jsonObject = obj.getJSONObject("result")
                    var jsonObject1 = jsonObject.getJSONObject("data")

                     if(jsonObject.getString("message").equals("You have successfully logged In")) {
                        var intent = Intent(context, GetStartedActivity::class.java)
                         intent.putExtra("nameLogin", jsonObject1.getString("full_name"))
                         startActivity(intent)
                         Toast.makeText(context, "Login", Toast.LENGTH_SHORT).show()

//                         val sharedPreferences: SharedPreferences = this.getSharedPreferences(sharedPrefFile, Context.MODE_PRIVATE)
//                         val editor: SharedPreferences.Editor =  sharedPreferences.edit()

//                         editor.putString("email",jsonObject.getString("email"))
//                         editor.apply()
//                         editor.commit()
//
//                         intent = Intent(applicationContext, Dashboard::class.java)
//                         startActivity(intent)
                     }
                    else{
                         Toast.makeText(context, "Login", Toast.LENGTH_SHORT).show()
                     }
                } catch (e: JSONException) {
                    e.printStackTrace()
                }
            },
            object : Response.ErrorListener {
                override fun onErrorResponse(volleyError: VolleyError) {
                    Toast.makeText(context, volleyError.message, Toast.LENGTH_LONG).show()
                }
            })
        {
            @Throws(AuthFailureError::class)
            override fun getParams(): Map<String, String> {
                val params = HashMap<String, String>()
                params.put("email_id", binding.edtEmail.text.toString())
                params.put("password", binding.edtPassword.text.toString())
                return params
            }
        }
        requestQueue = Volley.newRequestQueue(context)
        requestQueue?.add(stringRequest)
    }
}