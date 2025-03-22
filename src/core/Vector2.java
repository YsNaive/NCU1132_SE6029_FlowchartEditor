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

    public float GetLength() {
        return (float) Math.sqrt(x * x + y * y);
    }

    public void SetLength(float length) {
        float len = GetLength();
        if (len != 0) {
            float scale = length / len;
            this.x *= scale;
            this.y *= scale;
        }
    }

    public float Dot(Vector2 other) {
        return x * other.x + y * other.y;
    }

    public Vector2 Lerp(Vector2 other, float t) {
        return new Vector2(
                x * (1.0f - t) + other.x * t,
                y * (1.0f - t) + other.y * t
        );
    }

    public Vector2 Normalize() {
        float len = GetLength();
        if (len == 0) return new Vector2(0, 0);
        return new Vector2(x / len, y / len);
    }

    @Override
    public String toString() {
        return "(" + x + ", " + y + ")";
    }

    public String toString(int precise) {
        return String.format("(%." + precise + "f, %." + precise + "f)", x, y);
    }

    @Override
    public int hashCode() {
        return Objects.hash(x, y);
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        Vector2 other = (Vector2) obj;
        return Float.compare(x, other.x) == 0 && Float.compare(y, other.y) == 0;
    }

    public Vector2 Add(Vector2 other) {
        return new Vector2(this.x + other.x, this.y + other.y);
    }

    public Vector2 Sub(Vector2 other){
        return new Vector2(this.x - other.x, this.y - other.y);
    }

    public Vector2 Mul(Vector2 other) {
        return new Vector2(this.x * other.x, this.y * other.y);
    }

    public Vector2 Mul(float scalar) {
        return new Vector2(this.x * scalar, this.y * scalar);
    }

    public Vector2 Div(Vector2 other) {
        return new Vector2(this.x / other.x, this.y / other.y);
    }

    public Vector2 Div(float scalar) {
        return new Vector2(this.x / scalar, this.y / scalar);
    }
}
