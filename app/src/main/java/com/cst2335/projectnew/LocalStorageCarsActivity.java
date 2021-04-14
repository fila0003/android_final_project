package com.cst2335.projectnew;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class LocalStorageCarsActivity extends AppCompatActivity {

    List<Car> carList;
    ListView listView;
    LocalStorageCarsActivity.CarListAdapter adapter;
    SQLiteDatabase db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_local_storage_cars);

        listView = findViewById(R.id.listView);
        carList = new ArrayList<>();

        DBCars dbOpener = new DBCars(this);
        db = dbOpener.getWritableDatabase();

        loadFromDatabase();


        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                DetailsFragment.CAR = carList.get(position);

                Fragment fragment = new DetailsFragment();
                getSupportFragmentManager().beginTransaction().replace(R.id.contentMain, fragment).commit();

            }
        });

    }

    private void loadFromDatabase() {


        String [] columns = {DBCars.COL_ID, DBCars.COL_MAKE_ID, DBCars.COL_MAKE_NAME, DBCars.COL_MODEL_ID, DBCars.COL_MODEL_NAME};

        Cursor results = db.query(false, DBCars.TABLE_NAME, columns, null, null, null, null, null, null);

        int _id = results.getColumnIndex(DBCars.COL_ID);
        int makeId = results.getColumnIndex(DBCars.COL_MAKE_ID);
        int makeName = results.getColumnIndex(DBCars.COL_MAKE_NAME);
        int modelId = results.getColumnIndex(DBCars.COL_MODEL_ID);
        int modelName = results.getColumnIndex(DBCars.COL_MODEL_NAME);


        while(results.moveToNext())
        {
            long id = results.getLong(_id);
            String mkid = results.getString(makeId);
            String mkname = results.getString(makeName);
            String mdid = results.getString(modelId);
            String mdname = results.getString(modelName);

            carList.add(new Car(id, mkid, mkname, mdid, mdname));
        }

        adapter = new LocalStorageCarsActivity.CarListAdapter();

        listView.setAdapter(adapter);
        adapter.notifyDataSetChanged();
        //this.printCursor(results, 1);

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