package core;

import java.awt.*;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.TreeMap;

// the base class of shape object
public abstract class Shape {
    private static BucketSet<Shape> m_shapePool;
    static{
        m_shapePool = new BucketSet<Shape>();
    }

    // construct & destroy
    private int m_layer;
    public Shape(){
        m_layer = 0;
        m_shapePool.add(m_layer, this);

        position = new Vector2(0,0);
        parent = null;
        children = new ArrayList<Shape>();
    }
    public void Destroy(){
        m_shapePool.remove(m_layer, this);
    }

    public int  get_Layer(){return m_layer;}
    public void set_Layer(int newLayer){
        m_shapePool.remove(m_layer , this);
        m_shapePool.add   (newLayer, this);
        m_layer = newLayer;
    }

    // abstract
    public abstract void Render(Graphics2D g);

    // transform
    public Vector2 position;
    private Shape parent;
    private ArrayList<Shape> children;

    Vector2 getWorldPosition(){
        if(parent == null)
            return position;
        else
            return Vector2.add(parent.getWorldPosition(), position);
    }
}
