package visual;

import core.Drawer;
import core.Rect;
import core.Vector2;

import java.awt.*;
import java.util.*;
import java.util.List;

public class VisualElement {
    public Vector2 position;
    public float   rotation; // in degree
    public Vector2 scale;
    public boolean pickable;
    public boolean visible;
    public boolean linkable;
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
        this.visible  = true;
        this.linkable = true;
        this.m_parent = null;
        this.m_children = new ArrayList<>();
        this.m_layer  = layer;
    }

    public void SetParent(VisualElement newParent) {
        Vector2 newPos = GetWorldPosition();
        if (this.m_parent != null) {
            this.m_parent.m_children.remove(this);
        }
        this.m_parent = newParent;
        if (newParent != null) {
            newParent.m_children.add(this);
            newParent.sortChildren();
            newPos = m_parent.World2LocalPosition(newPos);
        }
        this.position = newPos;
    }

    public void Add(VisualElement ve){
        ve.SetParent(this);
    }
    public void Remove(VisualElement ve){
        ve.SetParent(null);
    }

    public <T extends VisualElement> T Q(Class<T> veType){
        for(var ve : this.VisitFromTop()){
            if(ve.getClass() == veType){
                return (T)ve;
            }
        }
        return null;
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

    public final Iterable<VisualElement> GetChildren() {
        return m_children;
    }

    public final Iterable<VisualElement> GetChildrenReversed() {
        List<VisualElement> reversed = new ArrayList<>(m_children);
        Collections.reverse(reversed);
        return reversed;
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

    public Vector2[] GetWorldPorts(){
        return new Vector2[]{};
    }

    public final Vector2 GetClosestWorldPort(Vector2 worldPos){
        float min = Float.MAX_VALUE;
        Vector2 ret = null;
        for(var port : GetWorldPorts()){
            float len = worldPos.Sub(port).GetLength();
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
        return worldPos.Sub(GetWorldPosition());
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
        for(Vector2 pos : GetWorldPorts())
            drawer.DrawPort(pos, Color.BLACK);
    }

    public Rect GetBoundingBox(){
        Vector2 worldPos = GetWorldPosition();
        return new Rect(worldPos.x, worldPos.y,0,0);
    }

    public boolean TryPick(Vector2 worldPos) {
        Vector2 localPos = World2LocalPosition(worldPos);
        Rect bound = GetBoundingBox();
        return localPos.x >= 0 && localPos.x <= bound.width &&
               localPos.y >= 0 && localPos.y <= bound.height;
    }

    public void MarkDirty(){
        VisualPanel panel = GetPanel();
        if(panel != null)
            panel.repaint();
    }

    private void printHierarchyRec(String prefix){
        System.out.println(prefix + this);
        for(var ve : m_children)
            ve.printHierarchyRec("  "+prefix);
    }
    public void PrintHierarchy(){
        printHierarchyRec("- ");
    }
}
