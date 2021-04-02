package fr.afpa.realmdatabase.myhelper;

import java.util.ArrayList;

import fr.afpa.realmdatabase.model.People;
import io.realm.Realm;
import io.realm.RealmResults;

public class MyHelper {

    Realm realm;
    RealmResults<People> people;

    public MyHelper(Realm realm) {
        this.realm = realm;
    }

    public void selectNameFromDB(){
        people = realm.where(People.class).findAll();
    }

    public ArrayList<People> justRefresh(){

        ArrayList<People> listItem = new ArrayList<>();
        for (People p : people){
            listItem.add(p);
        }

        return listItem;
    }

}
