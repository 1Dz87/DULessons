package lib;

public class Utils {

    public static Boolean stringIsEmpty(String str) {
        return str == null || str.length() < 1;
    }

    public static Boolean checkboxToBoolean(String value) {
        if (stringIsEmpty(value)) {
            return false;
        }
        return value.equals("on");
    }
}
