package dariojolo.com.proyectorealm.activities;

import android.content.DialogInterface;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import dariojolo.com.proyectorealm.R;
import dariojolo.com.proyectorealm.adapters.NoteAdapter;
import dariojolo.com.proyectorealm.models.Board;
import dariojolo.com.proyectorealm.models.Note;
import io.realm.Realm;
import io.realm.RealmList;

public class NoteActivity extends AppCompatActivity {

    private ListView listView;
    private FloatingActionButton fab;

    private NoteAdapter adapter;
    private RealmList<Note> notes;
    private Realm realm;

    private int boardId;
    private Board board;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_note);

        realm = Realm.getDefaultInstance();

        if (getIntent().getExtras() != null){
            boardId = getIntent().getExtras().getInt("id");

            board = realm.where(Board.class).equalTo("id", boardId).findFirst();
            notes = board.getNotes();

            this.setTitle(board.getTitle());

            fab = (FloatingActionButton)findViewById(R.id.fabAddNote);
            listView = (ListView)findViewById(R.id.listViewNotes);

            adapter = new NoteAdapter(this, notes, R.layout.list_view_note_item);

            listView.setAdapter(adapter);

            fab.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    showAlertForCreatingNote("title", "message");
                }
            });

        }

   }
    //Para mostrar un Alert Dialog
    private void showAlertForCreatingNote(String title, String message){
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        if (title != null){
            builder.setTitle(title);
        }
        if (message != null){
            builder.setMessage(message);
        }
        View viewInflated = LayoutInflater.from(this).inflate(R.layout.dialog_create_note, null);
        builder.setView(viewInflated);

        final EditText input = (EditText)viewInflated.findViewById(R.id.editTextNote);

        builder.setPositiveButton("Add", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                String note = input.getText().toString().trim();
                if (note.length()>0){
                    createNewNote(note);
                }else{
                    Toast.makeText(getApplicationContext(),"La nota no puede estar vacia",Toast.LENGTH_SHORT).show();
                }
            }
        });
        AlertDialog dialog = builder.create();
        dialog.show();

    }

    private void createNewNote(String note) {

    }

}
