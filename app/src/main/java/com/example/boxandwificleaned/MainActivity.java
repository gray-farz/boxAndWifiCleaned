package com.example.boxandwificleaned;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    //////////////////////////////// LIST OF ITEMS
    private List<ListMainMenuItems> listMainMenuItems;
    private AdapterMainMenuItems adapter;
    RecyclerView recyclerView;

    /////////////////////////////// OTHER WIDGET
    FloatingActionButton floatingActionButton;
    ImageView button_connect;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        defineWidgets();
        setSettingItemsInList();
    }
    private void defineWidgets()
    {
        //////////////////////////////// LIST OF ITEMS
        recyclerView = (RecyclerView) findViewById(R.id.rec);

        /////////////////////////////// OTHER WIDGET
        button_connect = findViewById(R.id.buttonConnect);
        floatingActionButton = findViewById(R.id.butControl);
    }
    private void setSettingItemsInList() {
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        listMainMenuItems=new ArrayList<>();
        getdata();
    }

    private void getdata() {

        listMainMenuItems.add(new ListMainMenuItems(R.drawable.settings,R.string.setting,"setting"));
        listMainMenuItems.add(new ListMainMenuItems(R.drawable.update,R.string.update,"update"));
        listMainMenuItems.add(new ListMainMenuItems(R.drawable.datetime,R.string.datetime,"time"));
        listMainMenuItems.add(new ListMainMenuItems(R.drawable.text,R.string.txtsetting,"text"));
        listMainMenuItems.add(new ListMainMenuItems(R.drawable.files,R.string.log,"flashfiles"));
        listMainMenuItems.add(new ListMainMenuItems(R.drawable.update,R.string.resetArduino,"resetArduino"));
        listMainMenuItems.add(new ListMainMenuItems(R.drawable.radio,R.string.radioCodeStr,"radioCode"));
        listMainMenuItems.add(new ListMainMenuItems(R.drawable.update,R.string.updateCode,"updateCode"));
        listMainMenuItems.add(new ListMainMenuItems(R.drawable.help,R.string.help,"help"));
        adapter=new AdapterMainMenuItems(listMainMenuItems,MainActivity.this,MainActivity.this);
        recyclerView.setAdapter(adapter);
    }

    protected void definefloatingButtonAction() {
        floatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(MainActivity.this, "float button", Toast.LENGTH_SHORT).show();

                Intent intent = new Intent(MainActivity.this, controlActivity.class);
                startActivity(intent);
                overridePendingTransition(R.anim.push_up_in, R.anim.push_up_out);
            }
        });
    }

}
