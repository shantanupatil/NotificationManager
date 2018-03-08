package in.shantanupatil.notificationmanager.TandP.RetriveInformation;

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

public class TNP extends AppCompatActivity implements TNP_Data {

    RecyclerView recyclerView;

    List<TnpList> tnpLists;

    public ProgressBar progressBar;

    FirebaseDatabase mDatabase;
    DatabaseReference mRef;

    private MyAdapter adapter;

    TNPController controller_tnp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tnp);

        mDatabase = FirebaseDatabase.getInstance();
        mRef = mDatabase.getReference().child("TNP");

        controller_tnp = new TNPController(this);

        recyclerView= (RecyclerView) findViewById(R.id.recyclerView_tnp);
        recyclerView.setHasFixedSize(true);

        LinearLayoutManager mLayoutManager = new LinearLayoutManager(this);
        mLayoutManager.setReverseLayout(true);
        mLayoutManager.setStackFromEnd(true);

        recyclerView.setLayoutManager(mLayoutManager);
        progressBar = (ProgressBar) findViewById(R.id.progressBar_tnp);

        tnpLists = new ArrayList<>();


        mRef.orderByChild("date").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                progressBar.setVisibility(View.VISIBLE);
                for (DataSnapshot ds: dataSnapshot.getChildren()) {
                    String key = (String) ds.getKey();

                    DatabaseReference keyRef = FirebaseDatabase.getInstance().getReference().child("TNP").child(key);

                    keyRef.addValueEventListener(new ValueEventListener() {
                        @Override
                        public void onDataChange(DataSnapshot dataSnapshot) {
                            String title = dataSnapshot.child("title").getValue(String.class);
                            String description = dataSnapshot.child("description").getValue(String.class);
                            String imageUrl = dataSnapshot.child("imageURI").getValue(String.class);
                            Long time = dataSnapshot.child("date").getValue(Long.class);
                            Date getDate = new Date(time);
                            String time_value = getDate.toString();

                            TnpList list = new TnpList(title, description, imageUrl, time_value);

                            tnpLists.add(list);

                            adapter= new MyAdapter();
                            recyclerView.setAdapter(adapter);
                            adapter.notifyDataSetChanged();
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
    public void startTnpDetail(String title, String description, String imageURI) {
        Intent intent = new Intent(getApplicationContext(), TNPDetail.class);
        intent.putExtra("Title", title);
        intent.putExtra("Description", description);
        intent.putExtra("Image", imageURI);
        startActivity(intent);
    }

    private class MyAdapter extends RecyclerView.Adapter<MyAdapter.ViewHolder> {

        @Override
        public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

            View view = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.tnp_list, parent, false);

            return new ViewHolder(view);
        }

        @Override
        public void onBindViewHolder(ViewHolder holder, int position) {

            final TnpList tnpList = tnpLists.get(position);

            holder.tnp_list_title.setText(tnpList.getTitle());
            holder.tnp_list_description.setText(tnpList.getDescription());
            String[] dates_array = tnpList.getTime().split(" ");
            holder.time.setText(dates_array[0] + " " + dates_array[1] + " " + dates_array[dates_array.length - 1] );
            holder.setImage(getApplicationContext(), tnpList.getImage());

        }


        @Override
        public int getItemCount() {
            return tnpLists.size();
        }

        public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

            TextView tnp_list_description;
            ImageView imageURI;
            TextView time;
            TextView tnp_list_title;
            RelativeLayout relativeLayout;

            public ViewHolder(View itemView) {
                super(itemView);

                tnp_list_description = (TextView) itemView.findViewById(R.id.tnp_list_description);
                tnp_list_title = (TextView) itemView.findViewById(R.id.tnp_list_title);
                Typeface typeFace = Typeface.createFromAsset(getAssets(), "sansproregular.otf");
                typeFace.isBold();
                tnp_list_title.setTypeface(typeFace, Typeface.BOLD);
                tnp_list_title.setTextColor(Color.parseColor("#333333"));
                Typeface typeFace1 = Typeface.createFromAsset(getAssets(), "sansproregular.otf");
                tnp_list_description.setTypeface(typeFace1);
                imageURI = (ImageView) itemView.findViewById(R.id.image_tnp);
                time = (TextView) itemView.findViewById(R.id.time);

                relativeLayout = (RelativeLayout) itemView.findViewById(R.id.linearLayout_tnp);

                this.relativeLayout.setOnClickListener(this);
            }

            @Override
            public void onClick(View view) {
                TnpList list = tnpLists.get(this.getAdapterPosition());
                controller_tnp.onItemClicked(list);
            }
            public void setImage(Context applicationContext, String image) {
                Picasso.with(applicationContext)
                        .load(image)
                        .config(Bitmap.Config.ARGB_4444)
                        .fit()
                        .centerCrop()
                        .into(imageURI);
            }
        }
    }
}
