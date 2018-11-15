package com.example.user.qrcode;

import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.WriterException;
import com.google.zxing.common.BitMatrix;
import com.journeyapps.barcodescanner.BarcodeEncoder;

public class CreateQRActivity extends AppCompatActivity {
    ImageView imgQrCode;
    Button btnGenerate;
    EditText edtInput;
    Bitmap bitmap;
    String userInput;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create);

        imgQrCode = findViewById(R.id.imgQr);
        btnGenerate = findViewById(R.id.btnGenerate);
        edtInput = findViewById(R.id.edtInput);

        btnGenerate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                userInput = edtInput.getText().toString();
                if (!userInput.equalsIgnoreCase("")) {
                    generateQrCode();
                } else {
                    Toast.makeText(CreateQRActivity.this, "no input form user", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    private void generateQrCode() {
        MultiFormatWriter multiFormatWriter = new MultiFormatWriter();
        try {
            BitMatrix bitMatrix = multiFormatWriter.encode(userInput, BarcodeFormat.QR_CODE, 200, 200);
            BarcodeEncoder barcodeEncoder = new BarcodeEncoder();
            bitmap = barcodeEncoder.createBitmap(bitMatrix);
            imgQrCode.setImageBitmap(bitmap);
        } catch (WriterException e) {
            e.printStackTrace();
        }
    }
}
