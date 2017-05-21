package com.omardhanishmemecreatorcoder786.omardhanish90.practiseapp;

import android.Manifest;
import android.app.ProgressDialog;
import android.content.ContentUris;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Looper;
import android.provider.ContactsContract;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import static android.content.Context.MODE_PRIVATE;


/**
 * Created by omar on 19-05-2017.
 */

public class ContactsListView extends Fragment implements SearchView.OnQueryTextListener{

    OtherData data = new OtherData();
    SharedPreferences sharespreferences;
    private Context context;
    RecyclerView recyclerView;
    SearchView search;
    ArrayList<StringNumber> listContacts;
    private RecyclerAdapterForContactsList adapter;
    public RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getContext());


    public ContactsListView() {
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.recyclerlist_view, container, false);

        recyclerView = (RecyclerView)rootView.findViewById(R.id.list);

        search = (SearchView) rootView.findViewById(R.id.searchview);

        new FetchAsynck(context).execute();

        recyclerView.addOnItemTouchListener(new RecyclerTouchListener(getContext(), recyclerView, new RecyclerTouchListener.ClickListener() {
            @Override
            public void onClick(View view, int position) {
                TextView tagText = (TextView) view.findViewById(R.id.number);
                TextView tagname = (TextView) view.findViewById(R.id.name);
                String tag = tagText.getText().toString();
                String tagn = tagname.getText().toString();
                sharespreferences = getActivity().getSharedPreferences("MyPrefs", MODE_PRIVATE);
                SharedPreferences.Editor editor = sharespreferences.edit();
                editor.putString(ProfileActivity.To, tag);
                editor.putString(ProfileActivity.ToName,tagn);
                editor.apply();
                Intent callIntent = new Intent(Intent.ACTION_CALL);
                callIntent.setData(Uri.parse("tel:" + tag));
                if (ActivityCompat.checkSelfPermission(getContext(),
                        Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {
                    return;
                }
                startActivity(callIntent);
            }

            @Override
            public void onLongClick(View view, int position) {

            }
        }));



        setupSearchView();

        return rootView;
    }



    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        // TODO Auto-generated method stub
        super.onActivityCreated(savedInstanceState);
        new FetchAsynck(getContext()).execute();
    }


    private void setupSearchView() {
        search.setIconifiedByDefault(false);
        search.setOnQueryTextListener(this);
        search.setSubmitButtonEnabled(true);
        search.setQueryHint("Search Here");
    }

    public boolean onQueryTextChange(String newText) {
        adapter.filter(newText);
        return true;
    }

    public boolean onQueryTextSubmit(String query) {
        return false;
    }




    private class FetchAsynck extends AsyncTask<Void,Void,ArrayList<StringNumber>>{

        private Context context;
        ProgressDialog loading;

        public FetchAsynck(Context context) {
            this.context = context;
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            loading = new ProgressDialog(getActivity());
            loading.setMessage("Loading Contacts. Please wait...");
            loading.setIndeterminate(false);
                loading.show();


        }

        @Override
        protected ArrayList<StringNumber> doInBackground(Void... params) {
            if(Looper.myLooper()==null) {
                Looper.prepare();
            }
            listContacts = new ContactFetcher(getContext()).fetchAll();
            return listContacts;

        }

        @Override
        protected void onPostExecute(ArrayList<StringNumber> stringNumbers) {
            super.onPostExecute(stringNumbers);
            if(loading!=null)
            loading.dismiss();
            adapter = new RecyclerAdapterForContactsList(getContext(),stringNumbers);
            recyclerView.setLayoutManager(mLayoutManager);
            recyclerView.setAdapter(adapter);
        }
    }

}
