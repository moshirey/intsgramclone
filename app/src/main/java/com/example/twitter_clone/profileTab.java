package com.example.twitter_clone;


import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.parse.ParseException;
import com.parse.ParseUser;
import com.parse.SaveCallback;


/**
 * A simple {@link Fragment} subclass.
 */
public class profileTab extends Fragment {

    private EditText username, bio, profession,hobbies,sport;
    private Button btnupdate ,btnlogout;


    public profileTab() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(final LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view= inflater.inflate(R.layout.fragment_profile_tab, container, false);
        username=view.findViewById(R.id.edtusername);
        bio= view.findViewById(R.id.edtbio);
        profession=view.findViewById(R.id.edtprofession);
        hobbies=view.findViewById(R.id.edthobbies);
        sport=view.findViewById(R.id.edtsport);
        btnupdate=view.findViewById(R.id.btnupdate);
        btnlogout=view.findViewById(R.id.btnlogout);
        final ParseUser parseUser=ParseUser.getCurrentUser();
        final ProgressDialog progressDialog=new ProgressDialog(getContext());
        if (parseUser.get("profile")==null){
            username.setText("");
        }else {username.setText(parseUser.get("profile")+"");}
        if (parseUser.get("profile_bio")==null){
            bio.setText("");
        }else {bio.setText(parseUser.get("profile_bio")+"");}
        if (parseUser.get("profile_profession")==null){
            profession.setText("");
        }else {profession.setText(parseUser.get("profile_profession")+"");}
        if (parseUser.get("profile_hobbies")==null){
            hobbies.setText("");
        }else {hobbies.setText(parseUser.get("profile_hobbies")+"");}
        if (parseUser.get("profile_fav_sport")==null){
            sport.setText("");
        }else {sport.setText(parseUser.get("profile_fav_sport")+"");}


        btnupdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                progressDialog.show();
                parseUser.put("profile",username.getText().toString());
                parseUser.put("profile_bio",bio.getText().toString());
                parseUser.put("profile_profession",profession.getText().toString());
                parseUser.put("profile_hobbies",hobbies.getText().toString());
                parseUser.put("profile_fav_sport",sport.getText().toString());
                parseUser.saveInBackground(new SaveCallback() {
                    @Override
                    public void done(ParseException e) {
                        if (e==null){
                            Toast.makeText(getContext(),"its saved",Toast.LENGTH_LONG).show();
                            progressDialog.dismiss();
                        }else {
                            Toast.makeText(getContext(),"an error",Toast.LENGTH_LONG).show();
                        }
                    }
                });
            }
        });

btnlogout.setOnClickListener(new View.OnClickListener() {
    @Override
    public void onClick(View view) {
        parseUser.getCurrentUser().logOut();
        Intent intent=new Intent();
        intent.setClass(getContext(),signup_activity.class);
        startActivity(intent);
    }
});


        return view;
    }

}
