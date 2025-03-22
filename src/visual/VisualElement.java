package visual;

import core.Drawer;
import core.Rect;
import core.Vector2;

import java.util.*;
import java.util.List;

public class VisualElement {
    public Vector2 position;
    public float   rotation; // in degree
    public Vector2 scale;
    public boolean pickable;
    public List<Port> ports;
    private VisualElement       m_parent;
    private List<VisualElement> m_children;
    private int m_layer;
    private VisualPanel m_panel;

    public VisualElement() {
        this(
                new Vector2(0, 0)
        );
    }
    public VisualElement(Vector2 position){
        this(
                position,
                0,
                new Vector2(1, 1),
                0
        );
    }
    public VisualElement(Vector2 position, float rotation, Vector2 scale, int layer) {
        this.position = position;
        this.rotation = rotation;
        this.scale    = scale;
        this.pickable = true;
        this.ports    = new ArrayList<Port>();
        this.m_parent = null;
        this.m_children = new ArrayList<>();
        this.m_layer  = layer;
    }

    public final void SetParent(VisualElement newParent) {
        Vector2 worldPos = GetWorldPosition();
        if (this.m_parent != null) {
            this.m_parent.m_children.remove(this);
        }
        this.m_parent = newParent;
        if (newParent != null) {
            newParent.m_children.add(this);
            newParent.sortChildren();
        }
        this.position = World2LocalPosition(worldPos);
    }

    public void Add(VisualElement ve){
        ve.SetParent(this);
    }

    public final void SetLayer(int newLayer) {
        this.m_layer = newLayer;
        if (this.m_parent != null) {
            this.m_parent.sortChildren();
        }
    }

    public final int GetLayer() {
        return this.m_layer;
    }

    public final VisualPanel GetPanel(){
        return ((m_parent != null) ? m_parent.GetPanel() : m_panel);
    }

    public final void SetPanel(VisualPanel panel){
        if(panel.root != null)
            throw new RuntimeException("This panel already has root element");
        m_panel = panel;
    }

    public final VisualElement GetParent() {
        return this.m_parent;
    }

    public final Iterator<VisualElement> GetChildren() {
        return m_children.iterator();
    }

    public final Iterator<VisualElement> GetChildrenReversed() {
        List<VisualElement> reversed = new ArrayList<>(m_children);
        Collections.reverse(reversed);
        return reversed.iterator();
    }

    public final VisualElement GetChildren(int index) {
        return m_children.get(index);
    }

    private  void sortChildren() {
        m_children.sort(Comparator.comparingInt(VisualElement::GetLayer));
    }

    public final Vector2 GetWorldPosition() {
        if (m_parent == null) {
            return position;
        }
        return m_parent.GetWorldPosition().Add(position);
    }

    public final Port GetPort(Vector2 localPos){
        float min = Float.MAX_VALUE;
        Port ret = null;
        for(var port : ports){
            float len = localPos.Sub(port.position).GetLength();
            if(len < min){
                min = len;
                ret = port;
            }
        }
        return ret;
    }

    public final Vector2 Local2WorldPosition(Vector2 localPos) {
        return GetWorldPosition().Add(localPos);
    }

    public final Vector2 World2LocalPosition(Vector2 worldPos) {
        return worldPos.Sub(m_parent != null ? m_parent.GetWorldPosition() : new Vector2(0, 0));
    }

    public final void Translate(Vector2 delta) {
        this.position = this.position.Add(delta);
    }

    public final void Rotate(float angle) {
        this.rotation += angle;
    }

    public final void Scale(Vector2 factor) {
        this.scale = this.scale.Mul(factor);
    }

    public final Iterable<VisualElement> VisitFromTop() {
        return () -> new Iterator<>() {
            private final Stack<Iterator<VisualElement>> stack = new Stack<>();
            private VisualElement current = VisualElement.this;

            {
                stack.push(Collections.singletonList(current).iterator());
            }

            @Override
            public boolean hasNext() {
                while (!stack.isEmpty()) {
                    if (stack.peek().hasNext()) {
                        return true;
                    } else {
                        stack.pop();
                    }
                }
                return false;
            }

            @Override
            public VisualElement next() {
                if (!hasNext()) {
                    throw new NoSuchElementException();
                }
                current = stack.peek().next();
                stack.push(current.m_children.iterator());
                return current;
            }
        };
    }

    public final Iterable<VisualElement> VisitFromBottom() {
        return () -> new Iterator<>() {
            private final Stack<Iterator<VisualElement>> stack = new Stack<>();
            private final Deque<VisualElement> postOrderList = new ArrayDeque<>();
            private final Set<VisualElement> visited = new HashSet<>();

            {
                stack.push(Collections.singletonList(VisualElement.this).iterator());
                while (!stack.isEmpty()) {
                    if (stack.peek().hasNext()) {
                        VisualElement node = stack.peek().next();
                        if (!visited.contains(node)) {
                            visited.add(node);
                            stack.push(node.m_children.iterator());
                            postOrderList.addFirst(node);
                        }
                    } else {
                        stack.pop();
                    }
                }
            }

            @Override
            public boolean hasNext() {
                return !postOrderList.isEmpty();
            }

            @Override
            public VisualElement next() {
                if (!hasNext()) {
                    throw new NoSuchElementException();
                }
                return postOrderList.poll();
            }
        };
    }

    public void Render(Drawer drawer){

    }

    public void RenderSelecting(Drawer drawer){

    }

    public Rect GetBoundingBox(){
        Vector2 worldPos = GetWorldPosition();
        return new Rect(worldPos.x, worldPos.y,0,0);
    }

    public boolean TryPick(Vector2 localPos) {
        Rect bound = GetBoundingBox();
        return localPos.x >= 0 && localPos.x <= bound.width &&
               localPos.y >= 0 && localPos.y <= bound.height;
    }
}
