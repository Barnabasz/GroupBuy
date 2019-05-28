package com.example.group_buy;

import android.content.Intent;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.Toast;

public class GroupActivity extends AppCompatActivity {

    class Product {
        String name;
        boolean bought = false;
//        private int quantity;
//        private String jednostka;

        Product(String name) {
            this.name = name;
        }

        void changeStatus() {
            bought = !bought;
        }

        @Override
        public String toString() {
            return name;
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_group);

        Intent intent = getIntent();
        String group = intent.getStringExtra("groupName");
        setActionBar(group);
//        TODO: Rozwijana lista użytkowników w action barze zamiast "..." z ustawieniami

//        TODO: Obsługa JSON-a
        Product[] products = {new Product("soki - 2l"), new Product("wódka - 3l"), new Product("chipsy - 2 paczki")};
        ListAdapter productListAdapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_multiple_choice, products);
//        TODO: Lista customizowana, checkbox z 3 stanami
//        https://stackoverflow.com/questions/3965484/custom-checkbox-image-android
//        https://developer.android.com/guide/topics/resources/drawable-resource.html#StateList
//        https://www.codeproject.com/Articles/1006843/An-Efficient-Way-to-Make-Button-Color-Change-on-An
//        https://stackoverflow.com/questions/18395075/change-the-state-of-button-to-pressed
//        https://stackoverflow.com/questions/12702045/disable-checkbox-after-checked-android

        ListView productListView = (ListView) findViewById(R.id.productListView);
        productListView.setAdapter(productListAdapter);

        productListView.setOnItemClickListener(
                new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                        Product product = (Product) parent.getItemAtPosition(position);
                        product.changeStatus();
                        if(product.bought)
                            Toast.makeText(GroupActivity.this, "you marked " + product + " as bought", Toast.LENGTH_SHORT).show();
                    }
                }
        );
    }

    public void setActionBar(String heading) {
        ActionBar actionBar = getSupportActionBar();
//        actionBar.setHomeButtonEnabled(true);
//        actionBar.setDisplayHomeAsUpEnabled(false);
//        actionBar.setDisplayShowHomeEnabled(false);
//        actionBar.setBackgroundDrawable(new ColorDrawable(getResources().getColor(R.color.title_bar_gray)));
        actionBar.setTitle(heading);
        actionBar.show();
    }
}
