package com.app.yuru.ui.testResult

import android.graphics.Color
import android.graphics.Typeface
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.SeekBar
import com.app.yuru.coreandroid.base.BaseFragmentBinding
import com.app.yuru.databinding.FragmentTestQuestionsBinding
import com.app.yuru.databinding.FragmentTestResultsBinding
import com.github.mikephil.charting.data.Entry
import com.github.mikephil.charting.highlight.Highlight
import com.github.mikephil.charting.listener.OnChartValueSelectedListener
import dagger.hilt.android.AndroidEntryPoint
import android.widget.TextView
import com.github.mikephil.charting.animation.Easing

import com.github.mikephil.charting.charts.PieChart
import com.github.mikephil.charting.components.Legend
import com.github.mikephil.charting.formatter.PercentFormatter

import com.github.mikephil.charting.data.PieData

import com.github.mikephil.charting.utils.ColorTemplate

import com.github.mikephil.charting.utils.MPPointF

import com.github.mikephil.charting.data.PieDataSet

import android.R
import android.content.Intent
import android.util.Log
import com.app.yuru.ui.transition.TransitionActivity

import com.github.mikephil.charting.data.PieEntry





@AndroidEntryPoint
class TestResultFragment : BaseFragmentBinding<FragmentTestResultsBinding>()/*,
    SeekBar.OnSeekBarChangeListener,
    OnChartValueSelectedListener*/ {

//    private lateinit var chart : PieChart
////    private lateinit var seekBar: SeekBar
//    private lateinit var seekBarX: SeekBar
//    private lateinit var seekBarY: SeekBar

    override val bindingInflater: (LayoutInflater, ViewGroup?, Boolean) -> FragmentTestResultsBinding
        get() = FragmentTestResultsBinding::inflate

    override fun setupView(binding: FragmentTestResultsBinding) {


        binding.btnProceed.setOnClickListener {
            startActivity(Intent(context, TransitionActivity::class.java))
        }
/*
        binding.pieChart.setUsePercentValues(true)
        binding.pieChart.description.isEnabled = false
        binding.pieChart.setExtraOffsets(5f, 10f, 5f, 5f)
        binding.pieChart.setDragDecelerationFrictionCoef(0.95f)
//        binding.pieChart.setCenterTextTypeface(tfLight)


        binding.pieChart.isDrawHoleEnabled = false
        binding.pieChart.setHoleColor(Color.WHITE)
        binding.pieChart.setTransparentCircleColor(Color.WHITE)
        binding.pieChart.setTransparentCircleAlpha(110)

        binding.pieChart.holeRadius = 58f
        binding.pieChart.transparentCircleRadius = 61f

        binding.pieChart.setDrawCenterText(true)
        binding.pieChart.rotationAngle = 0f

        binding.pieChart.isRotationEnabled = true
        binding.pieChart.isHighlightPerTapEnabled = true

        binding.pieChart.setOnChartValueSelectedListener(this)

        binding.pieChart.animateY(1400, Easing.EaseInOutQuad)

        var legend : Legend = binding.pieChart.legend
        legend.setVerticalAlignment(Legend.LegendVerticalAlignment.TOP)
        legend.setHorizontalAlignment(Legend.LegendHorizontalAlignment.RIGHT)
        legend.setOrientation(Legend.LegendOrientation.VERTICAL)
        legend.setDrawInside(false)
        legend.xEntrySpace = 7f
        legend.yEntrySpace = 0f
        legend.yOffset = 0f

        binding.pieChart.setEntryLabelColor(Color.WHITE)
        binding.pieChart.setEntryLabelTypeface(Typeface.DEFAULT)
        binding.pieChart.setEntryLabelTextSize(12f)*/


    }

    /*private fun setData(count: Int, range: Float) {
        val entries: ArrayList<PieEntry> = ArrayList()

        // NOTE: The order of the entries when being added to the entries array determines their position around the center of
        // the chart.
        for (i in 0 until count) {
            entries.add(
                PieEntry(
                    50f,
                    "pie chart ",
                    resources.getDrawable(R.drawable.ic_media_next)
                )
            )
        }
        val dataSet = PieDataSet(entries, "Answer Result")
        dataSet.setDrawIcons(false)
        dataSet.sliceSpace = 3f
        dataSet.iconsOffset = MPPointF(0f, 40f)
        dataSet.selectionShift = 5f

        // add a lot of colors
        val colors: ArrayList<Int> = ArrayList()
        for (c in ColorTemplate.VORDIPLOM_COLORS) colors.add(c)
        for (c in ColorTemplate.JOYFUL_COLORS) colors.add(c)
        for (c in ColorTemplate.COLORFUL_COLORS) colors.add(c)
        for (c in ColorTemplate.LIBERTY_COLORS) colors.add(c)
        for (c in ColorTemplate.PASTEL_COLORS) colors.add(c)
        colors.add(ColorTemplate.getHoloBlue())
        dataSet.colors = colors
        dataSet.setSelectionShift(0f);

        val data = PieData(dataSet)
        data.setValueFormatter(PercentFormatter())
        data.setValueTextSize(11f)
        data.setValueTextColor(Color.WHITE)
        data.setValueTypeface(Typeface.DEFAULT)
        chart.data = data

        // undo all highlights
        chart.highlightValues(null)
        chart.invalidate()
    }

    override fun onProgressChanged(seekBar: SeekBar?, progress: Int, fromUser: Boolean) {
        setData(10, 10f)
    }

    override fun onStartTrackingTouch(seekBar: SeekBar?) {
        TODO("Not yet implemented")
    }

    override fun onStopTrackingTouch(seekBar: SeekBar?) {
        TODO("Not yet implemented")
    }

    override fun onValueSelected(e: Entry?, h: Highlight?) {
        if (e == null)
            return;
        if (h != null) {
            Log.i("VAL SELECTED",
                "Value: " + e.getY() + ", index: " + h.getX()
                        + ", DataSet index: " + h.getDataSetIndex())
        };
    }

    override fun onNothingSelected() {
        Log.i("PieChart", "nothing selected")
    }*/
}