package com.example.permissiondemo;

import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContract;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import java.sql.Array;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class MainActivity extends AppCompatActivity {
    //Activity Result APIs
    ActivityResultLauncher<String[]> mPermissionResultLauncher;
    private boolean readPermission = false;
    private boolean locationPermission = false;
    private boolean recordPermission = false;
    private boolean cameraPermission = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Button reqButton = findViewById(R.id.btnRequestPermission);

        mPermissionResultLauncher = registerForActivityResult(new ActivityResultContracts.RequestMultiplePermissions()
                , new ActivityResultCallback<Map<String, Boolean>>() {
                    @Override
                    public void onActivityResult(Map<String, Boolean> result) {
                        if(result.get(Manifest.permission.READ_EXTERNAL_STORAGE) != null)
                            readPermission = result.get(Manifest.permission.READ_EXTERNAL_STORAGE);
                        if(result.get(Manifest.permission.RECORD_AUDIO) != null)
                            recordPermission = result.get(Manifest.permission.RECORD_AUDIO);
                        if(result.get(Manifest.permission.ACCESS_FINE_LOCATION) != null)
                            locationPermission = result.get(Manifest.permission.ACCESS_FINE_LOCATION);
                        if(result.get(Manifest.permission.CAMERA) != null)
                            cameraPermission = result.get(Manifest.permission.CAMERA);
                    }
                }
        );

        reqButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                requestPermission();
            }
        });

    }

    private int EXTERNAL_STORAGE_PERMISSION_CODE = 1;// k   quan trọng, ta có tể set tùy ý 1 số bất kì

    private void requestPermission(){
        // ContextCompat.checkSelfPermission hàm kiểm tra liệu user đã cấp quyền cho app chưa
    /*    if(ContextCompat.checkSelfPermission(this,Manifest.permission.READ_EXTERNAL_STORAGE)
                == PackageManager.PERMISSION_GRANTED){
            Toast.makeText(this, "thank you guys, u have already granted the permission. Enjoy the application!!", Toast.LENGTH_LONG).show();
        }else if ( ActivityCompat.shouldShowRequestPermissionRationale(this,Manifest.permission.READ_EXTERNAL_STORAGE)) {
            Toast.makeText(this, "I need u to accept this, please. Check your permission in app setting!", Toast.LENGTH_LONG).show();
        }
        else{
            Toast.makeText(this, "hello u guys for the very FIRST TIME !!!!", Toast.LENGTH_LONG).show();
            ActivityCompat.requestPermissions(this,
                    new String[] { Manifest.permission.READ_EXTERNAL_STORAGE}, EXTERNAL_STORAGE_PERMISSION_CODE);
        };*/

        readPermission = getApplicationContext().checkSelfPermission(this,Manifest.permission.READ_EXTERNAL_STORAGE)
                == PackageManager.PERMISSION_GRANTED);
        readPermission = ContextCompat.checkSelfPermission(this,Manifest.permission.READ_EXTERNAL_STORAGE)
                == PackageManager.PERMISSION_GRANTED;
        locationPermission = ContextCompat.checkSelfPermission(this,Manifest.permission.ACCESS_FINE_LOCATION)
                == PackageManager.PERMISSION_GRANTED;
        readPermission = ContextCompat.checkSelfPermission(this,Manifest.permission.RECORD_AUDIO)
                == PackageManager.PERMISSION_GRANTED;
        cameraPermission = ContextCompat.checkSelfPermission(this,Manifest.permission.CAMERA)
                == PackageManager.PERMISSION_GRANTED;

        List<String> permissionRequest = new ArrayList<String>();

        if(!readPermission){
            permissionRequest.add(Manifest.permission.READ_EXTERNAL_STORAGE);
        }
        if(!locationPermission){
            permissionRequest.add(Manifest.permission.ACCESS_FINE_LOCATION);
        }
        if(!recordPermission){
            permissionRequest.add(Manifest.permission.RECORD_AUDIO);
        }
        if(!cameraPermission){
            permissionRequest.add(Manifest.permission.CAMERA);
        }

        if(!permissionRequest.isEmpty()){
            mPermissionResultLauncher.launch(permissionRequest.toArray(new String[0]));
        }






    }

}