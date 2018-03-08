package in.shantanupatil.notificationmanager.Events.RetriveEvents;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.Typeface;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import in.shantanupatil.notificationmanager.R;


public class EventFragment extends Fragment implements DataPassing {

    public EventFragment() {
        // Required empty public constructor
    }

    RecyclerView fragment_recycler;

    List<ListItem> listItems;

    FirebaseDatabase mDatabase;
    DatabaseReference mRef;

    Controller controller;

    private MyAdapter adapter;


    ProgressDialog progressDialog;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_event, container, false);
        mDatabase = FirebaseDatabase.getInstance();
        mRef = mDatabase.getReference().child("Events");

        progressDialog = new ProgressDialog(getActivity());
        fragment_recycler = (RecyclerView) view.findViewById(R.id.fragment_recycler);
        fragment_recycler.setHasFixedSize(true);
        fragment_recycler.setLayoutManager(new LinearLayoutManager(getActivity()));

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
                                fragment_recycler.setAdapter(adapter);
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


        return view;
    }

    @Override
    public void startDetailActivity(String title, String description, String time, String forum, String price) {
        Intent intent = new Intent(getContext(), ActivityEventDetail.class);
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
                Typeface face = Typeface.createFromAsset(getActivity().getAssets(), "sansproregular.otf");
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
}
