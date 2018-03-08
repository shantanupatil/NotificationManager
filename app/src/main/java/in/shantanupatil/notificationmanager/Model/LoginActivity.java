package in.shantanupatil.notificationmanager.Model;

import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.Typeface;
import android.net.Uri;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthInvalidUserException;
import com.google.firebase.auth.FirebaseUser;

import br.com.simplepass.loading_button_lib.customViews.CircularProgressButton;
import in.shantanupatil.notificationmanager.HomePage;
import in.shantanupatil.notificationmanager.NetworkTester;
import in.shantanupatil.notificationmanager.R;

public class LoginActivity extends AppCompatActivity {


    //EdidText objects
    EditText email;
    EditText password;

    TextView text_signin;

    //Login button and register user textView
    CircularProgressButton login;
    TextView register;
    TextView forgetPassword;
    TextView verfication;

    //FirebaseAuth Object
    private FirebaseAuth mAuth;

    //To Get Text from EditText inputs
    public String email_text, password_text;

    private static String message;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        mAuth = FirebaseAuth.getInstance();

        initialization();
        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent reg = new Intent(LoginActivity.this, RegisterActivity.class);
                startActivity(reg);
            }
        });

        forgetPassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent forget = new Intent(LoginActivity.this, ForgetPassword.class);
                startActivity(forget);
            }
        });

//        //verfication.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
////                String[] emails = {"get.notifierx@gmail.com"};
////                Intent intent = new Intent(Intent.ACTION_SENDTO);
////                intent.setData(Uri.parse("mailto:"));
////                intent.putExtra(Intent.EXTRA_EMAIL, emails);
////                intent.putExtra(Intent.EXTRA_SUBJECT, "Need Help in Login");
////                if (intent.resolveActivity(getPackageManager()) != null) {
////                    startActivity(intent);
////                }
//            }
//        });
//
        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                loginUser();
            }
        });

    }


    private void initialization() {
        Typeface face = Typeface.createFromAsset(getAssets(), "sansproregular.otf");

        email = (EditText) findViewById(R.id.login_email);
        password = (EditText) findViewById(R.id.login_password);

        email.setTypeface(face);
        password.setTypeface(face);

        text_signin = (TextView) findViewById(R.id.text_signin);
        text_signin.setTypeface(face, Typeface.BOLD);
        text_signin.setTextColor(Color.parseColor("#333333"));

        login = (CircularProgressButton) findViewById(R.id.login);
        register = (TextView) findViewById(R.id.register);

        forgetPassword = (TextView) findViewById(R.id.forgetPassword);
        //verfication = (TextView) findViewById(R.id.verfication);

        forgetPassword.setTypeface(face);
        register.setTypeface(face);

//        ((TextInputLayout) findViewById(R.id.textInput_login_password)).setTypeface(face);
//        ((TextInputLayout) findViewById(R.id.textInput_login_email)).setTypeface(face);

    }

    public boolean check(String email, String password) {
        if (email.equals("") || password.equals("")) {
            message = "All fields are required";
            return false;
        }
        return true;
    }

    private void loginUser() {
        login.startAnimation();
        email_text = email.getText().toString();
        password_text = password.getText().toString();

        if (check(email_text, password_text)) {
            mAuth.signInWithEmailAndPassword(email_text, password_text)
                    .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull final Task<AuthResult> task) {
                            if (task.isSuccessful()) {
                                final FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
                                if (user.isEmailVerified()) {

                                    Handler handler = new Handler();
                                    handler.postDelayed(new Runnable() {
                                        @Override
                                        public void run() {
                                            Intent intent = new Intent(LoginActivity.this, HomePage.class);
                                            startActivity(intent);
//                                            killActivity();
                                        }
                                    }, 2000);

                                } else {

                                    Handler handler = new Handler();
                                    handler.postDelayed(new Runnable() {
                                        @Override
                                        public void run() {
                                            Toast.makeText(LoginActivity.this, "Email Not Verified\nCheck Spam if not received", Toast.LENGTH_LONG).show();
                                            user.sendEmailVerification();
                                            login.revertAnimation();
                                        }
                                    }, 2000);

                                }
                            } else {
                                Handler handler = new Handler();
                                handler.postDelayed(new Runnable() {
                                    @Override
                                    public void run() {
                                        try {
                                            throw task.getException();
                                        } catch (FirebaseAuthInvalidUserException m) {
                                            Toast.makeText(LoginActivity.this, "Email not registered", Toast.LENGTH_SHORT).show();
                                        } catch (Exception e) {
                                            Toast.makeText(getApplicationContext(), "Something went Wrong" + e, Toast.LENGTH_SHORT).show();
                                        }
                                        login.revertAnimation();
                                    }
                                }, 2000);

                            }
                        }});
        } else {

            Handler handler = new Handler();
            handler.postDelayed(new Runnable() {
                @Override
                public void run() {
                    Toast.makeText(getApplicationContext(), "" + message, Toast.LENGTH_SHORT).show();
                    login.revertAnimation();                                    }
            }, 2000);

        }
    }

//    private void killActivity() {
//        finish();
//    }

    @Override
    protected void onStart() {
        super.onStart();

        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        if (user != null && user.isEmailVerified()) {
            Intent intent = new Intent(getApplicationContext(), HomePage.class);
            startActivity(intent);
        }

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
                            Toast.makeText(LoginActivity.this, "Please Turn On Internet Connection", Toast.LENGTH_LONG).show();
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
