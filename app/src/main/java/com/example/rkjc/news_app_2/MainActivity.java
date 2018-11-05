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

    private RecyclerView mRecyclerView;
    private NewsRecyclerViewAdapter NewsAdapter;
    private ArrayList<String> titleString = new ArrayList<>();
    private ArrayList<String> descriptionString = new ArrayList<>();
    private ArrayList<String> dateString = new ArrayList<>();
    private ArrayList<String> urlString = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }


    private void setUpNews(String input) throws JSONException{
        Log.e("SetUp News:" , input);
        titleString.clear();
        descriptionString.clear();
        dateString.clear();
        urlString.clear();
        //JSONObject jsonObj = new JSONObject(input);
        JsonUtils utils = new JsonUtils();
        ArrayList<NewsItem> list = utils.parseNews(input);
        for(int i = 0; i < list.size(); i++){
            titleString.add(list.get(i).getTitle());
            descriptionString.add(list.get(i).getDescription());
            dateString.add(list.get(i).getPublishedAtFromJSON());
            urlString.add(list.get(i).getUrl());
        }
    }

    private void initNewsItems() throws JSONException,IOException{
        Log.e("Initializing: ", "one moment please...");
        initRecyclerView();
    }
    private void initRecyclerView(){
        mRecyclerView = findViewById(R.id.news_recyclerview);
        NewsAdapter = new NewsRecyclerViewAdapter(this,titleString,descriptionString,dateString,urlString);
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
        String json;

        @Override
        protected Void doInBackground(Void... voids) {
            URL url = NetworkUtils.buildUrl();
            try{
                json = NetworkUtils.getResponseFromHttpUrl(url);
                setUpNews(json);
            } catch(JSONException | java.io.IOException e){
                e.printStackTrace();
            }
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
            try {
                initNewsItems();
            }catch(IOException | JSONException e){
                e.printStackTrace();
            }
        }
    }
}
