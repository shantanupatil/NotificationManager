package in.shantanupatil.notificationmanager.Staff.AddStaffMessage;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.IOException;
import java.util.Random;

import in.shantanupatil.notificationmanager.R;

public class AddStaff extends AppCompatActivity {

    EditText title;
    EditText description;
    EditText staff_name;

    ImageButton imageButton;
    Button staffAdd;

    private long date;

    public static String title_text, description_text, message, notice_text, topic, staff_name_text;
    public static final int REQ_CODE = 1234;
    private Uri selectedImage;

    StorageReference storageReference;
    DatabaseReference databaseReference;
    FirebaseDatabase mDatabase;

    ProgressDialog mDailog;
    Spinner notice;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_staff);

        title = (EditText) findViewById(R.id.staff_titile);
        staff_name = (EditText) findViewById(R.id.staff_name);

        description = (EditText) findViewById(R.id.staff_description);
        imageButton = (ImageButton) findViewById(R.id.staff_image_upload);
        staffAdd = (Button) findViewById(R.id.staff_add);

        mDatabase = FirebaseDatabase.getInstance();
        databaseReference = mDatabase.getReference();
        storageReference = FirebaseStorage.getInstance().getReference();

        mDailog = new ProgressDialog(this);

        notice = (Spinner) findViewById(R.id.notice_spinner);

        date = System.currentTimeMillis();

        ArrayAdapter<String> noticeArray = new ArrayAdapter<String>(getApplicationContext(), R.layout.spinner_item_staff,
                getResources().getStringArray(R.array.branch_name));
        noticeArray.setDropDownViewResource(R.layout.support_simple_spinner_dropdown_item);
        notice.setAdapter(noticeArray);

        imageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent();
                intent.setAction(Intent.ACTION_GET_CONTENT);
                intent.setType("image/*");
                startActivityForResult(intent, REQ_CODE);
            }
        });

        staffAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                addToDatabase();
            }
        });
    }

    private boolean check(String title_text, String description_text, Uri selectedImage) {

        if (selectedImage == null) {
            message = "Please Select Image";
            return false;
        }
        if (title_text.equals("")) {
            message = "Title Required";
            return false;
        }
        if (description_text.equals("")) {
            message = "Description Required";
            return false;
        }
        int x = title_text.indexOf("/");
        if (x >= 1) {
            message = "Title should not contain '/'";
            return false;
        }
        return true;
    }

    private void addToDatabase() {
        mDailog.setMessage("Posting..");
        mDailog.show();
        title_text = title.getText().toString();
        description_text = description.getText().toString();
        staff_name_text = staff_name.getText().toString();

        notice_text = notice.getSelectedItem().toString();

        topic = notice_text.replaceAll(" ", "");

        if (check(title_text, description_text, selectedImage)) {

            char[] chars = "ABCDEF012GHIJKL345MNOPQR678STUVWXYZ9".toCharArray();
            StringBuilder sb = new StringBuilder();
            Random random = new Random();
            for (int i = 0; i < 6; i++)
            {
                char c = chars[random.nextInt(chars.length)];
                sb.append(c);
            }
            String random_string = sb.toString();
            StorageReference filePath = storageReference.child("Staff_Images").child(random_string);

            filePath.putFile(selectedImage).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                @Override
                public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                    Uri downloadURI = taskSnapshot.getDownloadUrl();
                    String title_changed = title_text.substring(0,1).toUpperCase() + title_text.substring(1, title_text.length());
                    StaffModel staffModel = new StaffModel(title_changed, description_text, downloadURI.toString(), date, staff_name_text);

                    databaseReference.child(topic)
                            .child(title_changed)
                            .setValue(staffModel);

                    Toast.makeText(AddStaff.this, "Notice Added", Toast.LENGTH_SHORT).show();
                    mDailog.dismiss();
                }
            });
        } else {
            Toast.makeText(this, "" + message, Toast.LENGTH_SHORT).show();
            mDailog.dismiss();
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        Log.d("TAGS", "" + requestCode);
        Log.d("TAGS", "" + RESULT_OK);
        Log.d("TAGS", "" + REQ_CODE);

        if (requestCode == REQ_CODE && resultCode == RESULT_OK && data != null) {
            selectedImage = data.getData();
            try {
                Bitmap bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), selectedImage);
                imageButton.setImageBitmap(bitmap);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
