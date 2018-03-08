package in.shantanupatil.notificationmanager;

import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.Toolbar;

import java.net.URL;

import in.shantanupatil.notificationmanager.Events.RetriveEvents.EventFragment;
import in.shantanupatil.notificationmanager.TandP.RetriveInformation.TNPFragment;

//import am.appwise.components.ni.NoInternetDialog;

public class HomePage extends AppCompatActivity {

    private TextView mTextMessage;

    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            FragmentManager fragmentManager = getSupportFragmentManager();
            FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
            switch (item.getItemId()) {
                case R.id.navigation_home:
                    Log.d("StringError", "Eror");
                    fragmentTransaction.replace(R.id.content, new FragmentHome()).commit();
                    return true;
                case R.id.navigation_profile:
                    fragmentTransaction.replace(R.id.content, new FragmentProfile()).commit();
                    return true;
                case R.id.navigation_tnp:
                    fragmentTransaction.replace(R.id.content, new TNPFragment()).commit();
                    return true;
            }
            return false;
        }
    };

//    NoInternetDialog noInternetDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_page);

        android.support.v7.widget.Toolbar toolbarmain = (android.support.v7.widget.Toolbar) findViewById(R.id.toolbarmain);
        setSupportActionBar(toolbarmain);
        toolbarmain.setTitleTextColor(Color.WHITE);


        BottomNavigationView navigation = (BottomNavigationView) findViewById(R.id.navigation);
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);

        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.content, new FragmentHome()).commit();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.navigationupper, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.navigation_about:
                Intent about = new Intent(getApplicationContext(), About.class);
                startActivity(about);
                return true;
            case R.id.navigation_contact:
                String[] emails = {"get.botifierx@gmail.com"};
                Intent intent = new Intent(Intent.ACTION_SENDTO);
                intent.setData(Uri.parse("mailto:"));
                intent.putExtra(Intent.EXTRA_EMAIL, emails);
                intent.putExtra(Intent.EXTRA_SUBJECT, "Contact Notifier");
                if (intent.resolveActivity(getPackageManager()) != null) {
                    startActivity(Intent.createChooser(intent, "Email Via"));
                }
                return true;
            case R.id.navigation_credits:
                Intent credits = new Intent(getApplicationContext(), Credits.class);
                startActivity(credits);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    protected void onStart() {
        super.onStart();

//        NetworkTester networkTester = new NetworkTester();
//        boolean status = networkTester.isOnline();
//        if (true) {
//            Toast.makeText(this, "Internet Connection", Toast.LENGTH_SHORT).show();
//        } else {
//            Toast.makeText(this, "Please Check Internet Connection", Toast.LENGTH_SHORT).show();
//        }
    }
}
