package com.example.twitter_clone;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;

import com.parse.FindCallback;
import com.parse.ParseException;
import com.parse.ParseFile;
import com.parse.ParseObject;
import com.parse.ParseQuery;

import java.util.List;

public class UsersPosts extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_users_posts);

        Intent receivedintentobject = getIntent();
        final String receiverdusername=receivedintentobject.getStringExtra("usermname");
        String hy="how abt now";
        Toast.makeText(this,hy,Toast.LENGTH_SHORT).show();

        setTitle(receiverdusername+"'s posts");

        final ParseQuery<ParseObject> parseObjectParseQuery=new ParseQuery<ParseObject>("username");
        parseObjectParseQuery.whereEqualTo("username",receiverdusername);
        parseObjectParseQuery.orderByAscending("createdAt");
        ProgressDialog dialog=new ProgressDialog(this);
        dialog.setMessage("loading");
        dialog.show();
        parseObjectParseQuery.findInBackground(new FindCallback<ParseObject>() {
            @Override
            public void done(List<ParseObject> objects, ParseException e) {
                if (objects.size()>0&& e==null){

                    for (ParseObject posts :objects){

                        TextView postdestcribtion=new TextView(UsersPosts.this);
                        postdestcribtion.setText("image_des"+"");
                        ParseFile postpicture =(ParseFile)posts.get("picture");
                    }
                }
            }
        });

    }
}
