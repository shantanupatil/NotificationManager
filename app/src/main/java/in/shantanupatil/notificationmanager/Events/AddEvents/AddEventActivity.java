package in.shantanupatil.notificationmanager.Events.AddEvents;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.TimePickerDialog;
import android.graphics.Typeface;
import android.os.Handler;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TimePicker;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import br.com.simplepass.loading_button_lib.customViews.CircularProgressButton;
import in.shantanupatil.notificationmanager.R;

public class AddEventActivity extends AppCompatActivity {


    EditText title;
    EditText description;
    EditText add_eventPrice;
    EditText add_eventForum;

    CircularProgressButton saveEvent;

    public static String title_text;
    public static String description_text, price_text, forum_text;

    public static String m, d;
    public static String h, s;

    public static long dates;

    ///Date and Time
    //some Dailog ID
    public static int DIALOG_ID = 0;
    public static int DIALOG_ID_TIME = 1;

    //Buttons for date
    ImageButton date;
    ImageButton time;

    //int variables to store date
    int s_year, s_day, s_month;

    //int variables to set Time
    int s_hour, s_minutes;

    public static String date_text = "";
    public static String time_text = "";


    //Variable to Toast Some error Message
    //Variable to Toast Some error Message
    public static String message;


    //Firebase Database Methods
    FirebaseDatabase mDatabase;
    DatabaseReference mRef;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_event);

        //Giving reference to Firebase Database
        mDatabase = FirebaseDatabase.getInstance();
        mRef = mDatabase.getReference();

