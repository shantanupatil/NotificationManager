package in.shantanupatil.notificationmanager.TandP.AddInformation;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.IOException;

import in.shantanupatil.notificationmanager.R;

public class AddTnp extends AppCompatActivity {

    private ImageButton uploadingImage;
    Button tnp_add;
    public EditText title;
    public EditText description;
    private StorageReference storageReference;

    private static final int req_code= 234;

    private Uri selectedImage;

    FirebaseDatabase mRef;
    DatabaseReference reference;

    String title_text, description_text;

    ProgressDialog progressBar;

    private static String message;

    private long date;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_tnp);

        mRef = FirebaseDatabase.getInstance();
        reference = mRef.getReference();
        progressBar = new ProgressDialog(this);

        date = System.currentTimeMillis();

        storageReference = FirebaseStorage.getInstance().getReference();
        uploadingImage = (ImageButton) findViewById(R.id.upload_image);
        tnp_add = (Button) findViewById(R.id.tnp_add);
        title = (EditText) findViewById(R.id.tnp_title);
        description = (EditText) findViewById(R.id.tnp_description);

        uploadingImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent();
                intent.setAction(Intent.ACTION_GET_CONTENT);
                intent.setType("image/*");
                startActivityForResult(intent, req_code);
            }
        });

        tnp_add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                postToDatabase();
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

    private void postToDatabase() {
        progressBar.setMessage("Posting..");
        progressBar.show();
        title_text = title.getText().toString();
        description_text = description.getText().toString();
        Log.d("IDX", ": " + reference.push().getKey());
        if (check(title_text, description_text, selectedImage)) {
            StorageReference filepath = storageReference.child("TNP_Images").child(selectedImage.getLastPathSegment());
            filepath.putFile(selectedImage).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                @Override
                public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                    Uri downloadUri = taskSnapshot.getDownloadUrl();

                    String newtitle = title_text.substring(0,1).toUpperCase() + title_text.substring(1, title_text.length());
                    Log.d("TAGSS", "" + newtitle);
                    TNPAddModel tnp = new TNPAddModel(newtitle, description_text, downloadUri.toString(), date);

                    reference.child("TNP")
                            .child(newtitle)
                            .setValue(tnp);

                    Log.d("TAGSS", "Title" + tnp.getTitle());

                    Toast.makeText(AddTnp.this, "Article Added", Toast.LENGTH_SHORT).show();
                    progressBar.dismiss();
                }
            });
        } else {
            Toast.makeText(this, "" + message, Toast.LENGTH_SHORT).show();
            progressBar.dismiss();
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == req_code && resultCode == RESULT_OK && data != null && data.getData() != null) {
            selectedImage = data.getData();
            try {
                Bitmap bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), selectedImage);
                uploadingImage.setImageBitmap(bitmap);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
