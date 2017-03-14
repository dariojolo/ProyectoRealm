package dariojolo.com.proyectorealm.app;

import android.app.Application;

import java.util.concurrent.atomic.AtomicInteger;

import dariojolo.com.proyectorealm.models.Board;
import dariojolo.com.proyectorealm.models.Note;
import io.realm.Realm;
import io.realm.RealmConfiguration;
import io.realm.RealmObject;
import io.realm.RealmResults;


/**
 * Created by rodrigrl on 14/03/2017.
 */

//Esta clase siempre se ejecuta antes del MainActivity cuando se lanza la aplicacion
public class MyApplication extends Application {

    public static AtomicInteger BoardID = new AtomicInteger();
    public static AtomicInteger NoteID = new AtomicInteger();

    @Override
    public void onCreate(){
        super.onCreate();
        setUpRealmConfig();
        Realm realm = Realm.getDefaultInstance();
        BoardID = getIdByTable(realm, Board.class);
        NoteID  = getIdByTable(realm, Note.class);
        realm.close();
    }
    private void setUpRealmConfig(){
        RealmConfiguration config = new RealmConfiguration
                                    .Builder(getApplicationContext())
                                    .deleteRealmIfMigrationNeeded()
                                    .build();
        Realm.setDefaultConfiguration(config);
    }
    private <T extends RealmObject> AtomicInteger getIdByTable( Realm realm, Class<T>anyClass){
        RealmResults<T> results = realm.where(anyClass).findAll();
        if (results.size()>0){
            return new AtomicInteger(results.max("id").intValue());
        }else{
            return new AtomicInteger();
        }
    }
}

