package com.bailang.bqrcodereader;

import android.graphics.Rect;
import android.os.Bundle;
import android.os.Handler;

import com.bailang.bqrcodereader.camera.CameraManager;
import com.google.zxing.Result;

public interface ScanListener {
    void handleDecode(Result rawResult, Bundle bundle);
    Handler getHandler();
    CameraManager getCameraManager();
    Rect getCropRect();
}
