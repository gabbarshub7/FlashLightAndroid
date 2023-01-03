package com.example.cameraandtorch;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.hardware.camera2.CameraAccessException;
import android.hardware.camera2.CameraManager;
import android.os.Build;
import android.os.Bundle;

import android.widget.Toast;

import com.example.cameraandtorch.databinding.ActivityMainBinding;

public class MainActivity extends AppCompatActivity {
    ActivityMainBinding binding;
    boolean hasCameraFlash = true;
    static boolean flashOn = false;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        binding.btnTorch.setOnClickListener(v -> {
            if (hasCameraFlash) {
                if (flashOn) {
                    flashOn = false;
                    // btn ka drawable off wala
                    try {
                        flashlightOff();
                    } catch (CameraAccessException e) {
                        e.printStackTrace();
                    }
                } else {
                    flashOn = true;
                    try {
                        flashlightOn();
                    } catch (CameraAccessException e) {
                        e.printStackTrace();
                    }
                }
            } else {
                Toast.makeText(MainActivity.this, "no available flashlight found on this device", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void flashlightOn() throws CameraAccessException {
        CameraManager manager = (CameraManager) getSystemService(Context.CAMERA_SERVICE);
        String cameraId = manager.getCameraIdList()[0];
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            manager.setTorchMode(cameraId, true);
        }
        Toast.makeText(this, "FlashLight is ON", Toast.LENGTH_SHORT).show();
    }

    private void flashlightOff() throws CameraAccessException {
        CameraManager manager = (CameraManager) getSystemService(Context.CAMERA_SERVICE);
        String cameraId = manager.getCameraIdList()[0];
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            manager.setTorchMode(cameraId, false);
        }
        Toast.makeText(this, "FlashLight is OFF", Toast.LENGTH_SHORT).show();
    }
}
