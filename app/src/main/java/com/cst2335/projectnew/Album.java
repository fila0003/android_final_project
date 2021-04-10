package com.cst2335.projectnew;

public class Album {
    private String albumName;
    String uniqueId;
    private long id;
    private String aName;

    public Album(){
        albumName = "album1";
        uniqueId = "0";
        aName = "artist1";
        id = 1;
    }

    public Album(String albumName, String uniqueId, String aName, long id) {
        this.albumName = albumName;
        this.uniqueId = uniqueId;
        this.aName = aName;
        this.id = id;
    }

    public void setAlbumName(String albumName) {
        this.albumName = albumName;
    }

    public void setUniqueId(String year) {
        this.uniqueId = year;
    }

    public String getAlbumName() {
        return albumName;
    }

    public String getUniqueId() {
        return uniqueId;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getaName() {
        return aName;
    }

    public void setaName(String aName) {
        this.aName = aName;
    }
}
