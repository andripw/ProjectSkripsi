package edu.bonn.cs.iv.bonnmotion.models;

import edu.bonn.cs.iv.bonnmotion.Position;
import java.awt.Polygon;

public class Area extends Polygon{

    /**
     * Identified area
     */
    int areaID;
    /**
     * Position min
     */
    Position a;
    /**
     * Position max
     */
    Position b;

    public Area(double x1, double x2, double y1, double y2, int ID) {
        this.a = new Position(x1, y1);
        this.b = new Position(x2, y2);
        this.areaID = ID;
    }

    /**
     * Position inside area
     * @param pos
     * @return 
     */
    public boolean isInsideArea(Position pos) {
        return a.x <= pos.x && pos.x <= b.x && a.y >= pos.y && pos.y >= b.y;
    }

    /**
     * Generate random position inside area
     * @return 
     */
    public Position generateRandomPos() {
        return new Position(Math.random() * (b.x - a.x) + a.x, Math.random() * (b.y - a.y) + a.y);
    }
}
