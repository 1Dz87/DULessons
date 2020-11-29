package entity;

import java.util.List;

public class Parking {

    private Long id;

    private String name;

    private List<ParkingArea> parkingAreas;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<ParkingArea> getParkingAreas() {
        return parkingAreas;
    }

    public void setParkingAreas(List<ParkingArea> parkingAreas) {
        this.parkingAreas = parkingAreas;
    }
}
