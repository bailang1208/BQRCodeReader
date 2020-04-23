package com.bailang.bqrcodescanner.permisions;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.pm.PackageManager;
import android.os.Build;

import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import java.util.ArrayList;

/**
 * Helps request permissions at runtime.
 */
public class PermissionsManager {

    public static final String COARSE_LOCATION_PERMISSION = Manifest.permission.ACCESS_COARSE_LOCATION;
    public static final String FINE_LOCATION_PERMISSION = Manifest.permission.ACCESS_FINE_LOCATION;
    public static final String CAMERA_PERMISSION = Manifest.permission.CAMERA;
    public static final String CALL_PHONE_PERMISSION = Manifest.permission.CALL_PHONE;
    public static final String READ_EXTERNAL_PERMISSION = Manifest.permission.READ_EXTERNAL_STORAGE;
    public static final String WRITE_EXTERNAL_PERMISSION = Manifest.permission.WRITE_EXTERNAL_STORAGE;

    public static int REQUEST_LOCATION_PERMISSIONS_CODE      = 1000;
    public static int REQUEST_CAMERA_PERMISSION_CODE         = 1001;
    public static int REQUEST_CALL_PHONE_PERMISSION_CODE     = 1002;
    public static int REQUEST_RW_EXTERNAL_PERMISSIONS_CODE   = 1003;

    public static boolean isPermissionGranted(Context context, String permission) {
        return ContextCompat.checkSelfPermission(context, permission)
            == PackageManager.PERMISSION_GRANTED;
    }

    public static boolean isCoarseLocationPermissionGranted(Context context) {
        return isPermissionGranted(context, COARSE_LOCATION_PERMISSION);
    }

    public static boolean isFineLocationPermissionGranted(Context context) {
        return isPermissionGranted(context, FINE_LOCATION_PERMISSION);
    }

    public static boolean areLocationPermissionsGranted(Context context) {
        return isCoarseLocationPermissionGranted(context)
            || isFineLocationPermissionGranted(context);
    }

    public static boolean isCameraPermissionGranted(Context context) {
        return isPermissionGranted(context, CAMERA_PERMISSION);
    }

    public static boolean isCallPhonePermissionGranted(Context context) {
        return isPermissionGranted(context, CALL_PHONE_PERMISSION);
    }

    public static boolean areRuntimePermissionsRequired() {
        return android.os.Build.VERSION.SDK_INT >= Build.VERSION_CODES.M;
    }

    public static void requestLocationPermissions(Activity activity) {
        // Request fine location permissions by default
        requestLocationPermissions(activity, true);
    }

    public static void requestLocationPermissions(Activity activity, boolean requestFineLocation) {
        String[] permissions = requestFineLocation
            ? new String[] {FINE_LOCATION_PERMISSION}
            : new String[] {COARSE_LOCATION_PERMISSION};
        requestPermissions(activity, permissions, REQUEST_LOCATION_PERMISSIONS_CODE);
    }

    public static void requestCameraPermission(Activity activity) {
        String[] permissions = new String[] {CAMERA_PERMISSION};
        requestPermissions(activity, permissions, REQUEST_CAMERA_PERMISSION_CODE);
    }

    public static void requestCallPhonePermission(Activity activity) {
        String[] permissions = new String[] {CALL_PHONE_PERMISSION};
        requestPermissions(activity, permissions, REQUEST_CALL_PHONE_PERMISSION_CODE);
    }

    public static void requestPermissions(Activity activity, String[] permissions, int request_code) {
        ArrayList<String> permissionsToExplain = new ArrayList<>();
        // The developer should show an explanation to the user asynchronously
        for (String permission : permissions) {
            if (ActivityCompat.shouldShowRequestPermissionRationale(activity, permission)) {
                permissionsToExplain.add(permission);
            }
        }

        ActivityCompat.requestPermissions(activity, permissions, request_code);
    }

    public static boolean isGranted(int[] grantResults) {
        boolean granted = grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED;

        return granted;
    }
}