//        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
//        setSupportActionBar(toolbar);

        initialisation();

        date.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showDialog(DIALOG_ID);
            }
        });

        time.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showDialog(DIALOG_ID_TIME);
            }
        });

        saveEvent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                saveEventToDatabase();
            }
        });
    }



    ///------------------------------------------------------------------------------------------------------------------------////
    ///----------------------------------------------Save Event to Database -----------------------------------------------------////
    ///------------------------------------------------------------------------------------------------------------------------////


    private boolean checkInput(String title_text, String description_text, String date_text, String time_text, String price, String forum) {


        if (title_text.equals("")) {
            message = "Title Required";
            return false;
        }

        if (price.equals("")) {
            message = "Entry Fee Required";
            return false;
        }

        if (forum.equals("")) {
            message = "Forum Required";
            return false;
        }
        if (description_text.equals("")) {
            message = "Description Required";
            return false;
        }
        if (date_text.equals("")) {
            message = "Please select date";
            return false;
        }
        if (time_text.equals("")) {
            message = "Event must have time";
            return false;
        }

        int x = title_text.indexOf("/");
        if (x >= 1 || title_text.startsWith("/")) {
            message = "Title should not contain '/'";
            return false;
        }
        return true;
    }

    private void saveEventToDatabase() {

        title_text = title.getText().toString();
        description_text = description.getText().toString();
        price_text = add_eventPrice.getText().toString();
        forum_text = add_eventForum.getText().toString();


        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date date = null;
        try {
            date = format.parse(date_text + " " + time_text);
        } catch (ParseException e) {
            e.printStackTrace();
        }

        if (date != null) {
            dates = date.getTime();
            Log.d("Shantanu", "Date: " + date.getTime());
        }

        saveEvent.startAnimation();
        if (checkInput(title_text, description_text, date_text, time_text, price_text, forum_text)) {
            mRef.addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    EventModel event = new EventModel(title_text, description_text, dates, price_text, forum_text);

                    mRef.child("Events")
                            .child(title_text)
                            .setValue(event);

                    Handler handler = new Handler();
                    handler.postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            Toast.makeText(AddEventActivity.this, "Event Added", Toast.LENGTH_SHORT).show();
                            saveEvent.revertAnimation();
                        }
                    }, 2000);
                }

                @Override
                public void onCancelled(DatabaseError databaseError) {

                }
            });
        } else {

            Handler handler = new Handler();
            handler.postDelayed(new Runnable() {
                @Override
                public void run() {
                    Toast.makeText(getApplicationContext(), "" + message, Toast.LENGTH_SHORT).show();
                    saveEvent.revertAnimation();
                }
            }, 2000);
        }
    }


    private void initialisation() {

        Typeface face = Typeface.createFromAsset(getAssets(), "sansproregular.otf");

        //Edittext
        title = (EditText) findViewById(R.id.add_eventName);
        description = (EditText) findViewById(R.id.add_eventDescription);
        add_eventPrice = (EditText) findViewById(R.id.add_eventPrice);
        add_eventForum = (EditText) findViewById(R.id.add_eventForum);

        title.setTypeface(face);
        description.setTypeface(face);
        add_eventPrice.setTypeface(face);
        add_eventForum.setTypeface(face);

        //Circular Progress Button
        saveEvent = (CircularProgressButton) findViewById(R.id.addEventFirebase);
        saveEvent.setTypeface(face);

        ((TextInputLayout) findViewById(R.id.textLayout_event_title)).setTypeface(face);
        ((TextInputLayout) findViewById(R.id.textLayout_event_description)).setTypeface(face);
        ((TextInputLayout) findViewById(R.id.textLayout_event_price)).setTypeface(face);
        ((TextInputLayout) findViewById(R.id.textLayout_event_Forum)).setTypeface(face);

        final Calendar calendar = Calendar.getInstance();
        s_year = calendar.get(Calendar.YEAR);
        s_month = calendar.get(Calendar.MONTH);
        s_day = calendar.get(Calendar.DAY_OF_MONTH);
        Log.d("Month", "Month: " + s_month);

        //Date andTime selector
        date = (ImageButton) findViewById(R.id.date);
        time = (ImageButton) findViewById(R.id.time);
    }


    ///------------------------------------------------------------------------------------------------------------------------////
    ///----------------------------------------------Date and Time Picker -----------------------------------------------------////
    ///------------------------------------------------------------------------------------------------------------------------////

    @Override
    protected Dialog onCreateDialog(int id) {
        if (id == DIALOG_ID)
            return new DatePickerDialog(this, datePicker, s_year, s_month, s_day);

        if (id == DIALOG_ID_TIME)
            return  new TimePickerDialog(AddEventActivity.this, timePicker, s_hour, s_minutes, true);
        return null;
    }

    private DatePickerDialog.OnDateSetListener datePicker = new DatePickerDialog.OnDateSetListener() {
        @Override
        public void onDateSet(DatePicker datePicker, int i, int i1, int i2) {
            int length = String.valueOf(i1).length();
            int length1 = String.valueOf(i2).length();
            if (length == 1)
            {
                m = Integer.toString(i1 + 1);
                StringBuilder sb = new StringBuilder();
                sb.append(0);
                sb.append(m);
                m = sb.toString();
            } else {
                m = Integer.toString(i1 + 1);
            }
            if (length1 == 1)
            {
                d = Integer.toString(i2);
                StringBuilder sb = new StringBuilder();
                sb.append(0);
                sb.append(d);
                d = sb.toString();

            } else {
                d = Integer.toString(i2);
            }
            s_year = i;

            date_text =   s_year + "-" + m + "-" + d;
            Log.d("DebugDate", "" + date_text);
            Toast.makeText(AddEventActivity.this, "Date: " + date_text, Toast.LENGTH_SHORT).show();
        }
    };


    private TimePickerDialog.OnTimeSetListener timePicker = new TimePickerDialog.OnTimeSetListener() {

        @Override
        public void onTimeSet(TimePicker timePicker, int hourOfDay, int minutesOfDay) {
            int length = String.valueOf(hourOfDay).length();
            int length1 = String.valueOf(minutesOfDay).length();
            if (length == 1)
            {
                h = Integer.toString(hourOfDay);
                StringBuilder sb = new StringBuilder();
                sb.append(0);
                sb.append(h);
                h = sb.toString();
            } else {
                h = Integer.toString(hourOfDay);
            }
            if (length1 == 1)
            {
                s = Integer.toString(minutesOfDay);
                StringBuilder sb = new StringBuilder();
                sb.append(0);
                sb.append(s);
                s = sb.toString();

            } else {
                s = Integer.toString(minutesOfDay);
            }

            time_text = h + ":" + s + ":00";
            Toast.makeText(AddEventActivity.this, "Time: " + time_text, Toast.LENGTH_SHORT).show();
        }
    };

}
