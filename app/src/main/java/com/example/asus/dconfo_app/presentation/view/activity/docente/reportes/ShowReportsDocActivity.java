package com.example.asus.dconfo_app.presentation.view.activity.docente.reportes;

import android.Manifest;
import android.content.pm.PackageManager;
import android.os.Build;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.asus.dconfo_app.R;
import com.example.asus.dconfo_app.domain.model.TemplateReportePDF;
import com.example.asus.dconfo_app.presentation.view.contract.CategoriaEjerciciosContract;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Locale;

public class ShowReportsDocActivity extends AppCompatActivity {

    private String[] header1 = {"Id", "Nombre", "Apellido"};
    private String[] header = {"Fónica", "Léxica", "Silábica"};
    private String shortText = "Saludos!";
    /*private String longText = "Lorem Ipsum is simply dummy text of the printing and typesetting industry. " +
            "Lorem Ipsum has been the industry's standard dummy text ever since the 1500s, " +
            "when an unknown printer took a galley of type and scrambled it to make a type specimen book."; */

    private String longText;
    private TemplateReportePDF templateReportePDF;
    private Button btn_pdf;
    private static final int MY_PERMISSIONS_REQUEST_WRITE_EXTERNAL = 1;
    EditText edt_pdf_doc;
    EditText edt_pdf_doc_comentario;
    String nameArchivo = "";
    int fonico;
    int lexico;
    int silabico;
    String nameEst;
    int idgrupo;
    String mFileDate;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_reports_doc);

        Bundle datos = this.getIntent().getExtras();
        fonico = datos.getInt("fonico");
        lexico = datos.getInt("lexico");
        silabico = datos.getInt("silabico");
        nameEst = datos.getString("nameEst");
        idgrupo = datos.getInt("idgrupo");

        btn_pdf = findViewById(R.id.btn_pdf_doc);
        edt_pdf_doc = findViewById(R.id.edt_pdf_doc);
        edt_pdf_doc_comentario = findViewById(R.id.edt_pdf_comentario);
        edt_pdf_doc.setText(nameEst);
        nameArchivo = edt_pdf_doc.getText().toString();

        checkPermission();
        mFileDate = new SimpleDateFormat("dd/MM/yyyy - HH:mm:ss", Locale.getDefault()).format(System.currentTimeMillis());

        showToolbar("Generar Reporte" + " - " + nameEst, true);

    }

    public void showToolbar(String tittle, boolean upButton) {
        android.support.v7.widget.Toolbar toolbar = (android.support.v7.widget.Toolbar) findViewById(R.id.toolbar_ejercicio);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle(tittle);
        //getSupportActionBar();
        getSupportActionBar().setDisplayHomeAsUpEnabled(upButton);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
    }

    //método que permite volver al padre conservando las variables
    @Override
    public boolean onSupportNavigateUp() {
        finish();
        return true;
    }
    //*************************************************************************************************

    private void crearTemplete() {
        //if (nameArchivo!=""){
        templateReportePDF = new TemplateReportePDF(getApplicationContext());
        templateReportePDF.openDocument(edt_pdf_doc.getText().toString());
        //templateReportePDF.savePDF();
        templateReportePDF.addGraphic();
        templateReportePDF.addMetaData(edt_pdf_doc.getText().toString(), "Informes", "Juan Valdez");
        templateReportePDF.addTitle(edt_pdf_doc.getText().toString(), "Desarrollo Conciencia Fonológica, Grupo: " + String.valueOf(idgrupo), mFileDate);

        templateReportePDF.addParagraph(shortText);
        templateReportePDF.addParagraph(longText);
        templateReportePDF.createTable(header, getClients());
        templateReportePDF.closeDocument();
        // }
    }

    private void checkPermission() {
        if (Build.VERSION.SDK_INT > Build.VERSION_CODES.M) {
            if (checkSelfPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE) == PackageManager.PERMISSION_DENIED) {
                String[] permission = {Manifest.permission.WRITE_EXTERNAL_STORAGE};
                requestPermissions(permission, MY_PERMISSIONS_REQUEST_WRITE_EXTERNAL);
            } else {
                //savePDF();
            }

        } else {
            // savePDF();
        }
    }

    //verificar permisos lectura y escritura Sd
  /*  private void checkPermission() {

        if (ContextCompat.checkSelfPermission(ShowReportsDocActivity.this, Manifest.permission.WRITE_EXTERNAL_STORAGE) !=
                PackageManager.PERMISSION_GRANTED) {
            if (ActivityCompat.shouldShowRequestPermissionRationale(ShowReportsDocActivity.this,
                    Manifest.permission.WRITE_EXTERNAL_STORAGE)) {
                Toast.makeText(getApplicationContext(), "Permiso escritura en memoria SD", Toast.LENGTH_SHORT).show();
            } else {
                ActivityCompat.requestPermissions(ShowReportsDocActivity.this,
                        new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE},
                        MY_PERMISSIONS_REQUEST_WRITE_EXTERNAL);
            }
        }
    }*/

    public void pdfview(View view) {
        longText = edt_pdf_doc_comentario.getText().toString();
        crearTemplete();
        templateReportePDF.viewPdf();
    }

    private ArrayList<String[]> getClients() {
        ArrayList<String[]> rows = new ArrayList<>();
        rows.add(new String[]{String.valueOf(fonico), String.valueOf(lexico), String.valueOf(silabico)});
       /* rows.add(new String[]{"2", "David", "Amaral"});
        rows.add(new String[]{"3", "Lina", "Aldana"});
        rows.add(new String[]{"4", "Sebastian", "Arias"});*/
        return rows;
    }


}
