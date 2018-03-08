package in.shantanupatil.notificationmanager;


import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import br.com.simplepass.loading_button_lib.customViews.CircularProgressButton;
import in.shantanupatil.notificationmanager.Model.LoginActivity;


/**
 * A simple {@link Fragment} subclass.
 */
public class FragmentProfile extends Fragment {


    public FragmentProfile() {
        // Required empty public constructor
    }


    FirebaseAuth mAuth;
    String userId;

    FirebaseDatabase mDatabase;
    DatabaseReference mRef;

    public static String email, branch, name;

    TextView textViewname, textViewemail, textViewbranch;

    ImageView image_email, image_branch;

    private ProgressBar progressBar;
    Button signOut;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_fragment_profile, container, false);

        getActivity().setTitle("User Profile");

        mAuth = FirebaseAuth.getInstance();
        mDatabase = FirebaseDatabase.getInstance();
        mRef = mDatabase.getReference();

        userId = mAuth.getCurrentUser().getUid();

        progressBar = (ProgressBar) view.findViewById(R.id.progress_user);

        progressBar.setVisibility(View.GONE);
        signOut = (Button) view.findViewById(R.id.signout);
        textViewname = (TextView) view.findViewById(R.id.user_name);
        textViewemail = (TextView) view.findViewById(R.id.user_email);
        textViewbranch = (TextView) view.findViewById(R.id.user_branch);


        signOut.setVisibility(View.GONE);


        signOut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                signOutUser();
            }
        });


        mRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                DatabaseReference keyRef = FirebaseDatabase.getInstance().getReference().child("Users").child(userId);
                keyRef.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        name = dataSnapshot.child("name").getValue(String.class);
                        branch = dataSnapshot.child("branch").getValue(String.class);
                        email = dataSnapshot.child("email").getValue(String.class);
                        textViewemail.setText(email);

                        textViewbranch.setText(branch);
                        textViewname.setText(name);

                        signOut.setVisibility(View.VISIBLE);
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

    private void signOutUser() {
        progressBar.setVisibility(View.VISIBLE);
        FirebaseAuth.getInstance().signOut();
        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                getActivity().finish();
                Intent intent = new Intent(getContext(), LoginActivity.class);
                startActivity(intent);
                progressBar.setVisibility(View.GONE);
            }
        }, 2000);
    }

}
