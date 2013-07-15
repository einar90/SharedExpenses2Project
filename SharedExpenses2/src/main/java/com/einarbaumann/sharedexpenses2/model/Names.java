package com.einarbaumann.sharedexpenses2.model;

/**
 * Created by Einar on 12.07.13.
 */
public class Names {

    private String nameOne = "Person One";
    private String nameTwo = "Person Two";

    private static Names instance = new Names();
    private Names() {}
    public static Names getInstance() {
        return instance;
    }

    public void setNameOne(String nameOne) {
        this.nameOne = nameOne;
    }

    public void setNameTwo(String nameTwo) {
        this.nameTwo = nameTwo;
    }

    public String getName(Person person) {
        if (person == Person.ONE) {
            return nameOne;
        }
        else return nameTwo;
    }

    public String getNameOne() {
        return nameOne;
    }

    public String getNameTwo() {
        return nameTwo;
    }



}
