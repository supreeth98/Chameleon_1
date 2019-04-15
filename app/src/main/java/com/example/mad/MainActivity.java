package com.example.mad;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Environment;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Base64;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.Toast;
import android.util.Log;


import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;

public class MainActivity extends AppCompatActivity {
    Uri uri = null;
    private static final int READ_REQUEST_CODE=42;
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
        s1.setAdapter(new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, FileFormatFrom));
        s2.setAdapter(new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, FileFormatTo));
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
            Intent i=new Intent(MainActivity.this,content.class);
            startActivity(i);
                return true;
            case R.id.contact:
                Intent i1=new Intent(MainActivity.this,about.class);
                startActivity(i1);

                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    private void startSearch(){
        Intent intent=new Intent(Intent.ACTION_GET_CONTENT);
        intent.setType("application/vnd.openxmlformats-officedocument.wordprocessingml.document");//Files having MIME data type- fyi this is what he commented in the video
        intent.addCategory(Intent.CATEGORY_OPENABLE);
        startActivityForResult(intent,READ_REQUEST_CODE);
    }

    @Override//Whatever you select after that this method will be called and result will be stored
    //in the parameter of onActivityResult having datatype Intent
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode==READ_REQUEST_CODE && resultCode== Activity.RESULT_OK){
            if(data!=null){
                uri=data.getData();
                Log.i("Testing", uri.toString());
                //Toast.makeText(this, "Uri"+uri, Toast.LENGTH_SHORT).show();
                //Toast.makeText(this, "Path"+uri, Toast.LENGTH_SHORT).show();
                        
            }
        }
    }

    protected void convertFile() {
        if(uri!=null) {
            File file = new File(uri.getPath());
            String filename = file.getName();
            Toast.makeText(this,"File "+filename,Toast.LENGTH_SHORT).show();
            int size = (int) file.length();
            Log.i("Testing", String.valueOf(size));
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
            Log.i("Testing", base64EncodedFile);
            //callAPI(filename, base64EncodedFile);
            callAPI(filename, "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");
        }
        else {
            Toast.makeText(this,"Choose a file", Toast.LENGTH_SHORT).show();
        }
    }

    protected void callAPI(String filename, String base64EncodedFile) {
        String URL = "https://v2.convertapi.com/convert/docx/to/pdf?Secret=vQXAaDU4PooP9Ju0";  //TODO Remove secret
        JSONObject json = new JSONObject();
        try {
            JSONArray parametersJSON = new JSONArray();
            JSONObject parametersJSONObject = new JSONObject();
            parametersJSONObject.put("Name", "File");
            JSONObject fileValue = new JSONObject();
            fileValue.put("Name", filename);
            fileValue.put("Data", base64EncodedFile);
            parametersJSONObject.put("FileValue", fileValue);
            parametersJSON.put(parametersJSONObject);
            json.put("Parameters", parametersJSON);
            Log.i("Testing", json.toString());//Make the JSON and send and receive the response and covert from base64

        }
        catch (JSONException e) {
            e.printStackTrace();
        }
        StringRequest stringRequest = new StringRequest(Request.Method.POST, URL, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Log.i("Testing", response);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        });
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);
    }
}
