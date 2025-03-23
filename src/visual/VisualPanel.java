package visual;

import core.Drawer;
import core.Rect;
import core.Vector2;
import ux.UX_AddElement;
import ux.UX_AddLink;
import ux.UX_Select;

import javax.swing.*;
import javax.swing.event.MouseInputListener;
import java.awt.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.function.Function;

public class VisualPanel extends JPanel{
    public VisualPanel() {
        VisualElement ve = new VisualElement();
        ve.SetPanel(this);
        root      = ve;
        selecting = new ArrayList<VisualElement>();
        m_mode    = VisualPanelMode.Select;
        m_inputListenerMap = new HashMap<>();
        initInputListenerMap();

        ve = new RectElement();
        ve.position = new Vector2(10,10);
        root.Add(ve);
        VisualElement ve2 = new OvalElement();
        ve2.position = new Vector2(150,10);
        root.Add(ve2);
        LinkElement ve3 = new LinkElement(ve, ve2);
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

    private boolean ignoreCheck(VisualElement ve){
        if(ve.pickable)
            return false;
        if(m_mode.IsLink() && !ve.linkable)
            return false;
        return true;
    }
    public void SelectElement(Vector2 position) { SelectElement(position, this::ignoreCheck);}
    public void SelectElement(Vector2 position, Function<VisualElement, Boolean> isIgnore){
        selecting.clear();
        for(var ve : root.VisitFromTop()){
            if(isIgnore.apply(ve))
                continue;
            if(ve.TryPick(position)){
                selecting.add(ve);
                break;
            }
        }
        repaint();
    }
    public void SelectElement(Rect bound){ SelectElement(bound, this::ignoreCheck); }
    public void SelectElement(Rect bound, Function<VisualElement, Boolean> isIgnore){
        selecting.clear();
        for(var ve : root.VisitFromTop()){
            if(!isIgnore.apply(ve))
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

        var select     = new UX_Select    (this);
        m_inputListenerMap.put(VisualPanelMode.Select, select);

        var addElement = new UX_AddElement(this);
        m_inputListenerMap.put(VisualPanelMode.Rect, addElement);
        m_inputListenerMap.put(VisualPanelMode.Oval, addElement);

        var addLink    = new UX_AddLink   (this);
        m_inputListenerMap.put(VisualPanelMode.Association   , addLink);
        m_inputListenerMap.put(VisualPanelMode.Generalization, addLink);
        m_inputListenerMap.put(VisualPanelMode.Composition   , addLink);
    }
}
