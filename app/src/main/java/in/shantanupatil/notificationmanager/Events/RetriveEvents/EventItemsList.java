package in.shantanupatil.notificationmanager.Events.RetriveEvents;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.Typeface;
import android.support.v4.app.NotificationCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.sql.Array;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import in.shantanupatil.notificationmanager.R;

public class EventItemsList extends AppCompatActivity implements DataPassing {

    RecyclerView recyclerView;

    List<ListItem> listItems;

    FirebaseDatabase mDatabase;
    DatabaseReference mRef;

    Controller controller;

    private MyAdapter adapter;

    NotificationCompat.Builder notification;
    private static final int notificationID = 69;

    ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_event_items_list);

        Toolbar toolbar_events = (Toolbar) findViewById(R.id.toolbar_events);
        setSupportActionBar(toolbar_events);
        toolbar_events.setTitleTextColor(Color.WHITE);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        mDatabase = FirebaseDatabase.getInstance();
        mRef = mDatabase.getReference().child("Events");

        progressDialog = new ProgressDialog(this);
        recyclerView = (RecyclerView) findViewById(R.id.dataRecycler);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        listItems = new ArrayList<>();

        controller = new Controller(this);


        progressDialog.setMessage("Loading..");
        progressDialog.show();
        mRef.orderByChild("date").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (DataSnapshot ds : dataSnapshot.getChildren()) {
                    final String key = (String) ds.getKey();
                    final DatabaseReference keyRef = FirebaseDatabase.getInstance().getReference().child("Events").child(key);
                    keyRef.addValueEventListener(new ValueEventListener() {
                        @Override
                        public void onDataChange(DataSnapshot dataSnapshot) {
                            try {
                                String title = dataSnapshot.child("title").getValue(String.class);
                                String description = dataSnapshot.child("description").getValue(String.class);
                                String forum = dataSnapshot.child("forum").getValue(String.class);
                                String price = dataSnapshot.child("price").getValue(String.class);
                                Long date = dataSnapshot.child("date").getValue(Long.class);
                                Date getDate = new Date(date);
                                String dates = getDate.toString();
                                ListItem listItem = new ListItem(title, description, dates, forum, price);
                                listItems.add(listItem);
                                adapter = new MyAdapter();
                                recyclerView.setAdapter(adapter);
                                adapter.notifyDataSetChanged();
                            } catch (Exception e) {

                            }
//                            try {
//                                Long now = System.currentTimeMillis();
//                                if (date < now) {
//                                    keyRef.removeValue();
//                                } else {
//                                    Date getDate = new Date(date);
//                                    if (getDate != null) {
//                                        String dates = getDate.toString();
//                                        ListItem listItem = new ListItem(title, description, dates);
//                                        listItems.add(listItem);
//                                    }
//                                    adapter = new MyAdapter();
//                                    recyclerView.setAdapter(adapter);
//                                    adapter.notifyDataSetChanged();
//                                }
//                            } catch (Exception e) {
//
//                            }
                            progressDialog.dismiss();
                        }

                        @Override
                        public void onCancelled(DatabaseError databaseError) {

                        }
                    });
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });


    }

    @Override
    public void startDetailActivity(String title, String description, String time, String forum, String price) {
        Intent intent = new Intent(getApplicationContext(), ActivityEventDetail.class);
        intent.putExtra("title", title);
        intent.putExtra("description", description);
        intent.putExtra("date", time);
        intent.putExtra("forum", forum);
        intent.putExtra("price", price);
        startActivity(intent);
    }

    private class MyAdapter extends RecyclerView.Adapter<MyAdapter.ViewHolder> {

        @Override
        public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View v = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.event_item, parent, false);
            return new ViewHolder(v, viewType);
        }

        @Override
        public void onBindViewHolder(final ViewHolder holder, int position) {

            ListItem listItem;
            listItem = listItems.get(position);
            holder.title.setText(listItem.getTitle());
            holder.description.setText(listItem.getDescription());
            holder.forum.setText(listItem.getForum());
            String text = listItem.getDate();

            String[] dates_parsed = text.split(" ");

            holder.month.setText(dates_parsed[1]);
            holder.day.setText(dates_parsed[2]);
            Log.d("StringText", "" + text);

        }

        @Override
        public int getItemCount() {
            return listItems.size();
        }

        public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

            TextView title;
            TextView description;
            TextView month;
            TextView day;
            TextView forum;
            LinearLayout linearLayout;

            public ViewHolder(View view, int ViewType) {
                super(view);
                title = (TextView) view.findViewById(R.id.eventTitle);
                Typeface face = Typeface.createFromAsset(getAssets(), "sansproregular.otf");
                title.setTypeface(face, Typeface.BOLD);
                title.setTextColor(Color.parseColor("#333333"));
                description = (TextView) view.findViewById(R.id.eventDescription);
                month = (TextView) view.findViewById(R.id.month);
                day = (TextView) view.findViewById(R.id.day);
                forum = (TextView) view.findViewById(R.id.forum);
                linearLayout = (LinearLayout) view.findViewById(R.id.linearLayout);
                this.linearLayout.setOnClickListener(this);
            }

            @Override
            public void onClick(View view) {
                ListItem listItem = listItems.get(this.getAdapterPosition());
                controller.OnListItemClicked(listItem);
            }
        }
    }

    @Override
    protected void onStart() {
        super.onStart();
        mRef.orderByChild("date").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (DataSnapshot ds: dataSnapshot.getChildren()){
                    final String key = (String) ds.getKey();
                    final DatabaseReference keyRef = FirebaseDatabase.getInstance().getReference().child("Events").child(key);
                    keyRef.addValueEventListener(new ValueEventListener() {
                        @Override
                        public void onDataChange(DataSnapshot dataSnapshot) {
                            Long date = dataSnapshot.child("date").getValue(Long.class);

                            try {
                                Long now = System.currentTimeMillis();
                                if (date < now) {
                                    keyRef.removeValue();
                                }
                            } catch (Exception e) {

                            }
                        }

                        @Override
                        public void onCancelled(DatabaseError databaseError) {

                        }
                    });
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }
}
