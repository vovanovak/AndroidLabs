package com.novak.volodymyr.lab1;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

public class CalculationResult {
    public double a;
    public double b;
    public char operator;
    public double result;
    public Date calculationDate;

    public CalculationResult(CalculationData data, double result, Date calculationDate) {
        this.a = data.parametersData.a;
        this.b = data.parametersData.b;
        this.operator = data.operation;
        this.result = result;
        this.calculationDate = calculationDate;
    }

    @Override
    public String toString() {
        DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
        Date date = new Date();
        return String.format("%.3f %s %.3f = %.3f, calculated on %s", a, operator, b, result, dateFormat.format(date));
    }
}
