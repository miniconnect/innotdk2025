package hu.webarticum.inno.holosky.render.viewstate;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.geom.Arc2D;
import java.awt.geom.Ellipse2D;
import java.awt.geom.Line2D;
import java.awt.geom.Path2D;
import java.util.Locale;

import hu.webarticum.inno.holosky.state.ViewState;

public class StateRenderer {
    
    private static final Color CLEAR_COLOR = new Color(20, 30, 70);
    
    private static final Color BACKGROUND_DOME_COLOR = new Color(150, 150, 200);
    
    private static final Color GROUND_COLOR = new Color(100, 170, 70);
    
    private static final Color GROUND_BORDER_COLOR = new Color(80, 90, 10);
    
    private static final Color GROUND_LINE_COLOR = new Color(70, 80, 30);
    
    private static final Color GROUND_BORDER_MARKER_COLOR = new Color(50, 40, 10);
    
    private static final Color PILLAR_COLOR = new Color(50, 10, 30);
    
    private static final Color PILLAR_BOTTOM_COLOR = new Color(40, 50, 20);
    
    private static final Color PILLAR_TOP_COLOR = new Color(140, 30, 150);
    
    private static final Color TRIANGLE_COLOR = new Color(0, 0, 0, 100);
    
    private static final double VIEW_ANGLE_DEG = 20d;
    
    private static final double BOTTOM_BORDER_MARKER_RADIUS = 3d;
    
    private static final double PILLAR_BOTTOM_MARKER_RADIUS = 5d;
    
    private static final double PILLAR_TOP_MARKER_MIN_RADIUS = 4d;
    
    private static final double PILLAR_TOP_MARKER_MAX_RADIUS = 20d;
    
    private static final Color LABEL_COLOR = new Color(250, 240, 220);
    
    private static final double LABEL_LEFT = 15d;
    
    private static final double LABEL_TOP = 15d;
    
    private static final double LABEL_LINE_HEIGHT = 32d;
    
    private static final Locale LABEL_LOCALE = Locale.US;
    
    private static final Font LABEL_FONT = new Font("Monospaced", Font.BOLD, 17);
    

    public void render(ViewState viewState, Graphics2D g2d, int width, int height) {
        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        g2d.setBackground(CLEAR_COLOR);
        g2d.clearRect(0, 0, width, height);
        
        double centerX = width * 0.5;
        double centerY = height * 0.75;
        double radius = width * 0.4;
        double viewAngleRad = VIEW_ANGLE_DEG / 180 * Math.PI;
        
        renderBackgroundDome(viewState, g2d, centerX, centerY, radius);
        renderGround(viewState, g2d, centerX, centerY, radius, viewAngleRad);
        renderInner(viewState, g2d, centerX, centerY, radius, viewAngleRad);
        renderLabels(viewState, g2d);
    }

    public void renderBackgroundDome(ViewState viewState, Graphics2D g2d, double centerX, double centerY, double radius) {
        g2d.setColor(BACKGROUND_DOME_COLOR);
        g2d.fill(new Arc2D.Double(centerX - radius, centerY - radius, radius * 2, radius * 2, 0, 180, Arc2D.CHORD));
    }
    
    public void renderGround(ViewState viewState, Graphics2D g2d, double centerX, double centerY, double radiusX, double viewAngleRad) {
        double radiusY = Math.sin(viewAngleRad) * radiusX;
        
        double width = radiusX * 2;
        double height = radiusY * 2;
        
        g2d.setColor(GROUND_COLOR);
        g2d.fill(new Ellipse2D.Double(centerX - radiusX, centerY - radiusY, width, height));
        g2d.setColor(GROUND_BORDER_COLOR);
        g2d.setStroke(new BasicStroke(3));
        g2d.draw(new Ellipse2D.Double(centerX - radiusX, centerY - radiusY, width, height));

        double azimuthRad = viewState.getAzimuthDeg() * Math.PI / 180;
        double azimuthCenterX = (int) ((Math.sin(azimuthRad) * radiusX) + centerX);
        double azimuthCenterY = (int) (-((Math.cos(azimuthRad) * radiusX) * (height / width)) + centerY);
        g2d.setColor(GROUND_LINE_COLOR);
        g2d.setStroke(new BasicStroke(2));
        g2d.drawLine((int) centerX, (int) centerY, (int) azimuthCenterX, (int) azimuthCenterY);
        
        double markerRadius = BOTTOM_BORDER_MARKER_RADIUS;
        double markerDiameter = markerRadius * 2;
        g2d.setColor(GROUND_BORDER_MARKER_COLOR);
        g2d.fill(new Ellipse2D.Double(azimuthCenterX - markerRadius, azimuthCenterY - markerRadius, markerDiameter, markerDiameter));
    }

