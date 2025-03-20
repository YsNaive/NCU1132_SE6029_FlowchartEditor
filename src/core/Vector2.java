package core;

import java.util.Objects;

public class Vector2 {
    public float x, y;

    public Vector2() {
        this.x = 0;
        this.y = 0;
    }

    public Vector2(float x, float y) {
        this.x = x;
        this.y = y;
    }

    public float getLength() {
        return (float) Math.sqrt(x * x + y * y);
    }

    public void setLength(float length) {
        float len = getLength();
        if (len != 0) {
            float scale = length / len;
            this.x *= scale;
            this.y *= scale;
        }
    }

    public float dot(Vector2 other) {
        return x * other.x + y * other.y;
    }

    public Vector2 lerp(Vector2 other, float t) {
        return new Vector2(
                x * (1.0f - t) + other.x * t,
                y * (1.0f - t) + other.y * t
        );
    }

    public Vector2 normalize() {
        float len = getLength();
        if (len == 0) return new Vector2(0, 0);
        return new Vector2(x / len, y / len);
    }

    public String toString() {
        return "(" + x + ", " + y + ")";
    }

    public String toString(int precise) {
        return String.format("(%." + precise + "f, %." + precise + "f)", x, y);
    }

    public int hashCode() {
        return Objects.hash(x, y);
    }

    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        Vector2 other = (Vector2) obj;
        return Float.compare(x, other.x) == 0 && Float.compare(y, other.y) == 0;
    }

    public Vector2 negate() {
        return new Vector2(-x, -y);
    }

    public void add(Vector2 other) {
        this.x += other.x;
        this.y += other.y;
    }

    public void subtract(Vector2 other) {
        this.x -= other.x;
        this.y -= other.y;
    }

    public void multiply(Vector2 other) {
        this.x *= other.x;
        this.y *= other.y;
    }

    public void divide(Vector2 other) {
        this.x /= other.x;
        this.y /= other.y;
    }

    public void scale(float scalar) {
        this.x *= scalar;
        this.y *= scalar;
    }

    public void divide(float scalar) {
        this.x /= scalar;
        this.y /= scalar;
    }

    public static Vector2 add(Vector2 a, Vector2 b) {
        return new Vector2(a.x + b.x, a.y + b.y);
    }

    public static Vector2 subtract(Vector2 a, Vector2 b) {
        return new Vector2(a.x - b.x, a.y - b.y);
    }

    public static Vector2 multiply(Vector2 a, Vector2 b) {
        return new Vector2(a.x * b.x, a.y * b.y);
    }

    public static Vector2 divide(Vector2 a, Vector2 b) {
        return new Vector2(a.x / b.x, a.y / b.y);
    }
}
