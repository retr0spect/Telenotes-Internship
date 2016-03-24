package com.intern.aditya.telenotes;

import android.os.AsyncTask;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.factual.driver.Factual;
import com.factual.driver.Query;
import com.factual.driver.ReadResponse;
import com.google.common.collect.Lists;

import org.json.JSONArray;
import org.json.JSONException;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Objects;

public class MainActivity extends AppCompatActivity {
    protected Factual factual = new Factual("vCs1yqnrxKttm2iZDpNXIEOuTW1RIjdJznpVmZvI", "Gtlw5aZPWr3lDfVKfytIEYVzbMpZo5q4Yhbh0MES");
    RecyclerView recyclerView;
    Button searchButton;
    EditText searchEditText;
    FloatingActionButton fab;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Email: aditya.tech07@gmail.com", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });

        searchButton = (Button) findViewById(R.id.searchBtn);
        searchEditText = (EditText) findViewById(R.id.searchEditText);

        searchButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String enteredText = searchEditText.getText().toString().trim();
                searchEditText.setText("");
                if (Utilities.isInternetAvailable(getApplicationContext())) {
                    if (!Objects.equals(enteredText, "")) {
                        Query query = new Query()
                                .field("locality").isEqual(enteredText);
                        FactualRetrievalTask task = new FactualRetrievalTask();
                        task.execute(query);
                    } else {
                        Toast.makeText(getApplicationContext(), "Enter Some City!", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Toast.makeText(getApplicationContext(), "Internet Not Available!", Toast.LENGTH_SHORT).show();
                }

            }
        });

        recyclerView = (RecyclerView) findViewById(R.id.cardList);
        if (recyclerView != null) {
            recyclerView.setHasFixedSize(true);
        }
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(linearLayoutManager);

    }


    protected class FactualRetrievalTask extends AsyncTask<Query, Integer, List<ReadResponse>> {
        @Override
        protected List<ReadResponse> doInBackground(Query... params) {
            List<ReadResponse> results = Lists.newArrayList();
            for (Query q : params) {
                results.add(factual.fetch("restaurants-us", q));
            }
            return results;
        }

        @Override
        protected void onProgressUpdate(Integer... progress) {
        }

        @Override
        protected void onPostExecute(List<ReadResponse> responses) {
            ArrayList<Restaurant> restaurants = new ArrayList<>();
            for (ReadResponse response : responses) {
                for (Map<String, Object> restaurant : response.getData()) {
                    String name = (String) restaurant.get("name");
                    String address = (String) restaurant.get("address");

                    StringBuilder sb = new StringBuilder();
                    JSONArray jsonarray = (JSONArray) restaurant.get("category_labels");
                    for (int i = 0; i < jsonarray.length(); i++) {
                        try {
                            JSONArray nestedJsonArray = jsonarray.getJSONArray(i);
                            String typex = String.valueOf(nestedJsonArray.get(nestedJsonArray.length() - 1));
                            if (i < jsonarray.length() - 1) {
                                sb.append(typex).append(", ");
                            } else {
                                sb.append(typex);
                            }

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                    String type = sb.toString();
                    String rating = String.valueOf(restaurant.get("rating"));
                    restaurants.add(new Restaurant(name, address, type, rating));
                }
            }
            if (restaurants.size() > 0) {
                RestaurantAdapter ca = new RestaurantAdapter(restaurants);
                recyclerView.setAdapter(ca);
            } else {
                Toast.makeText(getApplicationContext(), "Invalid City!", Toast.LENGTH_LONG).show();
            }
        }

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
