package visual;

import core.Drawer;
import core.Rect;
import core.Vector2;
import ux.UX_AddElement;
import ux.UX_Select;

import javax.swing.*;
import javax.swing.event.MouseInputListener;
import java.awt.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class VisualPanel extends JPanel{
    public VisualPanel() {
        VisualElement ve = new VisualElement();
        ve.SetPanel(this);
        root      = ve;
        selecting = new ArrayList<VisualElement>();
        m_mode    = VisualPanelMode.Select;
        m_inputListenerMap = new HashMap<>();
        initInputListenerMap();
    }

    public final VisualElement root;
    public final List<VisualElement> selecting;
    private VisualPanelMode m_mode;
    private MouseInputListener currInputListener;
    private final HashMap<VisualPanelMode, MouseInputListener> m_inputListenerMap;

    public VisualPanelMode GetMode(){return m_mode;}
    public void SetMode(VisualPanelMode mode){
        if(mode == m_mode)
            return;
        if(currInputListener != null){
            removeMouseListener      (currInputListener);
            removeMouseMotionListener(currInputListener);
        }

        currInputListener = m_inputListenerMap.get(mode);
        addMouseListener      (currInputListener);
        addMouseMotionListener(currInputListener);

        m_mode = mode;
    }

    public void SelectElement(Vector2 position){
        selecting.clear();
        for(var ve : root.VisitFromTop()){
            if(!ve.pickable)
                continue;
            if(ve.TryPick(position)){
                selecting.add(ve);
                break;
            }
        }
        repaint();
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
            if(!ve.visible)
                continue;
            ve.Render(drawer);
            if(selecting.contains(ve))
                ve.RenderSelecting(drawer);
        }
    }

    private void initInputListenerMap(){
        m_inputListenerMap.clear();
        var select = new UX_Select(this);
        m_inputListenerMap.put(VisualPanelMode.Select, select);
        var addElement = new UX_AddElement(this);
        m_inputListenerMap.put(VisualPanelMode.Rect, addElement);
        m_inputListenerMap.put(VisualPanelMode.Oval, addElement);
    }
}
