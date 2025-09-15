package hu.webarticum.inno.holosky.render.viewstate;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.event.HierarchyEvent;

import javax.swing.JPanel;

import hu.webarticum.inno.holosky.state.ViewState;
import hu.webarticum.inno.holosky.state.ViewState.ViewStateListener;

public class StateCanvas extends JPanel {

    private static final long serialVersionUID = 1L;

    private final ViewState viewState;
    private final ViewStateListener viewStateListener;
    
    private boolean showingState = false;
    
    public StateCanvas(ViewState viewState) {
        setPreferredSize(new Dimension(400, 400));
        setFocusable(true);
        setDoubleBuffered(true);
        setBackground(Color.BLACK);
        this.viewState = viewState;
        this.viewStateListener = v -> repaint();
        addHierarchyListener(this::onHierarchyChanged);
    }

    private void onHierarchyChanged(HierarchyEvent e) {
        if ((e.getChangeFlags() & HierarchyEvent.SHOWING_CHANGED) == 0) {
            return;
        }
        
        boolean showing = isShowing();
        if (showing == showingState) {
            return;
        }
        
        if (showing) {
            viewState.addListener(viewStateListener);
        } else {
            viewState.removeListener(viewStateListener);
        }
        showingState = showing;
    }

    @Override
    protected void paintComponent(Graphics g) {
        new StateRenderer().render(viewState, (Graphics2D) g, getWidth(), getHeight());
    }
    
}
