package com.verteil;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CalculatorService {

    private Calculator calculator;

    @Autowired
    public CalculatorService(Calculator calculator) {
        this.calculator = calculator;
    }

    public int calculateSum(int a, int b) {
        System.out.println("In calculateSum");
        return calculator.add(a, b);
    }
}
