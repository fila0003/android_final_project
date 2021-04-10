package com.cst2335.projectnew;

import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.material.navigation.NavigationView;

public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {


    SharedPreferences prefs = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_songster_start);
        // will know if we are running on tablet or on phone
        Toolbar tBar = findViewById(R.id.toolbar_music);// step 3
        //This gets the toolbar from the layout:
        //This loads the toolbar, which calls onCreateOptionsMenu below:
        // setSupportActionBar(tBar);
        //For NavigationDrawer:
        DrawerLayout drawer = findViewById(R.id.drawer_layout_music);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this,
                drawer, tBar, R.string.open, R.string.close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();
        NavigationView navigationView = findViewById(R.id.nav_view_music);
        navigationView.setNavigationItemSelectedListener(this);

        // get artist's name for search will be saved with help of getSharedPreferences
        prefs = getSharedPreferences("FileName", Context.MODE_PRIVATE);
        EditText artistName = findViewById(R.id.artistSearch);
        String savedString = prefs.getString("emptySearch", "");
        artistName.setText(savedString);

        Button searchButton = findViewById(R.id.artistSearchButton);
        Intent artistSearchResults = new Intent(this, ArtistSearchResults.class);

        searchButton.setOnClickListener( bt ->{
            artistSearchResults.putExtra("artistName", artistName.getText().toString());
            startActivity(artistSearchResults);
        } );
    }

    @Override
    protected void onPause() {
        super.onPause();
        EditText artistName = findViewById(R.id.artistSearch);
        SharedPreferences.Editor editor = prefs.edit();
        editor.putString("emptySearch", artistName.getText().toString()); // saving string into SharedPreferences
        editor.commit();
//        Log.e("onPause", emailField.getText().toString());
    }

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
        switch(item.getItemId())
        {
            //what to do when the menu item is selected:
            case R.id.homeReturnMusic:
                message = getResources().getString(R.string.HomeReturn);// this line will make toast messqage translatable
                startActivity(new Intent(this, MainActivity.class));
                break;
            case R.id.searchItemMusic:
                message = getResources().getString(R.string.searchToast); // this line will make toast messqage translatable
                break;
            case R.id.favoriteSongs:
                message = "Favorite songs";
                startActivity(new Intent(this, SavedSongsActivity.class));
                break;
        }
        Toast.makeText(this, message, Toast.LENGTH_LONG).show();
        return true;
    }

    public boolean onNavigationItemSelected( MenuItem item) {

        String message = null;

        switch(item.getItemId())
        {
            case R.id.homeReturnMusic:
                message = getResources().getString(R.string.HomeReturn);;
                Intent goToMainPage= new Intent(this, MainActivity.class);
                startActivity(goToMainPage);
                break;
            case R.id.searchItemMusic:
                message = "@string/searchToast";
                break;
            case R.id.favoriteSongs:
                message = "Favorite songs";
                startActivity(new Intent(this, SavedSongsActivity.class));
                break;
        }

        DrawerLayout drawerLayout = findViewById(R.id.drawer_layout_music);
        drawerLayout.closeDrawer(GravityCompat.START);

        Toast.makeText(this, "NavigationDrawer: " + message, Toast.LENGTH_LONG).show();
        return false;
    }// end of navigation item selections

    public void onPointerCaptureChanged(boolean hasCapture) {

    }
}