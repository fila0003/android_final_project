package com.cst2335.projectnew;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
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

import java.util.ArrayList;

public class SavedSongsActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    private ArrayList<Song> songs = new ArrayList<>();
    private MyListAdapter myAdapter;
    SQLiteDatabase db;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_saved_albums);

        Toolbar tBar = findViewById(R.id.toolbar_music);
       // setSupportActionBar(tBar);

        //For NavigationDrawer:
        DrawerLayout drawer = findViewById(R.id.drawer_layout_music);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawer, tBar, R.string.open, R.string.close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();
        NavigationView navigationView = findViewById(R.id.nav_view_music);
        navigationView.setNavigationItemSelectedListener(this);
        TextView headline = findViewById(R.id.savedAlbums);
        headline.setText(R.string.savedAlbumsHeader);
        ListView myList = findViewById(R.id.savedAlbumListView);
        // need to get all saved messages from database put them into elements ArrayList
        loadDataFromDatabase();
        myList.setAdapter( myAdapter = new MyListAdapter());
        myList.setOnItemLongClickListener((list, view, pos, id) -> {
            Song song = songs.get(pos);

            AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(this);
            alertDialogBuilder.setTitle(R.string.deleteFromFavButton)
                    .setMessage("Remove the song " + song.getName() + " from Favorites?")
                    .setPositiveButton("Yes", (click, arg) -> {
                        // delete from database
                        db.delete(MyDBOpener.TABLE_NAME, MyDBOpener.COL_ID + "= ?", new String[]{Long.toString(song.getId())});
                        // db.execSQL("delete from " + TABLE_NAME + " where _id='"+ elements.get(pos).getId()+previouseState+ "'");

                        myAdapter.notifyDataSetChanged();
                    }).setNegativeButton("NO", (click, arg) -> {})

                    .setView(getLayoutInflater().inflate(R.layout.dialog_layout, null))
                    .create().show();

            return true;
        });
    }

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

    private void loadDataFromDatabase(){
        MyDBOpener dbOpener = new MyDBOpener(this );
        db  = dbOpener.getWritableDatabase();
        String [] columns = {MyDBOpener.COL_ID, MyDBOpener.COL_NAME, MyDBOpener.COL_ARTIST_ID, MyDBOpener.COL_ARTIST_NAME};
        Cursor results = db.query(false, MyDBOpener.TABLE_NAME, columns, null, null,null, null, null, null);
        int idColIndex = results.getColumnIndex(MyDBOpener.COL_ID);
        int nameColIndex = results.getColumnIndex(MyDBOpener.COL_NAME);
        int artistIdColIndex = results.getColumnIndex(MyDBOpener.COL_ARTIST_ID);
        int artistNameColIndex = results.getColumnIndex(MyDBOpener.COL_ARTIST_NAME);
        //iterate over the results, return true if there is a next item:
        while(results.moveToNext())
        {
            long id = results.getLong(idColIndex);
            String name = results.getString(nameColIndex);
            long artistId = results.getLong(artistIdColIndex);
            String artistName = results.getString(artistNameColIndex);
            //add the new Contact to the array list:
            songs.add(new Song(id, name, artistId, artistName));
        }
        //At this point, the contactsList array has loaded every row from the cursor.
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
            Song a = songs.get(position);
            newView = inflater.inflate(R.layout.album_list_row_layout, parent, false);

            //set what the text should be for this row:
            TextView nameView = newView.findViewById(R.id.album);
            nameView.setText(a.getName() );
                TextView autor = newView.findViewById(R.id.autor);
                autor.setText(a.getArtist().getName());
                TextView byView = newView.findViewById(R.id.by);
            byView.setText(" by  ");
            return newView;
        }
    }

}