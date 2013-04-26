package com.example.simplecameraframe;

import java.io.File;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.hardware.Camera;
import android.os.Bundle;
import android.os.Environment;
import android.app.Activity;
import android.view.Menu;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup.LayoutParams;
import android.widget.CheckBox;
import android.widget.FrameLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

public class MainActivity extends Activity {

	private Camera mCamera;
	private CameraPreview camPreview;
	private FrameLayout mainLayout;
	private CheckBox checkBox1;
	private RadioButton badRadioButton;
	private RadioButton goodRadioButton;
	private RadioGroup radioGroup;
	public int selectedId = 1;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		File fileP = new File(Environment.getExternalStorageDirectory().getPath() + "/simplecamerapics/good");
		if (!fileP.exists())
			fileP.mkdirs();

		File fileP2 = new File(Environment.getExternalStorageDirectory().getPath() + "/simplecamerapics/bad");
		if (!fileP2.exists())
			fileP2.mkdirs();

		SurfaceView camView = new SurfaceView(this);
		SurfaceHolder camHolder = camView.getHolder();
		int width = 352; // must set a compatible value, otherwise it gets the
							// default width and height
		int height = 288;

		camPreview = new CameraPreview(width, height);
		camPreview.setParent(this);

		camHolder.addCallback(camPreview);
		camHolder.setType(SurfaceHolder.SURFACE_TYPE_PUSH_BUFFERS);

		mainLayout = (FrameLayout) findViewById(R.id.videoview);
		mainLayout.addView(camView, new LayoutParams(width, height));

		checkBox1 = (CheckBox) findViewById(R.id.checkBox1);
		radioGroup = (RadioGroup) findViewById(R.id.radioBadGood);

		badRadioButton = (RadioButton) findViewById(R.id.radioButton1);
		badRadioButton.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {

				// get selected radio button from radioGroup
				selectedId = 1;

				Toast.makeText(MainActivity.this, selectedId + " is selected", Toast.LENGTH_SHORT).show();

			}

		});
		goodRadioButton = (RadioButton) findViewById(R.id.radioButton2);
		goodRadioButton.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {

				// get selected radio button from radioGroup
				selectedId = 2;

				Toast.makeText(MainActivity.this, selectedId + " is selected", Toast.LENGTH_SHORT).show();

			}

		});
		badRadioButton.performClick();
	}

	public CheckBox getCheckBox() {
		return checkBox1;
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

}
