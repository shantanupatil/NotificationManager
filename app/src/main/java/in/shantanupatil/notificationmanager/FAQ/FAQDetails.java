package in.shantanupatil.notificationmanager.FAQ;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.Typeface;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.widget.TextView;

import in.shantanupatil.notificationmanager.R;

public class FAQDetails extends AppCompatActivity {

    TextView title;
    TextView description;

    CollapsingToolbarLayout collapsingTool;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_faqdetails);

        Toolbar toolbar_events = (Toolbar) findViewById(R.id.toolBarFaq);
        setSupportActionBar(toolbar_events);
        toolbar_events.setTitleTextColor(Color.WHITE);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        collapsingTool = (CollapsingToolbarLayout) findViewById(R.id.collapsingToolFAQ);
        title = (TextView) findViewById(R.id.title);
        description = (TextView) findViewById(R.id.description);

        Intent intent = getIntent();
        Typeface typeface = Typeface.createFromAsset(getAssets(), "sansproregular.otf");
        String title_text = intent.getStringExtra("title");
        String description_text = intent.getStringExtra("description");

        collapsingTool.setTitle(title_text);

        title.setText(title_text);
        title.setTypeface(typeface, Typeface.BOLD);
        description.setText(description_text);
        description.setTypeface(typeface);

    }
}
