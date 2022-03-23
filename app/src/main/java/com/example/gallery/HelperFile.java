package com.example.gallery;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.widget.Toast;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;

import static androidx.core.app.ActivityCompat.startActivityForResult;

public class HelperFile {

    public static void writeFileToInternalStorage(Context context, Bitmap bitmap, String filename) {
        SharedPreferences sp = context.getSharedPreferences("info", 0);
        int counter = sp.getInt("counter", 1);
        try {
            FileOutputStream os = ((Activity) context).openFileOutput(filename + counter, Context.MODE_PRIVATE);
            bitmap.compress(Bitmap.CompressFormat.PNG, 100, os);
            counter++;
            SharedPreferences.Editor editor = sp.edit();
            editor.putInt("counter", counter);
            editor.commit();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

    public static Bitmap readFileFromInternalStorage(Context context, String filename) {
        Bitmap b = null;
        try {
            InputStream in = ((Activity) context).openFileInput(filename);
            b = BitmapFactory.decodeStream(in);
            in.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return b;
    }

    public static ArrayList<Bitmap> getAllFils(Context context) {
        File mydir = context.getFilesDir();
        File lister = mydir.getAbsoluteFile();
        ArrayList<Bitmap> arraylist = new ArrayList<Bitmap>();
        for (String list : lister.list()) {
            Toast.makeText(context, list, Toast.LENGTH_LONG).show();
            Bitmap b = readFileFromInternalStorage(context, list);
            arraylist.add(b);
        }
        return arraylist;
    }


    /*public static void deletePic(String fileName){
        File fdelete = new File(context.getFilesDir().getAbsolutePath());
        if (fdelete.exists()) {
            if (fdelete.delete()) {
                System.out.println("file Deleted :" + context.getFilesDir().getAbsolutePath());
            } else {
                System.out.println("file not Deleted :" + context.getFilesDir().getAbsolutePath());
            }
        }*/

    }

}
