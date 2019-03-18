package com.example.mad;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Environment;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Base64;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.Toast;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;

public class MainActivity extends AppCompatActivity {
    Uri uri = null;
    private static final int REQUEST_CODE=43;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Spinner s1=(Spinner) findViewById(R.id.spinner);
        Spinner s2=(Spinner) findViewById(R.id.spinner2);
        Button Upload=(Button)findViewById(R.id.button);
        Button Convert=(Button)findViewById(R.id.button2);
        String FileFormatFrom[]={"docx"};
        String FileFormatTo[]={"pdf"};
        s1.setAdapter(new ArrayAdapter<String>(this,android.R.layout.simple_spinner_item,FileFormatFrom));
        s2.setAdapter(new ArrayAdapter<String>(this,android.R.layout.simple_spinner_item,FileFormatTo));
        Upload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startSearch();
            }
        });
        Convert.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                convertFile();
            }
        });
    }

    private void startSearch(){
        Intent intent=new Intent(Intent.ACTION_OPEN_DOCUMENT);
        intent.setType("application/vnd.openxmlformats-officedocument.wordprocessingml.document");//Files having MIME data type- fyi this is what he commented in the video
        intent.addCategory(Intent.CATEGORY_OPENABLE);
        startActivityForResult(intent,REQUEST_CODE);
    }

    @Override//Whatever you select after that this method will be called and result will be stored
    //in the parameter of onActivityResult having datatype Intent
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode==REQUEST_CODE && resultCode== Activity.RESULT_OK){
            if(data!=null){
                uri=data.getData();
                //Toast.makeText(this, "Uri"+uri, Toast.LENGTH_SHORT).show();
                //Toast.makeText(this, "Path"+uri, Toast.LENGTH_SHORT).show();
                        
            }
        }
    }

    protected void convertFile() {
        if(uri!=null) {
            File file = new File(uri.getPath());
            String filename = file.getName();
            Toast.makeText(this,"File"+filename,Toast.LENGTH_SHORT).show();
            int size = (int) file.length();
            byte[] bytes = new byte[size];
            try {
                BufferedInputStream buf = new BufferedInputStream(new FileInputStream(file));
                buf.read(bytes, 0, bytes.length);
                buf.close();
            } catch (FileNotFoundException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            } catch (IOException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
            String base64EncodedFile = Base64.encodeToString(bytes, Base64.DEFAULT);
        }
        else {
            Toast.makeText(this,"Choose a file", Toast.LENGTH_SHORT).show();
        }
    }
}
