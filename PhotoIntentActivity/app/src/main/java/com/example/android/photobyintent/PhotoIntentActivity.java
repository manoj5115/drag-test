package com.example.android.photobyintent;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.os.Environment;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import java.io.File;
import java.io.FileOutputStream;
import java.util.Random;

public class PhotoIntentActivity extends Activity {
	private static final int CAMERA_REQUEST = 1888;
	private ImageView imageView;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main);
		this.imageView = (ImageView)this.findViewById(R.id.imageView1);
		Button photoButton = (Button) this.findViewById(R.id.button1);
		photoButton.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				Intent cameraIntent = new Intent(android.provider.MediaStore.ACTION_IMAGE_CAPTURE);
				startActivityForResult(cameraIntent, CAMERA_REQUEST);
			}
		});
	}

	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		if (requestCode == CAMERA_REQUEST && resultCode == RESULT_OK) {
			Bitmap photo = (Bitmap) data.getExtras().get("data");
			imageView.setImageBitmap(photo);
			String root = Environment.getExternalStorageDirectory().toString();
			File myDir = new File(root + "/myPhotos");
			myDir.mkdirs();
			Random generator = new Random();
			int n = 10000;
			n = generator.nextInt(n);
			String fname = "Photo-"+ n +".jpg";
			File file = new File (myDir, fname);
			if (file.exists ()) file.delete ();
			try {
				FileOutputStream out = new FileOutputStream(file);
				photo.compress(Bitmap.CompressFormat.JPEG, 90, out);
				out.flush();
				out.close();

			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}


}