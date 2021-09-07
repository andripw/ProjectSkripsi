package edu.bonn.cs.iv.bonnmotion.models;

import java.io.FileNotFoundException;
import java.io.IOException;

import edu.bonn.cs.iv.bonnmotion.MobileNode;
import edu.bonn.cs.iv.bonnmotion.ModuleInfo;
import edu.bonn.cs.iv.bonnmotion.Position;
import edu.bonn.cs.iv.bonnmotion.Scenario;
import edu.bonn.cs.iv.bonnmotion.Waypoint;
import java.util.ArrayList;
import java.util.List;

public class ProbabilityHybrid extends Scenario {

    private static ModuleInfo info;
    /**
     * Create list area from class Area without create the object Area
     */
    private static List<Area> area = new ArrayList();

    static {
        info = new ModuleInfo("ProbabilityHybrid");
        info.description = "Application to construct Probabilistic Random Walk mobility scenarios";

        info.major = 1;
        info.minor = 0;
        info.revision = ModuleInfo.getSVNRevisionStringValue("$LastChangedRevision: 650 $");

        info.contacts.add(ModuleInfo.BM_MAILINGLIST);
        info.authors.add("Chris Walsh");

        info.affiliation = ModuleInfo.TOILERS;
    }

    public static ModuleInfo getInfo() {
        return info;
    }

    private double interval = 1;

    public ProbabilityHybrid(int nodes, double x, double y, double duration, double ignore,
            long randomSeed, double interval) {
        super(nodes, x, y, duration, ignore, randomSeed);
        this.interval = interval;
        generate();
    }

    public ProbabilityHybrid(String[] args) {
        go(args);
    }

    public void go(String[] args) {
        super.go(args);
        initArea();
        generate();
    }

    public ProbabilityHybrid(String args[], Scenario _pre) {
        // we've got a predecessor, so a transition is needed
        predecessorScenario = _pre;
        go(args);
    }

    /**
     * Generate movement from the we make movement
     */
    public void generate() {
        preGeneration();
        for (int i = 0; i < parameterData.nodes.length; i++) {
            parameterData.nodes[i] = new MobileNode();
            double t = 0.0;
            Position src = initialPosition(i);
            if (!parameterData.nodes[i].add(t, src)) // add source waypoint
            {
                throw new RuntimeException(getInfo().name + ".go: error while adding waypoint (1)");
            }

            while (t < parameterData.duration) {
                Position dst;
                dst = determineMovement(src, i);

                //report
                System.out.print(" " + t + " " + i);
                if ((400.0 <= src.x && src.x <= 500.0) && (500.0 >= src.y && src.y >= 400.0)) {
                    System.out.println(" 0");
                } else if ((10.0 <= src.x && src.x <= 100.0) && (500.0 >= src.y && src.y >= 400.0)) {
                    System.out.println(" 1");
                } else if ((400.0 <= src.x && src.x <= 500.0) && (100.0 >= src.y && src.y >= 10.0)) {
                    System.out.println(" 2");
                } else if ((10.0 <= src.x && src.x <= 100.0) && (100.0 >= src.y && src.y >= 10.0)) {
                    System.out.println(" 3");
                } else if ((200.0 <= src.x && src.x <= 300.0) && (300.0 >= src.y && src.y >= 200.0)) {
                    System.out.println(" 4");
                } else {
                    System.out.println("Tidak");
                }

                t += interval;

                if (!parameterData.nodes[i].add(t, dst)) {
                    throw new RuntimeException(getInfo().name + ".go: error while adding waypoint (1)");
                }

                src = dst;
            }
        }
        postGeneration();
    }

