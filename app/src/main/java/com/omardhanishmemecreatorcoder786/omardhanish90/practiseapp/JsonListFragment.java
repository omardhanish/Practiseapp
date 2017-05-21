package com.omardhanishmemecreatorcoder786.omardhanish90.practiseapp;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.os.Bundle;
import android.os.Environment;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import java.io.File;
import java.util.ArrayList;

/**
 * Created by omar on 21-05-2017.
 */

public class JsonListFragment extends Fragment {

    public JsonListFragment() {
    }

    TextView text;
    ListView listview;
    ArrayList<String> list;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.json_list_view, container, false);

        listview = (ListView) rootView.findViewById(R.id.listjson);
        text = (TextView) rootView.findViewById(R.id.nothing);

        if(GetFiles(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOCUMENTS)) == null){
            text.setVisibility(View.VISIBLE);
            return rootView;
        }else {
            list = GetFiles(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOCUMENTS ));
            listview.setVisibility(View.VISIBLE);
            text.setVisibility(View.INVISIBLE);
        }

        listview.setAdapter(new ArrayAdapter<String>(getContext(),
                android.R.layout.simple_list_item_1, list));

        listview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView<?> parent, View v, int position, long id) {
                AlertDialog.Builder ad = new AlertDialog.Builder(getContext());
                ad.setTitle("Hello");
                ad.setMessage("View the json files at 'Documents/practiseapp/' in your internal or external storage, Thanks");
                ad.show();
            }
        });

        return rootView;
    }



    public ArrayList<String> GetFiles(File DirectoryPath) {
        ArrayList<String> MyFiles = new ArrayList<String>();
        File f = new File(DirectoryPath,"practiseapp");

        f.mkdirs();
        File[] files = f.listFiles();
        if (files.length == 0) {
            return null;
        }
        else {
            for (int i=0; i<files.length; i++)
                MyFiles.add(files[i].getName());
        }
        return MyFiles;
    }
}
