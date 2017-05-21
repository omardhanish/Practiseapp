package com.omardhanishmemecreatorcoder786.omardhanish90.practiseapp;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.Nullable;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.util.Base64;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.io.ByteArrayOutputStream;
import java.io.IOException;

import static android.app.Activity.RESULT_OK;
import static android.content.Context.MODE_PRIVATE;

/**
 * Created by omar on 20-05-2017.
 */

public class ProfileActivity extends Fragment {

    private int PICK_IMAGE_REQUEST = 1;

    ImageView profile,profile1;
    EditText name , email;
    Button savebutton;
    private SharedPreferences sharedpreferences;
    private Bitmap bitmap;

    public static final String MyPREFERENCES = "MyPrefs" ;
    public static final String Name = "nameKey";
    public static final String Email = "emailKey";
    public static final String To = "toNumber";
    public static final String ToName = "toName";

    public ProfileActivity() {
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.profile_set_up, container, false);

        profile = (ImageView) rootView.findViewById(R.id.profileimage);
        name = (EditText) rootView.findViewById(R.id.yourname);
        email = (EditText) rootView.findViewById(R.id.youremail);
        savebutton = (Button) rootView.findViewById(R.id.buttonsave);

        sharedpreferences = getActivity().getSharedPreferences(MyPREFERENCES, MODE_PRIVATE);

        savebutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String n  = name.getText().toString();
                String e  = email.getText().toString();

                SharedPreferences.Editor editor = sharedpreferences.edit();

                editor.putString(Name, n);
                editor.putString(Email, e);
                editor.apply();
                Toast.makeText(getContext(),"saved successfully", Toast.LENGTH_SHORT).show();

                Intent i = new Intent(getContext(),MainActivity.class);
                startActivity(i);
            }
        });

        profile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setType("image/*");
                intent.setAction(Intent.ACTION_GET_CONTENT);
                startActivityForResult(Intent.createChooser(intent, "Select Picture"), PICK_IMAGE_REQUEST);
            }
        });




//        String namevalue = sharedpreferences.getString(Name, "your name");
//        String emailvalue = sharedpreferences.getString(Email, "youremail@gmail.com");
//
//        name1.setText(namevalue);
//        email.setText(emailvalue);

        return rootView;
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == PICK_IMAGE_REQUEST && resultCode == RESULT_OK && data != null && data.getData() != null) {

            Uri uri = data.getData();
            try {
                bitmap = MediaStore.Images.Media.getBitmap(getActivity().getContentResolver(), uri);
                profile.setImageBitmap(bitmap);
            } catch (IOException e) {
                e.printStackTrace();
            }
            sharedPreferences();
        }
    }

    private void sharedPreferences()
    {
        SharedPreferences shared = getActivity().getSharedPreferences("MyPrefs", MODE_PRIVATE);
        SharedPreferences.Editor editor = shared.edit();
        editor.putString("PRODUCT_PHOTO", encodeTobase64(bitmap));
        editor.apply();
    }


    public static String encodeTobase64(Bitmap image) {
        Bitmap bitmap_image = image;
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        bitmap_image.compress(Bitmap.CompressFormat.PNG, 100, baos);
        byte[] b = baos.toByteArray();
        String imageEncoded = Base64.encodeToString(b, Base64.DEFAULT);

        return imageEncoded;
    }

}
