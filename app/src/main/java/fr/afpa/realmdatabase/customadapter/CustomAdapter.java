package fr.afpa.realmdatabase.customadapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;

import fr.afpa.realmdatabase.DetailActivity;
import fr.afpa.realmdatabase.R;
import fr.afpa.realmdatabase.model.People;

public class CustomAdapter extends BaseAdapter {

    Context c;
    ArrayList<People> people;

    public CustomAdapter(Context c, ArrayList<People> people) {
        this.c = c;
        this.people = people;
    }

    @Override
    public int getCount() {
        return people.size();
    }

    @Override
    public Object getItem(int position) {
        return people.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View view, ViewGroup parent) {

        LayoutInflater inflater = (LayoutInflater) c.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        view = inflater.inflate(R.layout.list_item, parent, false);

        TextView txtName;
        txtName = view.findViewById(R.id.txt_name);

        People p = (People) this.getItem(position);

        final int numPosition = p.getPeople_id();
        txtName.setText(p.getPeople_name());

        //OnClick
        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(c, DetailActivity.class);
                intent.putExtra("numPosition", numPosition);
                c.startActivity(intent);
            }
        });

        return view;

    }
}
