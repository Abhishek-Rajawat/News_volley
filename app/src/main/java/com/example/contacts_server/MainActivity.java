package com.example.contacts_server;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.os.StrictMode;
import android.util.Log;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class MainActivity extends AppCompatActivity {
    RecyclerView recyclerView;
    CustomAdapter adapter;
    ArrayList<News> news;
    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        recyclerView=findViewById(R.id.recycle_view);
        news=new ArrayList<>();
        String url="https://newsapi.org/v2/top-headlines?country=in&apiKey=42fe7ed256644ae9b4efeb106bedae83";

        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();

        StrictMode.setThreadPolicy(policy);

        setAdapter();
        processData(url);


    }
    public void processData(String url){
        StringRequest request=new StringRequest(Request.Method.GET,
                url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    // System.out.println(response);

                    JSONObject jsonObject=new JSONObject(response);
                    JSONArray arr=jsonObject.getJSONArray("articles");
                    for (int i = 0; i < arr.length(); i++) {
                        JSONObject obj = arr.getJSONObject(i);
                        String title = obj.getString("title");
                        String description = obj.getString("description");
                        String image_url = obj.getString("urlToImage");
                        news.add(new News(title, description, image_url));
                        // System.out.println(title + "/n" + description + "/n" + image_url);
                    }
                    adapter.update(news);


                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(MainActivity.this, "Volley Error "+error, Toast.LENGTH_SHORT).show();
            }
        }){
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                Map headers = new HashMap();
                headers.put("User-Agent","Mozilla/5.0");
                return headers;
            }
        };
        RequestQueue queue= Volley.newRequestQueue(this);
        queue.add(request);
    }

    public void setAdapter(){
        LinearLayoutManager linearLayoutManager=new LinearLayoutManager(this);
        recyclerView.setLayoutManager(linearLayoutManager);
        adapter=new CustomAdapter(MainActivity.this,news);
        recyclerView.setAdapter(adapter);

    }


}