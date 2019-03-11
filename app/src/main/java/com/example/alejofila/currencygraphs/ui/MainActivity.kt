package com.example.alejofila.currencygraphs.ui

import android.graphics.Color
import android.graphics.DashPathEffect
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.alejofila.currencygraphs.R
import com.example.alejofila.currencygraphs.common.model.CurrencyExchangeUiModel
import com.example.alejofila.currencygraphs.presenter.CurrencyHistoryPresenter
import com.example.alejofila.currencygraphs.presenter.CurrencyHistoryView
import com.github.mikephil.charting.components.Legend
import com.github.mikephil.charting.components.YAxis
import com.github.mikephil.charting.data.Entry
import com.github.mikephil.charting.data.LineData
import com.github.mikephil.charting.data.LineDataSet
import com.github.mikephil.charting.interfaces.datasets.ILineDataSet
import kotlinx.android.synthetic.main.activity_main.*
import org.koin.android.ext.android.inject
import org.threeten.bp.LocalDate
import kotlin.collections.ArrayList

class MainActivity : AppCompatActivity(),
    CurrencyHistoryView {


    private val presenter: CurrencyHistoryPresenter by inject()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        configureGraph()
        presenter.view = this
        btnChangeDays.setOnClickListener {
            presenter.loadCurrencyHistory(edtDays.text.toString(), LocalDate.now())
        }
        presenter.loadCurrencyHistory("300", LocalDate.now())

    }

    private fun configureGraph() {

        chart.setBackgroundColor(Color.WHITE)

        chart.description.isEnabled = false

        chart.setTouchEnabled(true)

        // set listeners
        chart.setDrawGridBackground(false)
        val xAxis = chart.xAxis

        xAxis.enableGridDashedLine(10f, 10f, 0f)


        val yAxis: YAxis = chart.axisLeft


        // disable dual axis (only use LEFT axis)
        chart.axisRight.isEnabled = false
        chart.zoomToCenter(14.0f, 14.0f)
        chart.moveViewTo(0f,-240f,yAxis.axisDependency)

        // horizontal grid lines
        yAxis.enableGridDashedLine(10f, 10f, 0f)

        // axis range
        yAxis.axisMaximum = 5.0f
        yAxis.axisMinimum = 0.0f


        yAxis.setDrawLimitLinesBehindData(true)
        xAxis.setDrawLimitLinesBehindData(true)


        chart.animateX(1500)

        val l = chart.legend

        // draw legend entries as lines
        l.form = Legend.LegendForm.SQUARE
    }

    private fun setData(entries: List<Entry>, label: String) {


        val set1: LineDataSet

        if (chart.data != null && chart.data.dataSetCount > 0) {
            set1 = chart.data.getDataSetByIndex(0) as LineDataSet
            set1.values = entries
            set1.notifyDataSetChanged()
            chart.data.notifyDataChanged()
            chart.notifyDataSetChanged()
            hideLoader()
        } else {

            set1 = LineDataSet(entries, label)
            set1.setDrawIcons(false)
            // draw dashed line
            set1.enableDashedLine(10f, 5f, 0f)
            // black lines and points
            set1.color = Color.RED
            set1.setCircleColor(Color.RED)
            // line thickness and point size
            set1.lineWidth = 1f
            set1.circleRadius = 3f
            // draw points as solid circles
            set1.setDrawCircleHole(false)
            // customize legend entry
            set1.formLineWidth = 1f
            set1.formLineDashEffect = DashPathEffect(floatArrayOf(10f, 5f), 0f)
            set1.formSize = 15f

            // text size of values
            set1.valueTextSize = 9f

            // draw selection line as dashed
            set1.enableDashedHighlightLine(10f, 5f, 0f)
            val dataSets = ArrayList<ILineDataSet>()
            dataSets.add(set1) // add the data sets

            val data = LineData(dataSets)

            // set data
            chart.data = data
            hideLoader()
        }
    }


    override fun showHistoryGraph(currencyHistory: CurrencyExchangeUiModel) {
        setData(currencyHistory.currencyInfoDetils.listOfExchange, currencyHistory.currencyInfoDetils.currency)
    }


    override fun showLoader() {
        progressBar.visibility = View.VISIBLE
    }

    override fun hideLoader() {
        progressBar.visibility = View.GONE
    }

    override fun showEmptyDaysError() {
        Toast.makeText(this, "Insert a number between 1 and 365 days", Toast.LENGTH_SHORT)
            .show()
    }


    override fun showError() {
    }

    override fun setUpMinMaxHorinzotal(min: Float, max: Float) {
        chart.xAxis.mAxisMaximum = max
        chart.xAxis.axisMinimum = min
    }

}


