package com.app.yuru.ui.testResult

import android.content.Intent
import android.graphics.Color
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Toast
import com.app.yuru.coreandroid.base.BaseFragmentBinding
import com.app.yuru.databinding.FragmentTestResultsBinding
import com.app.yuru.ui.transition.TransitionActivity
import com.app.yuru.utility.ValueFormatter
import com.github.mikephil.charting.data.Entry
import com.github.mikephil.charting.data.PieData
import com.github.mikephil.charting.data.PieDataSet
import com.github.mikephil.charting.data.PieEntry
import com.github.mikephil.charting.highlight.Highlight
import com.github.mikephil.charting.listener.OnChartValueSelectedListener
import com.github.mikephil.charting.utils.ColorTemplate
import dagger.hilt.android.AndroidEntryPoint
import java.util.*


@AndroidEntryPoint
class TestResultFragment : BaseFragmentBinding<FragmentTestResultsBinding>() {

    override val bindingInflater: (LayoutInflater, ViewGroup?, Boolean) -> FragmentTestResultsBinding
        get() = FragmentTestResultsBinding::inflate

    override fun setupView(binding: FragmentTestResultsBinding) {


        binding.btnProceed.setOnClickListener {
            startActivity(Intent(context, TransitionActivity::class.java))
        }

        binding.pieChart.setUsePercentValues(true)
        binding.pieChart.description.isEnabled = false
        binding.pieChart.setExtraOffsets(5f, 10f, 5f, 5f)

        binding.pieChart.dragDecelerationFrictionCoef = 0.95f

        binding.pieChart.setExtraOffsets(20f, 0f, 20f, 0f)

        binding.pieChart.isDrawHoleEnabled = false
        binding.pieChart.setDrawEntryLabels(false)

        binding.pieChart.setTransparentCircleColor(Color.WHITE)
        binding.pieChart.setTransparentCircleAlpha(110)

        binding.pieChart.holeRadius = 58f
        binding.pieChart.transparentCircleRadius = 61f

        binding.pieChart.setDrawCenterText(false)

        binding.pieChart.rotationAngle = 0f
        // enable rotation of the chart by touch
        // enable rotation of the chart by touch
        binding.pieChart.isRotationEnabled = false
        binding.pieChart.isHighlightPerTapEnabled = true

        binding.pieChart.legend.isEnabled = false
        setData(5, 10F)
    }

    private fun setData(count: Int, range: Float) {
        val entries = ArrayList<PieEntry>()

        // NOTE: The order of the entries when being added to the entries array determines their position around the center of
        // the chart.
        for (i in 0 until count) {
            val entry = PieEntry(10F * (i + 1), "")
            entry.x = i.toFloat()
            entries.add(entry)
        }
        val dataSet = PieDataSet(entries, "Election Results")
        dataSet.sliceSpace = 3f
        dataSet.selectionShift = 5f
        binding.pieChart.setOnChartValueSelectedListener(object : OnChartValueSelectedListener {
            override fun onValueSelected(e: Entry, h: Highlight) {
                var message = ""
                when (e.x.toInt()) {
                    0 -> message = "Openness - How open a person is to new ideas and experiences"
                    1 -> message =
                        "Conscientiousness - How goal-directed, persistent, and organized a person is"
                    2 -> message =
                        "Neuroticism - How sensitive a person is to stress and negative emotional triggers"
                    3 -> message =
                        "Agreeableness - How much a person puts others' interests and needs ahead of their own"
                    4 -> message =
                        "Extraversion - How much a person is energized by the outside world"
                }
                Toast.makeText(baseActivity, message, Toast.LENGTH_SHORT).show()
            }

            override fun onNothingSelected() {}
        })

        // add a lot of colors
        val colors = ArrayList<Int>()
        for (c in ColorTemplate.VORDIPLOM_COLORS) colors.add(c)
        for (c in ColorTemplate.JOYFUL_COLORS) colors.add(c)
        for (c in ColorTemplate.COLORFUL_COLORS) colors.add(c)
        for (c in ColorTemplate.LIBERTY_COLORS) colors.add(c)
        for (c in ColorTemplate.PASTEL_COLORS) colors.add(c)
        colors.add(ColorTemplate.getHoloBlue())
        dataSet.colors = colors
        //dataSet.setSelectionShift(0f);
        dataSet.valueLinePart1OffsetPercentage = 80f
        dataSet.valueLinePart1Length = 0.2f
        dataSet.valueLinePart2Length = 0.4f
        dataSet.yValuePosition = PieDataSet.ValuePosition.OUTSIDE_SLICE
        val data = PieData(dataSet)
        data.setValueFormatter(ValueFormatter())
        data.setValueTextSize(8f)
        data.setValueTextColor(Color.BLACK)
        binding.pieChart.data = data

        // undo all highlights
        binding.pieChart.highlightValues(null)
        binding.pieChart.invalidate()
    }
}