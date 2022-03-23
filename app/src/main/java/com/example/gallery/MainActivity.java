package com.example.gallery;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;

import java.io.File;
import java.util.ArrayList;

import static androidx.core.app.ActivityCompat.startActivityForResult;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private static final int PICK_IMAGE = RESULT_OK;
    Button btnAdd, btnLoad;
    LinearLayout gallery;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        btnAdd = (Button) findViewById(R.id.btnAdd);
        btnAdd.setOnClickListener(this);
        gallery = (LinearLayout) findViewById(R.id.gallery);
        loadPicture();
    }

    @Override
    public void onClick(View v) {
        if (v == btnAdd) {
            Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
            startActivityForResult(intent, 0);
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode == RESULT_OK && requestCode == 0) {
            Bitmap b = (Bitmap) data.getExtras().get("data");
            HelperFile.writeFileToInternalStorage(this, b, "pic");
            loadPicture();
        }
        if (requestCode == PICK_IMAGE) {
        }
    }

    public void loadPicture() {
        gallery.removeAllViews();
        ArrayList<Bitmap> arrayList = HelperFile.getAllFils(this);
        for (int i = 0; i < arrayList.size(); i++) {
            ImageView iv = new ImageView(this);
            LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(1000, 1000);
            layoutParams.setMargins(10, 10, 0, 0);
            iv.setLayoutParams(layoutParams);
            iv.setImageBitmap(arrayList.get(i));
            gallery.addView(iv);
        }
    }

    @Override
    public void onPointerCaptureChanged(boolean hasCapture) {
        
    }

    public void choosePic(){
        int PICK_IMAGE = 1;
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(Intent.createChooser(intent, "Select Picture"), PICK_IMAGE);
    }


}
