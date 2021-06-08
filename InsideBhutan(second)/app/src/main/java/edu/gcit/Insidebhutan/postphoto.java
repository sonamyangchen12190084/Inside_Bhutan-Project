package edu.gcit.Insidebhutan;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.ContentResolver;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.webkit.MimeTypeMap;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.Continuation;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.StorageTask;
import com.theartofdev.edmodo.cropper.CropImage;

import org.w3c.dom.Text;

import java.util.HashMap;

public class postphoto extends AppCompatActivity {

    Uri imageUri;
    String myUrl = "";
    StorageTask uploadTask;
    StorageReference storageReference;

    ImageView mclose, mimage_added;
    TextView mpost;
    EditText mdescription;
    DatabaseReference reference;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_postphoto);

        mclose = findViewById(R.id.close);
        mimage_added = findViewById(R.id.image_added);
        mpost = findViewById(R.id.post);
        mdescription = findViewById(R.id.description);
        reference = FirebaseDatabase.getInstance().getReference("posts");


        storageReference = FirebaseStorage.getInstance().getReference("post");

        mclose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(postphoto.this,DashBoardActivity.class));
                finish();
            }
        });
        mpost.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                uploadImage();
            }
        });
        CropImage.activity()
                .setAspectRatio(1,1)
                .start(postphoto.this);
    }
    private String getFileExtension(Uri uri){
        ContentResolver contentResolver = getContentResolver();
        MimeTypeMap mime = MimeTypeMap.getSingleton();
        return mime.getExtensionFromMimeType(contentResolver.getType(uri));
    }
    private void uploadImage(){
        ProgressDialog progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Posting");
        progressDialog.show();

        if (imageUri != null){
            String name = reference.push().getKey();
            final StorageReference filerefrence = storageReference.child(name);

            uploadTask = filerefrence.putFile(imageUri);
            uploadTask.continueWithTask(new Continuation() {
                @Override
                public Object then(@NonNull Task task) throws Exception {
                    if (!task.isComplete()){
                        throw task.getException();
                    }

                    return filerefrence.getDownloadUrl();

                }
            }).addOnCompleteListener(new OnCompleteListener<Uri>() {
                @Override
                public void onComplete(@NonNull Task<Uri> task) {
                    if (task.isSuccessful()){
                        Uri downloadUri = task.getResult();
                        myUrl = downloadUri.toString();

                        reference = FirebaseDatabase.getInstance().getReference("posts");
                        photo p = new photo(myUrl, mdescription.getText().toString());
//                        HashMap<String, Object> hashMap = new HashMap<>();
//                        hashMap.put("postid",postid);
//                        hashMap.put("postimage", myUrl);
//                        hashMap.put("description", mdescription.getText().toString());
//                        hashMap.put("publisher",FirebaseAuth.getInstance().getCurrentUser().getUid());


                        reference.child(name).setValue(p);

                        progressDialog.dismiss();

                        startActivity(new Intent(postphoto.this,DashBoardActivity.class));
                        finish();

                    }else{
                        Toast.makeText(postphoto.this,"Failed",Toast.LENGTH_SHORT).show();
                    }
                }
            }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {
                    Toast.makeText(postphoto.this,""+e.getMessage(), Toast.LENGTH_SHORT).show();
                }
            });
        }else{
            Toast.makeText(this,"No Image Selected!", Toast.LENGTH_SHORT).show();
        }

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(requestCode == CropImage.CROP_IMAGE_ACTIVITY_REQUEST_CODE && resultCode == RESULT_OK ){
            CropImage.ActivityResult result = CropImage.getActivityResult(data);
            imageUri = result.getUri();

            mimage_added.setImageURI(imageUri);

        } else{
            Toast.makeText(this,"Something gone wrong!", Toast.LENGTH_SHORT).show();
            startActivity(new Intent(postphoto.this,DashBoardActivity.class));
            finish();
        }
    }

    public void add_photo(View view) {
    }
}