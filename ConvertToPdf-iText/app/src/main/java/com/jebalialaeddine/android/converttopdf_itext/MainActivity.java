package com.jebalialaeddine.android.converttopdf_itext;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.os.Environment;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.itextpdf.text.Document;
import com.itextpdf.text.Element;
import com.itextpdf.text.Image;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.pdf.PdfWriter;

import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStreamReader;


public class MainActivity extends Activity {
    final Context CONTEXT = this;

    /**
     * Convert text file to PDF
     *
     * @param textFilePath String
     * @param outputPath   String
     */
    private void convertText(String textFilePath, String outputPath) {
        FileInputStream fis = null;
        DataInputStream in = null;
        InputStreamReader isr = null;
        BufferedReader br = null;
        try {
            Document document = new Document();
            PdfWriter.getInstance(document, new FileOutputStream(outputPath));
            document.open();
            File file = new File(textFilePath);
            if (file.exists()) {
                fis = new FileInputStream(file);
                in = new DataInputStream(fis);
                isr = new InputStreamReader(in);
                br = new BufferedReader(isr);
                String strLine;
                while ((strLine = br.readLine()) != null) {
                    Paragraph para = new Paragraph(strLine + "\n");
                    para.setAlignment(Element.ALIGN_JUSTIFIED);
                    document.add(para);
                }
                showAlertDialog("Converting text...", "Converting text to PDF finished... Generated PDF saved in " + outputPath);
            } else {
                showAlertDialog("Converting text...", "File " + textFilePath + " does not exist!");
            }
            document.close();
        } catch (Exception e) {
            showAlertDialog("Converting text...", "An error has occurred: " + e.getMessage());
        }
    }

    /**
     * Convert image file to PDF
     *
     * @param imagePath  String
     * @param outputPath String
     */
    private void convertImage(String imagePath, String outputPath) {
        try {
            Document document = new Document();
            PdfWriter.getInstance(document, new FileOutputStream(outputPath));
            document.open();
            Image image = Image.getInstance(imagePath);
            //document.setPageSize( new Rectangle(image.getAbsoluteX(), ));
            document.add(image);
            document.close();
            showAlertDialog("Converting image...", "Converting image to PDF finished... Generated PDF saved in " + outputPath);
        } catch (Exception e) {
            showAlertDialog("Converting image...", "An error has occurred: " + e.getMessage());
        }
    }

    /**
     * show alert dialog
     *
     * @param title   String
     * @param message String
     */
    private void showAlertDialog(String title, String message) {
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(CONTEXT);
        alertDialogBuilder.setTitle(title);
        alertDialogBuilder
                .setMessage(message)
                .setCancelable(false)
                .setPositiveButton("Close", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        dialog.cancel();
                    }
                });
        // create alert dialog
        AlertDialog alertDialog = alertDialogBuilder.create();
        // show it
        alertDialog.show();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //Convert Text
        Button buttonConvertText = (Button) findViewById(R.id.buttonConvertText);
        buttonConvertText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EditText editTextTxtPath = (EditText) findViewById(R.id.editTextTxtPath);
                String textFilePath = Environment.getExternalStorageDirectory().getAbsolutePath() + "/" + editTextTxtPath.getText();
                String outputPath = textFilePath.substring(0, textFilePath.lastIndexOf('.')) + ".pdf";
                convertText(textFilePath, outputPath);
            }
        });

        //Convert image
        Button buttonConvertImage = (Button) findViewById(R.id.buttonConvertImage);
        buttonConvertImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EditText editTextImagePath = (EditText) findViewById(R.id.editTextImagePath);
                String imagePath = Environment.getExternalStorageDirectory().getAbsolutePath() + "/" + editTextImagePath.getText();
                String outputPath = imagePath.substring(0, imagePath.lastIndexOf('.')) + ".pdf";
                convertImage(imagePath, outputPath);
            }
        });
    }
}
