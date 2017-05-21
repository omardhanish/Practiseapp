package com.omardhanishmemecreatorcoder786.omardhanish90.practiseapp;

//about GSON http://howtodoinjava.com/apache-commons/google-gson-tutorial-convert-java-object-to-from-json/

import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.FragmentManager;
import android.util.Base64;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.TextView;

import java.io.ByteArrayInputStream;
import java.io.InputStream;

import static android.R.attr.bitmap;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    FragmentManager fragmentManager;
    ImageView profilenav;
    TextView email1 , name1;
    private SharedPreferences sharedPreferences;
    private Bitmap bitmap;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });


        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        fragmentManager = getSupportFragmentManager();

        View headerView = navigationView.getHeaderView(0);
        profilenav = (ImageView) headerView.findViewById(R.id.profile1);
        name1 = (TextView) headerView.findViewById(R.id.name1);
        email1 = (TextView) headerView.findViewById(R.id.email1);


        sharedPreferences = getSharedPreferences("MyPrefs", MODE_PRIVATE);

        String namevalue = sharedPreferences.getString(ProfileActivity.Name, "your name");
        String emailvalue = sharedPreferences.getString(ProfileActivity.Email, "your email");

        name1.setText(namevalue);
        email1.setText(emailvalue);
        retrivesharedPreferences();

    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.contactsfrag) {
            fragmentManager.beginTransaction()
                    .replace(R.id.content_main, new ContactsListView())
                    .commit();
        } else if (id == R.id.editprofile) {
            fragmentManager.beginTransaction()
                    .replace(R.id.content_main, new ProfileActivity())
                    .commit();
        } else if (id == R.id.viewjson) {
            fragmentManager.beginTransaction()
                    .replace(R.id.content_main, new JsonListFragment())
                    .commit();
        } else if (id == R.id.exit) {
                    finish();
        }
//        else if (id == R.id.nav_share) {
//
//        } else if (id == R.id.nav_send) {
//
//        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }


    private void retrivesharedPreferences()
    {
        SharedPreferences shared = getSharedPreferences("MyPrefs", MODE_PRIVATE);
        String photo = shared.getString("PRODUCT_PHOTO", "photo");
        assert photo != null;
        if(!photo.equals("photo"))
        {
            byte[] b = Base64.decode(photo, Base64.DEFAULT);
            InputStream is = new ByteArrayInputStream(b);
            bitmap = BitmapFactory.decodeStream(is);
            profilenav.setImageBitmap(bitmap);
        }

    }

}
