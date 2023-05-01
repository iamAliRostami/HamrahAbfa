package com.leon.hamrah_abfa.utils;

import static com.leon.hamrah_abfa.helpers.Constants.MAX_IMAGE_SIZE;
import static com.leon.toast.RTLToast.error;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Bitmap;
import android.os.Environment;

import com.leon.hamrah_abfa.R;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class FileCustomize {
    @SuppressLint({"SimpleDateFormat"})
    public static File createImageFile(Context context) throws IOException {
        // Create an image file name
        String timeStamp = new SimpleDateFormat(context.getString(R.string.save_format_name)).format(new Date());
        String imageFileName = "JPEG_" + timeStamp + "_";
        File storageDir = context.getExternalFilesDir(Environment.DIRECTORY_PICTURES);
        return File.createTempFile(
                imageFileName,  /* prefix */
                ".jpg",         /* suffix */
                storageDir      /* directory */
        );
    }

    public static Bitmap compressBitmap(Context context, Bitmap bitmapOriginal) {
        try {
            ByteArrayOutputStream stream = new ByteArrayOutputStream();
            bitmapOriginal.compress(Bitmap.CompressFormat.JPEG, 100, stream);
            if (stream.toByteArray().length > MAX_IMAGE_SIZE) {
                final int width, height;
                if (bitmapOriginal.getHeight() > bitmapOriginal.getWidth()) {
                    height = Math.min(MAX_IMAGE_SIZE / 100, bitmapOriginal.getHeight());
                    width = bitmapOriginal.getWidth() / (bitmapOriginal.getHeight() / height);
                } else {
                    width = Math.min(MAX_IMAGE_SIZE / 100, bitmapOriginal.getWidth());
                    height = bitmapOriginal.getHeight() / (bitmapOriginal.getWidth() / width);
                }
                bitmapOriginal = Bitmap.createScaledBitmap(bitmapOriginal, width, height, false);
                stream = new ByteArrayOutputStream();
                bitmapOriginal.compress(Bitmap.CompressFormat.JPEG, 80, stream);
            }
            return bitmapOriginal;
        } catch (Exception e) {
            error(context, e.getMessage()).show();
        }
        return null;
    }
}