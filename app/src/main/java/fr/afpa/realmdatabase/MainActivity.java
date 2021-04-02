package fr.afpa.realmdatabase;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import fr.afpa.realmdatabase.customadapter.CustomAdapter;
import fr.afpa.realmdatabase.model.People;
import fr.afpa.realmdatabase.myhelper.MyHelper;
import io.realm.Realm;
import io.realm.RealmChangeListener;

public class MainActivity extends AppCompatActivity {

    Realm realm;
    EditText editName;
    Button btnSave;
    ListView listView;
    MyHelper helper;
    RealmChangeListener realmChangeListener;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        realm = Realm.getDefaultInstance();
        editName = findViewById(R.id.edit_name);
        btnSave = findViewById(R.id.btn_save);
        listView = findViewById(R.id.listView);


        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                saveData();
            }
        });

        helper = new MyHelper(realm);
        helper.selectNameFromDB();

        CustomAdapter adapter = new CustomAdapter(this, helper.justRefresh());
        listView.setAdapter(adapter);

        Refresh();
    }

    private void saveData(){
        realm.executeTransactionAsync(new Realm.Transaction() {
            @Override
            public void execute(Realm bgRealm) {
                Number maxId = bgRealm.where(People.class).max("people_id");

                int newKey = (maxId == null) ? 1 : maxId.intValue()+1;

                People people = bgRealm.createObject(People.class, newKey);
                people.setPeople_name(editName.getText().toString());
            }
        }, new Realm.Transaction.OnSuccess() {
            @Override
            public void onSuccess() {
                // Transaction was a success.
                Toast.makeText(MainActivity.this, "Bravo", Toast.LENGTH_LONG).show();
            }
        }, new Realm.Transaction.OnError() {
            @Override
            public void onError(Throwable error) {
                // Transaction failed and was automatically canceled.
                Toast.makeText(MainActivity.this, "Nope..", Toast.LENGTH_LONG).show();
            }
        });
    }

    private void Refresh(){
            realmChangeListener = new RealmChangeListener() {
                @Override
                public void onChange(Object o) {
                    CustomAdapter adapter = new CustomAdapter(MainActivity.this, helper.justRefresh());
                    listView.setAdapter(adapter);
                }
            };
            realm.addChangeListener(realmChangeListener);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        realm.removeChangeListener(realmChangeListener);
        realm.close();
    }
}