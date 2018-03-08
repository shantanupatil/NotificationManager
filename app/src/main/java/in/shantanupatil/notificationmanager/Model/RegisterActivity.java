package in.shantanupatil.notificationmanager.Model;

import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthEmailException;
import com.google.firebase.auth.FirebaseAuthUserCollisionException;
import com.google.firebase.auth.FirebaseAuthWeakPasswordException;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.messaging.FirebaseMessaging;

import br.com.simplepass.loading_button_lib.customViews.CircularProgressButton;
import in.shantanupatil.notificationmanager.NetworkTester;
import in.shantanupatil.notificationmanager.R;

public class RegisterActivity extends AppCompatActivity {


    //Whatever User Enters
    EditText email;
    EditText name;
    EditText password;

    public static String token;

    TextView text_signup;

    //SignUp Button
    CircularProgressButton register;

    //To Get Text from EditText inputs
    public String email_text, name_text, password_text, branch_text;

    public static String userID, message;

    //FirebaseAuth objects
    private FirebaseAuth mAuth;
    private FirebaseAuth.AuthStateListener authStateListener;

    //FirebaseDatabase Objects
    private FirebaseDatabase mdatabase;
    private DatabaseReference databaseReference;

    //Spinner
    Spinner branch;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        mAuth = FirebaseAuth.getInstance();
        mdatabase = FirebaseDatabase.getInstance();
        databaseReference = mdatabase.getReference();

