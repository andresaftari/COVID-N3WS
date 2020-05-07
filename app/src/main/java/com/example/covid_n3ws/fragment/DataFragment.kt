package com.example.covid_n3ws.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.anychart.AnyChart
import com.anychart.chart.common.dataentry.DataEntry
import com.anychart.chart.common.dataentry.ValueDataEntry
import com.anychart.charts.Pie
import com.anychart.enums.Align
import com.anychart.enums.LegendLayout
import com.example.covid_n3ws.R
import com.google.firebase.firestore.DocumentReference
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.android.synthetic.main.fragment_data.*

class DataFragment : Fragment() {
    private lateinit var fStore: FirebaseFirestore

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View? = inflater.inflate(R.layout.fragment_data, container, false)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        retainInstance = true

        fStore = FirebaseFirestore.getInstance()
        val docRef: DocumentReference = fStore.collection("reports").document("cases")

        docRef.addSnapshotListener { documentSnapshot, _ ->
            val confirmed = documentSnapshot?.getString("confirmed")
            val local = documentSnapshot?.getString("indonesia")
            val recovered = documentSnapshot?.getString("recovered")
            val death = documentSnapshot?.getString("death")

            tv_confirmedNum.text = confirmed
            tv_localNum.text = local
            tv_recoveredNum.text = recovered
            tv_deathNum.text = death
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        // Pie Chart
        val pie: Pie = AnyChart.pie()

        pie.animation(true)

        val entries: MutableList<DataEntry> = arrayListOf()
        entries.add(ValueDataEntry("Confirmed", 3517345))
        entries.add(ValueDataEntry("Recovered", 1197735))
        entries.add(ValueDataEntry("Death", 256928))

        pie.data(entries)
        pie.title("OVERALL CASES (WORLD)")
        pie.labels().position("inside")
        pie.legend()
            .position("center-bottom")
            .itemsLayout(LegendLayout.HORIZONTAL)
            .align(Align.CENTER)

        pie_chart.setChart(pie)
    }
}
