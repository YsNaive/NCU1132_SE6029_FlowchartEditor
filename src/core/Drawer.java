package core;

import visual.Port;

import java.awt.*;

public class Drawer {
    public final Graphics graphics;
    public final Graphics2D graphics2d;

    public Drawer(Graphics g){
        this.graphics   = g;
        this.graphics2d = (Graphics2D) g;
    }

    public final void DrawText(Vector2 pos, String text, Font font, Color color){
        graphics2d.setColor(color);
        graphics2d.setFont(font);
        graphics2d.drawString(text, pos.x, pos.y);
    }
    public final void DrawRect(Rect rect, Color color){
        graphics2d.setColor(color);
        graphics2d.drawRect((int)rect.x, (int)rect.y, (int)rect.width, (int)rect.height);
    }
    public final void FillRect(Rect rect, Color color){
        graphics2d.setColor(color);
        graphics2d.fillRect((int)rect.x, (int)rect.y, (int)rect.width, (int)rect.height);
    }
    public final void DrawOval(Rect rect, Color color){
        graphics2d.setColor(color);
        graphics2d.drawOval((int)rect.x, (int)rect.y, (int)rect.width, (int)rect.height);
    }
    public final void FillOval(Rect rect, Color color){
        graphics2d.setColor(color);
        graphics2d.fillOval((int)rect.x, (int)rect.y, (int)rect.width, (int)rect.height);
    }
    public final void DrawPort(Port port, Color color){
        Rect bound = new Rect(0,0,5,5);
        bound.SetCenter(port.GetWorldPosition());
        FillRect(bound, color);
    }
}
