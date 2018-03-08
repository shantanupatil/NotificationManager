package in.shantanupatil.notificationmanager;


import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.facebook.shimmer.ShimmerFrameLayout;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import in.shantanupatil.notificationmanager.Events.AddEvents.AddEventActivity;
import in.shantanupatil.notificationmanager.Events.RetriveEvents.EventItemsList;
import in.shantanupatil.notificationmanager.FAQ.FAQActivity;
import in.shantanupatil.notificationmanager.Staff.AddStaffMessage.AddStaff;
import in.shantanupatil.notificationmanager.Staff.RetriveStaff.NoticeActivity;
import in.shantanupatil.notificationmanager.Staff.RetriveStaff.NoticeController;
import in.shantanupatil.notificationmanager.Staff.RetriveStaff.NoticeDetailActivity;
import in.shantanupatil.notificationmanager.Staff.RetriveStaff.NoticeList;
import in.shantanupatil.notificationmanager.Staff.RetriveStaff.Notice_Datapassing;
import in.shantanupatil.notificationmanager.TandP.AddInformation.AddTnp;
import in.shantanupatil.notificationmanager.TandP.RetriveInformation.TNP;
import in.shantanupatil.notificationmanager.TandP.RetriveInformation.TNPController;
import in.shantanupatil.notificationmanager.TandP.RetriveInformation.TNP_Data;
import in.shantanupatil.notificationmanager.TandP.RetriveInformation.TnpList;


/**
 * A simple {@link Fragment} subclass.
 */
public class FragmentHome extends Fragment implements Notice_Datapassing{


    public FragmentHome() {
        // Required empty public constructor
    }

    LinearLayout events;
    LinearLayout faq;
    LinearLayout tnp;
    LinearLayout notice;

    LinearLayout addnotice;
    LinearLayout addtnp;
    LinearLayout addevents;


    TextView notice_home;
    TextView notice_admin;
    TextView notice_stuff;
    TextView notifier;

    ImageView shareApp;
    private ShimmerFrameLayout mShimmerViewContainer;

    //RecyclerViews Notice
    RecyclerView recycler_home_notice;
    NoticeAdapter noticeAdapter;
    List<NoticeList> listOfData;
    //Firebase Objects
    FirebaseDatabase mDatabase;
    DatabaseReference mRef;
    DatabaseReference mData;
    String userId;
    //branch text
    public static String branch, branch_text;
    //controller to handle click events
    NoticeController controller;

    ImageView background_image;

    LinearLayout admin_layout;
    LinearLayout staff_hide_layout;
    LinearLayout linearLayout_tnp_hide;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_fragment_home, container, false);

        getActivity().setTitle("Home");

        mShimmerViewContainer = view.findViewById(R.id.shimmer_view_container);
        //background_image = (ImageView) view.findViewById(R.id.background_image);

