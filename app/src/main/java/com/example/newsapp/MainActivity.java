package com.example.newsapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.browser.customtabs.CustomTabsIntent;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.net.Uri;
import android.os.Bundle;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity implements newsItemclicked, categoryItemClicked {
    private RecyclerView mlinearRecycleView;
    private RecyclerView mhorizontalRecycleView;
    private NewsListAdapter madapter ;
    private categoryListAdapter categoryadapter;
    ArrayList<news> newsArray;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // setting linear layout manager of recycle View
        mlinearRecycleView = (RecyclerView)findViewById(R.id.linearRecycleView);
        mhorizontalRecycleView = (RecyclerView) findViewById(R.id.horizontalRecycleView);
        newsArray  = new ArrayList<>();
//        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(MainActivity.this, LinearLayoutManager.HORIZONTAL, false);
//        linearLayoutManager = LinearLayoutManager.HORIZONTAL;
        mlinearRecycleView.setLayoutManager(new LinearLayoutManager(MainActivity.this)); //defining linear layout mannager
        mhorizontalRecycleView.setLayoutManager(new LinearLayoutManager(MainActivity.this, LinearLayoutManager.HORIZONTAL, false)); //defining Horizontal layout manager
        // news api for everthing
        fetchData("https://saurav.tech/NewsAPI/everything/cnn.json");
        //binding adapter
        madapter = new NewsListAdapter(MainActivity.this);
        mlinearRecycleView.setAdapter(madapter);

        //adapter calling for catageory selection
        categoryadapter = new categoryListAdapter(categoryData(), MainActivity.this);
        mhorizontalRecycleView.setAdapter(categoryadapter);

    }


    private void fetchData(String category){
        // category of news api
        String url;
        //checking for everything and for category
        if(category == "https://saurav.tech/NewsAPI/everything/cnn.json") {   url = "https://saurav.tech/NewsAPI/everything/cnn.json"; }
        else{
            url = "https://saurav.tech/NewsAPI/top-headlines/category/"+category+"/in.json";
        }

        // array of jsonobject
//        final JSONArray jsonArray;
//       final ArrayList<news> newsArray  = new ArrayList<>();

//       String url = "https://newsapi.org/v2/top-headlines?country=in&apiKey=4e05a8e3eeab40628fa837bc8a7c0bed";
//        url = "https://saurav.tech/NewsAPI/top-headlines/category/health/in.json";
       JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, url, null, new Response.Listener<JSONObject>() {
           @Override
           public void onResponse(JSONObject response) {
               try{
                   JSONArray jsonArray = response.getJSONArray( "articles"); // storing array of json object

//                   JSONObject jsonObject;
                 // now using instance of news class we will access title, url ,image etc from each jsonobject
                   //loop to store data from json object to array list
                   for (int i =0 ; i < jsonArray.length(); i++){
                       JSONObject jsonObject = jsonArray.getJSONObject(i);
                       news News = new news(jsonObject.getString("title"),
                               jsonObject.getString("author"),
                               jsonObject.getString("url"),
                               jsonObject.getString("urlToImage"));
                       newsArray.add(News);

                   }

                   madapter.updateNews(newsArray);
               }catch (JSONException e){
                   e.printStackTrace();
               }
           }

       }, new Response.ErrorListener() {
           @Override
           public void onErrorResponse(VolleyError error) {
               System.out.println("unable to load data");
               Toast.makeText(MainActivity.this,"unable to load data", Toast.LENGTH_LONG ).show();

           }
       });

       MySingleton.getInstance(MainActivity.this).addToRequestQueue(jsonObjectRequest);

//       return newsArray;

    }

    @Override
    public void onItemClicked(news item) {
        CustomTabsIntent.Builder builder = new CustomTabsIntent.Builder();
        CustomTabsIntent customTabsIntent = builder.build();
        customTabsIntent.launchUrl(this, Uri.parse(item.getUrl()));
        }

// functions to fetch category

    public ArrayList<String> categoryData(){
        ArrayList<String> data = new ArrayList<>();
        data.add("https://saurav.tech/NewsAPI/everything/cnn.json");
        data.add("business");
        data.add("entertainment");
        data.add("general");
        data.add("health");
        data.add("science");
        data.add("sports");
        data.add("technology");

        return data;
    }

    @Override
    public void onCategoryClicked(String category) {
        Toast.makeText(MainActivity.this, category,Toast.LENGTH_LONG).show();
        fetchData(category);
    }
}
