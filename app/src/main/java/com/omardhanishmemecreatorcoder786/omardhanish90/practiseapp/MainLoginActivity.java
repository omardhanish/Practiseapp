package com.omardhanishmemecreatorcoder786.omardhanish90.practiseapp;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

/**
 * Created by omar on 21-05-2017.
 */

public class MainLoginActivity extends Activity {
    EditText user , pass;
    Button signin;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login_activity);

        user = (EditText) findViewById(R.id.username);
        pass = (EditText) findViewById(R.id.password);
        signin = (Button) findViewById(R.id.buttonlogin);

        signin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String name1 = user.getText().toString().trim();
                String pass1 = pass.getText().toString().trim();

                if(name1.equals("admin")&&pass1.equals("admin")){
                    Intent i = new Intent(MainLoginActivity.this,MainActivity.class);
                    startActivity(i);
                    finish();
                    Toast.makeText(MainLoginActivity.this,"Login , Successfull",Toast.LENGTH_SHORT).show();
                }else {
                    Toast.makeText(MainLoginActivity.this,"Wrong , Try again ",Toast.LENGTH_SHORT).show();
                }

            }
        });
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        finish();
    }
}
