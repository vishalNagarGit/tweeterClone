package com.parse.starter;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.ListView;
import android.widget.SimpleAdapter;

import com.parse.FindCallback;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.parse.ParseUser;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class feeds extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_feeds);

        setTitle("Feeds");

        final ListView listView=(ListView)findViewById(R.id.listView);





        ParseQuery<ParseObject> query =new ParseQuery<ParseObject>("Tweet");
        query.whereContainedIn("username", ParseUser.getCurrentUser().getList("isFollowing"));
        query.orderByDescending("createdAt");
         query.setLimit(20);
        query.findInBackground(new FindCallback<ParseObject>() {
            @Override
            public void done(List<ParseObject> objects, ParseException e) {
                if(e==null&&objects.size()>0) {
                    ArrayList<Map<String,String>> tweetData=new ArrayList<>();
                    for (ParseObject object : objects) {
                        Log.i("object", object.get("tweet").toString());
                        Map<String, String> temp = new HashMap<String, String>();
                        temp.put("username",  object.get("username").toString());
                        temp.put("tweet",   object.get("tweet").toString());
                        tweetData.add(temp);
                    }

                    SimpleAdapter simpleAdapter=new SimpleAdapter(feeds.this,tweetData,R.xml.mylistview,new String[]{"tweet","username"},new int[]{android.R.id.text1,android.R.id.text2});
                    listView.setAdapter(simpleAdapter);

                }
            }
        });







    }
}
