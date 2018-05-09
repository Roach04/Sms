package com.project.sms.app;

import android.content.ContentResolver;
import android.database.Cursor;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;

public class Inbox extends AppCompatActivity {

    ListView list;

    SimpleCursorAdapter adapter;
    Cursor cursor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_inbox);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        list = (ListView) findViewById(R.id.listViewInbox);

        //Create an in box uri
        Uri uri = Uri.parse("content://sms/inbox");

        //List the columns required.
        String[] reqCols = new String[]{"_id", "address", "body"};

        //Get the content resolver object which will deal with the content provider.
        ContentResolver contentResolver = getContentResolver();

        //Fetch the inbox sms messages from built in content resolver.
        cursor = contentResolver.query(uri,reqCols,null,null,null);

        //attach the cursor to the adapter and display the same to the list view.
        adapter = new SimpleCursorAdapter(this,R.layout.list_item_layout,cursor,
                new String[]{"body","address"}, new int[]{R.id.textViewBody, R.id.textViewAddress});

        //set the adapter to our list view.
        list.setAdapter(adapter);
    }
}
