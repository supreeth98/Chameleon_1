package com.example.mad;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

public class about extends AppCompatActivity {
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_about,menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId())
        {
            case R.id.about:
                Intent i=new Intent(getApplicationContext(),content.class);
                startActivity(i);
                return true;
            case R.id.contact:
                Intent i1=new Intent(getApplicationContext(),about.class);
                startActivity(i1);

                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about);
    }

    public void back(View view) {
        Intent i=new Intent(about.this,MainActivity.class);
        startActivity(i);
    }
}
