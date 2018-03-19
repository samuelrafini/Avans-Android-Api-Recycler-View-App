package com.example.snf.nasamarsrover;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements OnListItemAvailable {

    private static final String TAG = "MainActivity";

    private RecyclerView mRecyclerView;
    private RecyclerView.Adapter mAdapter;
    private ArrayList<ListItem> listItems;
    private ListItemDatabase listItemDatabase;
    private Spinner spinner;
    private ArrayAdapter<CharSequence> spinnerAdapter;
    private final String apiKey = "sjgKXtzpLWrvcJChWuZpcMQLfL2tn9BfBTtwsC5K";
    private String url;
    private String cameraNameQuery;

    protected void onCreate(Bundle savedInstanceState) {
        Log.d(TAG, " calls onCreate ");

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        spinner = (Spinner) findViewById(R.id.cameraSpinner);
        spinnerAdapter = ArrayAdapter.createFromResource(this, R.array.camera_names, android.R.layout.simple_spinner_item);
        spinnerAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(spinnerAdapter);
        listItemDatabase = new ListItemDatabase(this);

        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                Toast.makeText(getBaseContext(), (String)adapterView.getItemAtPosition(i), Toast.LENGTH_SHORT).show();


//                url = "https://api.nasa.gov/mars-photos/api/v1/rovers/curiosity/photos?sol=1000&api_key=" + apiKey;
//                cameraNameQuery = (String)adapterView.getItemAtPosition(i);

                //Dit werkt maar opeens last minute niet meer
                url = "https://api.nasa.gov/mars-photos/api/v1/rovers/curiosity/photos?sol=1000&page=1&camera=" + (String)adapterView.getItemAtPosition(i) + "&api_key=" + apiKey;
                fetchRandomUsers();


            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

                url = "https://api.nasa.gov/mars-photos/api/v1/rovers/curiosity/photos?sol=1000&page=1&camera=fhaz&api_key=" + apiKey;
                fetchRandomUsers();
            }
        });


        mRecyclerView = (RecyclerView) findViewById(R.id.recycler_view);
        mRecyclerView.setHasFixedSize(true);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));


            //When to get from Database
      listItems = listItemDatabase.getAllItems();
        listItems = new ArrayList<>();



        mAdapter = new ListAdapter(listItems, this);
        mRecyclerView.setAdapter(mAdapter);


    }
    private void fetchRandomUsers() {
        Log.d(TAG, " calls fetchRandomUser ");

        ListItemTask task = new ListItemTask(this);
        String[] urls = new String[] {url};
        task.execute(urls);

    }


    @Override
    public void OnListItemAvailable(ListItem listItem) {
        Log.d(TAG, " calls OnlistItemAvailable ");

      listItemDatabase.addListItem(listItem);
        listItems.add(listItem);
        mAdapter.notifyDataSetChanged();
    }
}
