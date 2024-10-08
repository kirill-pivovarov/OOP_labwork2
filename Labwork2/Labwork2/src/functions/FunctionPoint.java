package functions;

public class FunctionPoint {
    private double x, y;

    public FunctionPoint()
    {
        x = 0;
        y = 0;
    }
    public FunctionPoint(double x, double y)
    {
        this.x = x;
        this.y = y;
    }

    public FunctionPoint(FunctionPoint point)
    {
        x = point.x;
        y = point.y;
    }

    public String toString() {
        return String.format("x: %.2f, y: %.2f;", x, y);
    }

    public double getX() {return x;}

    public double getY() {return y;}

    public void setX(double x) {this.x = x;}

    public void setY(double y) {this.y = y;}
}
