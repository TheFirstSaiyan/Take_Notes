package com.example.nikhileshwar.takenotesnew;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ArrayAdapter;
import android.widget.EditText;

import java.io.IOException;

public class SecondActivity extends AppCompatActivity {
    EditText editText;
    Intent intent;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_second);
        editText=(EditText)findViewById(R.id.editText);
        intent=getIntent();
        editText.setText(intent.getStringExtra("value"));
        //if(intent.getIntExtra("position",0)==-1)
        //MainActivity.arrayList.add(editText.getText().toString());
        // else
        //     MainActivity.arrayList.set(intent.getIntExtra("position",0),editText.getText().toString());

        //Toast.makeText(this, editText.getText().toString(), Toast.LENGTH_SHORT).show();
    }
    public void saveLocation()
    {
        SharedPreferences sharedPreferences=this.getSharedPreferences("com.example.nikhileshwar.takenotes", Context.MODE_PRIVATE);
        try {


            sharedPreferences.edit().putString("notes",ObjectSerializer.serialize(MainActivity.arrayList)).apply();



        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onWindowFocusChanged(boolean hasFocus) {
        super.onWindowFocusChanged(hasFocus);

        if(!hasFocus) {


            if (intent.getIntExtra("position", 0) == -1) {
                MainActivity.arrayList.add(editText.getText().toString());
                if (editText.getText().toString().isEmpty())
                    MainActivity.arrayList.remove(intent.getIntExtra("crctposition", 0));
            }
            else {

                MainActivity.arrayList.set(intent.getIntExtra("position", 0), editText.getText().toString());if (editText.getText().toString().isEmpty())
                    MainActivity.arrayList.remove(intent.getIntExtra("position", 0));


            }



            ArrayAdapter arrayAdapter=new ArrayAdapter(this,android.R.layout.simple_list_item_1,MainActivity.arrayList);

            MainActivity.listView.setAdapter(arrayAdapter);
        }
        saveLocation();



    }
}
