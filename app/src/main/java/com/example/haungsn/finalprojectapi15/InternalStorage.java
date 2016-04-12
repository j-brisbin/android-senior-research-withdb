package com.example.haungsn.finalprojectapi15;

/**
 * Created by Nathan on 4/5/2016.
 */
import android.content.Context;
import android.content.ContextWrapper;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.widget.ImageView;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.UnsupportedEncodingException;


public class InternalStorage
{

    public static String writeIMG(Context context, Bitmap bitmapImage,String imgDirectory,String filename_with_ext) throws IOException{
        ContextWrapper cw = new ContextWrapper(context);
        // path to /data/data/yourapp/app_data/imageDir
        File directory = cw.getDir(imgDirectory, Context.MODE_PRIVATE);
        // Create imageDir
        File mypath=new File(directory,filename_with_ext);

        FileOutputStream fos = null;

        fos = new FileOutputStream(mypath);

        int i = filename_with_ext.lastIndexOf('.');
        String ext = "";
        if (i > 0) {
            ext = filename_with_ext.substring(i+1);
        }

        // Use the compress method on the BitMap object to write image to the OutputStream
        if(ext.toLowerCase().equals("png")){
            bitmapImage.compress(Bitmap.CompressFormat.PNG, 100, fos);
        }
        else if(ext.toLowerCase().equals("jpg")||ext.toLowerCase().equals("jpeg")){
            bitmapImage.compress(Bitmap.CompressFormat.JPEG, 100, fos);
        }
        else{
            throw new IOException("Unsupported format");
        }
        fos.close();
        return directory.getAbsolutePath();
    }

    public static Bitmap readImage(Context context,String imageDirectory,String imagename) throws FileNotFoundException
    {
        ContextWrapper cw = new ContextWrapper(context);
        // path to /data/data/yourapp/app_data/imageDir
        File directory = cw.getDir(imageDirectory, Context.MODE_PRIVATE);
        // Create imageDir
        File mypath=new File(directory,imagename);
        Bitmap b = BitmapFactory.decodeStream(new FileInputStream(mypath));
        return b;

    }

    public static String fileListAsString(Context context, String subDir,String listSeparator)
    {
        ContextWrapper cw = new ContextWrapper(context);
        // path to /data/data/yourapp/app_data/imageDir
        File directory = cw.getDir(subDir, Context.MODE_PRIVATE);

        String[] list = directory.list();
        String s = "";
        for(int i=0; i < list.length;i++)
        {
            s +=  list[i];
            if(i < list.length-1)
            {
                s+= listSeparator;
            }
        }
        return s;
    }
}
