package com.example.simplecameraframe;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

import android.graphics.ImageFormat;
import android.graphics.Rect;
import android.graphics.YuvImage;
import android.hardware.Camera;
import android.hardware.Camera.Parameters;
import android.os.Environment;
import android.util.Base64;
import android.util.Log;
import android.view.SurfaceHolder;

public class CameraPreview implements SurfaceHolder.Callback, Camera.PreviewCallback {
	int PreviewSizeWidth;
	int PreviewSizeHeight;
	SurfaceHolder mSurfHolder;
	Camera mCamera;
	private int frame;
	private int modCount = 0;
	private MainActivity mainActivity;

	public CameraPreview(int PreviewlayoutWidth, int PreviewlayoutHeight) {
		PreviewSizeWidth = PreviewlayoutWidth;
		PreviewSizeHeight = PreviewlayoutHeight;
		frame = 0;
	}

	@Override
	public void onPreviewFrame(byte[] data, Camera camera) {
		if (modCount++ % 3 == 0) {
			if (mainActivity.getCheckBox().isChecked()) {
				modCount = 1;
				Parameters p = camera.getParameters();
				int width = p.getPreviewSize().width;
				int height = p.getPreviewSize().height;

				ByteArrayOutputStream outstr = new ByteArrayOutputStream();
				Rect rect = new Rect(0, 0, width, height);
				YuvImage yuvimage = new YuvImage(data, ImageFormat.NV21, width, height, null);
				yuvimage.compressToJpeg(rect, 80, outstr); // outstr contains
															// image
															// in
															// jpeg
				String temp = "good";
				if (mainActivity.selectedId == 1)
					temp = "bad";


				File file = new File(Environment.getExternalStorageDirectory().getPath() + "/simplecamerapics/" + temp + "/" + System.currentTimeMillis()
						+ ".jpg");
				FileOutputStream filecon;
				try {
					file.createNewFile();
					filecon = new FileOutputStream(file);
					yuvimage.compressToJpeg(new Rect(0, 0, yuvimage.getWidth(), yuvimage.getHeight()), 99, filecon);
				} catch (FileNotFoundException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}

				Log.e("km", frame++ + " " + mainActivity.selectedId);
			}
		}
	}

	@Override
	public void surfaceChanged(SurfaceHolder arg0, int arg1, int arg2, int arg3) {
		Parameters parameters;
		mSurfHolder = arg0;

		parameters = mCamera.getParameters();
		parameters.setPreviewSize(PreviewSizeWidth, PreviewSizeHeight);
		mCamera.setParameters(parameters);
		mCamera.startPreview();
	}

	public void surfaceCreated(SurfaceHolder arg0) {
		mCamera = Camera.open();
		try {
			// If did not set the SurfaceHolder, the preview area will be black.
			mCamera.setPreviewDisplay(arg0);
			mCamera.setPreviewCallback(this);
			Parameters p = mCamera.getParameters();
			p.setPreviewSize(PreviewSizeWidth, PreviewSizeHeight);

			mCamera.setParameters(p);
		} catch (IOException e) {
			mCamera.release();
			mCamera = null;
		}
	}

	@Override
	public void surfaceDestroyed(SurfaceHolder arg0) {
		mCamera.setPreviewCallback(null);
		mCamera.stopPreview();
		mCamera.release();
		mCamera = null;
	}

	public void setParent(MainActivity mainActivity) {
		this.mainActivity = mainActivity;
	}
}