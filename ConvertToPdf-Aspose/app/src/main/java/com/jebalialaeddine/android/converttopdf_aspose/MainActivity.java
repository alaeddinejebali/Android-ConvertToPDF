package com.jebalialaeddine.android.converttopdf_aspose;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.os.Environment;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.aspose.words.Document;


public class MainActivity extends Activity {
    final Context CONTEXT = this;

    private void convertFile(String filePath, String outputPath) {
        try{


            Document document = new Document(filePath);
            document.save(outputPath);
            showAlertDialog("Converting text...", "Converting text to PDF finished... Generated PDF saved in: " + outputPath);
        } catch (Exception e) {
            showAlertDialog("Converting text...", "An error has occurred: " + e.getMessage());
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
        // load AW manually
        //AsposeWordsApplication awapp = new AsposeWordsApplication();

        // this context AW uses to find assets/ folder which contains the second part of the library.
        //awapp.loadLibs(CONTEXT);


        setContentView(R.layout.activity_main);

        //Convert File
        Button buttonConvert = (Button) findViewById(R.id.buttonConvert);
        buttonConvert.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EditText editTextFilePath = (EditText) findViewById(R.id.editTextFilePath);
                String filePath = Environment.getExternalStorageDirectory().getAbsolutePath() + "/" + editTextFilePath.getText();
                String outputPath = filePath.substring(0, filePath.lastIndexOf('.')) + ".pdf";
                convertFile(filePath, outputPath);
            }
        });
    }
}
