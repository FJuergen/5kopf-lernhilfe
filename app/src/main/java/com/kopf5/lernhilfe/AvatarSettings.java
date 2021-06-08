package com.kopf5.lernhilfe;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.content.Context;
import android.content.ContextWrapper;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

/*
 * Customization für den Avatar
 */

public class AvatarSettings extends Fragment {

    ImageView img;
    ContextWrapper cw;
    SharedPreferences mySP;

    public AvatarSettings() {
        super(R.layout.activity_avatar_settings);
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    /*
     * Methode der ImageButtons:
     * setzt die Priview auf das Drawable des Buttons
     */
    public void setPreview(View v){
        System.out.println("test");
        Drawable skin = ((ImageButton)v).getDrawable();
        if(skin!=null) {
            img.setImageDrawable(skin);
            saveDrawable(skin);
        }
    }

    /*
     * ruft die Methode "drawableToBitmap(Drawable drawable)" zum Umwandeln des Drawables in eine Bitmap auf
     * ruft die Methode "saveToInternalStorage(Bitmap bitmapImage)" zum Speichern der Bitmap auf
     */
    private void saveDrawable(Drawable d){
        Bitmap bm = drawableToBitmap(d);
        String path = saveToInternalStorage(bm);

        // Path-Speicherung in Sharedpreferences
        SharedPreferences.Editor editor = mySP.edit();
        editor.putString("selectedSkinPath", path);
        editor.commit();
    }


    // Wandelt ein Drawable-Object in ein Bitmap-Object um
    public static Bitmap drawableToBitmap(Drawable drawable) {
        Bitmap bitmap;

        if (drawable instanceof BitmapDrawable) {
            BitmapDrawable bitmapDrawable = (BitmapDrawable) drawable;
            if(bitmapDrawable.getBitmap() != null) {
                return bitmapDrawable.getBitmap();
            }
        }

        if(drawable.getIntrinsicWidth() <= 0 || drawable.getIntrinsicHeight() <= 0) {
            bitmap = Bitmap.createBitmap(1, 1, Bitmap.Config.ARGB_8888); // Single color bitmap will be created of 1x1 pixel
        } else {
            bitmap = Bitmap.createBitmap(drawable.getIntrinsicWidth(), drawable.getIntrinsicHeight(), Bitmap.Config.ARGB_8888);
        }

        Canvas canvas = new Canvas(bitmap);
        drawable.setBounds(0, 0, canvas.getWidth(), canvas.getHeight());
        drawable.draw(canvas);
        return bitmap;
    }

    /*
     * speichert die Bitmap bei "/imageDir"
     * return: directory als String
     */
    private String saveToInternalStorage(Bitmap bitmapImage){
        // path: /data/user/0/"APLICATIONNAME"/app_imageDir/chibiAvatar.jpg in internal storage
        File directory = cw.getDir("imageDir", Context.MODE_PRIVATE);
        // Create imageDir
        File mypath=new File(directory,"chibiAvatar.jpg");

        FileOutputStream fos = null;
        try {
            fos = new FileOutputStream(mypath);
            // Use the compress method on the BitMap object to write image to the OutputStream
            bitmapImage.compress(Bitmap.CompressFormat.PNG, 100, fos);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                fos.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return directory.getAbsolutePath();
    }

    /*
     * laedt Bild vom vorher erhaltenen Path (Typ String)
     */
    private void loadImageFromStorage(String path)
    {
        try {
            File f=new File(path, "chibiAvatar.jpg");
            Bitmap b = BitmapFactory.decodeStream(new FileInputStream(f));
            img.setImageBitmap(b);
        }
        catch (FileNotFoundException e)
        {
            e.printStackTrace();
            //falls Datei nicht vorhanden, setzte den skin auf den Skin1
            img.setImageDrawable(getResources().getDrawable(R.drawable.skin1));
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.activity_avatar_settings, container, false);
        img = view.findViewById(R.id.imageViewPreview);
        ImageButton avatar1 = view.findViewById(R.id.imageButtonSkin1);
        avatar1.setOnClickListener(this::setPreview);
        ImageButton avatar2 = view.findViewById(R.id.imageButtonSkin2);
        avatar2.setOnClickListener(this::setPreview);
        ImageButton avatar3 = view.findViewById(R.id.imageButtonSkin3);
        avatar3.setOnClickListener(this::setPreview);
        ImageButton avatar4 = view.findViewById(R.id.imageButtonSkin4);
        avatar4.setOnClickListener(this::setPreview);
        ImageButton avatar5 = view.findViewById(R.id.imageButtonSkin5);
        avatar5.setOnClickListener(this::setPreview);
        ImageButton avatar6 = view.findViewById(R.id.imageButtonSkin6);
        avatar6.setOnClickListener(this::setPreview);

        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable  Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mySP = getActivity().getSharedPreferences("UserInfo",0);
        cw = new ContextWrapper(getActivity().getBaseContext());
        loadImageFromStorage(mySP.getString("selectedSkinPath",""));
    }
}