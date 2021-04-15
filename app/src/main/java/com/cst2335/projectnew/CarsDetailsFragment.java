package com.cst2335.projectnew;

import android.content.ContentValues;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.fragment.app.Fragment;

import java.util.Objects;

import static android.widget.Toast.LENGTH_LONG;


public class CarsDetailsFragment extends Fragment {

    TextView make, model;
    Button save, details, buy;
    SQLiteDatabase db;

    public static int flag = 0;

    public static Car CAR;

    public CarsDetailsFragment() {
        // Required empty public constructor
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View root = inflater.inflate(R.layout.fragment_details, container, false);

        make = root.findViewById(R.id.tvMake);
        model = root.findViewById(R.id.tvModel);
        save = root.findViewById(R.id.btnSave);
        details = root.findViewById(R.id.btnDetails);
        buy = root.findViewById(R.id.btnBuy);

        DBCars dbOpener = new DBCars(getActivity());
        db = dbOpener.getWritableDatabase();

        make.setText("Make : " + CAR.getMake_Name());
        model.setText("Model : " + CAR.getModel_Name());

        if (flag == 1)
            save.setText("Delete");

        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (flag == 1) {

                    db.delete(DBCars.TABLE_NAME, "_id=?",
                            new String[]{Long.toString(CAR.getId())});
                    Toast.makeText(getActivity(), "Deleted", Toast.LENGTH_SHORT).show();
                    ((LocalStorageCarsActivity) Objects.requireNonNull(getActivity())).carList.remove(CAR);
                    ((LocalStorageCarsActivity) Objects.requireNonNull(getActivity())).adapter.notifyDataSetChanged();


                } else{

                    ContentValues carData = new ContentValues();
                    carData.put(DBCars.COL_MAKE_ID, CAR.getMake_ID());
                    carData.put(DBCars.COL_MAKE_NAME, CAR.getMake_Name());
                    carData.put(DBCars.COL_MODEL_ID, CAR.getModel_ID());
                    carData.put(DBCars.COL_MODEL_NAME, CAR.getModel_Name());

                    long newID = db.insert(DBCars.TABLE_NAME, null, carData);
                    Toast.makeText(getActivity(), "Inserted item id:" + newID, LENGTH_LONG).show();
                }



            }
        });

        details.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse("http://www.google.com/search?q="
                        +CAR.getMake_Name()
                        +"+"
                        +CAR.getModel_Name()));
                startActivity(browserIntent);

            }
        });

        buy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://www.autotrader.ca/cars/?mdl="
                        + CAR.getModel_Name()
                        + "&make=" + CAR.getMake_Name()
                        + "&loc=K2G1V8"));
                startActivity(browserIntent);

            }
        });


        return root;
    }
}