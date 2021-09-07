package com.app.yuru.ui.test

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.app.yuru.R
import com.app.yuru.databinding.ItemTestNextStepBinding
import com.app.yuru.databinding.ItemTestQuestionsBinding

class TestQuestionsAdapter(context: Context, private val listener: TestQuestionsListener? = null) :
    RecyclerView.Adapter<RecyclerView.ViewHolder>() {
    private val inflater: LayoutInflater = LayoutInflater.from(context)
    private val rateMap = mutableMapOf<Int, Int>()

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
                holder.setData("Question ${position + 1}", rateMap[position] ?: -1)
            }
        }
    }

    override fun getItemCount(): Int {
        return 11
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
        }
    }

    inner class QuestionNextViewHolder(binding: ItemTestNextStepBinding) :
        RecyclerView.ViewHolder(binding.root), View.OnClickListener {
        init {
            binding.btnNext.setOnClickListener(this)
        }

        override fun onClick(v: View?) {
            listener?.onNextClicked()
        }
    }
}