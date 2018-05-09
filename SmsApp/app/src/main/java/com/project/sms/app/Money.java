package com.project.sms.app;

import android.content.ContentResolver;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;

public class Money extends AppCompatActivity {

    ListView list;

    Cursor cursor;
    SimpleCursorAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_money);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        list = (ListView) findViewById(R.id.listViewMoney);

        //Create an in box uri
        Uri uri = Uri.parse("content://sms/inbox");

        //List the columns required.
        String[] reqCols = new String[]{"_id", "address", "body"};

        //Get the content resolver object which will deal with the content provider.
        ContentResolver contentResolver = getContentResolver();

        //Fetch the inbox sms messages from built in content resolver.
        cursor = contentResolver.query(uri,reqCols,null,null,null);

        //check if the address matches to mpesa. if so display the same.
        //String pp = cursor.getString(cursor.getColumnIndex("address"));



            //attach the cursor to the adapter and display the same to the list view.
            adapter = new SimpleCursorAdapter(this,R.layout.list_item_layout,cursor,
                    new String[]{"body","address"}, new int[]{R.id.textViewBody, R.id.textViewAddress});

            //set the adapter to our list view.
            list.setAdapter(adapter);

    }

}
