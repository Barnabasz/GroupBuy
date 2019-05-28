package com.example.group_buy;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListAdapter;
import android.widget.ListView;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

//        TODO: Obsługa JSON-a
        String[] groups = {"urodziny Oli", "22.05 316A", "sylwester"};
        ListAdapter groupListAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, groups);
        ListView groupListView = (ListView) findViewById(R.id.groupListView);
        groupListView.setAdapter(groupListAdapter);

        groupListView.setOnItemClickListener(
                new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                        String group = String.valueOf(parent.getItemAtPosition(position));
                        openGroupActivity(group);
                    }
                }
        );

//        TODO: Dodawanie grupy
    }

    public void openGroupActivity(String group) {
        Intent intent = new Intent(this, GroupActivity.class);
        intent.putExtra("groupName", group);
        startActivity(intent);
    }
    // Todo: Pasek nawigacji (wyloguj opcje)
}
