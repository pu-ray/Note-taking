package com.example.puray;

import android.app.DownloadManager;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.puray.R;
import com.example.puray.database.DatabaseHelper;
import com.example.puray.database.Note;

import org.json.JSONObject;

import java.io.File;
import java.io.FileOutputStream;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

import static com.example.puray.MainActivity.imageRoot;

public class AddNoteActivity extends AppCompatActivity {
    EditText etTitle;
    EditText etNote;
    Button btnAddPhoto;
    Button btnAddVoiceNote;
    Button btnSave;
    String title;
    String noteText;
    private String NOTES_API_URL = "https://akirachixnotesapi.herokuapp.com/api/v1/notes";
    private String TAG = "NOTES_API_RESPONSE";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_note);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        View btnEdit = findViewById(R.id.btnEdit);
        View imgView = findViewById(R.id.imgView);
        etTitle=findViewById(R.id.etTitle);
        etNote=findViewById(R.id.etNote);
        btnAddPhoto=findViewById(R.id.btnAddPhoto);
        btnAddVoiceNote=findViewById(R.id.btnAddVoiceNote);
        btnSave=findViewById(R.id.btnSave);

        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                title = etTitle.getText().toString();
                noteText = etNote.getText().toString();
                Note note = new Note(title,noteText);
                DatabaseHelper databaseHelper = new DatabaseHelper(getBaseContext(),"notes",null,1);
                long insert = databaseHelper.insertNote(note);
                Log.d("insertnote","note insertion value" + insert);


                Toast.makeText(getBaseContext(),"You have clicked the save button",Toast.LENGTH_LONG).show();

                postNote(title,noteText);
            }
        });
        btnAddPhoto.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.FROYO)
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
//                startActivityForResult(intent,CAPTURE_IMAGE_REQUEST_CODE);
                imageRoot.mkdirs();
                final File image = new File(imageRoot, "image1.jpg");

            }
        });





        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }
    private void postNote(final String title, final String noteText){
        StringRequest stringRequest = new StringRequest(Request.Method.POST, NOTES_API_URL, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Log.d(TAG, response);
                try {
                    JSONObject jsonObject = new JSONObject(response);
                    int id = jsonObject.getInt("id");
                    String myTitle = jsonObject.getString("title");
                    String mynoteText = jsonObject.getString("noteText");

                } catch (Exception exception) {
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
            Log.e(TAG,error.getLocalizedMessage());
            }
        }){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String ,String> params = new HashMap<String, String>();
                params.put("title",title);
                params.put("noteText",noteText);

                return params;
            }
        };
        RequestQueue requestQueue  = Volley.newRequestQueue(getBaseContext());
        requestQueue.add(stringRequest);
    }
    private  void SaveImage(Bitmap finalBitmap) {
        String root = Environment.getExternalStorageDirectory().toString();
        File myDir = new File(root + "/saved_images");
        if (!myDir.exists()) {
            myDir.mkdirs();
        }
        Random generator = new Random();
        int n = 10000;
        n = generator.nextInt(n);
        String  fname = "Image-" + n + ".jpg";
        File file = new File(myDir, fname);
        if (file.exists());
        file.delete();
        try {
            FileOutputStream out = new FileOutputStream(file);
            finalBitmap.compress(Bitmap.CompressFormat.JPEG, 90, out);
            out.flush();
            out.close();
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }
}


