package com.cst2335.Cinthia040976686;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.RatingBar;
import android.widget.Toast;

import java.util.Timer;
import java.util.TimerTask;

/*
@return void
@descriptionon oncreate method create a database
 @param SQLiteDatabase db
@author Cinthia
 */
public class SoccerDisplay extends AppCompatActivity {
    RatingBar rating;
    AlertDialog.Builder builder;
    int counter=0;
    ProgressBar simpleProgressBar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        rating= findViewById(R.id.rating);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_display);

        Toolbar toolbar=findViewById(R.id.toolbar);
        Button button =findViewById(R.id.button);
        Button button2 =findViewById(R.id.button2);

        simpleProgressBar= findViewById(R.id.simpleProgressBar);
        setSupportActionBar(toolbar);

        button.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                prog();//progressbar
                Intent i = new Intent(getApplicationContext(), SoccerMainActivity.class);
                startActivity(i);
            }

        });
        button2.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                String s =String.valueOf(rating.getRating());
                Toast.makeText(SoccerDisplay.this, s +"Star", Toast.LENGTH_SHORT).show();
               // rating.setRating(load());
            }

        });
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu,menu);
        return true ;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        int id= item.getItemId();
        if (id== R.id.item1){
            AlertDialog.Builder builder = new AlertDialog.Builder(
                    this);
            builder.setTitle("Help Alert");
            builder.setMessage("Click on the item to save any");
            builder.setPositiveButton("OK",
                    new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog,
                                            int which) {
                            Toast.makeText(getApplicationContext(),"OK is clicked",Toast.LENGTH_LONG).show();
                        }
                    });
            builder.show();
        }
        return true;
    }

    public void prog(){
        final Timer t=new Timer();
        TimerTask tk=new TimerTask(){
            @Override
            public void run(){
                counter++;
                simpleProgressBar.setProgress(counter);
                if(counter==100)
                    t.cancel();
            }
        };
        t.schedule(tk,0,100);
    }
    public void save( float f){
        SharedPreferences prefs = getSharedPreferences("FileName", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = prefs.edit();
        editor.putFloat("rating", f);
        editor.commit();
    }
    public float load(){
        SharedPreferences prefs = getSharedPreferences("FileName", Context.MODE_PRIVATE);
        float f =prefs.getFloat("rating", 0f);
        return f;

    }

}
