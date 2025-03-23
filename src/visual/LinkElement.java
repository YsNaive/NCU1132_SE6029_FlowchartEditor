package visual;

import core.Drawer;
import core.EndpointType;
import core.Rect;
import core.Vector2;

import java.awt.*;

/**
 * This class represent link relationship from parent to target.
 * Scene this element not allow any child and parent change, position and coord in this obj are meaningless.
 */
public class LinkElement extends VisualElement{
    public LinkElement(VisualElement src, VisualElement dst){
        if(!src.linkable)
            throw new RuntimeException("Given src is not linkable.");
        if(src.GetWorldPorts().length == 0)
            throw new RuntimeException("Given src has no ports.");
        if(!dst.linkable)
            throw new RuntimeException("Given dst is not linkable.");
        if(dst.GetWorldPorts().length == 0)
            throw new RuntimeException("Given dst has no ports.");
        super.SetParent(src);
        this.target = dst;
        this.endpointType = EndpointType.Association;
        this.lastRenderBound = new Rect();

        pickable = false;
        linkable = false;
    }

    public final VisualElement target;
    public EndpointType endpointType;
    private Rect lastRenderBound;
    @Override
    public void Render(Drawer drawer) {
        Rect srcBound = GetParent().GetBoundingBox();
        Rect dstBound = target     .GetBoundingBox();
        Vector2 beg = srcBound.GetCenter();
        Vector2 end = dstBound.GetCenter();
        Vector2 b2e = end.Sub(beg);
        Vector2 e2b = beg.Sub(end);
        b2e.SetLength(srcBound.GetPerimeter()/9f);
        e2b.SetLength(dstBound.GetPerimeter()/6.5f);
        b2e = b2e.Add(beg);
        e2b = e2b.Add(end);
        beg = GetParent().GetClosestWorldPort(b2e);
        end = target     .GetClosestWorldPort(e2b);
        lastRenderBound = Rect.From2Positions(beg, end);
        end = drawer.DrawEndpointOnLine(beg, end, 15, endpointType, Color.BLACK);
        drawer.DrawLine(beg, end);
    }

    @Override
    public boolean TryPick(Vector2 worldPos) {
        throw new RuntimeException("Not Implement yet but also should not be called.");
    }

    @Override
    public Rect GetBoundingBox() {
        return lastRenderBound;
    }

    @Override
    public void Add(VisualElement ve) {
        throw new RuntimeException("Not allow to add child to LinkElement");
    }

    @Override
    public void SetParent(VisualElement newParent) {
        if(newParent == null)
            super.SetParent(null);
        else
            throw new RuntimeException("Not allow to change parent of LinkElement, you can only set it to null as destroy");
    }

}
