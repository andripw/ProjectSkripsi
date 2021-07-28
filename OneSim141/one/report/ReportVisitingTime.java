package report;

import core.*;

public class ReportVisitingTime extends Report implements MovementListener {

    /**
     * node array's name -setting id ({@value})
     */
    public static final String NODE_ARR_S = "nodeArray";
    /**
     * default value for the array name ({@value})
     */
    public static final String DEF_NODE_ARRAY = "Node ";
    /**
     * formatting string for coordinate values ({@value})
     */
    public static final String COORD_FORMAT = "%.2f";
    /**
     * a value "close enough" to zero ({@value}). Used for fixing zero values
     */
    public static final double EPSILON = 0.1;

    public static final String TIME_FORMAT = "%.1f";

    private String nodeArray;

    private int cekInterval = 15;

    private double lastCek;

    public ReportVisitingTime() {
        Settings settings = getSettings();

        if (settings.contains(NODE_ARR_S)) {
            nodeArray = settings.getSetting(NODE_ARR_S);
        } else {
            nodeArray = DEF_NODE_ARRAY;
        }
        init();
    }

    public String simpanArea(Coord destination) {
        double x = destination.getX();
        double y = destination.getY();
        /*
        area.add(new Area(400, 500, 500, 400, 0));
        area.add(new Area(10, 100, 500, 400, 1));
        area.add(new Area(400, 500, 100, 10, 2));
        area.add(new Area(10, 100, 100, 10, 3));
        area.add(new Area(200, 300, 300, 200, 4));
        */
//        a.x <= pos.x && pos.x <= b.x && a.y >= pos.y && pos.y >= b.y
        if ((400.0 <= x && x <= 500.0) && (500.0 >= y && y >= 400.0)) {
            return "Kelas";
        } else if ((10.0 <= x && x <= 100.0) && (500.0 >= y && y >= 400.0)) {
            return "Kantin";
        } else if ((400.0 <= x && x <= 500.0) && (100.0 >= y && y >= 10.0)) {
            return "Laboratorium";
        } else if ((10.0 <= x && x <= 100.0) && (100.0 >= y && y >= 10.0)) {
            return "Perpustakaan";
        } else if ((200.0 <= x && x <= 300.0) && (300.0 >= y && y >= 200.0)) {
            return "Aula";
        } else {
            return "Tidak";
        }
    }

    @Override
    public void newDestination(DTNHost host, Coord destination, double speed) {
        int index = host.getAddress();
        double time = getSimTime();
        Coord loc = host.getLocation();

//        System.out.println("Node : " + host + "Time : " + time + " ," + destination.getX() + " " + destination.getY());
        write(String.format(TIME_FORMAT, time) + " " + " " + index + " " + simpanArea(loc));
    }

    @Override
    public void initialLocation(DTNHost host, Coord location) {
        int index = host.getAddress();
        write(nodeArray + "(" + index + ") set X_ " + (location.getX()));
        write(nodeArray + "(" + index + ") set Y_ " + (location.getY()));
    }

    /**
     * Fixes and formats coordinate values suitable for Ns2 module. I.e.
     * converts zero-values to {@value EPSILON} and formats values with
     * {@link #COORD_FORMAT}.
     *
     * @param val The value to fix
     * @return The fixed value
     */
    private String fix(double val) {
        val = val == 0 ? EPSILON : val;
        return String.format(COORD_FORMAT, val);
    }
}