//        Bitmap bitmap = BitmapFactory.decodeResource(getContext().getResources(), R.drawable.background);
//        background_image.setImageBitmap(bitmap);
//        //Picasso.with(getContext()).load(bitmap).into(background_image);
        //Views
        events = (LinearLayout) view.findViewById(R.id.events);
        faq = (LinearLayout) view.findViewById(R.id.faq);
        tnp = (LinearLayout) view.findViewById(R.id.tnp);
        notice = (LinearLayout) view.findViewById(R.id.notice);

        addnotice = (LinearLayout) view.findViewById(R.id.addnotice);
        linearLayout_tnp_hide = (LinearLayout) view.findViewById(R.id.linearLayout_tnp_hide);
        staff_hide_layout = (LinearLayout) view.findViewById(R.id.staff_hide_layout);
        admin_layout = (LinearLayout) view.findViewById(R.id.layout_admin);
        addtnp = (LinearLayout) view.findViewById(R.id.add_tnp);
        addevents = (LinearLayout) view.findViewById(R.id.addevents);


        admin_layout.setVisibility(View.GONE);

        notice_home = (TextView) view.findViewById(R.id.notice_home);
        notice_stuff = (TextView) view.findViewById(R.id.notice_stuff);
        notifier = (TextView) view.findViewById(R.id.notifier);
        notice_admin = (TextView) view.findViewById(R.id.notice_admin);

        shareApp = (ImageView) view.findViewById(R.id.shareApp);
        shareApp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Intent.ACTION_SEND);
                intent.setType("text/plain");
                String link = "Download: https://play.google.com/store/apps/details?id=in.shantanupatil.notificationmanager";
                String message = "Greetings. Have you tried KITS Notifier? If you belong to KITS College this " +
                        "Application will tell you everything you need to know\n\n";
                intent.putExtra(Intent.EXTRA_TEXT, "" + message + "" + link);
                startActivity(Intent.createChooser(intent, "Share Via"));
            }
        });




        addevents.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(), AddEventActivity.class);
                startActivity(intent);
            }
        });

        addtnp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(), AddTnp.class);
                startActivity(intent);
            }
        });

        addnotice.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(), AddStaff.class);
                startActivity(intent);
            }
        });

        faq.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(), FAQActivity.class);
                startActivity(intent);
            }
        });

        Typeface typeFace = Typeface.createFromAsset(getActivity().getAssets(), "sansproregular.otf");

        notice_home.setTypeface(typeFace, Typeface.BOLD);
        notice_home.setTextColor(Color.parseColor("#F44336"));

        notice_admin.setTypeface(typeFace, Typeface.BOLD);
        notice_admin.setTextColor(Color.parseColor("#F44336"));

        notice_stuff.setTypeface(typeFace, Typeface.BOLD);
        notice_stuff.setTextColor(Color.parseColor("#F44336"));

        notifier.setTypeface(typeFace, Typeface.BOLD);

        events.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(), EventItemsList.class);
                startActivity(intent);
            }
        });

        faq.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(), FAQActivity.class);
                startActivity(intent);
            }
        });

        notice.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(), NoticeActivity.class);
                startActivity(intent);
            }
        });


        tnp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(), TNP.class);
                startActivity(intent);
            }
        });

        //<!---------------------------------------------------------------------------------------------------------------------!>//
        //<!----------------------------------------//Recycler View for Notice displayed On Homepage//----------------------------!>
        //<!---------------------------------------------------------------------------------------------------------------------!>//

        recycler_home_notice = (RecyclerView) view.findViewById(R.id.recycler_home_notice);
        recycler_home_notice.setHasFixedSize(true);

        mDatabase = FirebaseDatabase.getInstance();
        mRef = mDatabase.getReference();

        //listData
        listOfData = new ArrayList<>();

        controller = new NoticeController(this);

        LinearLayoutManager mLayoutManager = new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, true);
        mLayoutManager.setReverseLayout(true);
        mLayoutManager.setStackFromEnd(true);
        recycler_home_notice.setLayoutManager(mLayoutManager);

        userId = FirebaseAuth.getInstance().getCurrentUser().getUid();

        mDatabase = FirebaseDatabase.getInstance();
        mRef = mDatabase.getReference();

        mRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                DatabaseReference keyRef = FirebaseDatabase.getInstance().getReference().child("Users").child(userId);
                keyRef.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
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

                                            noticeAdapter = new NoticeAdapter();
                                            recycler_home_notice.setAdapter(noticeAdapter);
                                            noticeAdapter.notifyDataSetChanged();
                                            // stop animating Shimmer and hide the layout
                                            mShimmerViewContainer.stopShimmerAnimation();
                                            mShimmerViewContainer.setVisibility(View.GONE);
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

        return view;

    }

    @Override
    public void startDetailActivity(String title, String description, String imageURI) {
        Intent intent = new Intent(getContext(), NoticeDetailActivity.class);
        intent.putExtra("Title", title);
        intent.putExtra("Description", description);
        intent.putExtra("Image", imageURI);
        startActivity(intent);
    }

    private class NoticeAdapter extends RecyclerView.Adapter<NoticeAdapter.ViewHolder> {

        @Override
        public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View v = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.notice_list_horizontal, parent, false);
            return new ViewHolder(v);
        }

        @Override
        public void onBindViewHolder(ViewHolder holder, int position) {

            final NoticeList noticeList = listOfData.get(position);
            holder.title.setText(noticeList.getTitle());
            String[] dates_array = noticeList.getDate().split(" ");
            holder.time_notice.setText(dates_array[0] + " " + dates_array[1] + " " + dates_array[dates_array.length - 1] );
            holder.staffName.setText(noticeList.getStaffName());
            holder.setImage(getContext(), noticeList.getImageURI());

        }

        @Override
        public int getItemCount() {
            return listOfData.size();
        }

        public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{

            TextView title;
            ImageView imageView;
            RelativeLayout relativelayout_notice;
            TextView staffName;
            TextView time_notice;

            public ViewHolder(View itemView) {
                super(itemView);

                title = (TextView)  itemView.findViewById(R.id.notice_list_title);
                time_notice = (TextView)  itemView.findViewById(R.id.time_notice);
                staffName= (TextView)  itemView.findViewById(R.id.staffName);
                imageView = (ImageView)  itemView.findViewById(R.id.image_notice);
                Typeface typeFace = Typeface.createFromAsset(getActivity().getAssets(), "sansproregular.otf");
                title.setTypeface(typeFace, Typeface.BOLD);
                title.setTextColor(Color.parseColor("#333333"));
                staffName.setTypeface(typeFace);
                staffName.setTextColor(Color.parseColor("#333333"));
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

    @Override
    public void onResume() {
        super.onResume();
        mShimmerViewContainer.startShimmerAnimation();
    }

    @Override
    public void onPause() {
        mShimmerViewContainer.stopShimmerAnimation();
        super.onPause();
    }


    @Override
    public void onStart() {
        super.onStart();
        String user = FirebaseAuth.getInstance().getCurrentUser().getEmail();
        if (user.equals("admin_notifier@studentrevise.com")) {
            admin_layout.setVisibility(View.VISIBLE);
            addevents.setVisibility(View.VISIBLE);
            notice_admin.setVisibility(View.VISIBLE);
            addtnp.setVisibility(View.VISIBLE);
            addnotice.setVisibility(View.VISIBLE);
        } else if (user.equals("tnp@studentrevise.com")) {
            admin_layout.setVisibility(View.VISIBLE);
            notice_admin.setVisibility(View.VISIBLE);
            addtnp.setVisibility(View.VISIBLE);
            addevents.setVisibility(View.GONE);
            linearLayout_tnp_hide.setVisibility(View.GONE);
        } else if (user.equals("events@studentrevise.com")) {
            admin_layout.setVisibility(View.VISIBLE);
            notice_admin.setVisibility(View.VISIBLE);
            addtnp.setVisibility(View.GONE);
            addevents.setVisibility(View.VISIBLE);
            linearLayout_tnp_hide.setVisibility(View.GONE);
        } else if (user.equals("staff@studentrevise.com")) {
            notice_admin.setVisibility(View.VISIBLE);
            addnotice.setVisibility(View.VISIBLE);
            admin_layout.setVisibility(View.VISIBLE);
            notice_admin.setVisibility(View.VISIBLE);
            staff_hide_layout.setVisibility(View.GONE);
        }
    }
}
