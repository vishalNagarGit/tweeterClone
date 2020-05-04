package com.parse.starter;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.CheckedTextView;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import com.parse.FindCallback;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.parse.ParseUser;
import com.parse.SaveCallback;

import java.util.ArrayList;
import java.util.List;

public class usersList extends AppCompatActivity {

    ArrayList<String> users=new ArrayList<>();
    ArrayAdapter arrayAdapter;

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater=new MenuInflater(this);
        inflater.inflate(R.menu.tweet_menu,menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        if(item.getItemId()==R.id.tweet)
        {  Log.i("inside","tweet");
            AlertDialog.Builder builder=new AlertDialog.Builder(this);
            builder.setTitle("Send a tweet");
            final EditText editText=new EditText(this);
            builder.setView(editText);

            builder.setPositiveButton("send", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {
                   Log.i("tweet",editText.getText().toString());

                    ParseObject  tweet=new ParseObject("Tweet");
                    tweet.put("username",ParseUser.getCurrentUser().getUsername());
                    tweet.put("tweet",editText.getText().toString());

                    tweet.saveInBackground(new SaveCallback() {
                        @Override
                        public void done(ParseException e) {
                            if(e==null)
                                Toast.makeText(usersList.this, "Tweet sent successfully", Toast.LENGTH_SHORT).show();
                            else
                                Toast.makeText(usersList.this, "failed", Toast.LENGTH_SHORT).show();
                        }
                    });

                }
            });

            builder.setNegativeButton("cancel", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {
                  dialogInterface.cancel();
                }
            });
            builder.show();
        }
        else if(item.getItemId()==R.id.logout)
        {
            ParseUser.logOut();
            startActivity(new Intent(getApplicationContext(),MainActivity.class));
        }

        else if(item.getItemId()==R.id.feed)
        {
            startActivity(new Intent(getApplicationContext(),feeds.class));
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_users_list);


        if(ParseUser.getCurrentUser().getList("isFollowing")==null)
        {
            ArrayList<String> list=new ArrayList<>();
            ParseUser.getCurrentUser().put("isFollowing",list);
        }

       final  ListView listView=(ListView)findViewById(R.id.listView);
        listView.setChoiceMode(AbsListView.CHOICE_MODE_MULTIPLE);

        arrayAdapter= new ArrayAdapter(this,android.R.layout.simple_list_item_checked,users);
        listView.setAdapter(arrayAdapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                CheckedTextView checkedTextView=(CheckedTextView)view;
                if(checkedTextView.isChecked())
                {
                    ParseUser.getCurrentUser().add("isFollowing",users.get(i));
                    ParseUser.getCurrentUser().saveInBackground();
                    Log.i(users.get(i),"followed");
                }
                else
                {
                    List alist=ParseUser.getCurrentUser().getList("isFollowing");
                    alist.remove(users.get(i));
                    ParseUser.getCurrentUser().put("isFollowing", alist);
                    ParseUser.getCurrentUser().saveInBackground();
                    Log.i(users.get(i),"unfollowed");
                }
            }
        });

        ParseQuery<ParseUser> query=ParseUser.getQuery();
        query.whereNotEqualTo("username",ParseUser.getCurrentUser().getUsername().toString());
        users.clear();
        query.findInBackground(new FindCallback<ParseUser>() {
            @Override
            public void done(List<ParseUser> objects, ParseException e) {
                if(e==null&&objects.size()>0)
                {
                    for(ParseUser user:objects)
                    {
                        users.add(user.getUsername());
                    }
                    arrayAdapter.notifyDataSetChanged();

                    for(String username:users)
                    {
                        if(ParseUser.getCurrentUser().getList("isFollowing").contains(username))
                            listView.setItemChecked(users.indexOf(username),true);
                    }
                }
            }
        });

    }
}
