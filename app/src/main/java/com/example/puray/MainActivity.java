package com.example.puray;

import android.app.Activity;
import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.Intent;
import android.media.MediaRecorder;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.annotation.RequiresApi;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.puray.adapters.NotesAdapter;
import com.example.puray.database.DatabaseHelper;
import com.example.puray.database.EditNoteActivity;
import com.example.puray.database.Note;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@RequiresApi(api = Build.VERSION_CODES.FROYO)
public class MainActivity extends AppCompatActivity {
    TextView Sauce;
    Button btnViewNoteActivity;
    ListView listNames;
    List<Note> noteList;
    ImageView fab;
    private String title;
    private DrawerLayout drawer;
    private String noteText;
    private String imageUrl;
    Button btnViewNote;
    private String NOTES_API_URL = "https://akirachixnotesapi.herokuapp.com/api/v1/notes";
    private String TAG = "NOTES_API_RESPONSE";
    MediaRecorder recorder;
    File audiofile = null;
//    static final String TAG = "MediaRecording";
    Button startButton, stopButton;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        startButton = (Button) findViewById(R.id.button1);
        stopButton = (Button) findViewById(R.id.button2);

        listNames = findViewById(R.id.listNames);
        fab = findViewById(R.id.fab);
        drawer = findViewById(R.id.drawer_layout);

//        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawer, toolbar,
//                R.string.navigation_drawer_open, R.string.navigation_drawer_close);
//       drawer.addDrawerListener(toggle);
//       toggle.syncState();

        String url = "https://akirachixnotesapi.herokuapp.com/api/v1/notes";

        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getBaseContext(), AddNoteActivity.class));
                //startActivity(new Intent(getBaseContext(),TrialActivity.class));
            }
        });

        listNames = findViewById(R.id.listViewNames);
        displayNotes();
//        displayNames();

    }

    public void onBackPressed() {
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    private void displayNotes() {
        DatabaseHelper databaseHelper = new DatabaseHelper(getBaseContext(), "notes", null, 1);
        noteList = new ArrayList<>();
        noteList = databaseHelper.getNotes();
        Log.d("notes", "My notelist has" + noteList.size() + "notes");
        NotesAdapter notesAdapter = new NotesAdapter(getBaseContext(), 0, noteList);
        listNames.setAdapter(notesAdapter);
        listNames.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Note note = noteList.get(position);
                Intent intent = new Intent(getBaseContext(), ViewNote.class);
                intent.putExtra("NOTE_ID", note.getId());
                startActivity(intent);

            }
        });
    }

    private void updateNotes() {
        DatabaseHelper databaseHelper = new DatabaseHelper(getBaseContext(), "notes", null, 1);
        noteList = new ArrayList<Note>();
        noteList = databaseHelper.getNotes();
        Log.d("notes", "My noteslist has " + noteList.size() + "notes");
        NotesAdapter notesAdapter = new NotesAdapter(getBaseContext(), 0, noteList);
        listNames.setAdapter(notesAdapter);
        listNames.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Note note = noteList.get(position);
                Intent intent = new Intent(getBaseContext(), EditNoteActivity.class);
                intent.putExtra("NOTE_ID", note.getId());
                startActivity(intent);
            }
        });
    }


    private void displayNames() {
        List<String> nameList = new ArrayList<String>();
        nameList.add("Joy Wanja");
        nameList.add("Irene Njoki");
        nameList.add("Paulime Brown");
        nameList.add("Beatrice kasembi");
        nameList.add("Purity Mbugua");
        nameList.add("Diana Muchiri");
        nameList.add("Catherine Watiri");
        nameList.add("Rose Wanjiku");
        nameList.add("Magdaline sisungo");
        nameList.add("Evelyne Mueni");

        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, nameList);
        listNames.setAdapter(arrayAdapter);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onResume() {
        super.onResume();
//        displayNotes();

    }
