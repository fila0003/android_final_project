package com.cst2335.projectnew;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.snackbar.Snackbar;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;


public class StartActivity extends AppCompatActivity implements View.OnClickListener {
    private ArrayList<Question> questionArray = new ArrayList<>();;
    private ProgressBar loader;
    private Global global;
    private LinearLayout buttonLayout;
    private TextView highscore;
    private ListView listView;
    private SharedPreferences sharedPreferences;
    private Dialog dialog;
    TriviaArrayList arrayAdapter = new TriviaArrayList();
    ArrayList<String> arrayList;
    Button continueBtn, credit, directions;
    EditText amount, difficulty, type;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start);


        credit = findViewById(R.id.credits);
        credit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(StartActivity.this, "Made By: Marko", Toast.LENGTH_SHORT).show();
            }
        });

        sharedPreferences = getSharedPreferences(Config.PREFERENCES, MODE_PRIVATE);
        global = Global.getInstance(getApplication());
        global.reset();
        getView();

        amount = findViewById(R.id.etAmount);
        difficulty = findViewById(R.id.etDif);
        type = findViewById(R.id.etType);

        continueBtn = findViewById(R.id.continueBtn);
        continueBtn.setOnClickListener(v -> {
            TriviaQuery trivia = new TriviaQuery();
            trivia.execute("https://opentdb.com/api.php?amount="+amount.getText()+"&difficulty="+difficulty.getText()+"&type="+type.getText()+"");
        });

        ListView highScore = findViewById(R.id.highScores);
        List<String> arrayList = new ArrayList<String>();
        arrayList.add("Highscore1");
        arrayList.add("Highscore2");
        arrayList.add("Highscore3");
        arrayList.add("Highscore4");
        arrayList.add("Highscore5");

        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(
                this, android.R.layout.simple_list_item_1,arrayList);
        highScore.setAdapter(arrayAdapter);


    }
    private class TriviaArrayList extends BaseAdapter {


        @Override
        public int getCount() {
            return  arrayList.size();
        }

        @Override
        public Object getItem(int i) {
            return arrayList.get(i);
        }

        @Override
        public long getItemId(int i) {
            return i;
        }


        @Override
        public View getView(int i, View old, ViewGroup parent)
        {
            LayoutInflater inflater = getLayoutInflater();
            View newView = inflater.inflate(R.layout.trivia_list, parent, false);
            TextView textview = newView.findViewById(R.id.triviaTitle);
            String thisTrivia = (String) getItem(i);
            textview.setText(thisTrivia);
            return newView;

        }
    }

    private class TriviaQuery extends AsyncTask<String, Integer, String> {

        Context context = getApplicationContext();
        CharSequence text = "";
        int duration = Toast.LENGTH_SHORT;
        public String catagory;
        public String question;
        public String CA;
        public ArrayList<String> IC;
        CheckBox correct;
        CheckBox incorrect1;
        CheckBox incorrect2;
        CheckBox incorrect3;
        @Override
        protected String doInBackground(String... args) {
            try {
                URL url = new URL(args[0]);
                HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
                InputStream response = urlConnection.getInputStream();

                BufferedReader reader = new BufferedReader(new InputStreamReader(response, "UTF-8"), 8);
                StringBuilder sb = new StringBuilder();
                String line = "";

                while ((line = reader.readLine()) != null) {
                    sb.append(line + "\n");
                }

                String TriviaWholeJSON = sb.toString();

                JSONObject object = new JSONObject(TriviaWholeJSON);

                setProgress(0);

                JSONArray jsonArray = object.getJSONArray("results");
                for (int i = 0; i < jsonArray.length(); i++) {
                    JSONObject JO = (JSONObject) jsonArray.get(i);
                    catagory = JO.getString("category");
                    question = JO.getString("question");
                    CA = JO.getString("correct_answer");

                    JSONArray incorrectAwnsers = JO.getJSONArray("incorrect_answers");
                    for(int f = 0; f < incorrectAwnsers.length(); f++) {
                        JSONObject JOI = (JSONObject) incorrectAwnsers.get(f);
                        IC.add(JOI.getString("incorrect_answers"));
                    }
                    questionArray.add(new Question(catagory, question, CA, IC));
                }

            } catch (FileNotFoundException e){
                e.printStackTrace();
            }catch (JSONException e){
                e.printStackTrace();
            } catch(Exception e) {
                e.printStackTrace();
            }
            return "Done";
        }

        //Updates the progress bar
        public void onProgressUpdate(Integer... value) {

        }

        //Updates listView with found results and shows a toast button
        public void onPostExecute(String fromDoInBackground) {

            Log.i("HTTP", fromDoInBackground);
        }
    }

    public void displayAlert(View v){
        AlertDialog.Builder alert = new AlertDialog.Builder(this);
        alert.setTitle("Instructions");
        String[] directions = {"1. Welcome", "2. Choose a category to proceed", "3. Enjoy your game!"};
        alert.setItems(directions, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                switch(which){
                    case 0:
                    case 1:
                    case 2:

                }
            }
        });
        alert.setPositiveButton("Continue", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
                Snackbar snackbar = Snackbar.make(buttonLayout, "Enjoy your game! Best of luck!", Snackbar.LENGTH_LONG);
                snackbar.show();
            }
        });
        alert.create().show();
    }


    private void getView(){
        loader = findViewById(R.id.loader);
        buttonLayout = findViewById(R.id.button_layout);

        highscore = findViewById(R.id.score);
        highscore.setTag("highscore");
        highscore.setOnClickListener(this);

        dialog = new Dialog(this);
        dialog.setContentView(R.layout.exit_window);
        dialog.setCancelable(false);
        dialog.setCanceledOnTouchOutside(false);

        if(sharedPreferences.contains(Config.SCORE)){
            highscore.setVisibility(View.VISIBLE);
        }else{
            highscore.setVisibility(View.GONE);
        }

    }




    @Override
    public void onClick(View view) {
        TextView textView = (TextView) view;
        String text = view.getTag().toString();
        boolean b_high_score_clicked = false;

        switch(text){
            case"highscore":
                showDialogWindow(view);
                b_high_score_clicked = true;
                break;
            case "gk":
                global.chosenCategory = Config.GENERAL;
                break;
            case "films":
                global.chosenCategory = Config.FILMS;
                break;
            case "celebrities":
                global.chosenCategory = Config.CELEBERITIES;
                break;
            case "books":
                global.chosenCategory = Config.BOOKS;
                break;
            case "music":
                global.chosenCategory = Config.MUSIC;
                break;
            case "tv":
                global.chosenCategory = Config.TV;
                break;
            case "art":
                global.chosenCategory = Config.ART;
                break;
            case "video_games":
                global.chosenCategory = Config.VIDEOGAMES;
                break;
            case "board_games":
                global.chosenCategory = Config.BOARD_GAMES;
                break;
            case "computers":
                global.chosenCategory = Config.COMPUTERS;
                break;
            case "gadgets":
                global.chosenCategory = Config.GADGETS;
                break;
            case "mathematics":
                global.chosenCategory = Config.MATH;
                break;
            case "nature":
                global.chosenCategory = Config.NATURE;
                break;
            case "animals":
                global.chosenCategory = Config.ANIMALS;
                break;
            case "mythology":
                global.chosenCategory = Config.GREEK;
                break;
            case "history":
                global.chosenCategory = Config.HISTORY;
                break;
            case "politics":
                global.chosenCategory = Config.POLITICS;
                break;
            case "geography":
                global.chosenCategory = Config.GEOGRAPHY;
                break;
            case "vehicles":
                global.chosenCategory = Config.VEHICLES;
                break;
            case "sports":
                global.chosenCategory = Config.SPORTS;
                break;
            case "comics":
                global.chosenCategory = Config.COMICS;
                break;
            case "anime":
                global.chosenCategory = Config.ANIME;
                break;
            case "cartoons":
                global.chosenCategory = Config.CARTOON;
                break;
        }

        if(global.networkAvailable()){
            enableAll(false);
        }else{
            Toast.makeText(this, "Network Timeout", Toast.LENGTH_SHORT).show();
        }
    }

    private void enableAll(boolean v){
        for(int i=0;i<buttonLayout.getChildCount();i++){
            ViewGroup child = (ViewGroup) buttonLayout.getChildAt(i);
            for (int q=0;q<child.getChildCount();q++){
                child.getChildAt(q).setEnabled(v);
            }
        }
    }

    private void showDialogWindow(View v){
        final TextView textView = (TextView) v;
        TextView title, score, trivia, correct, totalQuestions;
        Button exitButton;

        int marks = sharedPreferences.getInt(Config.SCORE, -1);
        int questionsAnsweredCorrectly = sharedPreferences.getInt(Config.ANSWERED, 0);
        int questionsGiven = sharedPreferences.getInt(Config.QGIVEN, 1);

        String wisdom = sharedPreferences.getString(Config.WISDOM, Config.ERROR);
        String category = sharedPreferences.getString(Config.CATEGORY, Config.HIGH_SCORE);

        score = dialog.findViewById(R.id.exit_score);
        trivia = dialog.findViewById(R.id.exit_trivia);
        exitButton = dialog.findViewById(R.id.exit_button);
        correct = dialog.findViewById(R.id.correct_number);
        totalQuestions = dialog.findViewById(R.id.total_questions);

        String correct_string = Integer.toString(questionsAnsweredCorrectly);
        String total_String = Integer.toString(questionsGiven);
        String score_string = Integer.toString(marks);

        correct.setText(correct_string);
        totalQuestions.setText(total_String);
        score.setText(total_String);
        trivia.setText(wisdom);

        exitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });
        dialog.show();
    }
}
