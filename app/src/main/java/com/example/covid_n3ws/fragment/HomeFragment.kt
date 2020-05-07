package com.example.covid_n3ws.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.anychart.AnyChart
import com.anychart.chart.common.dataentry.DataEntry
import com.anychart.chart.common.dataentry.ValueDataEntry
import com.anychart.charts.Cartesian
import com.anychart.core.cartesian.series.Area
import com.anychart.data.Set
import com.anychart.enums.MarkerType
import com.anychart.enums.ScaleStackMode
import com.anychart.graphics.vector.Stroke
import com.example.covid_n3ws.R
import kotlinx.android.synthetic.main.fragment_home.*

class HomeFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View? = inflater.inflate(R.layout.fragment_home, container, false)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        retainInstance = true

        // Area Chart
        val areaChart: Cartesian = AnyChart.area()

        areaChart.animation(true)
        areaChart.title("REPORTED CASES (WORLD)")

        val crosshair = areaChart.crosshair()
        crosshair.enabled(true)
        crosshair.yStroke(
            null as Stroke?,
            null,
            null,
            null as String?,
            null as String?
        )
            .xStroke("#fff", 1.0, null, null as String?, null as String?)
            .zIndex(39.0)

        areaChart.title().padding(0.0, 0.0, 20.0, 0.0)
        areaChart.yScale().stackMode(ScaleStackMode.VALUE)

        val areaEntry: MutableList<DataEntry> = arrayListOf()

        areaEntry.add(ValueDataEntry("24 Jan", 282))
        areaEntry.add(ValueDataEntry("1 Feb", 9826))
        areaEntry.add(ValueDataEntry("1 Mar", 87137))
        areaEntry.add(ValueDataEntry("1 Apr", 3090444))
        areaEntry.add(ValueDataEntry("5 May", 3517345))

        val set = Set.instantiate()
        set.data(areaEntry)
        val seriesData = set.mapAs("{ x: 'x', value: 'value'}")

        val series: Area = areaChart.area(seriesData)

        series.name("Confirmed")
        series.hovered().stroke("3 #fff")
        series.hovered().markers().enabled(true)
        series.hovered().markers()
            .type(MarkerType.CIRCLE)
            .size(4)
            .stroke("1.5 #fff")
        series.markers().zIndex(100.0)

        areaChart.legend().enabled(true)
        areaChart.legend().padding(0, 0, 20, 0)

        area_chart.setChart(areaChart)
    }
}
