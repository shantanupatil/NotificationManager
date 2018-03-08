package in.shantanupatil.notificationmanager.TandP.RetriveInformation;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.Typeface;
import android.media.Image;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import org.w3c.dom.Text;

import in.shantanupatil.notificationmanager.R;

public class TNPDetail extends AppCompatActivity {

    public String title;
    public String description;
    public String Image;

    TextView tnpTitle;
    TextView tnpDescription;
    ImageView image;

    ImageView share_app_detail_tnp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tnpdetail);

        Intent intent = getIntent();
        title = intent.getStringExtra("Title");
        description = intent.getStringExtra("Description");
        Image = intent.getStringExtra("Image");

        tnpTitle = (TextView) findViewById(R.id.detail_title_tnp);
        Typeface typeFace = Typeface.createFromAsset(getAssets(), "sansproregular.otf");
        typeFace.isBold();
        tnpTitle.setTypeface(typeFace, Typeface.BOLD);
        tnpTitle.setTextColor(Color.parseColor("#333333"));
        tnpDescription = (TextView) findViewById(R.id.detail_description_tnp);
        image = (ImageView) findViewById(R.id.image_tnp_detail);
        share_app_detail_tnp = (ImageView) findViewById(R.id.share_app_detail_tnp);

        share_app_detail_tnp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent share = new Intent(Intent.ACTION_SEND);
                share.setType("text/plain");
                String message = Image;
                share.putExtra(Intent.EXTRA_TEXT, "" + title + "\n\n" + "Image Download " + message);
                startActivity(share.createChooser(share, "Share Via"));
            }
        });

        tnpTitle.setText(title);
        tnpDescription.setText(description);
        Picasso.with(this)
                .load(Image)
                .into(image);
    }
}
