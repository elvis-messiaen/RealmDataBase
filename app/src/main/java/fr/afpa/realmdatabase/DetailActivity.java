package fr.afpa.realmdatabase;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.media.MediaCodecInfo;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import fr.afpa.realmdatabase.model.People;
import io.realm.Realm;

public class DetailActivity extends AppCompatActivity {

    Realm realm;

    EditText editNameDetail;
    Button btnUpdate, btnDelete;
    People people;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        realm = Realm.getDefaultInstance();
        editNameDetail = findViewById(R.id.edit_nameDetail);
        btnUpdate = findViewById(R.id.btn_update);
        btnDelete = findViewById(R.id.btn_delete);

        Intent getintent = getIntent();
        int position = getintent.getIntExtra("numPosition", 0);

        people = realm.where(People.class).equalTo("people_id", position).findFirst();

        editNameDetail.setText(people.getPeople_name());

        btnUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                updateData();
                onBackPressed();
            }
        });

        btnDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                deleteData();
                onBackPressed();
            }
        });
    }

    private void deleteData(){

        realm.beginTransaction();
        people.deleteFromRealm();
        realm.commitTransaction();
    }

    private void updateData(){

        realm.beginTransaction();
        people.setPeople_name(editNameDetail.getText().toString());
        realm.commitTransaction();
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }
}