    private void renderInner(ViewState viewState, Graphics2D g2d, double centerX, double centerY, double radiusX, double viewAngleRad) {
        double radiusY = Math.sin(viewAngleRad) * radiusX;
        double width = radiusX * 2;
        double height = radiusY * 2;
        double elevationRad = viewState.getElevationDeg() / 180 * Math.PI;
        double bottomFactor = Math.cos(elevationRad) * radiusX;
        double azimuthRad = viewState.getAzimuthDeg() * Math.PI / 180;
        double pillarX = (int) ((Math.sin(azimuthRad) * bottomFactor) + centerX);
        double pillarBottomY = (int) (-((Math.cos(azimuthRad) * bottomFactor) * (height / width)) + centerY);
        double pillarRawHeight = Math.sin(elevationRad) * radiusX;
        double pillarHeight = Math.cos(viewAngleRad) * pillarRawHeight;
        double pillarTopY = pillarBottomY - pillarHeight;
        boolean isAway = pillarBottomY < centerY;
        
        if (isAway) {
            renderPillar(viewState, g2d, pillarX, pillarTopY, pillarBottomY);
            renderTriangle(g2d, centerX, centerY, pillarX, pillarTopY, pillarBottomY);
        } else {
            renderTriangle(g2d, centerX, centerY, pillarX, pillarTopY, pillarBottomY);
            renderPillar(viewState, g2d, pillarX, pillarTopY, pillarBottomY);
        }
    }

    private void renderTriangle(Graphics2D g2d, double centerX, double centerY, double pillarX, double pillarTopY, double pillarBottomY) {
        g2d.setColor(TRIANGLE_COLOR);
        Path2D.Double polygon = new Path2D.Double();
        polygon.moveTo(centerX, centerY);
        polygon.lineTo(pillarX, pillarTopY);
        polygon.lineTo(pillarX, pillarBottomY);
        polygon.closePath();
        g2d.fill(polygon);
    }
    
    private void renderPillar(ViewState viewState, Graphics2D g2d, double pillarX, double pillarTopY, double pillarBottomY) {
        g2d.setColor(PILLAR_COLOR);
        g2d.setStroke(new BasicStroke(2));
        g2d.draw(new Line2D.Double(pillarX, pillarTopY, pillarX, pillarBottomY));

        double bottomMarkerRadius = PILLAR_BOTTOM_MARKER_RADIUS;
        double bottomMarkerDiameter = bottomMarkerRadius * 2;
        g2d.setColor(PILLAR_BOTTOM_COLOR);
        g2d.fill(new Ellipse2D.Double(pillarX - bottomMarkerRadius, pillarBottomY - bottomMarkerRadius, bottomMarkerDiameter, bottomMarkerDiameter));

        double topMarkerRadius = PILLAR_TOP_MARKER_MIN_RADIUS + ((PILLAR_TOP_MARKER_MAX_RADIUS - PILLAR_TOP_MARKER_MIN_RADIUS) / viewState.getZoom());
        double topMarkerDiameter = topMarkerRadius * 2;
        g2d.setColor(PILLAR_TOP_COLOR);
        g2d.fill(new Ellipse2D.Double(pillarX - topMarkerRadius, pillarTopY - topMarkerRadius, topMarkerDiameter, topMarkerDiameter));
    }

    private void renderLabels(ViewState viewState, Graphics2D g2d) {
        g2d.setColor(LABEL_COLOR);
        g2d.setFont(LABEL_FONT);
        double firstLineY = (int) LABEL_FONT.getLineMetrics("M", g2d.getFontRenderContext()).getHeight() + LABEL_TOP;
        g2d.drawString(String.format(LABEL_LOCALE, "Azimuth: %6.2f\u00B0", viewState.getAzimuthDeg()), (int) LABEL_LEFT, (int) firstLineY);
        g2d.drawString(String.format(LABEL_LOCALE, "Elevation: %5.2f\u00B0", viewState.getElevationDeg()), (int) LABEL_LEFT, (int) (firstLineY + LABEL_LINE_HEIGHT));
        g2d.drawString(String.format(LABEL_LOCALE, "Zoom: %.2f", viewState.getZoom()), (int) LABEL_LEFT, (int) (firstLineY + (LABEL_LINE_HEIGHT * 2)));
    }

}