        initialization();

        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                registerNewUser();
            }
        });
    }

    /*Adding References for All Views*/
    private void initialization() {

        Log.d("RegisterActivity", "Initialization of Views");
        email = (EditText) findViewById(R.id.register_email);
        name = (EditText) findViewById(R.id.fullName);
        password = (EditText) findViewById(R.id.register_password);
        branch = (Spinner) findViewById(R.id.branch);

        text_signup = (TextView) findViewById(R.id.text_signup);
        Typeface face = Typeface.createFromAsset(getAssets(), "sansproregular.otf");
        text_signup.setTypeface(face, Typeface.BOLD);
        text_signup.setTextColor(Color.parseColor("#333333"));

        email.setTypeface(face);
        password.setTypeface(face);
        name.setTypeface(face);

        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(RegisterActivity.this,
                R.layout.spinner_item, getResources().getStringArray(R.array.branch_name));

        arrayAdapter.setDropDownViewResource(R.layout.support_simple_spinner_dropdown_item);
        branch.setAdapter(arrayAdapter);

        register = (CircularProgressButton) findViewById(R.id.register);

        register.setTypeface(face);

        //((TextInputLayout) findViewById(R.id.textInput_email)).setTypeface(face);
        //((TextInputLayout) findViewById(R.id.textInput_name)).setTypeface(face);
        //((TextInputLayout) findViewById(R.id.textInput_password)).setTypeface(face);
    }

    public boolean check(String email, String password, String name, String branch) {
        if (email.equals("") || password.equals("") || name.equals("") || branch.equals("")) {
            message = "All fields are required";
            return false;
        }
        return true;
    }

    /*Register a new User by adding its Infomation directly in the Database
    * This method will call create user  method Available in the FirebaseAuth
    * and after that it will add the email and Password to database*/
    private void registerNewUser() {
        register.startAnimation();
        Log.d("RegisterActivity", "Registering New User");
        email_text = email.getText().toString();
        name_text = name.getText().toString();
        password_text = password.getText().toString();
        branch_text = branch.getSelectedItem().toString();
        String topic;
        topic = branch_text.replaceAll(" ", "");

        FirebaseMessaging.getInstance().subscribeToTopic("Users");
        FirebaseMessaging.getInstance().subscribeToTopic(topic);

        if (check(email_text, password_text, name_text,branch_text)) {
            mAuth.createUserWithEmailAndPassword(email_text, password_text)
                    .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull final Task<AuthResult> task) {
                            if (task.isSuccessful()) {
                                Log.d("RegisterActivity", "User Added Successfully");
                                databaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
                                    @Override
                                    public void onDataChange(DataSnapshot dataSnapshot) {

                                        String token = FirebaseInstanceId.getInstance().getToken();

                                        //Creating user Model
                                        User user = new User(email_text, name_text, branch_text, token);

                                        //getting userId to create new User on Database
                                        userID = mAuth.getCurrentUser().getUid();
                                        //creating new User
                                        databaseReference.child("Users")
                                                .child(userID)
                                                .setValue(user);
                                    }
                                    @Override
                                    public void onCancelled(DatabaseError databaseError) {

                                    }
                                });
                                Toast.makeText(RegisterActivity.this, "Registered Successfully\nCheck Inbox for Email verification", Toast.LENGTH_LONG).show();
                                FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
                                if (user != null) {
                                    Log.d("RegisterActivity", "Sending Verification Email");
                                    user.sendEmailVerification()
                                            .addOnCompleteListener(new OnCompleteListener<Void>() {
                                                @Override
                                                public void onComplete(@NonNull Task<Void> task) {
                                                    if (task.isSuccessful()) {
                                                    } else {
                                                        Handler handler = new Handler();
                                                        handler.postDelayed(new Runnable() {
                                                            @Override
                                                            public void run() {
                                                                Toast.makeText(getApplicationContext(), "Failed to Send Verification Email", Toast.LENGTH_SHORT).show();
                                                                register.revertAnimation();
                                                            }
                                                        }, 2000);
                                                    }
                                                }
                                            });
                                }
                                Handler handler = new Handler();
                                handler.postDelayed(new Runnable() {
                                    @Override
                                    public void run() {
                                        Intent intent = new Intent(RegisterActivity.this, LoginActivity.class);
                                        startActivity(
                                                intent
                                        );
                                        register.revertAnimation();
                                        finish();
                                    }
                                }, 2000);

                            } else {

                                Handler handler = new Handler();
                                handler.postDelayed(new Runnable() {
                                    @Override
                                    public void run() {

                                        try {
                                            throw task.getException();
                                        } catch (FirebaseAuthUserCollisionException getException) {
                                            Toast.makeText(RegisterActivity.this, "Email Already Exist", Toast.LENGTH_SHORT).show();
                                        } catch (FirebaseAuthWeakPasswordException weakPassword) {
                                            Toast.makeText(RegisterActivity.this, "Password is Weak", Toast.LENGTH_SHORT).show();
                                        } catch (Exception e) {
                                            Toast.makeText(RegisterActivity.this, "Something Went Wrong", Toast.LENGTH_SHORT).show();
                                        }

                                        Log.d("RegisterActivity", "Something went Wrong: ", task.getException());

                                        register.revertAnimation();
                                    }
                                }, 2000);

                            }
                        }
                    });
        } else {
            Handler handler = new Handler();
            handler.postDelayed(new Runnable() {
                @Override
                public void run() {
                    Toast.makeText(getApplicationContext(), "" + message, Toast.LENGTH_SHORT).show();
                    register.revertAnimation();
                }
            }, 2000);
        }
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Intent intent = new Intent(getApplicationContext(), LoginActivity.class);
        startActivity(intent);
    }

    @Override
    protected void onStart() {
        super.onStart();
        final NetworkTester networkTester = new NetworkTester(this);
        if (networkTester.isOnline()) {

        } else {
            try {
                AlertDialog.Builder builder = new AlertDialog.Builder(this);
                builder.setTitle("No internet Connection");
                builder.setMessage("Please turn on internet connection to continue");
                builder.setNegativeButton("Dismiss", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        if (networkTester.isOnline()) {
                            dialog.dismiss();
                        } else {
                            Toast.makeText(RegisterActivity.this, "Please Turn On Internet Connection", Toast.LENGTH_LONG).show();
                        }
                    }
                });
                AlertDialog alertDialog = builder.create();
                alertDialog.show();
            } catch (Exception e) {
                Log.d("ErrorInternet", "Show Dialog: " + e.getMessage());
            }
        }
    }
}
