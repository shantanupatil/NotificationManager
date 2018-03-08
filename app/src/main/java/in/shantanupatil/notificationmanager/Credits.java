package in.shantanupatil.notificationmanager;

import android.graphics.Typeface;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Html;
import android.widget.TextView;

import java.lang.reflect.Type;

public class Credits extends AppCompatActivity {

    TextView credits_string;
    TextView credits_links;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_credits);

        credits_string = (TextView) findViewById(R.id.credits_string);
        credits_links = (TextView) findViewById(R.id.credits_links);

        Typeface type = Typeface.createFromAsset(getAssets(), "sansproregular.otf");
        credits_string.setTypeface(type);
        credits_links.setTypeface(type);

        credits_links.setText(Html.fromHtml("Icons are downloaded from <a href=\"https://www.flaticon.com/\">Flat Icons</a>" +
                " and are created by the following authors<br/><br/>" +
                "1. <a href=\"https://www.flaticon.com/authors/vectors-market\">Vectors Market</a><br/><br/>" +
                "2. <a href=\"https://www.flaticon.com/authors/freepik\">Freepik</a><br/><br/>" +
                "3. <a href=\"https://www.flaticon.com/authors/pixel-perfect\">Pixel Perfect</a><br/><br/>" +
                "4. <a href=\"https://www.flaticon.com/authors/gregor-cresnar\">Gregor Cresnar</a><br/><br/>" +
                "5. <a href=\"https://www.flaticon.com/authors/smashicons\">Smashicons</a><br/><br/>" +
                "6. <a href=\"https://www.flaticon.com/authors/roundicons\">Roundicons</a>"));
    }
}
