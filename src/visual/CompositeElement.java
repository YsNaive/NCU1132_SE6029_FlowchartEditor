package visual;

import core.Drawer;
import core.Rect;
import core.Vector2;

import java.awt.*;

public class CompositeElement extends VisualElement{
    public CompositeElement(){
        this.linkable = false;
        this.pickable = true;
    }

    @Override
    public void RenderSelecting(Drawer drawer) {
        drawer.DrawRect(GetBoundingBox(), Color.BLACK);
    }

    @Override
    public boolean TryPick(Vector2 worldPos) {
        for(var ve : this.VisitFromTop()){
            if(ve == this) continue;
            if(ve.pickable){
                if(ve.TryPick(worldPos))
                    return true;
            }
        }
        return false;
    }

    @Override
    public Rect GetBoundingBox() {
        Vector2 min = new Vector2(Float.MAX_VALUE, Float.MAX_VALUE);
        Vector2 max = new Vector2(Float.MIN_VALUE, Float.MIN_VALUE);
        for(var ve : GetChildren()){
            Rect veBound = ve.GetBoundingBox();
            min.x = Math.min(veBound.GetXMin(), min.x);
            min.y = Math.min(veBound.GetYMin(), min.y);
            max.x = Math.max(veBound.GetXMax(), max.x);
            max.y = Math.max(veBound.GetYMax(), max.y);
        }
        return  new Rect(min, max.Sub(min));
    }
}
