import javafx.geometry.Point3D;
import javafx.scene.paint.Color;

import java.math.BigDecimal;

public class SequenceHitPoint {

    SequenceHit sequenceHit;

    //the following is data used for graphing
    Color color;
    Point3D location;
    boolean isHighlighted;

    public SequenceHitPoint(SequenceHit sequenceHit){
        this.location = new Point3D(sequenceHit.getNormalizedScoreOne(), sequenceHit.getNormalizedScoreTwo(), sequenceHit.geteValue().doubleValue());
        this.isHighlighted = false;
    }

    public Point3D getLocation() {
        return location;
    }

    public void setLocation(Point3D location) { this.location = location; }

    //These gets are necisarry to make the tableview populate with the data
    public String getXCord() {
        return Double.toString(location.getX());
    }
    public String getYCord() {
        return Double.toString(location.getY());
    }
    public String getZCord() {
        return Double.toString(location.getZ());
    }

    public void setColor(Color color) {
        this.color = color;
    }

    public boolean isIsHighlighted() {
        return isHighlighted;
    }

    public void setIsHighlighted(boolean isHighlighted) {
        this.isHighlighted = isHighlighted;
    }

    public double getX_3d() {
        return location.getX();
    }

    public double getY_3d() {
        return location.getY();
    }

    public double getZ_3d() {
        return location.getZ();
    }

    public double getNormalizedScoreOne() {
        return sequenceHit.getNormalizedScoreOne();
    }

    public double getNormalizedScoreTwo() {
        return sequenceHit.getNormalizedScoreTwo();
    }

    public String getHitSequence() {
        return sequenceHit.getHitSequence();
    }

    public String getHitFrom() {
        return sequenceHit.getHitFrom();
    }

    public String getHitTo() {
        return sequenceHit.getHitTo();
    }

    public BigDecimal geteValue() {
        return sequenceHit.geteValue();
    }

    public double getScoreOne() {
        return sequenceHit.getScoreOne();
    }

    public double getScoreTwo() {
        return sequenceHit.getScoreTwo();
    }

    public String getAccession() {
        return sequenceHit.getAccession();
    }

    public String getSigOneMatch() {
        return sequenceHit.sigOneMatch;
    }

    public String getSigTwoMatch() {
        return sequenceHit.getSigTwoMatch();
    }

    public SequenceHit getSequenceHit() {
        return sequenceHit;
    }
}
