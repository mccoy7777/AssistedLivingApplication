package com.example.assistedlivingapplication;

public class Tasks {

    private String name;
    private String description;

    public Tasks(){

    }//default constructor needed for firebase

    public Tasks (String name, String description){

        this.name = name;
        this.description = description;
    }//end of constructor

    public String getName() {
        return name;
    }//end of getter

    public String getDescription() {
        return description;
    }//end of getter


}//end of Tasks class
