package com.example.partia;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.*;



public final class Generator {

    private Generator () { // private constructor
    }

    /**
     * This function generates an equipment list for each kind of an event
     * @param kindOfEvent: the enum represents the type of the event
     * @return a hashmap with the Items in the suggested equipment list, and true/false for each,
     * or None if there was a problem
     */
    public static HashMap<Item,Boolean> generateEquipmentList(Utils.KindOfEvent kindOfEvent, Utils.Environment environment)
    {
        HashMap<Item, Boolean> equipmentList = new HashMap<>();
        try{
            String fileName = String.format("%sEquipmentJson.json", kindOfEvent.name());
            addItemsToEquipmentHash(equipmentList, fileName);
            fileName = String.format("%sEquipmentJson.json", environment.name());
            addItemsToEquipmentHash(equipmentList, fileName);
        }
        catch (Exception e){
            return null;
        }

        return  equipmentList;
    }

    private static void addItemsToEquipmentHash(HashMap<Item, Boolean> equipmentMap, String fileName)throws Exception{
        try {
            Object jsonContainer = new JSONParser().parse(new FileReader(fileName));
            JSONObject jsonEquipment = (JSONObject) jsonContainer;
            JSONArray equipmentArray = ((JSONArray)jsonEquipment.get("EquipmentList"));
            addSimpleItemToMap(equipmentMap, equipmentArray);
        }
        catch (Exception e){
            throw new Exception();
        }
    }

    public static  HashMap<Item,Boolean> generateFoodList(Utils.KindOfMeal kindOfMeal, int numberOfParticipats, HashMap<String, Integer> answersSum)
    {
        // This function generates food list according to the kind of meal the was chosen by the admin.
        HashMap<Item, Boolean> foodMap = new HashMap<>();
        Object jsonContainer = null;
        try {
            jsonContainer = new JSONParser().parse(new FileReader("FoodJson.json"));
            JSONObject jsonFoodLists = (JSONObject) jsonContainer;
            JSONObject foodJson = ((JSONObject)jsonFoodLists.get(kindOfMeal.name()));
            addFoodItemsToMap(foodMap, foodJson, kindOfMeal, numberOfParticipats, answersSum);
            return foodMap;
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        } catch (ParseException e) {
            e.printStackTrace();
            return null;
        }
    }

    private static void addFoodItemsToMap(HashMap<Item,Boolean> foodMap, JSONObject foodJson, Utils.KindOfMeal kindOfMeal, int numOfParticipants,HashMap<String, Integer> answersSum) {
        if (kindOfMeal == Utils.KindOfMeal.DAIRY) {
            JSONObject dairyFoodJson = (JSONObject) foodJson.get("food");
            JSONArray dairyEquipmentArr = (JSONArray) foodJson.get("equipment");
            addSimpleItemToMap(foodMap, dairyEquipmentArr);
            addComplexItemToMap(foodMap, dairyFoodJson, numOfParticipants);
        }
        else if (kindOfMeal == Utils.KindOfMeal.MEAT){
            JSONArray meatEquipmentArr = (JSONArray) foodJson.get("equipment");
            addSimpleItemToMap(foodMap, meatEquipmentArr);
            JSONObject meatFoodJson = (JSONObject) foodJson.get("meat");
            int veganEaters= answersSum.get(Utils.FoodPreference.VEGAN);
            int vegetarianEaters = answersSum.get(Utils.FoodPreference.VEGETARIAN);
            int meatEaters = numOfParticipants - veganEaters - vegetarianEaters;
            addComplexItemToMap(foodMap, meatFoodJson, meatEaters);
            JSONObject vegFoodJson = (JSONObject) foodJson.get("vegetarian");
            int numOfEatersAfterVeganRatio = (int) (numOfParticipants + 0.2 * (veganEaters + vegetarianEaters));
            addComplexItemToMap(foodMap, vegFoodJson, numOfEatersAfterVeganRatio);
            JSONObject generalFoodJson = (JSONObject) foodJson.get("general");
            addComplexItemToMap(foodMap, generalFoodJson, numOfParticipants);
        }
    }

    private static void addComplexItemToMap(HashMap<Item, Boolean> foodMap, JSONObject foodJson, int numOfParticipants) {
        /** This function gets a JsonObject and map, and creates new Items and add them to the map
         *  Each element in the json is in the format <name, ratio>
         *  The function multiply number of participants and ratio and creates new item with that amount
         **/
        Set <String> foodKeys = foodJson.keySet();
        for (String key: foodKeys)
        {
            double ratio = (double) foodJson.get(key);
            double amount = Math.ceil(ratio * numOfParticipants);
            Item newItem = new Item(key, (int)amount);
            foodMap.put(newItem, true);
        }
    }

    private static void addSimpleItemToMap(HashMap<Item,Boolean> ItemsMap, JSONArray jsonArr)
    /**
     * This function gets an Items map, and a json Array
     * The function iterates through keys in the json and add new item with amount=1 to the map
     */
    {
        for (int i = 0; i < jsonArr.size(); i++)
        {
            String itemName = jsonArr.get(i).toString();
            Item newItem = new Item(itemName, 1);
            ItemsMap.put(newItem, true);
        }
    }

}

