package in.shantanupatil.notificationmanager.Staff.RetriveStaff;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.Typeface;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import in.shantanupatil.notificationmanager.R;

public class NoticeActivity extends AppCompatActivity implements Notice_Datapassing {

    //RecyclerView
    RecyclerView recyclerView;

    //List to Store Data
    List<NoticeList>  listOfData;

    //Adapter
    private MyAdapter myAdapter;

    //FirebaseReferemce
    FirebaseDatabase mDatabase;
    DatabaseReference mRef;

    DatabaseReference mData;

    //userID
    String userId;

    //branch
    public static String branch, branch_text;

    //ProgressBar
    ProgressBar progressBar;

    NoticeController controller;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notice);

        mDatabase = FirebaseDatabase.getInstance();
        mRef = mDatabase.getReference();

        //Progressbar
        progressBar = (ProgressBar) findViewById(R.id.progressBar_notice);

        //listData
        listOfData = new ArrayList<>();

        controller = new NoticeController(this);

        recyclerView= (RecyclerView) findViewById(R.id.recyclerView_notice);
        recyclerView.setHasFixedSize(true);

        LinearLayoutManager mLayoutManager = new LinearLayoutManager(this);
        mLayoutManager.setReverseLayout(true);
        mLayoutManager.setStackFromEnd(true);

        recyclerView.setLayoutManager(mLayoutManager);

        userId = FirebaseAuth.getInstance().getCurrentUser().getUid();

        mRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                DatabaseReference keyRef = FirebaseDatabase.getInstance().getReference().child("Users").child(userId);
                keyRef.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        progressBar.setVisibility(View.VISIBLE);
                        branch = dataSnapshot.child("branch").getValue(String.class);
                        branch_text = branch.replaceAll(" ", "");

                        mData = mDatabase.getReference().child(branch_text);
                        mData.orderByChild("date").addValueEventListener(new ValueEventListener() {
                            @Override
                            public void onDataChange(DataSnapshot dataSnapshot) {
                                for (DataSnapshot ds: dataSnapshot.getChildren()) {
                                    String key = ds.getKey();

                                    DatabaseReference ref =FirebaseDatabase.getInstance().getReference().child(branch_text).child(key);

                                    ref.addValueEventListener(new ValueEventListener() {
                                        @Override
                                        public void onDataChange(DataSnapshot dataSnapshot) {
                                            String title = dataSnapshot.child("title").getValue(String.class);
                                            String staff_name = dataSnapshot.child("staffName").getValue(String.class);
                                            String description = dataSnapshot.child("description").getValue(String.class);
                                            String imageURI = dataSnapshot.child("imageURI").getValue(String.class);
                                            String title_changed = title.substring(0,1).toUpperCase() + title.substring(1, title.length());
                                            Long time = dataSnapshot.child("date").getValue(Long.class);

                                            Date getDate = new Date(time);

                                            String date = getDate.toString();

                                            NoticeList noticeList = new NoticeList(title_changed, description, imageURI, date, staff_name);

                                            listOfData.add(noticeList);

                                            myAdapter = new MyAdapter();
                                            recyclerView.setAdapter(myAdapter);
                                            myAdapter.notifyDataSetChanged();
                                            progressBar.setVisibility(View.GONE);
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
                    public void onCancelled(DatabaseError databaseError) {

                    }
                });
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });


    }

    @Override
    public void startDetailActivity(String title, String description, String imageURI) {
        Intent intent = new Intent(getApplicationContext(), NoticeDetailActivity.class);
        intent.putExtra("Title", title);
        intent.putExtra("Description", description);
        intent.putExtra("Image", imageURI);
        startActivity(intent);
    }


    private class MyAdapter extends RecyclerView.Adapter<MyAdapter.ViewHolder> {

        @Override
        public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View v = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.notice_list, parent, false);

            return new ViewHolder(v);
        }

        @Override
        public void onBindViewHolder(ViewHolder holder, int position) {
            final NoticeList noticeList = listOfData.get(position);

            holder.title.setText(noticeList.getTitle());
            holder.description.setText(noticeList.getDescription());
            String[] dates_array = noticeList.getDate().split(" ");
            holder.time_notice.setText(dates_array[0] + " " + dates_array[1] + " " + dates_array[dates_array.length - 1] );
            holder.staffName.setText(noticeList.getStaffName());
            holder.setImage(getApplicationContext(), noticeList.getImageURI());
        }

        @Override
        public int getItemCount() {
            return listOfData.size();
        }

        public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{

            TextView title;
            TextView description;
            TextView time_notice;
            TextView staffName;

            ImageView imageView;
            RelativeLayout relativelayout_notice;


            public ViewHolder(View itemView) {
                super(itemView);

                title = (TextView)  itemView.findViewById(R.id.notice_list_title);
                time_notice = (TextView)  itemView.findViewById(R.id.time_notice);
                staffName= (TextView)  itemView.findViewById(R.id.staffName);

                description = (TextView)  itemView.findViewById(R.id.notice_list_description);
                imageView = (ImageView)  itemView.findViewById(R.id.image_notice);

                Typeface typeFace = Typeface.createFromAsset(getAssets(), "sansproregular.otf");
                title.setTypeface(typeFace, Typeface.BOLD);
                title.setTextColor(Color.parseColor("#333333"));

                relativelayout_notice = (RelativeLayout) itemView.findViewById(R.id.relativelayout_notice);
                this.relativelayout_notice.setOnClickListener(this);
            }

            public void setImage(Context applicationContext, String image) {
                Picasso.with(applicationContext)
                        .load(image)
                        .config(Bitmap.Config.ARGB_4444)
                        .fit()
                        .centerCrop()
                        .into(imageView);
            }

            @Override
            public void onClick(View view) {
                NoticeList noticeList = listOfData.get(getAdapterPosition());
                controller.onItemClick(noticeList);
            }
        }
    }
}
