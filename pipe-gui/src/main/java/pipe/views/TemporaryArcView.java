package pipe.views;

import pipe.gui.Constants;
import pipe.models.component.arc.ArcPoint;
import pipe.models.component.Connectable;

import javax.swing.*;
import java.awt.*;
import java.awt.geom.GeneralPath;
import java.awt.geom.Point2D;
import java.util.ArrayList;
import java.util.List;

import static java.lang.Math.max;

/**
 * Building tool for creating an arc, allows the user to go from source to
 * target before adding to petri net
 * @param <T> source class
 */
public class TemporaryArcView<T extends Connectable> extends JComponent {
    private T source;

    /**
     * Current end location of the mouse
     */
    private Point2D end;

    /**
     * Intermediate points build up by clicking on the canvas
     * Press shift for curved points
     */
    private List<ArcPoint> intermediatePoints = new ArrayList<ArcPoint>();

    /**
     * Maximum intermediate point for setting rectangle bounds
     */
    private double maxX;
    private double maxY;

    public TemporaryArcView(T source) {
        super();
        this.source = source;
        Point2D centre = source.getCentre();
        end = new Point2D.Double(centre.getX(), centre.getY());
        updateMax(end);
        setBounds(0, 0, (int) centre.getX(), (int) centre.getY());
    }

    public void setEnd(Point2D end) {
        this.end = end;
        updateMax(end);
        updateBounds();
    }

    /**
     * Updates bounds to maxX and maxY
     */
    private void updateBounds() {
        int x = (int) maxX;
        int y = (int) maxY;
        setBounds(0, 0, x, y);
    }

    public void addIntermediatePoint(ArcPoint point) {
        intermediatePoints.add(point);
        updateMax(point.getPoint());
        updateBounds();
    }

    /**
     * Updates max points based on point
     */
    private void updateMax(Point2D point) {
        maxX = max(maxX, point.getX());
        maxY = max(maxY, point.getY());
    }

    public T getSourceConnectable() {
        return source;
    }

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2 = (Graphics2D) g;

        g2.setPaint(Constants.ELEMENT_LINE_COLOUR);
        GeneralPath path = new GeneralPath();
        path.moveTo(source.getCentre().getX(), source.getCentre().getY());

        for (ArcPoint arcPoint : intermediatePoints) {
            path.lineTo(arcPoint.getX(), arcPoint.getY());
        }
        path.lineTo(end.getX(), end.getY());
        g2.draw(path);
    }

    public List<ArcPoint> getIntermediatePoints() {
        return intermediatePoints;
    }
}
