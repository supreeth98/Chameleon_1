package com.example.mad;

import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

public class content extends AppCompatActivity {
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
        setContentView(R.layout.activity_content);
    }

    public void view(View view)
    {
        Intent i = new Intent(Intent.ACTION_DIAL, Uri.fromParts("tel","+918971673209","null"));
        startActivity(i);
    }

    public void view1(View view)
    {
        Intent i1 = new Intent(Intent.ACTION_DIAL, Uri.fromParts("tel","9742251638","null"));
        startActivity(i1);
    }

    public void back2(View view) {
        Intent i=new Intent(content.this,MainActivity.class);
        startActivity(i);
    }
}
