package com.example.twitter_clone;


import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import com.parse.FindCallback;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.parse.ParseUser;

import java.util.ArrayList;
import java.util.List;


/**
 * A simple {@link Fragment} subclass.
 */
public class UsersTab extends Fragment implements AdapterView.OnItemClickListener {
private ListView listView;
private ArrayList<String> arrayList;
private ArrayAdapter arrayAdapter;
    public UsersTab() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view= inflater.inflate(R.layout.fragment_users_tab, container, false);
        listView=view.findViewById(R.id.listView);
        arrayList=new ArrayList();
        arrayAdapter=new ArrayAdapter(getContext(),android.R.layout.simple_list_item_1,arrayList);
        listView.setOnItemClickListener(UsersTab.this);
      ParseQuery<ParseUser>parseQuery=ParseUser.getQuery();
      parseQuery.whereNotEqualTo("username",ParseUser.getCurrentUser().getUsername());
      parseQuery.findInBackground(new FindCallback<ParseUser>() {
          @Override
          public void done(List<ParseUser> users, ParseException e) {
              if (e==null){
                  if(users.size()>0){
                      for (ParseUser Usernames:users){
                          arrayList.add(Usernames.getUsername());
                      }
                      listView.setAdapter(arrayAdapter);
                  }
                         }
                         }
                         });
                         return view;
                         }
//on item click listerner method
@Override
public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
Intent intent=new Intent(getContext(),UsersPosts.class);
    Toast.makeText(getContext(),"heloow",Toast.LENGTH_SHORT).show();
intent.putExtra("username",arrayList.get(position));
startActivity(intent);
        }
        }
