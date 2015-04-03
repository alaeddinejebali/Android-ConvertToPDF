package com.jebalialaeddine.android.asposeimagestopdf;

import android.app.Activity;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.aspose.java.awt.Color;
import com.aspose.pdf.Page;
import com.itextpdf.text.Document;
import com.itextpdf.text.Image;
import com.itextpdf.text.Rectangle;
import com.itextpdf.text.pdf.PdfWriter;

import java.io.FileOutputStream;
import java.io.IOException;


public class MainActivity extends Activity {
    private static void HelloWorld(){
        String outputPath = Environment.getExternalStorageDirectory().getAbsolutePath()+"/test/HelloWorld.pdf";
        com.aspose.pdf.Document pdfDocument1 = new com.aspose.pdf.Document();
        // add a page to PDF file
        Page page= pdfDocument1.getPages().add();
        // add text in new page
        //create text fragment
        com.aspose.pdf.TextFragment textFragment = new com.aspose.pdf.TextFragment("Hello World!");
        textFragment.setPosition(new com.aspose.pdf.Position(100, 600));

        //set text properties
        textFragment.getTextState().setFont(com.aspose.pdf.FontRepository.findFont("Helvetica"));
        textFragment.getTextState().setFontSize(14);
        textFragment.getTextState().setForegroundColor(Color.BLUE);
        textFragment.getTextState().setBackgroundColor(Color.GRAY);

        // create TextBuilder object
        com.aspose.pdf.TextBuilder textBuilder = new com.aspose.pdf.TextBuilder(page);
        // append the text fragment to the PDF page
        textBuilder.appendText(textFragment);

        // save the PDF file
        pdfDocument1.save(outputPath);
    }


    private static void InsertImageDOM() throws IOException{
        //String inputPath=Environment.getExternalStorageDirectory().getAbsolutePath()+"/HelloWorld_TextReplace.pdf";
        String outputPath= Environment.getExternalStorageDirectory().getAbsolutePath()+"/test/HelloWorld.pdf";
        String imagePath=Environment.getExternalStorageDirectory().getAbsolutePath()+"/test/ala.jpg";

        //open first document
        com.aspose.pdf.Document pdfDocument1 = new com.aspose.pdf.Document();
        //set coordinates
        int lowerLeftX = 0;
        int lowerLeftY = 0;
        int upperRightX = 600;
        int upperRightY = 840;

        //get the page where image needs to be added
        com.aspose.pdf.Page page = pdfDocument1.getPages().add();
        //load image into stream
        java.io.FileInputStream  imageStream = new java.io.FileInputStream(new java.io.File(imagePath));
        //add image to Images collection of Page Resources
        page.getResources().getImages().add(imageStream);
        //using GSave operator: this operator saves current graphics state
        page.getContents().add(new com.aspose.pdf.Operator.GSave());
        //create Rectangle and Matrix objects
        com.aspose.pdf.Rectangle rectangle = new com.aspose.pdf.Rectangle(lowerLeftX, lowerLeftY, upperRightX, upperRightY);
        com.aspose.pdf.Matrix matrix = new com.aspose.pdf.Matrix(new double[] { rectangle.getURX() - rectangle.getLLX(), 0, 0, rectangle.getURY()- rectangle.getLLY(), rectangle.getLLX(), rectangle.getLLY() });
        //using ConcatenateMatrix (concatenate matrix) operator: defines how image must be placed
        page.getContents().add(new com.aspose.pdf.Operator.ConcatenateMatrix(matrix));
        com.aspose.pdf.XImage ximage = page.getResources().getImages().get_Item(page.getResources().getImages().size());
        //using Do operator: this operator draws image
        page.getContents().add(new com.aspose.pdf.Operator.Do(ximage.getName()));
        //using GRestore operator: this operator restores graphics state
        page.getContents().add(new com.aspose.pdf.Operator.GRestore());
        // save the newly generated PDF file
        pdfDocument1.save(outputPath);

        // close image stream
        imageStream.close();
    }



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    @Override
    protected void onStart() {
        super.onStart();

        //Convert Text to PDF
        Button buttonConvertImage = (Button) findViewById(R.id.buttonConvertText);
        /*
        buttonConvertImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.i("asposeLog", "Converting image to PDF started...");
                try {
                    InsertImageDOM();
                    Log.i("asposeLog", "Text added successfully, please check the root of your SD card");
                } catch (Exception e) {
                    Log.e("asposeLog", "Error during document processing: " + e.getMessage());
                }
                Log.i("asposeLog", "Converting text to PDF finished...");
            }
        });
        */

        //Convert image to PDF
        Button buttonConvertText = (Button) findViewById(R.id.buttonConvertText);
        buttonConvertText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.i("asposeLog", "Converting text to PDF started...");




                String outputPath= Environment.getExternalStorageDirectory().getAbsolutePath()+"/test/HelloWorld.pdf";
                String imagePath=Environment.getExternalStorageDirectory().getAbsolutePath()+"/test/ala.jpg";
                try {
                    Document document=new Document();
                    PdfWriter.getInstance(document, new FileOutputStream(outputPath));
                    document.open();
                    Image image = Image.getInstance(imagePath);
                    document.setPageSize( new Rectangle(image.getAbsoluteX(), ));
                    document.add(image);
                    document.close();
                } catch (Exception e) {
                    Log.e("asposeLog", "Error during document processing: " + e.getMessage());
                }
                Log.i("asposeLog", "Converting text to PDF finished...");








                /*
                try {
                    InsertImageDOM();
                    Log.i("asposeLog", "Image added successfully, please check the root of your SD card");
                } catch (Exception e) {
                    Log.e("asposeLog", "Error during document processing: " + e.getMessage());
                }
                Log.i("asposeLog", "Converting text to PDF finished...");
                */
            }
        });
    }

}
