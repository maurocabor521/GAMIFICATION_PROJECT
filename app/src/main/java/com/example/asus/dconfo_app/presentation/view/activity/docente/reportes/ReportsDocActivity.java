package com.example.asus.dconfo_app.presentation.view.activity.docente.reportes;

import android.content.Intent;
import android.net.Uri;
import android.os.Environment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.example.asus.dconfo_app.R;
import com.github.barteksc.pdfviewer.PDFView;

import java.io.File;

public class ReportsDocActivity extends AppCompatActivity {

    private PDFView pdfView;
    private File file;
    private Button btn_enviar;
    private String path;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reports_doc);

        showToolbar("Reporte PDF", true);

        pdfView = findViewById(R.id.pdfView);

        Bundle bundle = getIntent().getExtras();

        path=bundle.getString(path);
        System.out.println("path: "+path);

        if (bundle != null) {
            //obtiene direccion
            file = new File(bundle.getString("path", ""));
            System.out.println("file: "+file.getAbsolutePath());
        }
        pdfView.fromFile(file)
                .enableSwipe(true)
                .swipeHorizontal(false)
                .enableDoubletap(true)
                .enableAntialiasing(true)
                .load();

        btn_enviar = findViewById(R.id.btn_reporte_send_email);
        btn_enviar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String[] mailto = {""};
               // Uri uri = Uri.fromFile(new File(Environment.getExternalStorageDirectory().getAbsolutePath() + "/CALC/REPORTS/", pdfname));
                Uri uri = Uri.fromFile(file);
                Intent emailIntent = new Intent(Intent.ACTION_SEND);
                emailIntent.putExtra(Intent.EXTRA_EMAIL, mailto);
                emailIntent.putExtra(Intent.EXTRA_SUBJECT, "PDF Report");
                emailIntent.putExtra(android.content.Intent.EXTRA_TEXT, "Hi PDF is attached in this mail. ");
                emailIntent.setType("application/pdf");
                emailIntent.putExtra(Intent.EXTRA_STREAM, uri);
                startActivity(Intent.createChooser(emailIntent, "Send email using:"));
            }
        });

        //Crear una Lista de Uris.
     /*   List<Uri> listUris = new ArrayList<Uri>();
        //Agrega elementos (como ejemplo 3 elementos).
        lstUris.add(Uri.fromFile(new File(Environment.getExternalStorageDirectory().getAbsolutePath() + "/PDFS/", "archivo1.pdf")););
        lstUris.add(Uri.fromFile(new File(Environment.getExternalStorageDirectory().getAbsolutePath() + "/PDFS/", "archivo2.pdf")););
        lstUris.add(Uri.fromFile(new File(Environment.getExternalStorageDirectory().getAbsolutePath() + "/PDFS/", "archivo3.pdf")););

        //Crea intent para enviar el email.
        Intent i = new Intent(Intent.ACTION_SEND);
        i.setType("application/pdf");
        //Agrega email o emails de destinatario.
        i.putExtra(Intent.EXTRA_EMAIL, new String[] { "email@dominio.com" });
        i.putExtra(Intent.EXTRA_SUBJECT, "Envio de archivos PDF.");
        i.putExtra(Intent.EXTRA_TEXT, "Hola te envío archivos PDF.");
        //Define listado de Uris de cada archivo.
        i.putExtra(Intent.EXTRA_STREAM,  listUris);
        startActivity(Intent.createChooser(i, "Enviar e-mail mediante:"));*/



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

}


