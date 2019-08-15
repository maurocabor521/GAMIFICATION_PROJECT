package com.example.asus.dconfo_app.presentation.view.activity.docente.notas;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.asus.dconfo_app.R;
import com.example.asus.dconfo_app.presentation.view.activity.docente.reportes.ShowReportsDocActivity;
import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.utils.ColorTemplate;

import java.util.ArrayList;

public class PorcentNotasActivity extends AppCompatActivity {

    PieChart pieChart;
    ArrayList<Entry> entries;
    ArrayList<String> PieEntryLabels;
    PieDataSet pieDataSet;
    PieData pieData;

    BarChart chart;
    ArrayList<BarEntry> BARENTRY;
    ArrayList<String> BarEntryLabels;
    ArrayList<String> lista_idEstudiante;
    ArrayList<Integer> lista_Calif_Ejercicio;
    BarDataSet Bardataset;
    BarData BARDATA;

    int fonico;
    int lexico;
    int silabico;
    String nameEst;
    String tipoEst;
    int idgrupo;
    TextView txt_nameEst;
    Button btn_gen_reportes;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_porcent_notas);

        Bundle datos = this.getIntent().getExtras();
        fonico = datos.getInt("fonico");
        lexico = datos.getInt("lexico");
        silabico = datos.getInt("silabico");
        idgrupo = datos.getInt("idgrupo");
        nameEst = datos.getString("nameEst");
        tipoEst = datos.getString("tipoEst");


        btn_gen_reportes = findViewById(R.id.btn_docente_porcentajes_reportes);
        if (tipoEst.equals("est")) {
            btn_gen_reportes.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Bundle bundle = new Bundle();
                    bundle.putString("nameEst", nameEst);
                    bundle.putInt("fonico", fonico);
                    bundle.putInt("lexico", lexico);
                    bundle.putInt("silabico", silabico);
                    bundle.putInt("idgrupo", idgrupo);
                    Intent intent = new Intent(PorcentNotasActivity.this, ShowReportsDocActivity.class);
                    intent.putExtras(bundle);
                    startActivity(intent);
                }
            });
        } else {
            btn_gen_reportes.setVisibility(View.GONE);
        }

       /* lista_idEstudiante=null;
        lista_Calif_Ejercicio=null;*/

        lista_idEstudiante = datos.getStringArrayList("list");
        lista_Calif_Ejercicio = datos.getIntegerArrayList("listCalificacion");

        System.out.println("nameEst: " + nameEst);

        if (!(lista_idEstudiante == null)) {
            for (int i = 0; i < lista_idEstudiante.size(); i++) {
                System.out.println("lista_idEstudiante : " + lista_idEstudiante.get(i));
            }
        }
