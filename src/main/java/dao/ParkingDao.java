package dao;

import entity.Parking;

public interface ParkingDao {

    Parking saveParking(Parking parking);

    Parking getParking(Long id);

    Parking getParking(String name);
}
