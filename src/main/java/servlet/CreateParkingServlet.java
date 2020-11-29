package servlet;

import entity.Parking;
import entity.ParkingArea;
import entity.ParkingPlace;
import lib.ParkingAreaSide;
import lib.Utils;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.*;

@WebServlet("/createParking")
public class CreateParkingServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        RequestDispatcher dispatcher = req.getRequestDispatcher("create_parking.jsp");
        dispatcher.forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        final String parkingName = req.getParameter("parkingName");
        final List<ParkingArea> areas = new ArrayList<>();
        String error = null;
        for (ParkingAreaSide side : ParkingAreaSide.values()) {
            Boolean isAreaActive = Utils.checkboxToBoolean(req.getParameter(side.name()));
            if (isAreaActive) {
                ParkingArea area = new ParkingArea();
                area.setSide(side);
                String obj = req.getParameter(side.name() + "_places");
                Integer placesQuantity = Utils.stringIsEmpty(obj) ? 0 : Integer.parseInt(obj);
                if (placesQuantity == 0) {
                    error = "Не заполнено количество парковочных мест для зоны парковки: " + side.getDisplayField();
                    break;
                }
                for (int i = 0; i < placesQuantity; i++) {
                    ParkingPlace place = new ParkingPlace();
                    place.setNumber(i);
                    place.setOccupied(false);
                    place.setParkingArea(area);
                    area.getPlaces().add(place);
                }
                areas.add(area);
            }
        }
        if (Utils.stringIsEmpty(error)) {
            Parking parking = new Parking();
            parking.setName(parkingName);
            parking.setParkingAreas(areas);
            //TODO: redorect user to somewhere
        }
        req.setAttribute("error", error);
        RequestDispatcher dispatcher = req.getRequestDispatcher("create_parking.jsp");
        dispatcher.forward(req, resp);
    }
}
