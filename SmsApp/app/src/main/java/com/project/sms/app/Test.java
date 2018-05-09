package com.project.sms.app;

import android.Manifest;
import android.app.Activity;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.PackageManager;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.telephony.SmsManager;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class Test extends AppCompatActivity {

    //declare.
    EditText editTextNo;

    TextView textViewMobile;

    int REQUEST_SEND_SMS = 1;

    //proper messaging within the app.
    String SENT = "SMS_SENT";
    String DELIVERED = "SMS_DELIVERED";

    //fonts.
    Typeface typeface;

    //pending intent.
    PendingIntent sentPI, deliveredPI;

    //broadcast receivers.
    BroadcastReceiver smsSentReceiver, smsDeliveredReceiver;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        //initialize.
        editTextNo = (EditText) findViewById(R.id.editTextNo);
        textViewMobile = (TextView) findViewById(R.id.textViewMobile);

        //create/set the font.
        typeface = Typeface.createFromAsset(getAssets(), "fonts/WalkwayBold.ttf");

        //apply the font to the text view.
        textViewMobile.setTypeface(typeface);

        //pending intents.
        sentPI = PendingIntent.getBroadcast(this, 0, new Intent(SENT), 0);
        deliveredPI = PendingIntent.getBroadcast(this,0,new Intent(DELIVERED),0);
    }

    @Override
    protected void onResume() {
        super.onResume();

        //The sent sms receiver.
        smsSentReceiver = new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {

                switch (getResultCode()){

                    case Activity.RESULT_OK:
                        Toast.makeText(Test.this, "SMS Sent.", Toast.LENGTH_SHORT).show();
                        break;
                    case SmsManager.RESULT_ERROR_GENERIC_FAILURE:
                        Toast.makeText(Test.this, "Generic Failure.", Toast.LENGTH_SHORT).show();
                        break;
                    case SmsManager.RESULT_ERROR_NO_SERVICE:
                        Toast.makeText(Test.this, "No Service.", Toast.LENGTH_SHORT).show();
                        break;
                    case SmsManager.RESULT_ERROR_NULL_PDU:
                        Toast.makeText(Test.this, "Null PDU.", Toast.LENGTH_SHORT).show();
                        break;
                    case SmsManager.RESULT_ERROR_RADIO_OFF:
                        Toast.makeText(Test.this, "Radio Off.", Toast.LENGTH_SHORT).show();
                        break;
                }
            }
        };

        //The delivered sms receiver.
        smsDeliveredReceiver = new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {

                switch (getResultCode()){

                    case Activity.RESULT_OK:
                        Toast.makeText(Test.this, "SMS Delivered.", Toast.LENGTH_SHORT).show();
                        break;
                    case Activity.RESULT_CANCELED:
                        Toast.makeText(Test.this, "SMS Not Delivered.", Toast.LENGTH_SHORT).show();
                }
            }
        };

        //Register the above receivers.
        registerReceiver(smsSentReceiver,new IntentFilter(SENT));
        registerReceiver(smsDeliveredReceiver, new IntentFilter(DELIVERED));
    }

    @Override
    protected void onPause() {
        super.onPause();

        //unregister the broadcast receivers.
        unregisterReceiver(smsSentReceiver);
        unregisterReceiver(smsDeliveredReceiver);
    }

    public void sms(View view) {

        //convert our inputs to string.
        String number = editTextNo.getText().toString();
        String message = " Welcome to SMS App." +
                "Use the Verification Code to Confirm Ownership.";

        //Check whether the SMS permission are enabled.
        //if not, pop up a dialog to tell the user to enable the same.
        if (ContextCompat.checkSelfPermission(getApplicationContext(), Manifest.permission.SEND_SMS)
                != PackageManager.PERMISSION_GRANTED){
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.SEND_SMS},
                    REQUEST_SEND_SMS);
        }
        else{

            SmsManager smsManager = SmsManager.getDefault();

            smsManager.sendTextMessage(number,null,message,sentPI,deliveredPI);

            Log.d("DANTE", number + " " + message + " " + sentPI + " " + deliveredPI);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        //inflate my menu.
        getMenuInflater().inflate(R.menu.menu_tabs, menu);

        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        //get the position of each item in our menu.
        int position = item.getItemId();

        //iterate through these items.
        switch (position){

            //access the id of our menu item
            case R.id.action_money:
                startActivity( new Intent(this, Money.class));

            case R.id.action_inbox:
                startActivity(new Intent(this, Inbox.class));

            case R.id.action_sent:
                //startActivity(new Intent(this, Sent.class));
        }

        return super.onOptionsItemSelected(item);
    }
}
