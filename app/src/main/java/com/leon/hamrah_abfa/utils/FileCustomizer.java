package com.leon.hamrah_abfa.utils;

import static com.leon.hamrah_abfa.helpers.Constants.MAX_IMAGE_SIZE;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import androidx.exifinterface.media.ExifInterface;
import android.os.Environment;

import com.leon.hamrah_abfa.R;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Random;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;

public class FileCustomizer {
    @SuppressLint({"SimpleDateFormat"})
    public static File createImageFile(Context context) throws IOException {
        final String timeStamp = new SimpleDateFormat(context.getString(R.string.save_format_name)).format(new Date());
        final String imageFileName = "JPEG_" + timeStamp + "_";
        final File storageDir = context.getExternalFilesDir(Environment.DIRECTORY_PICTURES);
        return File.createTempFile(
                imageFileName,  /* prefix */
                ".jpg",         /* suffix */
                storageDir      /* directory */
        );
    }

    public static Bitmap prepareImage(File fileImage) throws IOException {
        return compressBitmap(recognizeImageAngle(fileImage.getAbsolutePath()));
    }

    private static Bitmap compressBitmap(Bitmap bitmap) {
        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, stream);
        if (stream.toByteArray().length > MAX_IMAGE_SIZE) {
            final int width, height;
            if (bitmap.getHeight() > bitmap.getWidth()) {
                height = Math.min(MAX_IMAGE_SIZE / 100, bitmap.getHeight());
                width = bitmap.getWidth() / (bitmap.getHeight() / height);
            } else {
                width = Math.min(MAX_IMAGE_SIZE / 100, bitmap.getWidth());
                height = bitmap.getHeight() / (bitmap.getWidth() / width);
            }
            bitmap = Bitmap.createScaledBitmap(bitmap, width, height, false);
            stream = new ByteArrayOutputStream();
            bitmap.compress(Bitmap.CompressFormat.JPEG, 80, stream);
        }
        return bitmap;
    }

    private static Bitmap recognizeImageAngle(String path) throws IOException {
        final ExifInterface ei = new ExifInterface(path);
        switch (ei.getAttributeInt(ExifInterface.TAG_ORIENTATION, ExifInterface.ORIENTATION_UNDEFINED)) {
            case ExifInterface.ORIENTATION_ROTATE_90:
                return rotateImage(BitmapFactory.decodeFile(path, new BitmapFactory.Options()), 90);
            case ExifInterface.ORIENTATION_ROTATE_180:
                return rotateImage(BitmapFactory.decodeFile(path, new BitmapFactory.Options()), 180);
            case ExifInterface.ORIENTATION_ROTATE_270:
                return rotateImage(BitmapFactory.decodeFile(path, new BitmapFactory.Options()), 270);
            case ExifInterface.ORIENTATION_NORMAL:
            default:
                return BitmapFactory.decodeFile(path, new BitmapFactory.Options());
        }
    }

    private static Bitmap rotateImage(Bitmap bitmap, float angle) {
        final Matrix matrix = new Matrix();
        matrix.postRotate(angle);
        return Bitmap.createBitmap(bitmap, 0, 0, bitmap.getWidth(), bitmap.getHeight(), matrix, true);
    }

    @SuppressLint("SimpleDateFormat")
    public static MultipartBody.Part bitmapToFile(Bitmap bitmap, Context context) {
        final String timeStamp = (new SimpleDateFormat(context.getString(R.string.save_format_name))).format(new Date());
        final String fileNameToSave = "JPEG_" + new Random().nextInt() + "_" + timeStamp + ".jpg";
        final File f = new File(context.getCacheDir(), fileNameToSave);
        try {
            if (!f.createNewFile()) return null;
        } catch (IOException e) {
            e.printStackTrace();
        }
        try {
            final byte[] bitmapData = compressBitmapToByte(bitmap);
            final FileOutputStream fos = new FileOutputStream(f);
            fos.write(bitmapData);
            fos.flush();
            fos.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        RequestBody requestBody = RequestBody.create(f, MediaType.parse("image/jpeg"));
        return MultipartBody.Part.createFormData("File", f.getName(), requestBody);
    }

    public static byte[] compressBitmapToByte(Bitmap bitmap) {
        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, stream);
        return stream.toByteArray();
    }
}
