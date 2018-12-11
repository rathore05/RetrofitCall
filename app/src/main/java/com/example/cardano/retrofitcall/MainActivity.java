package com.example.cardano.retrofitcall;

import android.support.v4.view.MenuItemCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

import retrofit.Callback;
import retrofit.RestAdapter;
import retrofit.RetrofitError;
import retrofit.client.Response;

public class MainActivity extends AppCompatActivity {

    Toolbar mToolbar;
    Button mClickbtn;

    private RecyclerView mRecyclerView;
    private ModelAdapter mModelAdapter;

    private Button mBtn;
    public static final String ROOT_URL = "http://34.199.49.88:3000/";
    public static final String QUERY_PARAMETER = "choo";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mToolbar = findViewById(R.id.toolbar);

        setSupportActionBar(mToolbar);

        mClickbtn = findViewById(R.id.click_btn);

        mClickbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mRecyclerView = findViewById(R.id.recycler_view);
                mRecyclerView.setHasFixedSize(true);
                RecyclerView.LayoutManager layoutManager=new LinearLayoutManager(getApplicationContext());
                mRecyclerView.setLayoutManager(layoutManager);

                loadData(QUERY_PARAMETER);
            }
        });



    }

    private void loadData(String query){
        RestAdapter adapter = new RestAdapter.Builder()
                .setEndpoint(ROOT_URL).build();
        SearchInterface searchInterface = adapter.create(SearchInterface.class);

        searchInterface.details(query, new Callback<Response>() {
            @Override
            public void success(Response response, Response response2) {
                BufferedReader reader = null;
                String output = "";
                try {
                    reader = new BufferedReader(new InputStreamReader(response.getBody().in()));

                    //Reading the output in the string
                    output = reader.readLine();
                    Log.d("MainActivity", output);
                    Toast.makeText(MainActivity.this, output, Toast.LENGTH_SHORT).show();

                } catch (IOException e) {
                    e.printStackTrace();
                }

                ArrayList<Model> modelList = new ArrayList<>();

                try {
                    JSONArray jsonArray = new JSONArray(output);

                    for (int i =0; i < jsonArray.length(); i++) {
                        JSONObject jsonObject = jsonArray.getJSONObject(i);

                        Integer id = jsonObject.getInt("id");
                        String name = jsonObject.getString("name");
                        Double rating = jsonObject.getDouble("avg_rating");

                        Log.d("Crash Report", (id.toString() + " " + name ));

                        Model model = new Model(id, name, rating);

                        modelList.add(model);

                        Log.d("Main Activity", modelList.toString());

                        mModelAdapter = new ModelAdapter(modelList, getApplicationContext());
                        mModelAdapter.notifyDataSetChanged();
                        mRecyclerView.setAdapter(mModelAdapter);

                    }

                }catch (JSONException e){
                    e.printStackTrace();
                }
            }

            @Override
            public void failure(RetrofitError error) {
                Toast.makeText(MainActivity.this, error.toString(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        getMenuInflater().inflate(R.menu.menu_main, menu);

        MenuItem search = menu.findItem(R.id.search);
        SearchView searchView = (SearchView) MenuItemCompat.getActionView(search);
        search(searchView);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        return super.onOptionsItemSelected(item);
    }

    private void search(SearchView searchView) {

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String s) {
                Toast.makeText(MainActivity.this, s + "Clicked", Toast.LENGTH_SHORT).show();
                return false;
            }

            @Override
            public boolean onQueryTextChange(String s) {

                //loadData(QUERY_PARAMETER);


                Toast.makeText(MainActivity.this, s + ": Clicked", Toast.LENGTH_SHORT).show();
                return true;
            }
        });
    }


}
