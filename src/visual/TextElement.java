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
    }

    public Font   font;
    public String text;
    private int m_descent = 0;
    @Override
    public void Render(Drawer drawer){
        Rect bound = GetBoundingBox();
        Vector2 pos = new Vector2(bound.x, bound.y + bound.height - m_descent);
        drawer.FillRect(bound, Color.lightGray);
        drawer.DrawText(pos, text, font, Color.BLACK);
    }
    @Override
    public Rect GetBoundingBox(){
        Rect ret = new Rect(position.x, position.y,0,0);
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
}
