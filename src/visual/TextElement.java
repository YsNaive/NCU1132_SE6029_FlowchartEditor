package visual;

import core.Drawer;
import core.Rect;
import core.Vector2;

import java.awt.*;

public class TextElement extends VisualElement{
    public enum BackGroundType{
        Rect,
        Oval,
    }

    public TextElement(){
        this("");
    }
    public TextElement(String text){
        font = new Font("Arial", Font.PLAIN, 20);
        this.text = text;
        this.pickable = false;
        this.linkable = false;
    }

    public Font   font;
    public String text;
    private int m_descent = 0;
    @Override
    public void Render(Drawer drawer){
        Rect bound = this.GetTextBoundingBox();
        Vector2 pos = new Vector2(bound.x, bound.y + bound.height - m_descent);
        drawer.DrawText(pos, text, font, Color.BLACK);
    }
    public final Rect GetTextBoundingBox(){
        Rect ret = new Rect(GetWorldPosition(),new Vector2(0,0));
        VisualPanel panel = GetPanel();
        FontMetrics metrics = panel.getFontMetrics(font);

        ret.width   = metrics.stringWidth(text); // 取得字串的寬度
        ret.height  = metrics.getHeight();       // 取得字高（包含 ascent、descent 和 leading）
        int ascent  = metrics.getAscent();       // 文字基線上方的高度
        m_descent   = metrics.getDescent();      // 文字基線下方的高度
        int leading = metrics.getLeading();      // 行間距
        ret.x -= ret.width /2;
        ret.y -= ret.height/2;
        return ret;
    }
    @Override
    public Rect GetBoundingBox(){
        return GetTextBoundingBox();
    }
}
