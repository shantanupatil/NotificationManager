package in.shantanupatil.notificationmanager.Staff.RetriveStaff;

import android.Manifest;
import android.annotation.TargetApi;
import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.Typeface;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Environment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URL;
import java.net.URLConnection;
import java.util.Random;

import in.shantanupatil.notificationmanager.R;

public class NoticeDetailActivity extends AppCompatActivity {

    public String title;
    public String description;
    public String Image;

    TextView noticeTitle;
    TextView noticeDescription;
    ImageView image;

    ProgressDialog showSomeProgress;
    ImageView share_app_image_detail_notice;

//    Button download_notice;

    ProgressDialog progressDialog;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notice_detail);


        showSomeProgress = new ProgressDialog(this);
        Intent intent = getIntent();
        title = intent.getStringExtra("Title");
        description = intent.getStringExtra("Description");
        Image = intent.getStringExtra("Image");

        share_app_image_detail_notice = (ImageView) findViewById(R.id.share_app_image_detail_notice);

        share_app_image_detail_notice.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showSomeProgress.setMessage("Loading..");
                Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(Image));
                startActivity(intent.createChooser(intent, "Open Via"));
                showSomeProgress.dismiss();
            }
        });

        progressDialog = new ProgressDialog(NoticeDetailActivity.this);

//        download_notice = (Button) findViewById(R.id.download_notice);
//
//        download_notice.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                new DownloadImageNotice().execute(Image);
//
//                Log.d("Image", ""+Image);
//            }
//        });

        noticeTitle = (TextView) findViewById(R.id.detail_title_notice);
        Typeface typeFace = Typeface.createFromAsset(getAssets(), "sansproregular.otf");
        typeFace.isBold();
        noticeTitle.setTypeface(typeFace, Typeface.BOLD);
        noticeTitle.setTextColor(Color.parseColor("#333333"));
        noticeDescription = (TextView) findViewById(R.id.detail_description_notice);
        image = (ImageView) findViewById(R.id.image_notice_detail);

        noticeTitle.setText(title);
        noticeDescription.setText(description);
        Picasso.with(this)
                .load(Image)
                .into(image);


    }

//   class DownloadImageNotice extends AsyncTask<String, Integer, Long>{
//
//        @Override
//        protected void onPreExecute() {
//            super.onPreExecute();
//            progressDialog.setMessage("Downloading");
//            progressDialog.setIndeterminate(false);
//            progressDialog.setMax(100);
//            progressDialog.setCancelable(true);
//            progressDialog.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
//            progressDialog.show();
//
//        }
//
//        @Override
//        protected Long doInBackground(String... urls) {
//            int  count;
//            try {
//                URL url = new URL(urls[0]);
//                Log.d("TAGGS", "URL" + (String)urls[0]);
//                URLConnection connection = url.openConnection();
//                connection.connect();
//                char[] chars = "ABCDEF012GHIJKL345MNOPQR678STUVWXYZ9".toCharArray();
//                StringBuilder sb = new StringBuilder();
//                Random random = new Random();
//                for (int i = 0; i < 6; i++)
//                {
//                    char c = chars[random.nextInt(chars.length)];
//                    sb.append(c);
//                }
//                String random_string = sb.toString() + ".jpg";
//
//                Log.d("TAGGGS", "" +random_string);
//
//                int length_of_file = connection.getContentLength();
//                String path = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS) + File.separator + "NotificationManager" + File.separator;
//                File folder = new File(path);
//                if (!folder.exists()) {
//                    folder.mkdir();
//                }
//                InputStream inputStream  = new BufferedInputStream(url.openStream());
//                OutputStream  outputStream = new FileOutputStream(path + random_string);
//                Log.d("TAGGGS", "OutputStream: " +outputStream);
//                byte data[] = new byte[1024];
//                long total = 0;
//                while ((count = inputStream.read(data)) != -1) {
//                    total += count;
//                    publishProgress((int)(total*100/length_of_file));
//                    outputStream.write(data, 0, count);
//                }
//                outputStream.flush();
//                outputStream.close();
//                inputStream.close();
//
//            } catch (Exception e){
//                Log.d("TAGGGS", "Exception: " +e);
//            }
//            return null;
//        }
//
//        protected void onProgressUpdate(Integer... progress) {
//            progressDialog.setProgress(progress[0]);
//            if(progressDialog.getProgress()==progressDialog.getMax()){
//                progressDialog.dismiss();
//                Toast.makeText(getApplicationContext(), "File Downloaded", Toast.LENGTH_SHORT).show();
//            }
//        }
//        protected void onPostExecute(String result) {
//        }
//    }

}
