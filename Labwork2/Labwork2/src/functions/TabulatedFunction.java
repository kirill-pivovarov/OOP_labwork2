package functions;

public class TabulatedFunction {
    private FunctionPoint[] points;

    private int pointsCount;

    private int arrayCapacity;

    public TabulatedFunction(double leftX, double rightX, int pointsCount) {
        double step = (rightX - leftX) / (pointsCount);

        points = new FunctionPoint[pointsCount + 1];
        this.pointsCount = pointsCount;
        arrayCapacity = pointsCount;
        for (int i = 0; i < pointsCount; i++) {
            points[i] = new FunctionPoint(leftX + step * i, 0);
        }
    }

    public TabulatedFunction(double leftX, double rightX, double[] values) {
        double step = (rightX - leftX) / (values.length - 1);

        points = new FunctionPoint[values.length];
        this.pointsCount = values.length;
        this.arrayCapacity = values.length;
        for (int i = 0; i < values.length; i++) {
            points[i] = new FunctionPoint(leftX + step * i, values[i]);
        }
    }

    public double getLeftDomainBorder() {
        return points[0].getX();
    }

    public double getRightDomainBorder() {
        return points[pointsCount - 1].getX();
    }

    public String toString() {
        String result = new String();
        for (int i = 0; i < pointsCount; i++) {
            result += points[i].toString() + "\n";
        }
        return result;
    }

    private int search(double x) // Бинарный поиск интервала, в который входит данное значене x
    {
        if (Double.compare(getLeftDomainBorder(), x) <= 0 && Double.compare(x, getRightDomainBorder()) <= 0) {
            int begin = 0;
            int end = pointsCount - 1;
            int i = end / 2;
            while (!(points[i].getX() <= x && x <= points[i + 1].getX()) && i != 0) {
                if (x < points[i].getX()) {
                    end = i + (end - i) / 2;
                } else {
                    begin = begin + (end - begin) / 2;
                }

                i = begin + (i - begin) / 2;
            }
            return i;
        } else {
            return -1;
        }
    }

    public double getFunctionValue(double x) {
        if (points[0].getX() <= x && x <= points[pointsCount - 1].getX()) {
            int i = search(x);
            /* Если данное значение x принадлежит одной из точек табулирования функции, то можно сразу вернуть значение y. */
            if (Double.compare(x, points[i].getX()) == 0)
                return points[i].getY();
            else if (Double.compare(x, points[i + 1].getX()) == 0)
                return points[i + 1].getY();
            else {
                double dx = points[i + 1].getX() - points[i].getX(); // изменения значения x на интервале, которому принадлежит данное x
                double dy = points[i + 1].getY() - points[i].getY(); // изменнеия значения y

                double k = dy / dx; // коэффициент k линейной функции, заданной на данном интервале
                double b = points[i].getY() - k * points[i].getX(); // свободный член b линейной функции, заданной на данном интервале

                return k * x + b;
            }
        } else {
            return Double.NaN;
        }
    }

    public int getPointsCount() {
        return pointsCount;
    }

    public FunctionPoint getPoint(int index) {
        return points[index];
    }

    public void setPoint(int index, FunctionPoint point) {
        if (index >= 0 && index < pointsCount - 1) {
            if (points[index].getX() < point.getX() && point.getX() < points[index + 1].getX()) {
                points[index].setX(point.getX());
                points[index].setY(point.getY());
            }
        }
    }

    public double getPointX(int index) {
        return points[index].getX();
    }

    public double getPointY(int index) {
        return points[index].getY();
    }

    public void setPointX(int index, double x) {
        if (index < pointsCount - 1) {
            if (points[index].getX() < x && x < points[index + 1].getX()) {
                points[index].setX(x);
            }
        } else if (Double.compare(x, points[index].getX()) == 0) {
            points[index].setX(x);
        }
    }

    public void setPointY(int index, double y) {
        points[index].setY(y);
    }

    public void deletePoint(int index) {
        for (int i = index; i < pointsCount - 1; i++) {
            points[i] = points[i + 1];
        }
        points[pointsCount - 1] = null;
        --pointsCount;
    }

    public void addPoint(FunctionPoint point) {
        if (pointsCount < arrayCapacity) {
            int index = search(point.getX()) + 1;

            FunctionPoint cur = points[index];
            FunctionPoint next;
            points[index] = new FunctionPoint(point);

            for (int i = index; i < pointsCount; i++) {
                next = points[i + 1];
                points[i + 1] = cur;
                cur = next;
            }

            pointsCount++;
        } else {
            int i = search(point.getX()) + 1;

            FunctionPoint[] temp = new FunctionPoint[arrayCapacity << 1];
            System.arraycopy(points, 0, temp, 0, i);

            temp[i] = new FunctionPoint(point);
            System.arraycopy(points, i, temp, i + 1, pointsCount - i);

            pointsCount++;
            arrayCapacity <<= 1;

            points = temp;
        }
    }

}
