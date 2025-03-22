package ux;

import core.Rect;
import core.Vector2;
import visual.RectElement;
import visual.VisualElement;
import visual.VisualPanel;

import javax.swing.event.MouseInputListener;
import java.awt.*;
import java.awt.event.MouseEvent;

public class UX_Select implements MouseInputListener {
    public UX_Select(VisualPanel target){
        this.target = target;
        this.boundPreview = new RectElement();
        this.boundPreview.SetColor(new Color(0f,0f,1f,0.15f), new Color(0f, 0f, 0f, 0f));
        this.boundPreview.SetLayer(-99);
        this.isGroupSelecting  = false;
        this.isDragingElement  = false;
        this.pressedPos        = new Vector2(0,0);
        this.releasedPos       = new Vector2(0,0);
        this.dragElementOffset = new Vector2(0,0);
    }
    private final VisualPanel target;
    private final RectElement boundPreview;
    private boolean isGroupSelecting;
    private boolean isDragingElement;
    private Vector2 pressedPos;
    private Vector2 releasedPos;
    private Vector2 dragElementOffset;
    @Override
    public void mouseClicked(MouseEvent e) {
    }

    @Override
    public void mousePressed(MouseEvent e) {
        target.SelectElement(new Vector2(e.getX(), e.getY()));
        pressedPos = new Vector2(e.getX(),e.getY());
        if(target.selecting.isEmpty()){
            isGroupSelecting = true;
            boundPreview.height = 0;
            boundPreview.width  = 0;
            target.root.Add(boundPreview);
        }
        else {
            isDragingElement = true;
            dragElementOffset = target.selecting.get(0).World2LocalPosition(new Vector2(e.getX(),e.getY()));
        }
        target.repaint();
    }

    @Override
    public void mouseReleased(MouseEvent e) {
        releasedPos.x = e.getX();
        releasedPos.y = e.getY();
        if(isGroupSelecting){
            target.SelectElement(Rect.From2Positions(pressedPos, releasedPos));
            boundPreview.SetParent(null);
            target.repaint();
        }
        isGroupSelecting = false;
        isDragingElement = false;
    }

    @Override
    public void mouseEntered(MouseEvent e) {

    }

    @Override
    public void mouseExited(MouseEvent e) {
        isGroupSelecting = false;
        boundPreview.SetParent(null);
    }

    @Override
    public void mouseDragged(MouseEvent e) {
        if(isGroupSelecting){
            releasedPos.x = e.getX();
            releasedPos.y = e.getY();
            Rect bound = Rect.From2Positions(pressedPos, releasedPos);
            boundPreview.position = bound.GetPosition();
            boundPreview.width    = bound.width;
            boundPreview.height   = bound.height;
            target.repaint();
        }
        if(isDragingElement){
            VisualElement ve = target.selecting.get(0);
            ve.position.x = e.getX();
            ve.position.y = e.getY();
            if(ve.GetParent() != null)
                ve.position = ve.GetParent().World2LocalPosition(ve.position).Sub(dragElementOffset);
            target.repaint();
        }
    }

    @Override
    public void mouseMoved(MouseEvent e) {

    }
}
