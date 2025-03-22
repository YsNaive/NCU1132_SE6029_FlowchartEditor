package core;

public class Matrix22 {
    public float m00, m01;
    public float m10, m11;

    public Matrix22() {
        this(0,0,0,0);
    }
    public Matrix22(float m00, float m01, float m10, float m11) {
        this.m00 = m00;
        this.m01 = m01;
        this.m10 = m10;
        this.m11 = m11;
    }

    public static Matrix22 CreateRotateMatrix(float degree) {
        float radius = (float) Math.toRadians(degree);
        float cos = (float) Math.cos(radius);
        float sin = (float) Math.sin(radius);
        return new Matrix22(cos, -sin, sin, cos);
    }

    public Vector2 Mul(Vector2 vector) {
        return new Vector2(
                vector.x * this.m00 + vector.y * this.m01,
                vector.x * this.m10 + vector.y * this.m11
        );
    }
}