package com.bailang.bqrcodescanner;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.bailang.bqrcodescanner.permisions.PermissionsManager;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button btnScanWithActivity = findViewById(R.id.btn_scan_with_activity);
        Button btnScanWithFragment = findViewById(R.id.btn_scan_with_fragment);

        btnScanWithActivity.setOnClickListener(this);
        btnScanWithFragment.setOnClickListener(this);

        enablePermissions();
    }

    @Override
    public void onClick(View v) {
        if(v.getId() == R.id.btn_scan_with_activity) {
            onClickBtnScanWithActivity();
        }
        else if(v.getId() == R.id.btn_scan_with_fragment) {
            onClickBtnScanWithFragment();
        }
    }

    private void onClickBtnScanWithActivity() {
        Intent intent = new Intent(this, ScanActivity.class);
        startActivity(intent);
        finish();
    }

    private void onClickBtnScanWithFragment() {
        Intent intent = new Intent(this, ScanFragmentActivity.class);
        startActivity(intent);
        finish();
    }

    public void enablePermissions() {
        if(!PermissionsManager.isCameraPermissionGranted(this)) {
            PermissionsManager.requestCameraPermission(this);
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        boolean granted = PermissionsManager.isGranted(grantResults);
        if(granted) {
            if(requestCode == PermissionsManager.REQUEST_CAMERA_PERMISSION_CODE) {
                enablePermissions();
            }
        }
    }
}
