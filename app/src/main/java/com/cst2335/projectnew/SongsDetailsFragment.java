package com.cst2335.projectnew;

import android.content.ContentValues;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;

public class SongsDetailsFragment extends Fragment {
    private Bundle dataFromPreviousActivity;
    ArrayList<Song> songs = new ArrayList<>();
    private AppCompatActivity parentActivity;
    MyListAdapter myAdapter = new MyListAdapter();
    private Song song;


    public SongsDetailsFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        MyDBOpener dbOpener = new MyDBOpener( getActivity() );
        SQLiteDatabase db  = dbOpener.getWritableDatabase();

        dataFromPreviousActivity = getArguments();

        song = (Song) dataFromPreviousActivity.getSerializable("song");

 //       Song s = new Song();
        // Inflate the layout for this fragment
        View result =  inflater.inflate(R.layout.fragment_songs_details, container, false);
       TextView songId = ((TextView)result.findViewById(R.id.songId));
       songId.setText(String.valueOf(song.getId()));
       songId.setOnClickListener(click -> {
           Intent i = new Intent(Intent.ACTION_VIEW);
           i.setData(Uri.parse("http://www.songsterr.com/a/wa/song?id=" + song.getId()));
           startActivity(i);
       });

        ((TextView)result.findViewById(R.id.songName)).setText(song.getName());

        TextView artistId = ((TextView)result.findViewById(R.id.artistId));

        artistId.setText(String.valueOf(song.getArtist().getId()));
        songId.setOnClickListener(click -> {
            Intent i = new Intent(Intent.ACTION_VIEW);
            i.setData(Uri.parse("http://www.songsterr.com/a/wa/artist?id=" + song.getArtist().getId()));
            startActivity(i);
        });

        ((TextView)result.findViewById(R.id.artistName)).setText(song.getArtist().getName());



        Button saveButton = (Button)result.findViewById(R.id.saveAlbumButton);
        Intent savedAlbumsIntent = new Intent(getActivity(), SavedSongsActivity.class);

        saveButton.setOnClickListener(click -> {
            ContentValues newRowValues = new ContentValues();
            AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(getActivity());
            alertDialogBuilder.setTitle(R.string.dialogMessage1)
                    .setMessage(getString(R.string.dialogMessage2) + " "+ song.getName() + " by " + song.getArtist().getName() )
                    .setPositiveButton(R.string.dialogYes, (cl, arg) -> {
                                newRowValues.put(MyDBOpener.COL_ID, song.getId());
                                newRowValues.put(MyDBOpener.COL_NAME, song.getName());
                                newRowValues.put(MyDBOpener.COL_ARTIST_ID, song.getArtist().getId());
                                newRowValues.put(MyDBOpener.COL_ARTIST_NAME, song.getArtist().getName());
                                db.insert(MyDBOpener.TABLE_NAME, null, newRowValues);
                                savedAlbumsIntent.putExtra("justSaved", song.getName() );
                        startActivity(savedAlbumsIntent);

                    }).setNegativeButton(R.string.dialogNo, (cl, arg) -> {})
                    .setView(getLayoutInflater().inflate(R.layout.dialog_layout, null)).create().show();

        });
        return result;
    }

    private class SearchForSongsQuerry extends AsyncTask<String, Integer, String> {

        public String doInBackground(String ... args) {

            try {
                URL url = new URL(args[0]);
                HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
                InputStream response = urlConnection.getInputStream();
                BufferedReader reader = new BufferedReader(new InputStreamReader(response, "UTF-8"), 8);
                StringBuilder sb = new StringBuilder(); // for adding 1 line in a time
                String line = null;
                while ((line = reader.readLine()) != null) {
                    sb.append(line + "\n");
                }
                String result = sb.toString();
                JSONObject jObj = new JSONObject(result);
                JSONArray jArrayAlbums = jObj.getJSONArray("track");
                for (int i = 0; i < jArrayAlbums.length(); i++)
                    try {
                        JSONObject anObject = jArrayAlbums.getJSONObject(i);
                      //  Song song = new Song(anObject.getLong("id"), anObject.getString("title"));
                        // Pulling items from the array
             //           songs.add(song);
                    } catch (JSONException e) {
                        Log.e("not done", e.getMessage());
                    }

            } catch (Exception e) {
                Log.e("not done", e.getMessage());
            }

            return "done";
        }


        protected void onProgressUpdate(String... values) {

//            progressBar.setVisibility(View.VISIBLE);
//            progressBar.setProgress(0);
                for (int i = 0; i < values.length; i++){
                    Log.i("Calling  back for data", "integer = " + values[i]); //this way we can see the data from do in background
            }
        }

        @Override
        protected void onPostExecute(String s) {
            myAdapter.notifyDataSetChanged();
        }

    }

    private class MyListAdapter extends BaseAdapter {

        public int getCount() {
            return songs.size();
        }

        public Song getItem(int position) {
            return songs.get(position);
        }

        public long getItemId(int position) {
            return ((Song) getItem(position)).getId();
        }

        public View getView(int position, View convertView, ViewGroup parent) {
            LayoutInflater inflater = getLayoutInflater();
            View newView;
            Song s = songs.get(position);
            newView = inflater.inflate(R.layout.song_list_row_layout, parent, false);

            //set what the text should be for this row:
            TextView nameView = newView.findViewById(R.id.song);
            nameView.setText(s.getName());
            return newView;
        }
    }





}