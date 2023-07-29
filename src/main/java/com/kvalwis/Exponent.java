package com.kvalwis;

public class Exponent implements Compute {
    @Override
    public double doCompute(double a, double b) {
        return Math.pow(b,a);
    }
}
