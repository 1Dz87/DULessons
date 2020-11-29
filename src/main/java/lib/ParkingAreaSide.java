package lib;

public enum ParkingAreaSide {

    SOUTH("ЮГ"),
    NORTH("СЕВЕР"),
    EAST("ВОСТОК"),
    WEST("ЗАПАД");

    private String displayField;

    ParkingAreaSide(String displayField) {
        this.displayField = displayField;
    }

    public String getDisplayField() {
        return displayField;
    }
}
