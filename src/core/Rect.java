package core;

import java.util.Objects;
import java.util.ArrayList;
import java.util.List;

public class Rect {
    public float x, y, width, height;

    public Rect() {
        this(0, 0, 0, 0);
    }

    public Rect(float x, float y, float width, float height) {
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
    }

    public Rect(Vector2 position, Vector2 size) {
        this(position.x, position.y, size.x, size.y);
    }

    public static Rect From2Positions(Vector2 p1, Vector2 p2){
        Rect ret = new Rect();
        if(p1.x < p2.x){
            ret.x = p1.x;
            ret.width = p2.x-p1.x;
        }
        else {
            ret.x = p2.x;
            ret.width = p1.x - p2.x;
        }
        if(p1.y < p2.y){
            ret.y = p1.y;
            ret.height = p2.y-p1.y;
        }
        else {
            ret.y = p2.y;
            ret.height = p1.y - p2.y;
        }
        return ret;
    }

    public String ToString() {
        return "(" + x + ", " + y + ", " + width + ", " + height + ")";
    }

    public int HashCode() {
        return Objects.hash(x, y, width, height);
    }

    public Vector2 GetPosition() {
        return new Vector2(x, y);
    }

    public void SetPosition(Vector2 position) {
        this.x = position.x;
        this.y = position.y;
    }

    public Vector2 GetSize() {
        return new Vector2(width, height);
    }

    public void SetSize(Vector2 size) {
        this.width  = size.x;
        this.height = size.y;
    }

    public Vector2 GetCenter() {
        return new Vector2(x + width / 2, y + height / 2);
    }

    public void SetCenter(Vector2 position) {
        this.x = position.x - width / 2;
        this.y = position.y - height / 2;
    }

    public float GetArea() {
        return width * height;
    }

    public float GetPerimeter() {
        return 2 * (width + height);
    }

    public float GetXMin() {
        return Math.min(x, x + width);
    }

    public void SetXMin(float value) {
        width += x - value;
        x = value;
    }

    public float GetXMax() {
        return Math.max(x, x + width);
    }

    public void SetXMax(float value) {
        width = value - x;
    }

    public float GetYMin() {
        return Math.min(y, y + height);
    }

    public void SetYMin(float value) {
        height += y - value;
        y = value;
    }

    public float GetYMax() {
        return Math.max(y, y + height);
    }

    public void SetYMax(float value) {
        height = value - y;
    }

    public Vector2 GetMin() {
        return new Vector2(GetXMin(), GetYMin());
    }

    public void SetMin(Vector2 value) {
        SetXMin(value.x);
        SetYMin(value.y);
    }

    public Vector2 GetMax() {
        return new Vector2(GetXMax(), GetYMax());
    }

    public void SetMax(Vector2 value) {
        SetXMax(value.x);
        SetYMax(value.y);
    }

    public Rect Intersection(Rect other) {
        float xMin = Math.max(GetXMin(), other.GetXMin());
        float xMax = Math.min(GetXMax(), other.GetXMax());
        float yMin = Math.max(GetYMin(), other.GetYMin());
        float yMax = Math.min(GetYMax(), other.GetYMax());
        if (xMax > xMin && yMax > yMin) {
            return new Rect(xMin, yMin, xMax - xMin, yMax - yMin);
        }
        return new Rect(0, 0, 0, 0);
    }

    public boolean Equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        Rect rect = (Rect) obj;
        return Float.compare(rect.x, x) == 0 &&
                Float.compare(rect.y, y) == 0 &&
                Float.compare(rect.width, width) == 0 &&
                Float.compare(rect.height, height) == 0;
    }
}
