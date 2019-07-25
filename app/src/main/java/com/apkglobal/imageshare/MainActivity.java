package com.apkglobal.imageshare;

import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import java.io.IOException;

public class MainActivity extends AppCompatActivity {
    Button share, pick;
    ImageView imageView;
    //to contain the internal directory address
    Uri uri;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        share = findViewById(R.id.share);
        pick = findViewById(R.id.pick);
        imageView = findViewById(R.id.image);
        //to pick the image from gallery
        pick.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //to get the image from gallery
                Intent pick = new Intent(Intent.ACTION_GET_CONTENT);
                //to define type of content for pick
                pick.setType("image/*");
                //request for image
                startActivityForResult(pick, 1);
            }
        });

        //to share the image to other applications
        share.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //for data sending
                Intent intent=new Intent(Intent.ACTION_SEND);
                //type of data to send
                intent.setType("image/*");
                //to get the data using address
                intent.putExtra(Intent.EXTRA_STREAM, uri);
                //to open the social media app to send the image
                startActivity(Intent.createChooser(intent, "Share Image Via:"));
            }
        });
    }

    //to show the image from gallery after selection
    @Override
    protected void onActivityResult(int requestCode, int resultCode,
                                    @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        //to check the process is ours and user select at least one pic
        if (requestCode == 1 && resultCode == RESULT_OK && data != null) {
            //to pass the data image address to uri
            uri=data.getData();
            //to store the image from uri to bitmap
            try {
                Bitmap bitmap= MediaStore.Images.Media
                        .getBitmap(getContentResolver(), uri);
                //to display the image
                imageView.setImageBitmap(bitmap);
            } catch (IOException e) {
                e.printStackTrace();
            }

        }
    }
}