//
//    @Override
//    public boolean onCreateOptionsMenu(Menu menu) {
//        getMenuInflater().inflate(R.menu.menu_main, menu);
//        return true;
//    }

    static final String appDirectoryName = "Demo";
    static final File imageRoot = new File(Environment.getExternalStoragePublicDirectory(
            Environment.DIRECTORY_PICTURES), appDirectoryName);


    private List<Note> getNote(final String title, final String noteText) {
        StringRequest stringRequest = new StringRequest(Request.Method.GET, NOTES_API_URL, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Log.d(TAG, response);
                try {
                    JSONArray jsonArray = new JSONArray(response);

                    for (int i = 0; i < jsonArray.length(); i++) {
                        JSONObject jsonObject = jsonArray.getJSONObject(i);
                        int id = jsonObject.getInt("id");
                        String title = jsonObject.getString("title");
                        String noteText = jsonObject.getString("noteText");
                        Note note = new Note(id, title, noteText);
                        noteList.add(note);
                    }
                    NotesAdapter notesAdapter = new NotesAdapter(getBaseContext(), 0, noteList);
                    listNames.setAdapter(notesAdapter);
                    listNames.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                        @Override
                        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                            Note clickedNote = noteList.get(position);
                            Intent intent = new Intent(getBaseContext(), ViewNoteActivity.class);
                            intent.putExtra("NOTE_ID", clickedNote.getId());
                            startActivity(intent);
                        }
                    });
                } catch (JSONException e) {
                    Log.e("error", e.getLocalizedMessage());
                    e.printStackTrace();
                }
            }

        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        });
        RequestQueue requestQueue = Volley.newRequestQueue(getBaseContext());
        requestQueue.add(stringRequest);
        return noteList;

    }

//        public void startRecording(View view) throws IOException {
//                startButton.setEnabled(false);
//                stopButton.setEnabled(true);
//                //Creating file
//                File dir = Environment.getExternalStorageDirectory();
//                try {
//                    audiofile = File.createTempFile("sound", ".3gp", dir);
//                } catch (IOException e) {
//                    Log.e(TAG, "external storage access error");
//                    return ;
//                }
//                //Creating MediaRecorder and specifying audio source, output format, encoder & output format
//                recorder = new MediaRecorder();
//                recorder.setAudioSource(MediaRecorder.AudioSource.MIC);
//                recorder.setOutputFormat(MediaRecorder.OutputFormat.THREE_GPP);
//                recorder.setAudioEncoder(MediaRecorder.AudioEncoder.AMR_NB);
//                recorder.setOutputFile(audiofile.getAbsolutePath());
//                recorder.prepare();
//                recorder.start();
//            }
//
//            public void stopRecording(View view) {
//                startButton.setEnabled(true);
//                stopButton.setEnabled(false);
//                //stopping recorder
//                recorder.stop();
//                recorder.release();
//                //after stopping the recorder, create the sound file and add it to media library.
//                addRecordingToMediaLibrary();
//            }

//            protected void addRecordingToMediaLibrary() {
//                //creating content values of size 4
//                ContentValues values = new ContentValues(4);
//                long current = System.currentTimeMillis();
//                values.put(MediaStore.Audio.Media.TITLE, "audio" + audiofile.getName());
//                values.put(MediaStore.Audio.Media.DATE_ADDED, (int) (current / 1000));
//                values.put(MediaStore.Audio.Media.MIME_TYPE, "audio/3gpp");
//                values.put(MediaStore.Audio.Media.DATA, audiofile.getAbsolutePath());
//
//                //creating content resolver and storing it in the external content uri
//                ContentResolver contentResolver = getContentResolver();
//                Uri base = MediaStore.Audio.Media.EXTERNAL_CONTENT_URI;
//                Uri newUri = contentResolver.insert(base, values);
//
//                //sending broadcast message to scan the media file so that it can be available
//                sendBroadcast(new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE, newUri));
//                Toast.makeText(this, "Added File " + newUri, Toast.LENGTH_LONG).show();
//            }
        }
