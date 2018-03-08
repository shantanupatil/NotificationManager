package in.shantanupatil.notificationmanager.Events.RetriveEvents;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import in.shantanupatil.notificationmanager.R;

public class ActivityEventDetail extends AppCompatActivity {

    public String title;
    public String description;
    public String date;
    public String time;
    public String price;
    public String forum;

    TextView eventTitle;
    TextView eventDescription;
    TextView eventMonth;
    TextView eventTime;
    TextView eventPrice;
    TextView eventForum;

    CollapsingToolbarLayout collapsingTool;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_event_detail);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolBar);
        setSupportActionBar(toolbar);
        collapsingTool = (CollapsingToolbarLayout) findViewById(R.id.collapsingTool);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);


        eventTitle = (TextView) findViewById(R.id.detail_title_event);
        eventDescription = (TextView) findViewById(R.id.detail_description_event);
        eventTime = (TextView) findViewById(R.id.detail_time_time);
        eventMonth = (TextView) findViewById(R.id.detail_time_month);
        eventPrice = (TextView) findViewById(R.id.detail_price_event);
        eventForum = (TextView) findViewById(R.id.detail_forum_event);

        Intent i= getIntent();
        title = i.getStringExtra("title");
        collapsingTool.setTitle(title);
        description = i.getStringExtra("description");
        date = i.getStringExtra("date");
        Log.d("DateOfEvent", "" + date);
        String[] arrayOfDate = date.split(" ");
        price = i.getStringExtra("price");
        forum = i.getStringExtra("forum");
        Log.d("Forum", " " + forum + " " + price);
        eventTitle.setText(title);
        eventDescription.setText(description);
        //eventDate.setText(date);
        eventMonth.setText(arrayOfDate[0] + " " + arrayOfDate[1] + " " + arrayOfDate[2]);
        eventTime.setText(arrayOfDate[3] + " " + arrayOfDate[4] + " " + arrayOfDate[5]);
        eventPrice.setText(price);
        eventForum.setText(forum);

    }
}
