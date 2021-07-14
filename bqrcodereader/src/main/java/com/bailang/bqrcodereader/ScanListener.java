package com.bailang.bqrcodereader;

import android.graphics.Rect;
import android.os.Handler;

import com.bailang.bqrcodereader.camera.CameraManager;

public interface ScanListener {
    void handleDecode(String decode);
    Handler getHandler();
    CameraManager getCameraManager();
    Rect getCropRect();
}
