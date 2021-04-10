package com.cst2335.projectnew;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

public class SongsActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.placeholder_for_fragment);
        Song song = (Song) getIntent().getSerializableExtra("song");
        SongsDetailsFragment songsFragment = new SongsDetailsFragment();

        Bundle bundle = new Bundle();
        bundle.putSerializable("song", song);
        songsFragment.setArguments(bundle);
        getSupportFragmentManager().beginTransaction()
                .addToBackStack(null)
                .replace(R.id.fragmentLocationMusic,songsFragment)
                .commit();

    }
}