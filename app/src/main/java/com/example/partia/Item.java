package com.example.partia;
import java.util.LinkedList;


import android.provider.Telephony;

import java.nio.channels.FileLock;

public class Item {
    private String name;
    private LinkedList<Participant> inCharge = new LinkedList<>();

    private Float price;
    private int amount=1;

    public Item(String itemName, int amount)
    {
        name = itemName;
        inCharge = null;
        price = Float.NaN;
        this.amount= amount;
    }

    public void setPrice(Float newPrice, Participant whosIncharge)
    {
        price += newPrice;
        whosIncharge.increaseInvestment(newPrice);

    }

    public Float getPrice()
    {
        return price;
    }

    public void addInCharge(Participant newInCharge, int amount) {
        if(this.amount< amount)
            throw new ArrayStoreException("No need, thank you!");
        else {
            this.inCharge.add(newInCharge);
            this.amount-=amount;
        }

    }

    public LinkedList<Participant> getInCharges()
    {
        return inCharge;
    }
}
