package com.example.twitter_clone;


import android.Manifest;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.app.ActivityCompat;
import androidx.fragment.app.Fragment;

import android.provider.MediaStore;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.parse.ParseException;
import com.parse.ParseFile;
import com.parse.ParseObject;
import com.parse.ParseUser;
import com.parse.SaveCallback;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.time.Instant;


/**
 * A simple {@link Fragment} subclass.
 */
public class SharePicture extends Fragment implements View.OnClickListener  {

private ImageView imgshare;
private Button btnshare;
private TextView edtdec;
Bitmap receivedimgbitmap;
    public SharePicture() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view= inflater.inflate(R.layout.fragment_share_picture, container, false);
       imgshare= view.findViewById(R.id.imgshare);
       btnshare=view.findViewById(R.id.btnshareimg);
       edtdec=view.findViewById(R.id.edtdec);

        imgshare.setOnClickListener(SharePicture.this);
        btnshare.setOnClickListener(SharePicture.this);
       return view;
    }

    @Override
    public void onClick(View view) {
    switch (view.getId()){
        case R.id.imgshare:
//here i requested a permission for the external image storage callback method below
            if (android.os.Build.VERSION.SDK_INT >=23 && ActivityCompat.checkSelfPermission(getContext(),
                    Manifest.permission.READ_EXTERNAL_STORAGE) !=PackageManager.PERMISSION_GRANTED){
                requestPermissions(new String[]{Manifest.permission.READ_EXTERNAL_STORAGE},1000);

            }else{
             getchoseenimg();
            }
            break;
        case R.id.btnshareimg:
            //here we are compressing the image and uploading it on the server
        if (receivedimgbitmap!=null){

                ByteArrayOutputStream byteArrayoutputStream=new ByteArrayOutputStream();
                receivedimgbitmap.compress(Bitmap.CompressFormat.PNG,100,byteArrayoutputStream);
                byte[]bytes=byteArrayoutputStream.toByteArray();
                ParseFile parseFile=new ParseFile("img.png",bytes );
                ParseObject parseObject =new ParseObject("photos");
                parseObject.put("picture",parseFile);
                parseObject.put("description",edtdec.getText().toString());
                parseObject.put("username", ParseUser.getCurrentUser().getUsername());
                final ProgressDialog progressDialog=new ProgressDialog(getContext());
                progressDialog.setMessage("loading");
                progressDialog.show();
                parseObject.saveInBackground(new SaveCallback() {
                    @Override
                    public void done(ParseException e) {
                        if (e==null){
                            Toast.makeText(getContext(),"Done",Toast.LENGTH_SHORT).show();
                        }
                        progressDialog.dismiss();
                    }
                });
            }




        else{
            Toast.makeText(getContext(),"img plz",Toast.LENGTH_SHORT).show();
        }


            break;
    }
    }
    //choosing an image going to image picker callback method below
    private void getchoseenimg() {
       Intent intent=new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
       startActivityForResult(intent,2000);

    }
//callback method for permission request for external image request
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == 1000){

            if (grantResults.length>0 && grantResults[0]==PackageManager.PERMISSION_GRANTED){

                getchoseenimg();
            }
        }
    }

    //image picker call back
    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode==2000){

            if (resultCode== Activity.RESULT_OK){

                try {
                    Uri selectedimg=data.getData();
                    String[]filepathcolum={MediaStore.Images.Media.DATA};
                    Cursor cursor=getActivity().getContentResolver().query(selectedimg, filepathcolum,null,null,null);
                    cursor.moveToFirst();
                    int columindex=cursor.getColumnIndex(filepathcolum[0]);
                    String picturepath=cursor.getString(columindex);
                    cursor.close();
                    receivedimgbitmap= BitmapFactory.decodeFile(picturepath);
                    imgshare.setImageBitmap(receivedimgbitmap);



                }catch (Exception e){
                    e.printStackTrace();
                }
            }
        }

    }
}
