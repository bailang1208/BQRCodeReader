package com.bailang.bqrcodereader.utils;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;

import com.bailang.bqrcodereader.R;
import com.bailang.bqrcodereader.ScanListener;
import com.bailang.bqrcodereader.camera.CameraManager;
import com.bailang.bqrcodereader.decode.DecodeThread;
import com.google.zxing.Result;

/**
 * This class handles all the messaging which comprises the state machine for
 * capture.
 */
public class CaptureFragmentHandler extends Handler {

	private final ScanListener listener;
	private final DecodeThread decodeThread;
	private final CameraManager cameraManager;
	private State state;

	private enum State {
		PREVIEW, SUCCESS, DONE
	}

	public CaptureFragmentHandler(ScanListener listener, CameraManager cameraManager, int decodeMode) {
		this.listener = listener;
		decodeThread = new DecodeThread(listener, decodeMode);
		decodeThread.start();
		state = State.SUCCESS;

		// Start ourselves capturing previews and decoding.
		this.cameraManager = cameraManager;
		cameraManager.startPreview();
		restartPreviewAndDecode();
	}

	@Override
	public void handleMessage(Message message) {
		Bundle bundle = message.getData();
		if (message.what == R.id.restart_preview) {
			restartPreviewAndDecode();
		}
		else if(message.what == R.id.decode_succeeded) {
			state = State.SUCCESS;

			listener.handleDecode((Result) message.obj, bundle);
		}
		else if(message.what == R.id.decode_failed) {
			// We're decoding as fast as possible, so when one decode fails,
			// start another.
			state = State.PREVIEW;
			cameraManager.requestPreviewFrame(decodeThread.getHandler(), R.id.decode);
		}
		else if(message.what == R.id.return_scan_result) {
			listener.handleDecode((Result) message.obj, bundle);
		}
	}

	public void quitSynchronously() {
		state = State.DONE;
		cameraManager.stopPreview();
		Message quit = Message.obtain(decodeThread.getHandler(), R.id.quit);
		quit.sendToTarget();
		try {
			// Wait at most half a second; should be enough time, and onPause()
			// will timeout quickly
			decodeThread.join(500L);
		} catch (InterruptedException e) {
			// continue
		}

		 //Be absolutely sure we don't send any queued up messages
		removeMessages(R.id.decode_succeeded);
		removeMessages(R.id.decode_failed);
	}

	private void restartPreviewAndDecode() {
		if (state == State.SUCCESS) {
			state = State.PREVIEW;
			cameraManager.requestPreviewFrame(decodeThread.getHandler(), R.id.decode);
		}
	}

}
