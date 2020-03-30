package com.example.partia;

import java.util.LinkedList;

public class RidesBoard {
    private LinkedList<Ride> rides = new LinkedList<>();

    public void addNewRide(Participant driver, int availableSeats, String source)
    {
        Ride newRide = new Ride(driver);
        newRide.setAvailableSeats(availableSeats);
        newRide.setSource(source);
    }

    public void removeRide(Ride rideToRemove)
    {
        rides.remove(rideToRemove);
    }

    public LinkedList<Ride> getRides() {
        return rides;
    }
}
