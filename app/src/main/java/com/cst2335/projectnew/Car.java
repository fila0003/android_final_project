package com.cst2335.projectnew;

public class Car {

    long id;
    String Make_ID;
    String Make_Name;
    String Model_ID;
    String Model_Name;

    public Car(long id, String make_ID, String make_Name, String model_ID, String model_Name) {
        this.id = id;
        Make_ID = make_ID;
        Make_Name = make_Name;
        Model_ID = model_ID;
        Model_Name = model_Name;
    }

    public long getId() {
        return id;
    }

    public Car setId(long id) {
        this.id = id;
        return this;
    }

    public String getMake_ID() {
        return Make_ID;
    }

    public Car setMake_ID(String make_ID) {
        Make_ID = make_ID;
        return this;
    }

    public String getMake_Name() {
        return Make_Name;
    }

    public Car setMake_Name(String make_Name) {
        Make_Name = make_Name;
        return this;
    }

    public String getModel_ID() {
        return Model_ID;
    }

    public Car setModel_ID(String model_ID) {
        Model_ID = model_ID;
        return this;
    }

    public String getModel_Name() {
        return Model_Name;
    }

    public Car setModel_Name(String model_Name) {
        Model_Name = model_Name;
        return this;
    }

    public Car() {
    }
}
