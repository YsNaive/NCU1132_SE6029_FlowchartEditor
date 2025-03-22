package core;

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

    public final void DrawLine(Vector2 beg, Vector2 end){
        graphics2d.drawLine((int)beg.x, (int)beg.y, (int)end.x, (int)end.y);
    }
    public final void DrawLine(Vector2 beg, Vector2 end, Color color){
        graphics2d.setColor(color);
        graphics2d.drawLine((int)beg.x, (int)beg.y, (int)end.x, (int)end.y);
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

    public final void DrawPort(Vector2 pos, Color color){
        Rect bound = new Rect(0,0,7,7);
        bound.SetCenter(pos);
        FillRect(bound, color);
    }

    /**
    * @return new end position of input line
    */
    public final Vector2 DrawEndpointOnLine(Vector2 beg, Vector2 end, float size, EndpointType type, Color color){
        float deg = end.Sub(beg).ToDegree();
        Matrix22 rotaMatrix = Matrix22.CreateRotateMatrix(deg);
        float halfSize = size/2;
        Vector2[] points = new Vector2[]{
                new Vector2(0, 0),
                new Vector2(-size, -halfSize),
                new Vector2(-size,  halfSize),
                new Vector2(-size/0.65f,  0)// for Composition
        };
        for(int i=0; i<4; i++)
            points[i] = rotaMatrix.Mul(points[i]).Add(end);

        graphics2d.setColor(color);
        Vector2 ret = end;
        switch (type){
            case Association -> {
                DrawLine(points[0], points[1]);
                DrawLine(points[0], points[2]);
            }
            case Generalization -> {
                DrawLine(points[0], points[1]);
                DrawLine(points[0], points[2]);
                DrawLine(points[1], points[2]);
                ret = points[1].Add(points[2]).Div(2f);
            }
            case Composition -> {
                DrawLine(points[0], points[1]);
                DrawLine(points[0], points[2]);
                DrawLine(points[3], points[1]);
                DrawLine(points[3], points[2]);
                ret = points[3];
            }
        }

        return ret;
    }
}
