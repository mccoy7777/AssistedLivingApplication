package com.example.assistedlivingapplication;

import com.google.firebase.database.Exclude;

import java.sql.Time;
import java.util.List;

public class Tasks {

    private String documentId;
    private String name;
    private String description;
    private String time;
    List<String> tags;

    public Tasks(){

    }//default constructor needed for firebase

    public Tasks (String name, String description, String time, List<String> tags){
        this.name = name;
        this.description = description;
        this.time = time;
        this.tags = tags;
    }//end of constructor

    public String getName() {
        return name;
    }//end of getter

    public String getDescription() {
        return description;
    }//end of getter

    @Exclude
    public String getDocumentId() {
        return documentId;
    }//end of getter

    public void setDocumentId(String documentId) {
        this.documentId = documentId;
    }//end of setter

    public String getTime() {
        return time;
    }//end of getter

    public List<String> getTags() {
        return tags;
    }//end of getter

}//end of Tasks class