    /**
     * Generate movement by area by generating a random value from each node 
     * which is then compared with the probability in the area
     * @param currentPos
     * @param i
     * @return 
     */
    private Position determineMovement(Position currentPos, int i) {
        Position pos = null;
        double rand = Math.random();
        switch (cekPosition(currentPos, i).areaID) {//jika node berada di area
            case 0:
                if (rand <= 0.5) {//50%
                    pos = area.get(0).generateRandomPos();
                } else if (rand <= 0.7) {//20%
                    pos = area.get(1).generateRandomPos();
                } else if (rand <= 0.8) {//10%
                    pos = area.get(2).generateRandomPos();
                } else if (rand <= 0.9) {//10%
                    pos = area.get(4).generateRandomPos();
                } else {//10%
                    pos = area.get(3).generateRandomPos();
                }
                break;
            case 1:
                if (rand <= 0.6) {//60%
                    pos = area.get(1).generateRandomPos();
                } else if (rand <= 0.7) {//10%
                    pos = area.get(2).generateRandomPos();
                } else if (rand <= 0.8) {//10%
                    pos = area.get(3).generateRandomPos();
                } else if (rand <= 0.9) {//10$
                    pos = area.get(4).generateRandomPos();
                } else {//10%
                    pos = area.get(0).generateRandomPos();
                }
                break;
            case 2:
                if (rand <= 0.6) {//60%
                    pos = area.get(2).generateRandomPos();
                } else if (rand <= 0.7) {//10%
                    pos = area.get(4).generateRandomPos();
                } else if (rand <= 0.8) {//10%
                    pos = area.get(3).generateRandomPos();
                } else if (rand <= 0.9) {//10%
                    pos = area.get(1).generateRandomPos();
                } else {//10%
                    pos = area.get(0).generateRandomPos();
                }
                break;
            case 3:
                if (rand <= 0.5) {//50%
                    pos = area.get(3).generateRandomPos();
                } else if (rand <= 0.7) {//20%
                    pos = area.get(0).generateRandomPos();
                } else if (rand <= 0.8) {//10%
                    pos = area.get(4).generateRandomPos();
                } else if (rand <= 0.9) {//10%
                    pos = area.get(2).generateRandomPos();
                } else {//10%
                    pos = area.get(1).generateRandomPos();
                }
                break;
            case 4:
                if (rand <= 0.6) {//60%
                    pos = area.get(4).generateRandomPos();
                } else if (rand <= 0.7) {//10%
                    pos = area.get(0).generateRandomPos();
                } else if (rand <= 0.8) {//10%
                    pos = area.get(1).generateRandomPos();
                } else if (rand <= 0.9) {//10%
                    pos = area.get(2).generateRandomPos();
                } else {//10%
                    pos = area.get(3).generateRandomPos();
                }
                break;
            default:
                System.exit(0);
        }
        return pos;
    }

    /**
     * Check position inside areas
     * @param p
     * @param i
     * @return 
     */
    private Area cekPosition(Position p, int i) {
        for (Area a : area) {
            if (a.isInsideArea(p)) {
                return a;
            }
        }
        return null;
    }

    /**
     * Init node position in each area  
     * @param a
     * @return 
     */
    private Position initialPosition(int a) {
        if (a >= 0 && a <= 9) {
            return area.get(0).generateRandomPos();
        } else if (a >= 10 && a <= 19) {
            return area.get(1).generateRandomPos();
        } else if (a >= 20 && a <= 29) {
            return area.get(2).generateRandomPos();
        } else if (a >= 30 && a <= 39) {
            return area.get(3).generateRandomPos();
        }
        return null;
    }

    /**
     * Make the coordinates of all the required areas 
     */
    private void initArea() {
        area.add(new Area(400, 500, 500, 400, 0));
        area.add(new Area(10, 100, 500, 400, 1));
        area.add(new Area(400, 500, 100, 10, 2));
        area.add(new Area(10, 100, 100, 10, 3));
        area.add(new Area(200, 300, 300, 200, 4));
    }

    protected boolean parseArg(String key, String value) {
        if (key.equals("interval")) {
            interval = Double.parseDouble(value);
            return true;
        } else {
            return super.parseArg(key, value);
        }
    }

    public void write(String _name) throws FileNotFoundException, IOException {
        String[] p = new String[1];
        p[0] = "interval=" + interval;
        super.writeParametersAndMovement(_name, p);
    }

    protected boolean parseArg(char key, String val) {
        switch (key) {
            case 't': // interval
                interval = Double.parseDouble(val);
                return true;
            default:
                return super.parseArg(key, val);
        }
    }

    public static void printHelp() {
        System.out.println(getInfo().toDetailString());
        Scenario.printHelp();
        System.out.println(getInfo().name + ":");
        System.out.println("\t-t <time interval to advance by>");
    }

    protected void postGeneration() {
        for (int i = 0; i < parameterData.nodes.length; i++) {
            Waypoint l = parameterData.nodes[i].getLastWaypoint();
            if (l.time > parameterData.duration) {
                Position p = parameterData.nodes[i].positionAt(parameterData.duration);
                parameterData.nodes[i].removeLastElement();
                parameterData.nodes[i].add(parameterData.duration, p);
            }
        }
        super.postGeneration();
    }
}
