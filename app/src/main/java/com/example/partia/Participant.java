package com.example.partia;

import org.json.JSONObject;

import java.util.HashMap;
import java.util.Iterator;

/*
This class represent a participant of the event
 */
public class Participant {
    private String userName; // as shown in app
    private HashMap<String, Boolean> queryAnswers = new HashMap<>(); // answers for the query
    private Float investment; // how much spent so far

    public Participant(String name)
    {
        userName = name;
        investment = Float.NaN;
    }

    public boolean setAnswers(JSONObject answers)
    {
        Iterator<String> keys = answers.keys();
        String question;
        while(keys.hasNext()) {
            question = keys.next();
            try {
                if (answers.getString(question).equalsIgnoreCase("true")) {
                    queryAnswers.put(question, true);
                } else
                    queryAnswers.put(question, false);
            } catch (Exception e) {
                return false;
            }
        }

        return true;
    }

    public String getUserName() {return userName;}

    public void setAnswer(String question, Boolean answer)
    {
        queryAnswers.put(question, answer);
    }

    public Boolean getAnswer(String question)
    {
        return queryAnswers.get(question);
    }

    public void increaseInvestment(Float sumToAdd)
    {
        investment += sumToAdd;
    }
}
