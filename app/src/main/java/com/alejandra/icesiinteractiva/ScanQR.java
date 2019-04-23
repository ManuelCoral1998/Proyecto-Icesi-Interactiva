package com.alejandra.icesiinteractiva;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.util.SparseArray;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

import com.alejandra.icesiinteractiva.DB.DBHandler;
import com.alejandra.icesiinteractiva.model.Pregunta;
import com.google.android.gms.vision.CameraSource;
import com.google.android.gms.vision.Detector;
import com.google.android.gms.vision.barcode.Barcode;
import com.google.android.gms.vision.barcode.BarcodeDetector;

import java.io.IOException;
import java.util.ArrayList;

public class ScanQR extends AppCompatActivity implements DBHandler.OnFinishQuestion {


    private SurfaceView camara;
    private BarcodeDetector barcodeDetector;
    private CameraSource cameraSource;
    DBHandler dbHandler;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_scan_qr);

        camara = findViewById(R.id.camarePreview);

        dbHandler = DBHandler.getInstance();
        dbHandler.setOnFinishQuestion(this);

        barcodeDetector = new BarcodeDetector.Builder(this)
        .setBarcodeFormats(Barcode.QR_CODE)
        .build();

        cameraSource = new CameraSource.Builder(this, barcodeDetector)
                .setRequestedPreviewSize(640, 640)
                .build();

        camara.getHolder().addCallback(new SurfaceHolder.Callback() {
            @SuppressLint("MissingPermission")
            @Override
            public void surfaceCreated(SurfaceHolder holder) {

                try {
                    cameraSource.start(camara.getHolder());
                } catch (IOException e) {
                    e.printStackTrace();
                }

            }

            @Override
            public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {

            }

            @Override
            public void surfaceDestroyed(SurfaceHolder holder) {

            }
        });

        barcodeDetector.setProcessor(new Detector.Processor<Barcode>() {
            @Override
            public void release() {

            }

            @Override
            public void receiveDetections(Detector.Detections<Barcode> detections) {
                SparseArray<Barcode> grcore = detections.getDetectedItems();

                if (grcore.size() > 0) {
                    Log.d("QR", grcore.valueAt(0).displayValue);

                    String proyectoQr = grcore.valueAt(0).displayValue;

                    dbHandler.traerPreguntas(proyectoQr);
                    barcodeDetector.release();
                    setResult(70);
                    finish();
                    cameraSource.stop();

                }
            }
        });
    }

    @Override
    public void onFinishQuestion(ArrayList<Pregunta> preguntas) {
        Intent intent = new Intent(ScanQR.this, Question.class);
        intent.putExtra("Preguntas", preguntas);
        startActivity(intent);
        //barcodeDetector.release();
        //finish();
        //cameraSource.stop();
    }

}
