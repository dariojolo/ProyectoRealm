package dariojolo.com.proyectorealm.models;

import java.util.Date;

import dariojolo.com.proyectorealm.app.MyApplication;
import io.realm.RealmList;
import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;
import io.realm.annotations.Required;

/**
 * Created by rodrigrl on 13/03/2017.
 */

public class Board extends RealmObject{

    @PrimaryKey
    private int id;
    @Required
    private String title;
    @Required
    private Date createAt;
    private RealmList<Note> notes;

    public Board(){}
    public Board(String title){
        this.id = MyApplication.BoardID.incrementAndGet();
        this.title = title;
        this.createAt = new Date();
        this.notes = new RealmList<Note>();
    }

    public int getId() {
        return id;
    }
    public String getTitle() {
        return title;
    }
    public void setTitle(String title) {
        this.title = title;
    }
    public Date getCreateAt() {
        return createAt;
    }
    public RealmList<Note> getNotes() {
        return notes;
    }
}