//        System.out.println("lista_idEstudiante: " + lista_idEstudiante.size());

        txt_nameEst = (TextView) findViewById(R.id.txt_nameEst);
        txt_nameEst.setText(nameEst);

        showToolbar("Global notas" + " - " + nameEst, true);

        pieChart = (PieChart) findViewById(R.id.piechart);

        entries = new ArrayList<>();

        PieEntryLabels = new ArrayList<String>();

        AddValuesToPIEENTRY();

        AddValuesToPieEntryLabels();

        pieDataSet = new PieDataSet(entries, "");

        pieData = new PieData(PieEntryLabels, pieDataSet);

        pieDataSet.setColors(ColorTemplate.COLORFUL_COLORS);

        pieChart.setData(pieData);

        pieChart.animateY(3000);
        //******************************************************************************************
        chart = (BarChart) findViewById(R.id.chart1);

        BARENTRY = new ArrayList<>();

        BarEntryLabels = new ArrayList<String>();

        AddValuesToBARENTRY();

        AddValuesToBarEntryLabels();

        Bardataset = new BarDataSet(BARENTRY, "Projects");

        BARDATA = new BarData(BarEntryLabels, Bardataset);

        Bardataset.setColors(ColorTemplate.COLORFUL_COLORS);

        chart.setData(BARDATA);

        chart.animateY(3000);

    }

    public void AddValuesToPIEENTRY() {
        if (!(lista_idEstudiante == null) && !(lista_idEstudiante.size() == 0)) {
            for (int i = 0; i < lista_idEstudiante.size(); i++) {
                entries.add(new BarEntry(lista_Calif_Ejercicio.get(i), i));
            }

        } else {
            if ((fonico != 0) && (lexico != 0) && (silabico != 0)) {
                entries.add(new BarEntry(fonico, 0));
                entries.add(new BarEntry(lexico, 1));
                entries.add(new BarEntry(silabico, 2));
            }
            if ((fonico != 0) && (lexico != 0) && (silabico == 0)) {
                entries.add(new BarEntry(fonico, 0));
                entries.add(new BarEntry(lexico, 1));
            }
            if ((fonico != 0) && (lexico == 0) && (silabico != 0)) {
                entries.add(new BarEntry(fonico, 0));
                entries.add(new BarEntry(silabico, 1));
            }
            if ((fonico == 0) && (lexico != 0) && (silabico != 0)) {
                entries.add(new BarEntry(lexico, 0));
                entries.add(new BarEntry(silabico, 1));
            }
            if ((fonico != 0) && (lexico == 0) && (silabico == 0)) {
                entries.add(new BarEntry(fonico, 0));
            }
            if ((fonico == 0) && (lexico != 0) && (silabico == 0)) {
                entries.add(new BarEntry(lexico, 0));
            }
            if ((fonico == 0) && (lexico == 0) && (silabico != 0)) {
                entries.add(new BarEntry(silabico, 0));
            }
        }
    }

    public void AddValuesToPieEntryLabels() {

        if (!(lista_idEstudiante == null) && !(lista_idEstudiante.size() == 0)) {
            for (int i = 0; i < lista_idEstudiante.size(); i++) {
                PieEntryLabels.add(lista_idEstudiante.get(i));
            }
        } else {

            PieEntryLabels.add("Fónica");
            PieEntryLabels.add("Léxica");
            PieEntryLabels.add("Silábica");
        }

    }

    public void showToolbar(String tittle, boolean upButton) {
        android.support.v7.widget.Toolbar toolbar = (android.support.v7.widget.Toolbar) findViewById(R.id.toolbar_ejercicio);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle(tittle);
        //getSupportActionBar();
        getSupportActionBar().setDisplayHomeAsUpEnabled(upButton);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
    }

    //**********************************************************************************************
    public void AddValuesToBARENTRY() {

        if (!(lista_idEstudiante == null) && !(lista_idEstudiante.size() == 0)) {
            for (int i = 0; i < lista_idEstudiante.size(); i++) {
                BARENTRY.add(new BarEntry(lista_Calif_Ejercicio.get(i), i));
            }

        } else {

            if ((fonico != 0) && (lexico != 0) && (silabico != 0)) {
                BARENTRY.add(new BarEntry(fonico, 0));
                BARENTRY.add(new BarEntry(lexico, 1));
                BARENTRY.add(new BarEntry(silabico, 2));
            }
            if ((fonico != 0) && (lexico != 0) && (silabico == 0)) {
                BARENTRY.add(new BarEntry(fonico, 0));
                BARENTRY.add(new BarEntry(lexico, 1));
            }
            if ((fonico != 0) && (lexico == 0) && (silabico != 0)) {
                BARENTRY.add(new BarEntry(fonico, 0));
                BARENTRY.add(new BarEntry(silabico, 1));
            }
            if ((fonico == 0) && (lexico != 0) && (silabico != 0)) {
                BARENTRY.add(new BarEntry(lexico, 0));
                BARENTRY.add(new BarEntry(silabico, 1));
            }
            if ((fonico != 0) && (lexico == 0) && (silabico == 0)) {
                BARENTRY.add(new BarEntry(fonico, 0));
            }
            if ((fonico == 0) && (lexico != 0) && (silabico == 0)) {
                BARENTRY.add(new BarEntry(lexico, 0));
            }
            if ((fonico == 0) && (lexico == 0) && (silabico != 0)) {
                BARENTRY.add(new BarEntry(silabico, 0));
            }
        }

    }

    public void AddValuesToBarEntryLabels() {

        if (!(lista_idEstudiante == null) && !(lista_idEstudiante.size() == 0)) {
            for (int i = 0; i < lista_idEstudiante.size(); i++) {
                BarEntryLabels.add(lista_idEstudiante.get(i));
            }
        } else {

            BarEntryLabels.add("Fónica");
            BarEntryLabels.add("Léxica");
            BarEntryLabels.add("Silábica");
        }


    }

    //método que permite volver al padre conservando las variables
    @Override
    public boolean onSupportNavigateUp() {
        finish();
        return true;
    }
}
