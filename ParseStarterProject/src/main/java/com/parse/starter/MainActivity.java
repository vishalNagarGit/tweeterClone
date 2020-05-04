/*
 * Copyright (c) 2015-present, Parse, LLC.
 * All rights reserved.
 *
 * This source code is licensed under the BSD-style license found in the
 * LICENSE file in the root directory of this source tree. An additional grant
 * of patent rights can be found in the PATENTS file in the same directory.
 */
package com.parse.starter;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.parse.LogInCallback;
import com.parse.ParseAnalytics;
import com.parse.ParseException;
import com.parse.ParseUser;
import com.parse.SignUpCallback;


public class  MainActivity extends AppCompatActivity {

   EditText username;
  EditText passwd;

  public void redirect()
  {
      if(ParseUser.getCurrentUser()!=null)
      {
          Intent intent=new Intent(getApplicationContext(),usersList.class);
          startActivity(intent);
      }
  }

  public void loginMethod(View view)
  {  
    if(username.getText()==null||passwd.getText().toString()==null)
      Toast.makeText(this, "invalid username or passwd", Toast.LENGTH_SHORT).show();
    else
    ParseUser.logInInBackground(username.getText().toString(), passwd.getText().toString(), new LogInCallback() {
      @Override
      public void done(ParseUser user, ParseException e) {
         if(e==null)
         {
           Log.i("login","success");
             redirect();
         }
        else{
           Toast.makeText(MainActivity.this, "could not log in", Toast.LENGTH_SHORT).show();
         }

      }
    });
  }

  public void signupMethod(View view)
  {
      if(username.getText()==null||passwd.getText().toString()==null)
    Toast.makeText(this, "invalid username or passwd", Toast.LENGTH_SHORT).show();

   else
      {
         ParseUser user=new ParseUser();
         user.put("username",username.getText().toString());
         user.put("password",passwd.getText().toString());
        user.signUpInBackground(new SignUpCallback() {
          @Override
          public void done(ParseException e) {
            if(e==null)
              Toast.makeText(MainActivity.this, "signup success", Toast.LENGTH_SHORT).show();
            else
              Toast.makeText(MainActivity.this, "could not signup", Toast.LENGTH_SHORT).show();
          }
        });
      }
  }


  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_main);
     setTitle("Tweeter Login/signup");
    username=(EditText)findViewById(R.id.userNameEditText);
    passwd=(EditText)findViewById(R.id.passwdEditText);
    ParseAnalytics.trackAppOpenedInBackground(getIntent());
      redirect();
  }

}
