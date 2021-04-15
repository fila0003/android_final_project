package com.cst2335.projectnew;

import java.util.ArrayList;

public class Question {
    private String catagory, question, CA;
    private ArrayList<String> IC;

    Question(String catagory, String question, String CA, ArrayList<String> IC){
        this.catagory = catagory;
        this.question = question;
        this.CA = CA;
        this.IC = IC;
    }
}
