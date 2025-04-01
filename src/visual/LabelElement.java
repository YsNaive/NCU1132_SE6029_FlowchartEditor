package visual;

import core.Drawer;
import core.Rect;
import core.ShapeType;
import core.Vector2;

import java.awt.*;

public class LabelElement extends TextElement{
    public LabelElement(){
        this.bgShape = ShapeType.Rect;
        this.bgColor = new Color(.75f,.75f,.95f);
    }
    public ShapeType bgShape;
    public Color     bgColor;

    @Override
    public Rect GetBoundingBox() {
        Rect ret = super.GetBoundingBox();
        Vector2 extendSize = ret.GetSize();
        extendSize = extendSize.Mul(0.25f);
        ret.width  += extendSize.x;
        ret.height += extendSize.y;
        ret.x      -= extendSize.x / 2;
        ret.y      -= extendSize.y / 2;
        return ret;
    }

    @Override
    public void Render(Drawer drawer) {
        if(this.bgShape == ShapeType.Rect)
            drawer.FillRect(this.GetBoundingBox(), bgColor);
        if(this.bgShape == ShapeType.Oval)
            drawer.FillOval(this.GetBoundingBox(), bgColor);
        super.Render(drawer);
    }
}
