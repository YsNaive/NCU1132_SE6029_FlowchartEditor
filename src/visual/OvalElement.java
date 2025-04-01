package visual;

import core.Drawer;
import core.Rect;
import core.ShapeType;
import core.Vector2;

import java.awt.*;

public class OvalElement extends VisualElement{
    public OvalElement(){
        var ve = new LabelElement();
        ve.text     = "";
        ve.bgShape  = ShapeType.Oval;
        ve.position = new Vector2(this.width/2f, this.height/2f);

        this.Add(ve);
    }

    public float width  = 100;
    public float height = 60;

    @Override
    public void Render(Drawer drawer){
        Rect bound = GetBoundingBox();
        drawer.FillOval(bound, new Color(.95f,.95f,.95f));
        drawer.DrawOval(bound, new Color(.65f,.65f,.65f));
    }

    @Override
    public void RenderSelecting(Drawer drawer) {
        super.RenderSelecting(drawer);
        Rect bound = GetBoundingBox();
        drawer.DrawOval(bound, Color.BLACK);

        Vector2 center = bound.GetCenter();
        float a = width / 2;
        float b = height / 2;
        float sqrt2 = (float) Math.sqrt(2);
        Vector2 beg = new Vector2(center.x + (a / sqrt2), center.y + (b / sqrt2));
        Vector2 end = new Vector2(center.x - (a / sqrt2), center.y - (b / sqrt2));
        drawer.DrawLine(beg,end,Color.BLACK);
        beg = new Vector2(center.x + (a / sqrt2), center.y - (b / sqrt2));
        end = new Vector2(center.x - (a / sqrt2), center.y + (b / sqrt2));
        drawer.DrawLine(beg,end,Color.BLACK);
    }

    @Override
    public Vector2[] GetWorldPorts() {
        float halfW = width /2f;
        float halfH = height/2f;
        Vector2 worldPos = GetWorldPosition();
        return new Vector2[]{
                new Vector2(worldPos.x, worldPos.y + halfH),
                new Vector2(worldPos.x + halfW, worldPos.y + height),
                new Vector2(worldPos.x + width, worldPos.y + halfH),
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

    @Override
    public boolean TryPick(Vector2 worldPos) {
        Vector2 localPos = World2LocalPosition(worldPos);
        Vector2 center = new Vector2(width/2, height/2);
        Vector2 norm = localPos.Sub(center).Div(center);
        norm = norm.Mul(norm);
        return (norm.x + norm.y) <= 1;
    }
}
