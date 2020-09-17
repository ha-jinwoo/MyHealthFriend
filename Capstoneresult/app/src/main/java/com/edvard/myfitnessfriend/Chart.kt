package com.edvard.myfitnessfriend

import android.graphics.Color
import com.github.mikephil.charting.charts.BarLineChartBase
import com.github.mikephil.charting.charts.LineChart
import com.github.mikephil.charting.components.Legend
import com.github.mikephil.charting.components.XAxis
import com.github.mikephil.charting.components.YAxis
import com.github.mikephil.charting.data.Entry
import com.github.mikephil.charting.data.LineData
import com.github.mikephil.charting.data.LineDataSet
import com.github.mikephil.charting.formatter.ValueFormatter
import com.github.mikephil.charting.interfaces.datasets.ILineDataSet
import java.util.*


class Chart {
    companion object{
        fun drawChart(chart: LineChart, chartData: ArrayList<Entry>,xAxisName: String, yAxisName: String){
            val xAxis: XAxis = chart.xAxis
            xAxis.apply{
                position = XAxis.XAxisPosition.BOTTOM
                textSize = 10f
                setDrawGridLines(false)
                granularity = 1f // x축 데이터 표시 간격
                axisMaximum = 8f // x 축 데이터 최소 표시값
                isGranularityEnabled = true //x축 간격을 제한하는 세분화 기능
                valueFormatter = DayAxisValueFormatter(chart)
            }
            chart.apply{
                axisRight.isEnabled = false
                setPinchZoom(false)
                isDoubleTapToZoomEnabled = false
                description.text = xAxisName
                legend.apply{
                    textSize = 15f
                    verticalAlignment = Legend.LegendVerticalAlignment.TOP
                    horizontalAlignment = Legend.LegendHorizontalAlignment.CENTER
                    orientation = Legend.LegendOrientation.HORIZONTAL
                    setDrawInside(false)
                }
            }
            val yAxis = chart.axisLeft
            yAxis.apply{
                axisMinimum = 0f
            }
            var set= LineDataSet(chartData, yAxisName)
            set.apply{
                axisDependency = YAxis.AxisDependency.LEFT
                mode = LineDataSet.Mode.STEPPED
                setDrawFilled(true)
            }
            var dataSets = ArrayList<ILineDataSet>()
            dataSets.add(set)

            var data = LineData(dataSets)
            set.setColor(Color.BLACK)
            set.setCircleColor(Color.GREEN)

            chart.setData(data)
        }
        class DayAxisValueFormatter(private val chart: LineChart) : ValueFormatter() {
            override fun getFormattedValue(value: Float): String {
                var ret:String = ""
                when(value){
                    1f -> ret = "월"
                    2f -> ret = "화"
                    3f -> ret = "수"
                    4f -> ret = "목"
                    5f -> ret = "금"
                    6f -> ret = "토"
                    7f -> ret = "일"
                }
                return ret
            }

        }
    }
}