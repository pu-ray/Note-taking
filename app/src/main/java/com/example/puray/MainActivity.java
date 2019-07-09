package com.example.puray;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import com.example.puray.R;
import com.example.puray.adapters.NotesAdapter;
import com.example.puray.database.DatabaseHelper;
import com.example.puray.database.Note;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    TextView Sauce;
    Button btnViewNote;
    ListView listNames;
    List<Note> noteList;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getBaseContext(),AddNoteActivity.class));
                //startActivity(new Intent(getBaseContext(),TrialActivity.class));
            }
        });

        listNames = findViewById(R.id.listViewNames);
        displayNotes();
//        displayNames();

    }

    private void displayNotes() {
        DatabaseHelper databaseHelper = new DatabaseHelper(getBaseContext(),"notes",null,1);
        noteList = new ArrayList<>();
        noteList = databaseHelper.getNotes();
        Log.d("notes","My notelist has" + noteList.size() + "notes");
        NotesAdapter notesAdapter = new NotesAdapter(getBaseContext(),0,noteList);
        listNames.setAdapter(notesAdapter);
        listNames.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Note note = noteList.get(position);
                Intent intent = new Intent(getBaseContext(),ViewNote.class);
                intent.putExtra("NOTE_ID",note.getId());
                startActivity(intent);

            }
        });

    }

    private void displayNames() {
        List<String>nameList = new ArrayList<String>();
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

        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(this,android.R.layout.simple_list_item_1,nameList);
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
}
