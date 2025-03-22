package visual;

import core.Drawer;
import core.Rect;
import core.Vector2;
import ux.UX_AddElement;

import javax.swing.*;
import javax.swing.event.MouseInputListener;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class VisualPanel extends JPanel{
    public VisualPanel() {
        VisualElement ve = new VisualElement();
        ve.SetPanel(this);
        root      = ve;
        selecting = new ArrayList<VisualElement>();
        m_mode    = VisualPanelMode.Select;

        SetMode(VisualPanelMode.Rect);
    }

    public final VisualElement root;
    public final List<VisualElement> selecting;
    private VisualPanelMode m_mode;
    private MouseInputListener ux_handler;

    public VisualPanelMode GetMode(){return m_mode;}
    public void SetMode(VisualPanelMode mode){
        if(mode == m_mode)
            return;
        removeMouseListener      (ux_handler);
        removeMouseMotionListener(ux_handler);

        if(mode == VisualPanelMode.Rect ||
           mode == VisualPanelMode.Oval){
            ux_handler = new UX_AddElement(this);
        }
        addMouseListener      (ux_handler);
        addMouseMotionListener(ux_handler);

        m_mode = mode;
    }

    public void SelectElement(Vector2 position){
        selecting.clear();
        for(var ve : root.VisitFromTop()){
            if(!ve.pickable)
                continue;
            if(ve.TryPick(ve.World2LocalPosition(position))){
                selecting.add(ve);
                break;
            }
        }
    }
    public void SelectElement(Rect bound){
        selecting.clear();
        for(var ve : root.VisitFromTop()){
            if(!ve.pickable)
                continue;
            Rect veBound = ve.GetBoundingBox();
            if(veBound.GetXMin() < bound.GetXMin()) continue;
            if(veBound.GetYMin() < bound.GetYMin()) continue;
            if(veBound.GetXMax() > bound.GetXMax()) continue;
            if(veBound.GetYMax() > bound.GetYMax()) continue;
            selecting.add(ve);
        }
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Drawer drawer = new Drawer(g);
        for(var ve : root.VisitFromBottom()){
            ve.Render(drawer);
            if(selecting.contains(ve))
                ve.RenderSelecting(drawer);
        }
    }
}
