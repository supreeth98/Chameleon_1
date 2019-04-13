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
            callAPI(filename, base64EncodedFile);
        }
        else {
            Toast.makeText(this,"Choose a file", Toast.LENGTH_SHORT).show();
        }
    }

    protected void callAPI(String filename, String base64EncodedFile) {
        String URL = "https://v2.convertapi.com/convert/docx/to/pdf?Secret=vQXAaDU4PooP9Ju0";
        try {
            JSONObject json = new JSONObject();
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
