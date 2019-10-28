package com.example.twitter_clone;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.parse.ParseException;
import com.parse.ParseInstallation;
import com.parse.ParseUser;
import com.parse.SignUpCallback;

public class signup_activity extends AppCompatActivity implements View.OnClickListener{

    //ui components
    private EditText edtemail, edtusername, edtpassword;
    private Button btnregister,btnlogin;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTitle("signup/login");
        setContentView(R.layout.signup_activity);
        ParseInstallation.getCurrentInstallation().saveInBackground();
        //initialising ui components
        edtemail=findViewById(R.id.edtsignupemail);
        edtpassword=findViewById(R.id.edtsignuppassword);
        edtemail.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View view, int i, KeyEvent keyEvent) {
                if (i == keyEvent.KEYCODE_ENTER && keyEvent.getAction()==keyEvent.ACTION_DOWN){
                    onClick(btnregister);
                }
                return  false;
            }

        });
        edtusername=findViewById(R.id.edtsignupusername);
        btnregister=findViewById(R.id.btnregisterinsignup);
        btnlogin=findViewById(R.id.btnlogininsignup);
        btnlogin.setOnClickListener(this);
        btnregister.setOnClickListener(this);
        if (ParseUser.getCurrentUser() !=null){
           transitiontoactivity();
            Toast.makeText(signup_activity.this,"you logged in",Toast.LENGTH_LONG).show();
        }

    }
//ON CLICK LISTENER FOR BUTTONS
    @Override
    public void onClick(View view) {

        switch (view.getId()){


            case R.id.btnregisterinsignup:
                if (edtemail.getText().toString().equals("")||edtusername.getText().toString().equals("")||edtpassword.getText().toString().equals("")){
                    Toast.makeText(signup_activity.this,"fill all the empty fields to signup",Toast.LENGTH_LONG).show();
                }else {
                final ParseUser appuer=new ParseUser();
                appuer.setEmail(edtemail.getText().toString());
                appuer.setPassword(edtpassword.getText().toString());
                appuer.setUsername(edtusername.getText().toString());
                final ProgressDialog progressDialog=new ProgressDialog(this);
                progressDialog.setMessage("singing up "+ edtusername.getText().toString());
                progressDialog.show();
                appuer.signUpInBackground(new SignUpCallback() {
                    @Override
                    public void done(ParseException e) {
                        if (e==null){

                            Toast.makeText(signup_activity.this,"you are signed in",Toast.LENGTH_LONG).show();
                            transitiontoactivity();
                        }else{
                            Toast.makeText(signup_activity.this,"the account exist",Toast.LENGTH_LONG).show();
                        }
                        progressDialog.dismiss();
                        transitiontoactivity();


                    }
                });}
                break;
            case R.id.btnlogininsignup:
                Intent intent=new Intent();
                intent.setClass(signup_activity.this, login.class);
                startActivity(intent);
                break;
        }
    }
    public void tapped(View view) {
        try {
            InputMethodManager inputMethodManager = (InputMethodManager) getSystemService(INPUT_METHOD_SERVICE);
            inputMethodManager.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(), 0);
        }catch (Exception e){
            e.printStackTrace();
        }
    }

public void transitiontoactivity(){
        Intent intent=new Intent();
        intent.setClass(signup_activity.this, SocialNetwork.class);
        startActivity(intent);

}

}
