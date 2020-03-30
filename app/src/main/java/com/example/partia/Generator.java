package com.example.partia;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

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
            for (int i = 0; i < equipmentArray.size(); i++)
            {
                String itemName = equipmentArray.get(i).toString();
                Item newItem = new Item(itemName, 1);
                equipmentMap.put(newItem, true);
            }
        }
        catch (Exception e){
            throw new Exception();
        }
    }



}

