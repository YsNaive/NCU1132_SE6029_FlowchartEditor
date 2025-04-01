package visual;

import core.Drawer;
import core.Rect;
import core.Vector2;

import java.awt.*;

public class RectElement extends VisualElement{
    public float width  = 100;
    public float height = 60;
    private Color backgroundColor = new Color(.95f,.95f,.95f);
    private Color borderColor     = new Color(.65f,.65f,.65f);

    public void SetColor(Color backgroundColor, Color borderColor){
        this.backgroundColor = backgroundColor;
        this.borderColor     = borderColor;
        MarkDirty();
    }

    @Override
    public void Render(Drawer drawer){
        Rect bound = GetBoundingBox();
        drawer.FillRect(bound, backgroundColor);
        drawer.DrawRect(bound, borderColor);
    }

    @Override
    public void RenderSelecting(Drawer drawer) {
        super.RenderSelecting(drawer);
        Rect bound = GetBoundingBox();
        drawer.DrawRect(bound, Color.BLACK);
        Vector2 beg = bound.GetMin();
        Vector2 end = bound.GetMax();
        drawer.DrawLine(beg, end);
        beg.y = bound.GetYMax();
        end.y = bound.GetYMin();
        drawer.DrawLine(beg, end);
    }

    @Override
    public Vector2[] GetWorldPorts() {
        float halfW = width /2f;
        float halfH = height/2f;
        Vector2 worldPos = GetWorldPosition();
        return new Vector2[]{
                new Vector2(worldPos.x, worldPos.y),
                new Vector2(worldPos.x, worldPos.y + halfH),
                new Vector2(worldPos.x, worldPos.y + height),
                new Vector2(worldPos.x + halfW, worldPos.y + height),
                new Vector2(worldPos.x + width, worldPos.y + height),
                new Vector2(worldPos.x + width, worldPos.y + halfH),
                new Vector2(worldPos.x + width, worldPos.y),
                new Vector2(worldPos.x + halfW, worldPos.y),
        };
    }

    @Override
    public Rect GetBoundingBox(){
        Rect ret = super.GetBoundingBox();
        ret.width  = width;
        ret.height = height;
        return ret;
    }
}
