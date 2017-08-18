package com.fct.TEDxISTAlameda;

import android.graphics.Bitmap;
import android.os.Bundle;

import android.support.v7.app.AppCompatActivity;
import android.widget.ImageView;


import com.fct.TEDxISTAlameda.R;
import com.fct.TEDxISTAlameda.qrcode.Contents;
import com.fct.TEDxISTAlameda.qrcode.QRCodeEncoder;
import com.google.zxing.BarcodeFormat;
import com.google.zxing.WriterException;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;

import static android.R.attr.width;
import static com.fct.TEDxISTAlameda.R.attr.height;


/**
 * Created by Veloso on 15/08/2017.
 */

public class QrcodeTab extends AppCompatActivity {

        String line ;

        @Override
        protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.qrtab);

            //ler
            try {

                String FILENAME = "data";
                FileInputStream fez = getApplicationContext().openFileInput(FILENAME);

                BufferedReader reader;
                reader = new BufferedReader(new InputStreamReader(fez));
                line = reader.readLine();


                fez.close();
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }

            //fim do ler

            //gerar o qrcode
            //qrcode
            int smallerDimension = width < height ? width : height;
            smallerDimension = smallerDimension * 3/4;


            //Encode with a QR Code image
            QRCodeEncoder qrCodeEncoder = new QRCodeEncoder(line,
                    null,
                    Contents.Type.TEXT,
                    BarcodeFormat.QR_CODE.toString(),
                    smallerDimension);
            try {
                Bitmap bitmap = qrCodeEncoder.encodeAsBitmap();
                ImageView myImage = (ImageView) findViewById(R.id.qrcode);
                myImage.setImageBitmap(bitmap);

            } catch (WriterException e) {
                e.printStackTrace();
            }


            //fim do code


        }




}
