

package com.cst2335.projectnew;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import com.google.android.material.navigation.NavigationView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;

// contains fragment for tablet
public class ArtistSearchResults extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {
    ArrayList<Song> songs = new ArrayList<>();
    public static final String ALBUM_SELECTED = "ALBUM";
    public static final String ARTIST = "ARTIST";
    public static final String UNIQUEID_SELECTED = "UNIQUEID";
    SongsDetailsFragment songsFragment;

    MyListAdapter myAdapter = new MyListAdapter();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_artist_search_results);

        //for tool bar
        Toolbar tBar = findViewById(R.id.toolbar_music);
        //setSupportActionBar(tBar);

        //For NavigationDrawer:
        DrawerLayout drawer = findViewById(R.id.drawer_layout_music);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawer, tBar, R.string.open, R.string.close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();
        NavigationView navigationView = findViewById(R.id.nav_view_music);
        navigationView.setNavigationItemSelectedListener(this);

        // let's get artist name
        Intent fromMainActivity = getIntent();
        String artistName = fromMainActivity.getStringExtra("artistName");
        TextView aName = findViewById(R.id.nameInResults);
        aName.setText(artistName);
        ListView albumList = findViewById(R.id.albumListView);
        albumList.setAdapter(myAdapter);
        // preparation to searching in the internet

        SearchingForAlbumsQuery req = new SearchingForAlbumsQuery();
        AsyncTask<String, String, String> task = req.execute("http://www.songsterr.com/a/ra/songs.json?pattern=" + artistName);
//         for tablet and phone will be different ways of displaying
        boolean isTablet = findViewById(R.id.fragmentLocationMusic)!=null;

        albumList.setOnItemClickListener((list, view, pos, id) -> {
            Song song = songs.get(pos);
//            if( isTablet ) {
//                songsFragment = new SongsDetailsFragment();
//                songsFragment.setArguments(dataToPass);
//                getSupportFragmentManager().beginTransaction()
//                        .addToBackStack(null)
//                        .replace(R.id.fragmentLocationMusic,songsFragment)
//                        .commit(); // calls on create in MusicGalleryDetailesFragment
//            } else {
                Intent nextMusicActivity = new Intent(this, SongsActivity.class);
                nextMusicActivity.putExtra("song", song); //send data to next activity
                startActivity(nextMusicActivity); //make the transition to the nextMusicActivity
//            }
        });
    }

    //
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu items for use in the action bar
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.songster_menu, menu);
        // slide 15 material:
        MenuItem searchItem = menu.findItem(R.id.searchItemMusic);
        SearchView sView = (SearchView) searchItem.getActionView();
        sView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return true;
            }// end of TextSubmit

            @Override
            public boolean onQueryTextChange(String newText) {
                return false;
            }// end of TextChange
        });
        return true;
    }

    @SuppressLint("NonConstantResourceId")
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        String message = null;
        //Look at your menu XML file. Put a case for every id in that file:
        switch (item.getItemId()) {
            //what to do when the menu item is selected:
            case R.id.homeReturnMusic:
                message = getResources().getString(R.string.HomeReturn);// this line will make toast messqage translatable
                Intent goToMainPage = new Intent(this, MainActivity.class);
                startActivity(goToMainPage);
                break;
            case R.id.searchItemMusic:
                message = getResources().getString(R.string.searchToast); // this line will make toast messqage translatable
                break;
        }
        Toast.makeText(this, message, Toast.LENGTH_LONG).show();
        return true;
    }

    public boolean onNavigationItemSelected(MenuItem item) {

        String message = null;

        switch (item.getItemId()) {
            case R.id.homeReturnMusic:
                message = getResources().getString(R.string.HomeReturn);
                ;
                Intent goToMainPage = new Intent(this, MainActivity.class);
                startActivity(goToMainPage);
                break;
            case R.id.searchItemMusic:
                message = "@string/searchToast";
                break;
        }

        DrawerLayout drawerLayout = findViewById(R.id.drawer_layout_music);
        drawerLayout.closeDrawer(GravityCompat.START);

        Toast.makeText(this, "NavigationDrawer: " + message, Toast.LENGTH_LONG).show();
        return false;
    }// end of navigation item selections

    public void onPointerCaptureChanged(boolean hasCapture) {

    }

    public class SearchingForAlbumsQuery extends AsyncTask<String, String, String> {

        @Override
        protected String doInBackground(String... args) {
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
                Log.i("result", result);
                JSONArray jArrayAlbums = new JSONArray(result);
//                JSONArray jArrayAlbums = jObj.getJSONArray("album");
                for (int i = 0; i < jArrayAlbums.length(); i++)
                    try {
                        JSONObject anObject = jArrayAlbums.getJSONObject(i);
                        JSONObject a = anObject.getJSONObject("artist");

                        Song song = new Song(anObject.getLong("id"), anObject.getString("title"), a.getLong("id"), a.getString("name"));
                        // Pulling items from the array
                        songs.add(song);
                    } catch (JSONException e) {
                    }
            } catch (Exception e) {
                Log.e("not done", e.getMessage());
            }
            return "Done";
        }

        @Override
        protected void onProgressUpdate(String... values) {

//            progressBar.setVisibility(View.VISIBLE);
//            progressBar.setProgress(0);
            for (String value : values) {
                Log.i("Calling  back for data", "integer = " + value); //this way we can see the data from do in background
            }
        }

        @Override
        protected void onPostExecute(String s) {
            ListView albumList = findViewById(R.id.albumListView);
            MyListAdapter myAdapter = new MyListAdapter();
            albumList.setAdapter(myAdapter);
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
                Song song = songs.get(position);
                newView = inflater.inflate(R.layout.album_list_row_layout, parent, false);

                //set what the text should be for this row:
                TextView nameView = newView.findViewById(R.id.album);
                nameView.setText(song.getName());

                return newView;
            }
        }

    }

