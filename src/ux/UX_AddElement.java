package ux;

import core.Vector2;
import visual.OvalElement;
import visual.RectElement;
import visual.VisualElement;
import visual.VisualPanel;

import javax.swing.event.MouseInputListener;
import java.awt.event.MouseEvent;

public final class UX_AddElement implements MouseInputListener {
    public UX_AddElement(VisualPanel target){
        this.target = target;
    }
    private final VisualPanel target;

    @Override
    public void mouseClicked(MouseEvent e) {
        VisualElement element;
        switch (target.GetMode()){
            case Rect -> element = new RectElement();
            case Oval -> element = new OvalElement();
            default   -> element = null;
        };
        if(element == null)
            return;
        element.position = new Vector2(e.getX(), e.getY());
        target.root.Add(element);
        target.repaint();
    }

    @Override
    public void mousePressed(MouseEvent e) {

    }

    @Override
    public void mouseReleased(MouseEvent e) {

    }

    @Override
    public void mouseEntered(MouseEvent e) {

    }

    @Override
    public void mouseExited(MouseEvent e) {

    }

    @Override
    public void mouseDragged(MouseEvent e) {

    }

    @Override
    public void mouseMoved(MouseEvent e) {

    }
}
