package com.app.yuru.ui.test

import android.content.Context
import android.content.SharedPreferences
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.app.yuru.R
import com.app.yuru.coreandroid.BaseActivity
import com.app.yuru.databinding.ItemTestNextStepBinding
import com.app.yuru.databinding.ItemTestQuestionsBinding
import com.app.yuru.domain.entity.Json4Kotlin_Base
import com.app.yuru.utility.showToast
import org.json.JSONArray
import org.json.JSONObject
import java.io.Serializable
import kotlin.properties.Delegates

class TestQuestionsAdapter(context: Context, private val listener: TestQuestionsListener? = null, private val response : Json4Kotlin_Base, fragName : String) :
    RecyclerView.Adapter<RecyclerView.ViewHolder>() {
    private val inflater: LayoutInflater = LayoutInflater.from(context)
    private val rateMap = mutableMapOf<Int, Int>()

    private val qno = mutableMapOf<Int, Int>()

    private var jsonResponse = JSONObject()
   private var jsonArray  = JSONArray()
   var fragmentName : String = fragName
   val context : Context = context
   var x : Int = 0

    companion object {
        const val TEST_QUESTION = 1
        const val TEST_NEXT_STEP = 2


    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return when (viewType) {
            TEST_QUESTION -> {

                QuestionViewHolder(ItemTestQuestionsBinding.inflate(inflater, parent, false))
            }
            else -> {
                QuestionNextViewHolder(
                    ItemTestNextStepBinding.inflate(
                        inflater,
                        parent,
                        false
                    )
                )
            }
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when (holder) {
            is QuestionViewHolder -> {
                holder.setData(response.result.data.questions.get(position).question, rateMap[position] ?: 0)
                qno.put(position, response.result.data.questions.get(position).question_id)





                jsonArray.put(jsonResponse)
//                x = position
            }
        }
    }

    override fun getItemCount(): Int {
       return response.result.data.questions.size
    }

    override fun getItemViewType(position: Int): Int {
        return when (position < itemCount - 1) {
            true -> {
                TEST_QUESTION
            }
            else -> {
                TEST_NEXT_STEP
            }
        }
    }

    inner class QuestionViewHolder(val binding: ItemTestQuestionsBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun setData(question: String, rate: Int) {


            binding.tvQuestion.text = question





//             val sharedPrefFile = "kotlinsharedpreference"
//            val sharedPreferences: SharedPreferences = itemView.context.getSharedPreferences(sharedPrefFile, Context.MODE_PRIVATE)
//            val editor:SharedPreferences.Editor =  sharedPreferences.edit()
//
//            editor.putString("jsonArray"+fragmentName, ""+jsonArray)
//            editor.apply()
//            editor.commit()

            binding.rgQuestion.setOnCheckedChangeListener(null)
            when (rate) {
                1 -> {
                    binding.rbTotallyDisagree.isChecked = true
                }
                2 -> {
                    binding.rbDisagree.isChecked = true
                }
                3 -> {
                    binding.rbNeutral.isChecked = true
                }
                4 -> {
                    binding.rbAgree.isChecked = true
                }
                5 -> {
                    binding.rbTotallyAgree.isChecked = true
                }
                else -> {
                    binding.rgQuestion.clearCheck()
                }
            }
            binding.rgQuestion.setOnCheckedChangeListener { _, checkedId ->
                when (checkedId) {
                    R.id.rbTotallyDisagree -> {
                        rateMap[adapterPosition] = 1


                    }
                    R.id.rbDisagree -> {
                        rateMap[adapterPosition] = 2


                    }
                    R.id.rbNeutral -> {
                        rateMap[adapterPosition] = 3


                    }
                    R.id.rbAgree -> {
                        rateMap[adapterPosition] = 4


                    }
                    R.id.rbTotallyAgree -> {
                        rateMap[adapterPosition] = 5


                    }
                }
            }


//            jsonResponse.put("question_id",qno.get(x))
//            jsonResponse.put("rating", rateMap.get(x))
//            jsonArray.put(jsonResponse)


        }
    }

    inner class QuestionNextViewHolder(binding: ItemTestNextStepBinding) :
        RecyclerView.ViewHolder(binding.root), View.OnClickListener {
        init {

                for(i in 0..8){
                    jsonResponse.put("question_id",qno.get(i))
                    jsonResponse.put("rating", rateMap.get(i))
                    jsonArray.put(jsonResponse)
                }
                binding.btnNext.setOnClickListener(this)


        }

        override fun onClick(v: View?) {
            if(jsonArray.length() == response.result.data.questions.size){
            listener?.onNextClicked(jsonArray)
            }else{
                Toast.makeText(context, "Please select your answer for each question", Toast.LENGTH_SHORT).show()
            }
        }
    }
}