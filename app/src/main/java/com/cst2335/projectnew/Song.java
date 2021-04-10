package com.cst2335.projectnew;

import java.io.Serializable;

public class Song implements Serializable {

    private final long id;
    private final String name;
    private  final Artist artist;
    public Song(long id, String name, long artistId, String artistName) {
        this.id = id;
        this.name = name;
        artist = new Artist(artistId, artistName);
    }

    public long getId() {
        return id;
    }


    public String getName() {
        return name;
    }

    public Artist getArtist() {
        return artist;
    }
}
