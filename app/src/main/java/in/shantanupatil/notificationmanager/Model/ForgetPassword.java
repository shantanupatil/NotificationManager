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
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthInvalidUserException;

import br.com.simplepass.loading_button_lib.customViews.CircularProgressButton;
import in.shantanupatil.notificationmanager.NetworkTester;
import in.shantanupatil.notificationmanager.R;



public class ForgetPassword extends AppCompatActivity {

    EditText forget;
    CircularProgressButton forget_button;

    String email_text;

    TextView forget_text_description;
    TextView forget_text;

    FirebaseAuth mAuth;

    private static String message;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forget_password);

        mAuth = FirebaseAuth.getInstance();

        forget_text_description = (TextView) findViewById(R.id.forget_text_description);
        forget_text = (TextView) findViewById(R.id.forget_text);

        Typeface face = Typeface.createFromAsset(getAssets(), "sansproregular.otf");
        forget_text.setTypeface(face, Typeface.BOLD);
        forget_text.setTextColor(Color.parseColor("#333333"));

        Typeface typeface = Typeface.createFromAsset(getAssets(), "sansproregular.otf");
        forget_text.setTypeface(typeface);

        Typeface typefacedescription = Typeface.createFromAsset(getAssets(), "sansproregular.otf");
        forget_text.setTypeface(typefacedescription);
        forget_text_description.setTypeface(typefacedescription);

        //((TextInputLayout) findViewById(R.id.input_layout_password)).setTypeface(face);

        forget = (EditText) findViewById(R.id.forget_email);
        forget.setTypeface(typeface);
        forget_button = (CircularProgressButton) findViewById(R.id.forget_email_button);

        forget_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                sendResetEmail();
            }
        });
    }

    public boolean checkInput(String email) {
        if (email.equals("")) {
            message = "Enter E-mail";
            return false;
        }
        return true;
    }

    private void sendResetEmail() {
    forget_button.startAnimation();
        email_text = forget.getText().toString();

        if (checkInput(email_text)) {
            mAuth.sendPasswordResetEmail(email_text)
                    .addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull final Task<Void> task) {
                            if (task.isSuccessful()) {
                                Handler handler = new Handler();
                                handler.postDelayed(new Runnable() {
                                    @Override
                                    public void run() {
                                        Toast.makeText(ForgetPassword.this, "Email sent", Toast.LENGTH_SHORT).show();
                                        Intent log = new Intent(getApplicationContext(), LoginActivity.class);
                                        startActivity(log);
                                    }
                                }, 1000);
                            } else {
                                Handler handler = new Handler();
                                handler.postDelayed(new Runnable() {
                                    @Override
                                    public void run() {
                                        try {
                                            throw task.getException();
                                        } catch (FirebaseAuthInvalidUserException m) {
                                            Toast.makeText(ForgetPassword.this, "Email not registered", Toast.LENGTH_SHORT).show();
                                        } catch (Exception e) {
                                            Toast.makeText(getApplicationContext(), "Something went Wrong", Toast.LENGTH_SHORT).show();
                                        }
                                        forget_button.revertAnimation();
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
                    forget_button.revertAnimation();                                    }
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
                            Toast.makeText(ForgetPassword.this, "Please Turn On Internet Connection", Toast.LENGTH_LONG).show();
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
