package com.example.mad;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {
private static final int REQUEST_CODE=43;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Spinner s1=(Spinner) findViewById(R.id.spinner);
        Spinner s2=(Spinner) findViewById(R.id.spinner2);
        Button Upload=(Button)findViewById(R.id.button);
        Button Convert=(Button)findViewById(R.id.button2);
        String FileFormats[]={"pdf","docx"};
        s1.setAdapter(new ArrayAdapter<String>(this,android.R.layout.simple_spinner_item,FileFormats));
        s2.setAdapter(new ArrayAdapter<String>(this,android.R.layout.simple_spinner_item,FileFormats));
        Upload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startSearch();
            }
        });
    }

    private void startSearch(){
        Intent intent=new Intent(Intent.ACTION_OPEN_DOCUMENT);
        intent.setType("image/*");//Files having MIME data type- fyi this is what he commented in the video
        intent.addCategory(Intent.CATEGORY_OPENABLE);
        startActivityForResult(intent,REQUEST_CODE);
    }

    @Override//Whatever you select after that this method will be called and result will be stored
    //in the parameter of onActivityResult having datatype Intent
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode==REQUEST_CODE && resultCode== Activity.RESULT_OK){
            if(data!=null){
                Uri uri=data.getData();
                Toast.makeText(this, "Uri"+uri, Toast.LENGTH_SHORT).show();
                Toast.makeText(this, "Path"+uri, Toast.LENGTH_SHORT).show();
                        
            }
        }



    }
}
