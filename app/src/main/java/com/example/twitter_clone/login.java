package com.example.twitter_clone;

import android.content.Intent;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.parse.LogInCallback;
import com.parse.ParseException;
import com.parse.ParseUser;

public class login extends AppCompatActivity implements View.OnClickListener {

  private  EditText edtloginemail,edtloginpassword;
  private  Button btnlogininlogin,btnregisterinlogin;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login);
        setTitle("Logged in");
    btnlogininlogin=findViewById(R.id.btnlogininlogin);
    btnregisterinlogin=findViewById(R.id.btnregisterinlogin);
    edtloginemail=findViewById(R.id.edtemailinlogin);
    edtloginpassword=findViewById(R.id.edtpasswordinlogin);
    //removing keyboard when user clicks away
    edtloginpassword.setOnKeyListener(new View.OnKeyListener() {
      @Override
      public boolean onKey(View view, int i, KeyEvent keyEvent) {
        if (i==keyEvent.KEYCODE_ENTER&&keyEvent.getAction()==keyEvent.ACTION_DOWN){
          onClick(btnlogininlogin);
        }
        return false;
      }
    });
    btnregisterinlogin.setOnClickListener(this);
    btnlogininlogin.setOnClickListener(this);

    if (ParseUser.getCurrentUser() !=null)
      transitiontoactivity();

    }
//button listener (register and login)
    @Override
    public void onClick(View view) {

      switch(view.getId()){
        case R.id.btnlogininlogin:
          if (edtloginpassword.getText().toString().equals("")||edtloginemail.getText().toString().equals("")){
            Toast.makeText(login.this, "fill all the fields", Toast.LENGTH_LONG).show();
        }else {
            ParseUser.logInInBackground(edtloginemail.getText().toString(), edtloginpassword.getText().toString(), new LogInCallback() {
              @Override
              public void done(ParseUser user, ParseException e) {
                if (user != null && e == null) {
                  Toast.makeText(login.this, "you are logged  in", Toast.LENGTH_LONG).show();
                  transitiontoactivity();
                } else {
                  Toast.makeText(login.this, "username of passwrd exists", Toast.LENGTH_LONG).show();
                }
              }
            });
          }
          break;
        case R.id.btnregisterinlogin:
          Intent intent = new Intent();
          intent.setClass(login.this,signup_activity.class);
          startActivity(intent);
          break;

      }
      //end of button listener (register and login)
    }
public void istapped(View view){
  InputMethodManager inputMethodManager=(InputMethodManager)getSystemService(INPUT_METHOD_SERVICE);
  inputMethodManager.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(),0);
}
  public void transitiontoactivity(){
    Intent intent=new Intent();
    intent.setClass(login.this, SocialNetwork.class);
    startActivity(intent);

  }
}

