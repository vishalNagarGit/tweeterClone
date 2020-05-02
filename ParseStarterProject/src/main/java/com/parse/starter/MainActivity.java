/*
 * Copyright (c) 2015-present, Parse, LLC.
 * All rights reserved.
 *
 * This source code is licensed under the BSD-style license found in the
 * LICENSE file in the root directory of this source tree. An additional grant
 * of patent rights can be found in the PATENTS file in the same directory.
 */
package com.parse.starter;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.parse.ParseAnalytics;
import com.parse.ParseObject;
import com.parse.ParseQuery;


public class  MainActivity extends AppCompatActivity {


  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_main);

   /* ParseObject score=new ParseObject("Score");
    score.put("username","vishal");
    score.put("score",89);
    score.saveInBackground(new SaveCallback() {
      @Override
      public void done(ParseException e) {
        if(e==null)
          Log.i("save result","passed");
        else Log.i("save failed",e.toString());
      }
    });
*/
    ParseQuery<ParseObject> query=ParseQuery.getQuery("Score");
    /*query.getInBackground("NzKXHnGRQ4", new GetCallback<ParseObject>() {
      @Override
      public void done(ParseObject object, ParseException e) {
         if(e==null)Log.i("value",object.getString("username")+Integer.toString(object.getInt("score")));
        else Log.i("failed","fiaej");
      }
    });*/

   /* query.whereEqualTo("username","vijay");
    query.setLimit(1);
    query.findInBackground(new FindCallback<ParseObject>() {
      @Override
      public void done(List<ParseObject> objects, ParseException e) {
        if(e!=null)Log.i("failed","sjdf");
        else if(objects.size()>0)
        {
          for(ParseObject p:objects)
            Log.i("result",Integer.toString(p.getInt("score")));
        }
      }
    });
   */
   /* query.whereGreaterThan("score",200);
    query.setLimit(1);
    query.findInBackground(new FindCallback<ParseObject>() {
      @Override
      public void done(List<ParseObject> objects, ParseException e) {
        if(e!=null)Log.i("failed","sjdf");
        else if(objects.size()>0)
        {
          for(ParseObject p:objects) {
            p.put("score",p.getInt("score")+50);
            p.saveInBackground();
            Log.i("result", Integer.toString(p.getInt("score")));
          }
        }
      }
    });*/
   /* ParseUser user=new ParseUser();
    user.setUsername("vishal");
    user.setPassword("nagar");
    user.signUpInBackground(new SignUpCallback() {
      @Override
      public void done(ParseException e) {
        if(e==null)
          Log.i("signup","success");
        else Log.i("signup","failed");

      }
    });*/

    /*ParseUser.logInInBackground("vishal", "asdf", new LogInCallback() {
      @Override
      public void done(ParseUser user, ParseException e) {
        if(user!=null)Log.i("login","success");
        else Log.i("login",e.toString());
      }
    });
    */
    /*ParseUser.logOut();
    if(ParseUser.getCurrentUser()!=null)
    {
      Log.i("logged in",ParseUser.getCurrentUser().getUsername().toString());
    }
    else
      Log.i("logged status","kat gaya");
    */
    ParseAnalytics.trackAppOpenedInBackground(getIntent());
  }

}