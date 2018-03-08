package in.shantanupatil.notificationmanager;

import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Typeface;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

public class About extends AppCompatActivity implements View.OnClickListener{

    TextView guide_name;
    TextView name_shantanu;
    TextView name_pranali;
    TextView name_akshay;
    TextView name_tapan;

    ImageView guide_email;
    ImageView guide_linkedIn;
    ImageView shantanu_email;
    ImageView shantanu_linkedIn;
    ImageView pranali_email;
    ImageView pranali_linkedIn;
    ImageView akshay_email;
    ImageView akshay_linkedIn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about);

        guide_name = (TextView) findViewById(R.id.guide_name);
        name_shantanu = (TextView) findViewById(R.id.name_shantanu);
        name_pranali = (TextView) findViewById(R.id.name_pranali);
        name_akshay = (TextView) findViewById(R.id.name_akshay);
        name_tapan = (TextView) findViewById(R.id.name_tapan);


        guide_email = (ImageView) findViewById(R.id.guide_email);

        shantanu_email = (ImageView) findViewById(R.id.shantanu_email);
        shantanu_linkedIn = (ImageView) findViewById(R.id.shantanu_linkedIn);
        pranali_email = (ImageView) findViewById(R.id.pranali_email);
        pranali_linkedIn = (ImageView) findViewById(R.id.pranali_linkedIn);
        akshay_linkedIn = (ImageView) findViewById(R.id.akshay_linkedIn);
        akshay_email = (ImageView) findViewById(R.id.akshay_email);

        guide_email.setOnClickListener(this);
        shantanu_email.setOnClickListener(this);
        shantanu_linkedIn.setOnClickListener(this);
        pranali_email.setOnClickListener(this);
        pranali_linkedIn.setOnClickListener(this);
        akshay_email.setOnClickListener(this);
        akshay_linkedIn.setOnClickListener(this);


        Typeface typeface = Typeface.createFromAsset(getAssets(), "sansproregular.otf");
        guide_name.setTypeface(typeface, Typeface.BOLD);
        name_shantanu.setTypeface(typeface, Typeface.BOLD);
        name_pranali.setTypeface(typeface, Typeface.BOLD);
        name_akshay.setTypeface(typeface, Typeface.BOLD);
        name_tapan.setTypeface(typeface, Typeface.BOLD);

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
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.guide_email:
                String[] guide_emails = {"sanjaykhajure@gmail.com"};
                Intent guide_email = new Intent(Intent.ACTION_SENDTO);
                guide_email.setData(Uri.parse("mailto:"));
                guide_email.putExtra(Intent.EXTRA_EMAIL, guide_emails);
                guide_email.putExtra(Intent.EXTRA_SUBJECT, "Contacting From Notification Manager");
                if (guide_email.resolveActivity(getPackageManager()) != null) {
                    startActivity(guide_email.createChooser(guide_email, "Email Via"));
                }
                break;
            case R.id.shantanu_email:
                String[] shantanu_emails = {"ishantanu3@gmail.com", "ishantanu@hotmail.com"};
                Intent shantanu_email_intent = new Intent(Intent.ACTION_SENDTO);
                shantanu_email_intent.setData(Uri.parse("mailto:"));
                shantanu_email_intent.putExtra(Intent.EXTRA_EMAIL, shantanu_emails);
                shantanu_email_intent.putExtra(Intent.EXTRA_SUBJECT, "Contact Notifier");
                if (shantanu_email_intent.resolveActivity(getPackageManager()) != null) {
                    startActivity(Intent.createChooser(shantanu_email_intent, "Email Via"));
                }
                break;
            case R.id.shantanu_linkedIn:
                Intent shantanu_linkedIn = new Intent(Intent.ACTION_VIEW, Uri.parse("https://linkedin.com/in/ishantanupatil"));
                startActivity(shantanu_linkedIn.createChooser(shantanu_linkedIn, "Open Via"));
                break;
            case R.id.pranali_email:
                String[] pranali_emails = {"pranalikalambe917@gmail.com"};
                Intent pranali_email_intent = new Intent(Intent.ACTION_SENDTO);
                pranali_email_intent.setData(Uri.parse("mailto:"));
                pranali_email_intent.putExtra(Intent.EXTRA_EMAIL, pranali_emails);
                pranali_email_intent.putExtra(Intent.EXTRA_SUBJECT, "Contacting From Notification Manager");
                if (pranali_email_intent.resolveActivity(getPackageManager()) != null) {
                    startActivity(Intent.createChooser(pranali_email_intent, "Email Via"));
                }
                break;
            case R.id.pranali_linkedIn:
                Intent pranali_linkedIn = new Intent(Intent.ACTION_VIEW, Uri.parse("https://linkedin.com/in/pranali-kalambe-8023a1151"));
                startActivity(pranali_linkedIn.createChooser(pranali_linkedIn, "Open Via"));
                break;
            case R.id.akshay_email:
                String[] akshay_emails = {"akshaychatur123@gmail.com"};
                Intent akshay_email_intent = new Intent(Intent.ACTION_SENDTO);
                akshay_email_intent.setData(Uri.parse("mailto:"));
                akshay_email_intent.putExtra(Intent.EXTRA_EMAIL, akshay_emails);
                akshay_email_intent.putExtra(Intent.EXTRA_SUBJECT, "Contacting From Notification Manager");
                if (akshay_email_intent.resolveActivity(getPackageManager()) != null) {
                    startActivity(Intent.createChooser(akshay_email_intent, "Email Via"));
                }
                break;
            case R.id.akshay_linkedIn:
                Intent akshay_linkedIn = new Intent(Intent.ACTION_VIEW, Uri.parse("https://linkedin.com/in/akshay-chatur-447287128"));
                startActivity(akshay_linkedIn.createChooser(akshay_linkedIn, "Open Via"));
                break;
//            case R.id.tapan_email:
//                String[] tapan_emails = {""};
//                Intent tapan_email_intent = new Intent(Intent.ACTION_SENDTO);
//                tapan_email_intent.setData(Uri.parse("mailto:"));
//                tapan_email_intent.putExtra(Intent.EXTRA_EMAIL, tapan_emails);
//                tapan_email_intent.putExtra(Intent.EXTRA_SUBJECT, "Contacting From Notification Manager");
//                if (tapan_email_intent.resolveActivity(getPackageManager()) != null) {
//                    startActivity(Intent.createChooser(tapan_email_intent, "Email Via"));
//                }
//            case R.id.tapan_linkedIn:
//                Toast.makeText(this, "Akshay LinkedIn", Toast.LENGTH_SHORT).show();
        }
    }
}
