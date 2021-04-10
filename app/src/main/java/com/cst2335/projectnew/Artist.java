package com.cst2335.projectnew;

import java.io.Serializable;

public class Artist implements Serializable {

    private final long id;
    private final String name;

    public Artist(long id, String name) {
        this.id = id;
        this.name = name;
    }

    public long getId() {
        return id;
    }

    public String getName() {
        return name;
    }
}
