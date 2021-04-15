package com.cst2335.projectnew;

import android.os.AsyncTask;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public class CarsActivity extends AppCompatActivity {

    ProgressBar progressBar;
    List<Car> carList;
    ListView listView;
    CarListAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cars);

        progressBar = findViewById(R.id.progressBar);
        listView = findViewById(R.id.listView);
        carList = new ArrayList<>();

        String searchItem = getIntent().getStringExtra("searchString");

        String url = "https://vpic.nhtsa.dot.gov/api/vehicles/GetModelsForMake/"+ searchItem + "?format=json";

        CarQuery req = new CarQuery();
        req.execute(url);

        adapter = new CarListAdapter();
        listView.setAdapter(adapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                CarsDetailsFragment.CAR = carList.get(position);

                Fragment fragment = new CarsDetailsFragment();
                getSupportFragmentManager().beginTransaction().replace(R.id.contentMain, fragment).commit();

            }
        });


    }

    class CarQuery extends AsyncTask<String, Integer, String> {

        @Override
        protected String doInBackground(String... args) {
            try {
                URL url = new URL(args[0]);
                HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
                InputStream response = urlConnection.getInputStream();

                BufferedReader Reader = new BufferedReader(new InputStreamReader(response, "UTF-8"), 8);
                StringBuilder SB = new StringBuilder();

                String line = null;
                while ((line = Reader.readLine()) != null) {
                    SB.append(line + "\n");
                }
                String Result = SB.toString(); //result is the whole string

                JSONObject jObj = new JSONObject(Result);

                if (jObj.getInt("Count") == 0)
                    Toast.makeText(CarsActivity.this, "Nothing found", Toast.LENGTH_SHORT).show();
                else
                {

                    JSONArray jsonArray = jObj.getJSONArray("Results");

                    for (int i = 0; i < jsonArray.length(); i++)
                    {

                        JSONObject data = (JSONObject) jsonArray.get(i);

                        Car car = new Car();
                        car.setMake_ID(data.getString("Make_ID"));
                        car.setMake_Name(data.getString("Make_Name"));
                        car.setModel_ID(data.getString("Model_ID"));
                        car.setModel_Name(data.getString("Model_Name"));

                        carList.add(car);

                    }


                }



            } catch (Exception e) {

                return e.toString();
            }
            return "Done";
        }

        @Override
        public void onPostExecute(String fromDoInBackground) {

            Toast.makeText(CarsActivity.this, fromDoInBackground, Toast.LENGTH_SHORT).show();
            progressBar.setVisibility(View.INVISIBLE);

            listView.setVisibility(View.VISIBLE);
            adapter.notifyDataSetChanged();
        }
    }

    public class CarListAdapter extends BaseAdapter {


        @Override
        public Car getItem(int position) {
            return carList.get(position);
        }

        @Override
        public long getItemId(int position) {
            return (long) position;
        }

        @Override
        public int getCount() {
            return carList.size();
        }

        @Override
        public View getView(int position, View view, ViewGroup parent) {

            Car car = getItem(position);
            LayoutInflater flater = getLayoutInflater();
            View itemView = view;

            itemView = flater.inflate(R.layout.list_item, parent, false);

            TextView carName = itemView.findViewById(R.id.carName);
            TextView carModel = itemView.findViewById(R.id.carModel);

            carName.setText(car.getMake_Name());
            carModel.setText(car.getModel_Name());
            return itemView;
        }

    }

}