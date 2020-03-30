package com.example.partia;


import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedList;

public class Event {
    static int EventCounter=1;
    private int pinCode;
    private String eventName;
    private String location= null;
    private Date date=null;
    private LinkedList<Participant> participants = new LinkedList<>();// the managers will also appear here
    private String info;
    private LinkedList<Item> items = new LinkedList<>(); // Todo: make a separate class
    private RidesBoard rides;
    private Cashier cashier;
    private Utils.KindOfEvent kindOfEvent;
    private Utils.Environment environment;
    private Utils.Organization organizationOfMeal;
    private Utils.Organization organizationOfBeverage;
    private HashMap<String, Integer> answersSum;

    public Event(JSONObject eventAnswers) throws Exception
    {
        try {
            this.pinCode = EventCounter;
            increaseCounter();
            this.location=eventAnswers.getString("location");
            this.eventName=eventAnswers.getString("name");
            this.info=eventAnswers.getString("info");
            this.environment=Utils.Environment.valueOf(eventAnswers.getString("environment"));
            this.kindOfEvent=Utils.KindOfEvent.valueOf(eventAnswers.getString("kindOfEvent"));
            if(!eventAnswers.getString("date").equalsIgnoreCase("NONE"))
            {
                SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm'Z'");
                this.date = format.parse(eventAnswers.getString("date"));
            }
            this.organizationOfMeal = Utils.Organization.valueOf(eventAnswers.getString("mealOrganization"));
            this.organizationOfBeverage = Utils.Organization.valueOf(eventAnswers.getString("beverageOrganization"));
        } catch (java.text.ParseException | JSONException e){
            throw e;
        }
    }

    public int getPinCode() {
        return pinCode;
    }

    public String getEventName() {
        return eventName;
    }

    public void setEnvironment(Utils.Environment environment) {
        this.environment = environment;
    }

    public void setKindOfEvent(Utils.KindOfEvent kindOfEvent) {
        this.kindOfEvent = kindOfEvent;
    }

    public void setOrganizationOfBeverage(Utils.Organization organizationOfBeverage) {
        this.organizationOfBeverage = organizationOfBeverage;
    }

    public Utils.Environment getEnvironment() {
        return environment;
    }

    public Utils.KindOfEvent getKindOfEvent() {
        return kindOfEvent;
    }

    public void setOrganizationOfMeal(Utils.Organization organizationOfMeal) {
        this.organizationOfMeal = organizationOfMeal;
    }

    public void increaseCounter()
    {
        EventCounter++;
    }

    public void setLocation(String location)
    {
        this.location= location;
    }

    public String getLocation() {
        return location;
    }

    public Date getDate()
    {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public void addParticipant(Participant newParticipant){
        participants.add(newParticipant);
    }

    public LinkedList<Participant> getParticipants() {
        return participants;
    }

    public void setInfo(String info) {
        this.info = info;
    }

    public String getInfo() {
        return info;
    }

    public LinkedList<Item> getItems()
    {
        return items;
    }

    public void addItem(Item newItem)
    {
        this.items.add(newItem);
    }

    public RidesBoard getRides() {
        return rides;
    }

    public Cashier getCashier() {
        return cashier;
    }

    public HashMap<String, Double> getBeveragesStatistics()
    /**
     * this function returns a hashMap of beverages preference and it's statistic
            */
        {
            double totalParticipants = participants.size();
            HashMap<String, Double> beveragesStatistics = new HashMap<>();
            for (Utils.Beverages beverage : Utils.Beverages.values())
            {
                beveragesStatistics.put(beverage.name(), (answersSum.get(beverage.name())/totalParticipants));
            }

            return beveragesStatistics;
    }

    public HashMap<String, Double> getFoodStatistics()
    /**
     * this function returns a hashMap of food preference and it's statistic
     */
    {
        double totalParticipants = participants.size();
        HashMap<String, Double> foodStatistics = new HashMap<>();
        for (Utils.FoodPreference preference : Utils.FoodPreference.values())
        {
            foodStatistics.put(preference.name(), (answersSum.get(preference.name())/totalParticipants));
        }

        return foodStatistics;
    }

    private void sumParticipantsAnswers(){
        /**
         * this function sums the participants food preferences,
         * and saves the hashMap in data members of the event.
         *  <the preference, number of participant who answered yes>
         */
        HashMap<String, Integer> queryAnswersSum = new HashMap<>();
        for (Utils.FoodPreference preference : Utils.FoodPreference.values())
        {
            queryAnswersSum.put(preference.name(), 0);
        }

        for (Participant participant : participants) {
            for (Utils.FoodPreference preference : Utils.FoodPreference.values()){
                if (participant.getAnswer(preference.name()))
                {
                    queryAnswersSum.put(preference.name(),queryAnswersSum.get(preference.name() + 1));
                }
            }
        }

        this.answersSum = queryAnswersSum;
    }


}
