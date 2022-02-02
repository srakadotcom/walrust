package pl.pogerteam.walrust.structure.builder.impl;

public final class Matrix2x3D {
    private final double xMin;
    private final double xMax;
    private final double yMin;
    private final double yMax;
    private final double zMin;
    private final double zMax;

    Matrix2x3D(
            double xMin, double xMax,
            double yMin, double yMax,
            double zMin, double zMax
    ) {
        this.xMin = xMin;
        this.xMax = xMax;
        this.yMin = yMin;
        this.yMax = yMax;
        this.zMin = zMin;
        this.zMax = zMax;
    }

    public double xMin() {
        return xMin;
    }

    public double xMax() {
        return xMax;
    }

    public double yMin() {
        return yMin;
    }

    public double yMax() {
        return yMax;
    }

    public double zMin() {
        return zMin;
    }

    public double zMax() {
        return zMax;
    }
}