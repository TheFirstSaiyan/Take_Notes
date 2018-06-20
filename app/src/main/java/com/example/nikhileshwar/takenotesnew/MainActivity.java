package com.example.nikhileshwar.takenotesnew;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.io.IOException;
import java.lang.reflect.Array;
import java.util.ArrayList;


public class MainActivity extends AppCompatActivity {
    static ArrayList<String>arrayList=new ArrayList<>();

    static ListView listView;
    static ArrayAdapter arrayAdapter;
    Intent intent;
    String value;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        listView=(ListView)findViewById(R.id.listView);
        SharedPreferences sharedPreferences=this.getSharedPreferences("com.example.nikhileshwar.takenotes", Context.MODE_PRIVATE);

        try {
            arrayList=(ArrayList<String>)ObjectSerializer.deserialize(sharedPreferences.getString("notes",ObjectSerializer.serialize(new ArrayList<String>())));



        } catch (IOException e) {
            e.printStackTrace();
        }


        arrayAdapter=new ArrayAdapter(this,android.R.layout.simple_list_item_1,arrayList);
        listView.setAdapter(arrayAdapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                value=arrayList.get(position);
                intent=new Intent(getApplicationContext(),SecondActivity.class);
                intent.putExtra("value",value);
                intent.putExtra("position",position);
                intent.putExtra("crctPosition",arrayList.size()-1);

                startActivity(intent);
            }

        });
        listView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, final int position, long id) {

                new AlertDialog.Builder(MainActivity.this)
                        .setTitle("Delete Note?")
                        .setIcon(android.R.drawable.ic_dialog_alert).setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                        //  Toast.makeText(MainActivity.this, ((TextView)dialog).getText().toString(), Toast.LENGTH_SHORT).show();
                        arrayList.remove(position);
                        arrayAdapter=new ArrayAdapter(MainActivity.this,android.R.layout.simple_list_item_1,arrayList);
                        listView.setAdapter(arrayAdapter);
                        saveLocation();

                    }
                }).setNegativeButton("No", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        //text.setText(Integer.toString(which));

                    }
                }).show();

                return true;
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater=getMenuInflater();
        menuInflater.inflate(R.menu.main_menu,menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {


        arrayAdapter=new ArrayAdapter(this,android.R.layout.simple_list_item_1,arrayList);
        listView.setAdapter(arrayAdapter);
        intent=new Intent(getApplicationContext(),SecondActivity.class);
        intent.putExtra("position",-1);
        intent.putExtra("crctPosition",arrayList.size()-1);

        startActivity(intent);


        return super.onOptionsItemSelected(item);
    }
    public void saveLocation()
    {
        SharedPreferences sharedPreferences=this.getSharedPreferences("com.example.nikhileshwar.takenotes", Context.MODE_PRIVATE);
        try {


            sharedPreferences.edit().putString("notes",ObjectSerializer.serialize(arrayList)).apply();



        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
