package com.example.rkjc.news_app_2;

import android.content.Context;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;



public class MainActivity extends AppCompatActivity {

    private static String results = "Your URL should be: ";
    private static String json;
    private static final String TAG = "News App";
    public TextView textView;
    private RecyclerView mRecyclerView;
    private NewsRecyclerViewAdapter NewsAdapter;
    private ArrayList<String> titleString = new ArrayList<>();
    private ArrayList<String> descriptionString = new ArrayList<>();
    private ArrayList<String> dateString = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
//        android.support.v7.app.ActionBar ab = getSupportActionBar();
//        ab.setDisplayShowHomeEnabled(true);
//        textView = findViewById(R.id.textView);
//        textView.setText(results);
        try {
        initNewsItems();
//        ArrayList<NewsItem> n = getItems();
//        for(int i = 0; i < n.size(); i++ ){
//            String title = n.get(i).getTitleFromJSON();
//            String date = n.get(i).getPublishedAtFromJSON();
//            String description = n.get(i).getDescriptionFromJSON();
//            titleString.add(title);
//            dateString.add(date);
//            descriptionString.add(description);
//        }
        }catch (JSONException e){
            e.printStackTrace();
        }
    }

    public ArrayList<NewsItem> getItems() throws JSONException{
        NetworkUtils n = new NetworkUtils();
        URL url = n.buildUrl();
        try {
            json = n.getResponseFromHttpUrl(url);
        }catch (IOException e){
            e.printStackTrace();
        }
        JSONObject jobj = new JSONObject(json);
        JsonUtils j = new JsonUtils();
        ArrayList<NewsItem> g = j.parseNews(jobj);
        return g;
    }

    private void initNewsItems() throws JSONException{
        Log.e("Initializing: ", "one moment please...");



        titleString.add("This should be a title");
        descriptionString.add("This should be a description");

        titleString.add("hey");
        descriptionString.add("there");

        titleString.add("heyq");
        descriptionString.add("thereq");

        titleString.add("heyq1");
        descriptionString.add("thereq1");
        initRecyclerView();
    }
    private void initRecyclerView(){
        mRecyclerView = findViewById(R.id.news_recyclerview);
        NewsAdapter = new NewsRecyclerViewAdapter(this,titleString,descriptionString,dateString);
        mRecyclerView.setAdapter(NewsAdapter);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu){
        MenuInflater mMenuInflater = getMenuInflater();
        mMenuInflater.inflate(R.menu.get_news,menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item){
        switch(item.getItemId()){
            case R.id.action_search:
                Toast.makeText(getApplicationContext(),"Refresh is selected",Toast.LENGTH_SHORT).show();
                NewsQueryTask n = new NewsQueryTask();
                n.execute();
        }
        return super.onOptionsItemSelected(item);
    }

    class NewsQueryTask extends AsyncTask<Void, Void, Void> {


        @Override
        protected Void doInBackground(Void... voids) {
            NetworkUtils n = new NetworkUtils();
            JSONObject jobj = new JSONObject();
            URL url = n.buildUrl();
            try{
                json = n.getResponseFromHttpUrl(url);
            } catch(java.io.IOException e){
                e.printStackTrace();
            }
//            try {
//                jobj = new JSONObject(json);
//                JsonUtils j = new JsonUtils();
//                stories = j.parseNews(jobj);
//                NewsAdapter.mList.addAll(stories);
//                Log.e("Heres the array",stories.get(2).getUrlFromJSON());
//            } catch (JSONException e) {
//                e.printStackTrace();
//            }
            results += json;
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
//            textView.setText(results);
        }
    }
}
