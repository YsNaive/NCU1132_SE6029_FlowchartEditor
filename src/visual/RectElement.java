package visual;

import core.Drawer;
import core.Rect;

import java.awt.*;

public class RectElement extends VisualElement{
    public float width  = 100;
    public float height = 60;

    @Override
    public void Render(Drawer drawer){
        Rect bound = GetBoundingBox();
        drawer.FillRect(bound, new Color(.95f,.95f,.95f));
        drawer.DrawRect(bound, new Color(.65f,.65f,.65f));
    }

    @Override
    public Rect GetBoundingBox(){
        Rect ret = super.GetBoundingBox();
        ret.width  = width;
        ret.height = height;
        return ret;
    }
}
