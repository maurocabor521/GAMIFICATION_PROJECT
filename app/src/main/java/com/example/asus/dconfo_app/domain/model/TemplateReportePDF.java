package com.example.asus.dconfo_app.domain.model;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.os.Environment;
import android.util.Log;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.example.asus.dconfo_app.R;
import com.example.asus.dconfo_app.presentation.view.activity.docente.reportes.ReportsDocActivity;
import com.itextpdf.text.BadElementException;
import com.itextpdf.text.BaseColor;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Element;
import com.itextpdf.text.Font;
import com.itextpdf.text.Image;
import com.itextpdf.text.PageSize;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.Phrase;
import com.itextpdf.text.pdf.PdfContentByte;
import com.itextpdf.text.pdf.PdfDocument;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Locale;


public class TemplateReportePDF {
    private Context context;
    private File pdffile;
    private Document document;
    private PdfWriter pdfWriter;
    PdfWriter writer;
    private Paragraph paragraph;
    private Font ftitle = new Font(Font.FontFamily.TIMES_ROMAN, 20, Font.BOLD);
    private Font fsubTitle = new Font(Font.FontFamily.TIMES_ROMAN, 18, Font.BOLD);
    private Font fText = new Font(Font.FontFamily.TIMES_ROMAN, 12, Font.BOLD);
    private Font fHighText = new Font(Font.FontFamily.TIMES_ROMAN, 15, Font.BOLD, BaseColor.RED);
    private String mFilePath;
    //LinearLayout linearLayout;
    //public static final String IMG = "resources/images/bruno.jpg";

    public TemplateReportePDF(Context context) {
        this.context = context;
    }

    //abrir archivo
    //public void openDocument() {
    public void openDocument(String nameFile) {
        createFile(nameFile);
        try {
            document = new Document(PageSize.LETTER);
            //pdfWriter = PdfWriter.getInstance(document, new FileOutputStream(pdffile));
            pdfWriter = PdfWriter.getInstance(document, new FileOutputStream(mFilePath));
            //abrir documento
            document.open();
        } catch (Exception e) {
            Log.e("openDocument", e.toString());
        }
    }

    //private void createFile() {
    public void createFile(String nameFile) {
        String mFileName = new SimpleDateFormat("ddMMyyyy_HHmm", Locale.getDefault()).format(System.currentTimeMillis());
        //mFilePath = Environment.getExternalStorageDirectory() + "/" + mFileName + ".pdf";
        mFilePath = Environment.getExternalStorageDirectory() + "/" + nameFile + ".pdf";
       /* File folder = new File(Environment.getExternalStorageDirectory().toString(), "PDF");
        if (!folder.exists()) {
            folder.mkdir();
           //pdffile = new File(folder, "TemplatePDF.pdf");
            pdffile = new File(folder, nombre+".pdf");

        }*/
    }



    //cerrar documento
    public void closeDocument() {
        document.close();
    }

    //metadatos a ver en prop de archivo, biografia
    public void addMetaData(String title, String subject, String author) {
        document.addTitle(title);
        document.addSubject(subject);
        document.addAuthor(author);
    }

    //title
    public void addTitle(String title, String subTitle, String date) {
        try {
            paragraph = new Paragraph();
            addChildP(new Paragraph(title, ftitle));
            addChildP(new Paragraph(subTitle, fsubTitle));
            addChildP(new Paragraph("Generado: " + date, fHighText));
            paragraph.setSpacingAfter(30);
            document.add(paragraph);
        } catch (Exception e) {
            Log.e("addTitle", e.toString());
        }
    }

    private void addChildP(Paragraph childParagraph) {
        childParagraph.setAlignment(Element.ALIGN_CENTER);
        paragraph.add(childParagraph);
    }

    //agregar parrafo en general
    public void addParagraph(String text) {
        try {
            paragraph = new Paragraph(text, fText);
            paragraph.setSpacingAfter(1);
            paragraph.setSpacingAfter(5);
            document.add(paragraph);
        } catch (Exception e) {
            Log.e("addParagraph", e.toString());
        }
    }

    public  void addGraphic()  {
      /*  Document document = new Document();
        PdfWriter.getInstance(document, new FileOutputStream("sample1.pdf"));
        document.open();
        Drawable drawable = context.getResources().getDrawable(R.drawable.ic_conciencia_fonica);
        //document.add(drawable);
        Image img = Image.getInstance("dconfo.png");
        document.add(new Paragraph("Sample 1: This is simple image demo."));
        document.add(img);
        document.close();
        System.out.println("Done");*/
        Bitmap bm = BitmapFactory.decodeResource(context.getResources(), R.drawable.dconfo);
        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        bm.compress(Bitmap.CompressFormat.PNG, 100, stream);
        Image img = null;
        byte[] byteArray = stream.toByteArray();
        try {
            img = Image.getInstance(byteArray);
            img.setAlignment(Element.ALIGN_CENTER);
            document.add(img);
        } catch (BadElementException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (DocumentException e) {
            e.printStackTrace();
        }
    }

    //crear Tablas
    //filas son clients
    public void createTable(String[] header, ArrayList<String[]> clients) {
        try {
            paragraph = new Paragraph();
            paragraph.setSpacingBefore(10);
            paragraph.setFont(fText);
            //numero de columnas
            PdfPTable pdfPTable = new PdfPTable(header.length);
            pdfPTable.setWidthPercentage(100);
            //celdas
            PdfPCell pdfPCell;
            //variable que representa la columna
            int indexC = 0;
            //rellenar tabla de encabezado
            while (indexC < header.length) {
                //dentro texto en celda
                pdfPCell = new PdfPCell(new Phrase(header[indexC++], fsubTitle));
                //alinear celdas
                pdfPCell.setHorizontalAlignment(Element.ALIGN_CENTER);
                pdfPCell.setBackgroundColor(BaseColor.GREEN);
                //agregar celda a tabla
                pdfPTable.addCell(pdfPCell);
            }
            //llenar resto de tabla
            for (int indexR = 0; indexR < clients.size(); indexR++) {
                String[] row = clients.get(indexR);
                for (indexC = 0; indexC < header.length; indexC++) {
                    //crear nueva celda
                    pdfPCell = new PdfPCell(new Phrase(row[indexC]));
                    pdfPCell.setHorizontalAlignment(Element.ALIGN_CENTER);
                    //ancho de celda
                    pdfPCell.setFixedHeight(40);
                    pdfPTable.addCell(pdfPCell);
                }
            }

            //agregar tabla a un parrafo
            paragraph.add(pdfPTable);
            document.add(paragraph);

        } catch (Exception e) {
            Log.e("createTable", e.toString());
        }
    }

    public void viewPdf() {
        Intent intent = new Intent(context, ReportsDocActivity.class);
        //intent.putExtra("path", pdffile.getAbsolutePath());
        intent.putExtra("path", mFilePath.toString());
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        context.startActivity(intent);
    }
}
