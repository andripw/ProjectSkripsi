package report;

import core.*;

public class ReportProbHybrid extends Report implements MovementListener {

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
    public static final double EPSILON = 0.00001;

    public static final String TIME_FORMAT = "%.1f";
    
    private String nodeArray;

    public ReportProbHybrid() {
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
        catatastropheArea0=400,550,500,550,400,450,500,450,400,500,500,500,0,10,10
        catatastropheArea1=10,550,100,550,10,450,100,450,10,500,100,500,1,10,10
        catatastropheArea2=400,150,500,150,400,60,500,60,400,105,500,105,2,10,10
        catatastropheArea3=10,150,100,150,10,60,100,60,10,105,100,105,3,10,10
         */
        if ((x >= 400.0 && x <= 501.0) && (y >= 450.0 && y <= 551.0)) {
            return "Kelas";
        } else if ((x >= 10.0 && x <= 101.0) && (y >= 450 && y <= 551.0)) {
            return "Kantin";
        } else if ((x >= 400.0 && x <= 501.0) && (y >= 60.0 && y <= 151.0)) {
            return "Laboratorium";
        } else if ((x >= 10.0 && x <= 101.0) && (y >= 60.0 && y <= 151.0)) {
            return "Perpustakaan";
        } else if ((x >= 200.0 && x <= 301.0) && (y >= 200.0 && y <= 301.0)) {
            return "Aula";
        } else {
            return "Tidak berada di area manapun";
        }
    }

    @Override
    public void newDestination(DTNHost host, Coord destination, double speed) {
        int index = host.getAddress();
        double time = getSimTime();

        write(String.format(TIME_FORMAT, time) + " " + " " + index + " "
                + " berada di " + simpanArea(destination)
        );
    }

    @Override
    public void initialLocation(DTNHost host, Coord location) {
        int index = host.getAddress();
        write(nodeArray + "(" + index + ") set X_ " + fix(location.getX()));
        write(nodeArray + "(" + index + ") set Y_ " + fix(location.getY()));
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
