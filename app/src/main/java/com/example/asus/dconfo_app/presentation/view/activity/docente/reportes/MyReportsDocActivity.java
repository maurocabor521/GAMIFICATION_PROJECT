package com.example.asus.dconfo_app.presentation.view.activity.docente.reportes;

import android.Manifest;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Environment;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.asus.dconfo_app.R;
import com.itextpdf.text.Document;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.pdf.PdfWriter;

import java.io.FileOutputStream;
import java.text.SimpleDateFormat;
import java.util.Locale;

public class MyReportsDocActivity extends AppCompatActivity {
    private EditText mTextEt;
    private Button btn_createPdf;
    private static final int STORAGE_SERVICE=1000;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_reports_doc);
        mTextEt = findViewById(R.id.edt_pdf_doc_new);
        btn_createPdf = findViewById(R.id.btn_pdf_doc_new);
        btn_createPdf.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                permission();
            }
        });
    }

    private void permission() {
        if (Build.VERSION.SDK_INT > Build.VERSION_CODES.M){
            if (checkSelfPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE)== PackageManager.PERMISSION_DENIED){
                String[]permission={Manifest.permission.WRITE_EXTERNAL_STORAGE};
                requestPermissions(permission,STORAGE_SERVICE);
            }else{
                savePDF();
            }

        }else {
            savePDF();
        }
    }

    private void savePDF() {
        Document mDoc=new Document();
        String mFileName=new SimpleDateFormat("ddMMyyyy_HHmm", Locale.getDefault()).format(System.currentTimeMillis());
        String mFilePath= Environment.getExternalStorageDirectory()+"/"+mFileName+".pdf";
        try {
            PdfWriter.getInstance(mDoc,new FileOutputStream(mFilePath));
            mDoc.open();
            String mText=mTextEt.getText().toString();
            mDoc.addAuthor("Docente");
            mDoc.add(new Paragraph(mText));
            mDoc.close();
            Toast.makeText(getApplicationContext(),mFileName+".pdf\nis Saved to\n"+mFilePath,Toast.LENGTH_SHORT).show();
        }catch (Exception e){
            Toast.makeText(getApplicationContext(),e.getMessage(),Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        //super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        switch (requestCode){
            case STORAGE_SERVICE:{
                if (grantResults.length>0 && grantResults[0]==PackageManager.PERMISSION_GRANTED){
                    savePDF();
                }else{
                    Toast.makeText(getApplicationContext(),"Permisos denegados",Toast.LENGTH_SHORT).show();
                }
            }
        }
    }


}//clase
