package com.example.partia;
import java.time.LocalTime;
import java.util.LinkedList;

public class Ride {
    private Participant driver;
    private LinkedList<Participant> passengers = new LinkedList<>();
    private int availableSeats;
    private String source;
    private LocalTime departureTime;

    public Ride(Participant driver)
    {
        this.driver = driver;
    }

    public void setAvailableSeats(int availableSeats) {
        this.availableSeats = availableSeats;
    }

    public void setSource(String source)
    {
        this.source = source;
    }

    public void setDepartureTime(LocalTime departureTime)
    {
        this.departureTime = departureTime;
    }

    public int getAvailableSeats()
    {
        return availableSeats;
    }

    public Participant getDriver() {
        return driver;
    }

    public LocalTime getDepartureTime() {
        return departureTime;
    }

    public String getSource() {
        return source;
    }

    public LinkedList<Participant> getPassengers() {
        return passengers;
    }

    public void addPassenger(Participant passenger)
    {
        if (passengers.size() < availableSeats)
        {
            passengers.add(passenger);
            availableSeats--;
        }
        else
            throw new ArrayStoreException("No available seats");
    }

    public void removePassenger(Participant passenger)
    {
        boolean removedPassenger = passengers.remove(passenger);
        if (removedPassenger)
            availableSeats++;
    }
}
