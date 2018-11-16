# QrCodeScanner-Generator
App to generate and scan the QR code. 

This is simple APP to generate and scan the QR code.

1. Add below libraries
    
        implementation 'com.google.zxing:core:3.3.2'
        implementation 'com.journeyapps:zxing-android-embedded:3.6.0@aar'

2. Create file an button to call **scanNow()** function to initiate the Camera for scanning purpose.

        /**this function call the above library to initiate the camera to scan the QRCode**/
       
        private void scanNow() {
        IntentIntegrator integrator = new IntentIntegrator(ScanQRActivity.this);
        integrator.setDesiredBarcodeFormats(IntentIntegrator.QR_CODE);
        integrator.setPrompt("Scanning");
        integrator.setCameraId(0);
        integrator.setBeepEnabled(false);
        integrator.setBarcodeImageEnabled(false);
        integrator.setOrientationLocked(true);
        integrator.setCaptureActivity(CaptureActivityPotrait.class); 
        /**CaptureActivityPotrait.class is java class to customize the scanning layout**/
        integrator.initiateScan();
    }

        /**After scanning process Override the **onActivityResult()** function in class to get the result**/
   
         @Override
        protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        IntentResult result = IntentIntegrator.parseActivityResult(requestCode, resultCode, data);
        if (result != null) {
            if (result.getContents() == null) {
                Toast.makeText(this, "You cancelled the scanning", Toast.LENGTH_LONG).show();
            } else {
                QRResult = result.getContents();/**get the scanning result and store in QRResult variable**/
                txtDesc.setText(QRResult);/**set the String result in the textView**/
            }
        } else {
            super.onActivityResult(requestCode, resultCode, data);
        }
    }

3. Create file an button to call the **generateQRCode()** function to start activity.

        /**userInput = get the text **/
  
        private void generateQrCode() {
        MultiFormatWriter multiFormatWriter = new MultiFormatWriter();
        try {
            BitMatrix bitMatrix = multiFormatWriter.encode(userInput, BarcodeFormat.QR_CODE, 200, 200);
            BarcodeEncoder barcodeEncoder = new BarcodeEncoder();
            bitmap = barcodeEncoder.createBitmap(bitMatrix);
            imgQrCode.setImageBitmap(bitmap);/**set the QRCode image in ImageView**/
        } catch (WriterException e) {
            e.printStackTrace();
        }
    }

4.  Create **CaptureActivityPotrait.class** and just copy paste all the code.
    
        public class CaptureActivityPotrait extends Activity {
        private CaptureManager capture;
        private DecoratedBarcodeView barcodeScannerView;

        @Override
        protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        barcodeScannerView = initializeContent();

        capture = new CaptureManager(this, barcodeScannerView);
        capture.initializeFromIntent(getIntent(), savedInstanceState);
        capture.decode();
        }

        /**
        * Override to use a different layout.
        *
        * @return the DecoratedBarcodeView
        */
         protected DecoratedBarcodeView initializeContent() {
        setContentView(R.layout.activity_qrcode);/**make sure create the XML layout for QR code custom layout**/
        return (DecoratedBarcodeView)findViewById(R.id.zxing_barcode_scanner);
        }

        @Override
        protected void onResume() {
        super.onResume();
        capture.onResume();
        }

        @Override
        protected void onPause() {
        super.onPause();
        capture.onPause();
        }

        @Override
        protected void onDestroy() {
        super.onDestroy();
        capture.onDestroy();
        }

        @Override
        protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        capture.onSaveInstanceState(outState);
        }

        @Override
        public void onRequestPermissionsResult(int requestCode, @NonNull String permissions[], @NonNull int[] grantResults) {
        capture.onRequestPermissionsResult(requestCode, permissions, grantResults);
        }

        @Override
        public boolean onKeyDown(int keyCode, KeyEvent event) {
        return barcodeScannerView.onKeyDown(keyCode, event) || super.onKeyDown(keyCode, event);
        }
        }


5. Create **activity_qrcode** XML layout and jsut copy paste all the code.

         <?xml version="1.0" encoding="UTF-8"?>
    <!--
     Copyright (C) 2008 ZXing authors
     Licensed under the Apache License, Version 2.0 (the "License");
     you may not use this file except in compliance with the License.
    You may obtain a copy of the License at

         http://www.apache.org/licenses/LICENSE-2.0

         Unless required by applicable law or agreed to in writing, software
         distributed under the License is distributed on an "AS IS" BASIS,
         WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
         See the License for the specific language governing permissions and
            limitations under the License.
         -->
         <merge xmlns:android="http://schemas.android.com/apk/res/android"
            xmlns:app="http://schemas.android.com/apk/res-auto">

        <!--
        This Activity is typically full-screen. Therefore we can safely use centerCrop scaling with
         a SurfaceView, without fear of weird artifacts. -->
      
        <RelativeLayout
         android:layout_width="match_parent"
        android:layout_height="match_parent">

        <com.journeyapps.barcodescanner.DecoratedBarcodeView
            android:layout_width="match_parent"
            android:layout_height="match_parent"
            android:id="@+id/zxing_barcode_scanner"
            app:zxing_preview_scaling_strategy="centerCrop"
            app:zxing_use_texture_view="false"/>

        </RelativeLayout>

         </merge>



<img src="app\src\main\res\drawable\main.png" alt="Main page layout"/>

