package com.cst2335.Cinthia040976686;

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Toast;

import android.database.sqlite.SQLiteDatabase;
import android.app.Activity;
import android.widget.ListView;

import androidx.appcompat.app.AlertDialog;

/*
Class activity which contain a la string and an interger type
 */
public class SoccerMainActivity extends Activity {

    ListView simpleList;
    String countryList[] = {"Chat", "Chhat_buble", "Chhat_buble_outline", "person search", "payment"};
    int flags[] = {R.drawable.ic_baseline_chat_24, R.drawable.ic_baseline_chat_bubble_24, R.drawable.ic_baseline_chat_bubble_outline_24, R.drawable.ic_baseline_person_search_24, R.drawable.ic_baseline_payment_24};

    SQLiteDatabase db;
    int positionClicked = 0;


    /*
    @description on create method called when the class its lauch
    @author Cinthia
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        simpleList = (ListView) findViewById(R.id.simpleListView);
        SoccerCustomAdapter soccerCustomAdapter = new SoccerCustomAdapter(getApplicationContext(), countryList, flags);
        simpleList.setAdapter(soccerCustomAdapter);

    /*
    @return void
    @description listener on o listview when clicked gives a toast message
    @author Cinthia
     */
        simpleList.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                //showContact(position);
                Toast.makeText(SoccerMainActivity.this, "Item Selected " + position, Toast.LENGTH_SHORT).show();

            }
        });

            /*
    @return boolean
    @description listener on o listview when clicked gives a toast message
    @author Cinthia
     */

        simpleList.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {

            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                //soccerCustomAdapter.notifyDataSetChanged();
                AlertDialog.Builder builder = new AlertDialog.Builder(getApplicationContext());
                builder.setTitle("You clicked on item #" + position)
                        .setMessage("You can delete the item and then click or to save in the favorite")
                        .setPositiveButton("Save", (click, b) -> {
                            //selectedContact.update(rowName.getText().toString(), rowEmail.getText().toString());
                            //  Delete(selectedContact);
                            finish();
                            Toast.makeText(getApplicationContext(), "Item have been save ", Toast.LENGTH_SHORT).show();
                          //  soccerCustomAdapter.notifyDataSetChanged(); //the email and name have changed so rebuild the list
                        })
                        .setNegativeButton("Delete", (click, b) -> {
                            //deleteContact(selectedContact); //remove the contact from database
                            //contactsList.remove(position); //remove the contact from contact list
                            Toast.makeText(getApplicationContext(), "Item have been deleted ", Toast.LENGTH_SHORT).show();
                           // soccerCustomAdapter.notifyDataSetChanged(); //there is one less item so update the list
                        })
                        .setNeutralButton("dismiss", (click, b) -> {
                        })
                        .create().show();
                return true;
            }


        });
    }
}

/*
        protected void Delete(Contact c)
        {
            //Create a ContentValues object to represent a database row:
            ContentValues updatedValues = new ContentValues();
            updatedValues.put(MyOpener.COL_NAME, c.getName());
            updatedValues.put(MyOpener.COL_EMAIL, c.getEmail());

            //now call the update function:
            db.update(MyOpener.TABLE_NAME, updatedValues, MyOpener.COL_ID + "= ?", new String[] {Long.toString(c.getId())});
        }

        protected void deleteContact(Contact c)
        {
            db.delete(MyOpener.TABLE_NAME, MyOpener.COL_ID + "= ?", new String[] {Long.toString(c.getId())});
        }*/









