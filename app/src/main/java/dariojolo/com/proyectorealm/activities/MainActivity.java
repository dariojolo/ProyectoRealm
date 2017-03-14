package dariojolo.com.proyectorealm.activities;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import dariojolo.com.proyectorealm.R;
import dariojolo.com.proyectorealm.adapters.BoardAdapter;
import dariojolo.com.proyectorealm.models.Board;
import io.realm.Realm;
import io.realm.RealmChangeListener;
import io.realm.RealmResults;

public class MainActivity extends AppCompatActivity implements RealmChangeListener<RealmResults<Board>>, AdapterView.OnItemClickListener {

    private FloatingActionButton fab;
    private Realm realm;

    private ListView listView;
    private BoardAdapter adapter;

    private RealmResults<Board> boards;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //DB Realm
        realm = Realm.getDefaultInstance();
        boards = realm.where(Board.class).findAll();

        boards.addChangeListener(this);


        adapter = new BoardAdapter(this,boards,R.layout.list_view_board_item);
        listView = (ListView)findViewById(R.id.listViewBoards);
        listView.setAdapter(adapter);

        fab = (FloatingActionButton)findViewById(R.id.fabAddBoard);

        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showAlertForCreatingBoard("Titulo", "Mensaje");
            }
        });
    }

    // Acciones CRUD
    private void createNewBoard(String boardName) {
        realm.beginTransaction();
        Board board = new Board(boardName);
        realm.copyToRealm(board);
        realm.commitTransaction();

       /* realm.executeTransaction(new Realm.Transaction() {
            @Override
            public void execute(Realm realm) {
                Board board = new Board(boardName);
                realm.copyToRealm(board);
            }
        });*/
    }

    //Para mostrar un Alert Dialog
    private void showAlertForCreatingBoard(String title, String message){
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        if (title != null){
            builder.setTitle(title);
        }
        if (message != null){
            builder.setMessage(message);
        }
        View viewInflated = LayoutInflater.from(this).inflate(R.layout.dialog_create_board, null);
        builder.setView(viewInflated);

        final EditText input = (EditText)viewInflated.findViewById(R.id.editTextBoard);

        builder.setPositiveButton("Add", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                String boardName = input.getText().toString().trim();
                if (boardName.length()>0){
                    createNewBoard(boardName);
                }else{
                    Toast.makeText(getApplicationContext(),"Debe escribir un nombre para el Board nuevo",Toast.LENGTH_SHORT).show();
                }
            }
        });
        AlertDialog dialog = builder.create();
        dialog.show();

    }

    @Override
    public void onChange(RealmResults<Board> element) {
        adapter.notifyDataSetChanged();
    }

    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int position, long id) {
        Intent intent = new Intent(MainActivity.this, NoteActivity.class);
        intent.putExtra("id", boards.get(position).getId());
        startActivity(intent);
    }
}
