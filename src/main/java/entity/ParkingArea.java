package entity;

import lib.ParkingAreaSide;

import java.util.ArrayList;
import java.util.List;

public class ParkingArea {

    private Long id;

    private List<ParkingPlace> places;

    private ParkingAreaSide side;

    private Parking parking;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public List<ParkingPlace> getPlaces() {
        if (places == null) {
            places = new ArrayList<>();
        }
        return places;
    }

    public void setPlaces(List<ParkingPlace> places) {
        this.places = places;
    }

    public ParkingAreaSide getSide() {
        return side;
    }

    public void setSide(ParkingAreaSide side) {
        this.side = side;
    }

    public Parking getParking() {
        return parking;
    }

    public void setParking(Parking parking) {
        this.parking = parking;
    }
}
