package com.example.octosloths;

import androidx.appcompat.app.AppCompatActivity;

import android.content.ClipData;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.PopupMenu;
import android.widget.Toast;


public class MainActivity extends AppCompatActivity {

    com.google.android.material.floatingactionbutton.FloatingActionButton button;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        button = (com.google.android.material.floatingactionbutton.FloatingActionButton) findViewById(R.id.floatingActionButton);
       button.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick (View view){

               PopupMenu popupMenu = new PopupMenu(MainActivity.this, button);
               popupMenu.getMenuInflater().inflate(R.menu.popup_menu,popupMenu.getMenu());

                       popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                           @Override
                           public boolean onMenuItemClick(MenuItem item) {
                       Toast.makeText(MainActivity.this,"" + item.getTitle(),Toast.LENGTH_SHORT).show();
                       return true;
                   }

               });
                       popupMenu.show();
           }
       });
    }
}