package ux;

import core.EndpointType;
import core.Vector2;
import visual.LinkElement;
import visual.VisualElement;
import visual.VisualPanel;
import visual.VisualPanelMode;

import javax.swing.event.MouseInputListener;
import java.awt.event.MouseEvent;

public class UX_AddLink implements MouseInputListener {
    public UX_AddLink(VisualPanel target){
        this.target = target;
        this.isDraging = false;
        traceElement = new VisualElement(){
            @Override
            public Vector2[] GetWorldPorts() {
                return new Vector2[]{this.GetWorldPosition()};
            }
        };
        traceElement.pickable = false;
        traceElement.linkable = true;
    }
    private final VisualPanel target;
    private final VisualElement traceElement;
    private LinkElement previewElement;
    private VisualElement src;
    private VisualElement dst;
    private boolean isDraging;

    private static boolean selectIgnoreCheck(VisualElement ve){
        if(!ve.pickable)
            return true;
        if(!ve.linkable)
            return true;
        return false;
    }

    @Override
    public void mousePressed(MouseEvent e) {
        target.SelectElement(new Vector2(e.getX(),e.getY()), UX_AddLink::selectIgnoreCheck);
        if(!target.selecting.isEmpty()){
            src = target.selecting.get(0);
            previewElement = new LinkElement(src, traceElement);
            previewElement.endpointType = EndpointType.ParseFromVisualPanelMode(target.GetMode());
            target.root.Add(traceElement);
            isDraging = true;
        }
    }

    @Override
    public void mouseReleased(MouseEvent e) {
        if(!isDraging)
            return;
        target.SelectElement(new Vector2(e.getX(),e.getY()), UX_AddLink::selectIgnoreCheck);
        if(!target.selecting.isEmpty()){
            dst = target.selecting.get(0);
            LinkElement element = new LinkElement(src, dst);
            element.endpointType = EndpointType.ParseFromVisualPanelMode(target.GetMode());
        }

        src = null;
        dst = null;
        isDraging = false;
        traceElement  .SetParent(null);
        previewElement.SetParent(null);
        previewElement = null;
        target.repaint();
    }

    @Override
    public void mouseDragged(MouseEvent e) {
        if(traceElement.GetParent() == null)
            return;
        traceElement.position.x = e.getX();
        traceElement.position.y = e.getY();
        traceElement.position = traceElement.GetParent().World2LocalPosition(traceElement.position);
        target.repaint();
    }

    @Override
    public void mouseClicked(MouseEvent e) {
    }
    @Override
    public void mouseEntered(MouseEvent e) {}
    @Override
    public void mouseExited(MouseEvent e) {

    }
    @Override
    public void mouseMoved(MouseEvent e) {

    }
}
