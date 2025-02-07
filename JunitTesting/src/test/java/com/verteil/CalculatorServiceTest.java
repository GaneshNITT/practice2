package com.verteil;

import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import static org.junit.jupiter.api.Assertions.*;

class CalculatorServiceTest {

    @Test
    public void testCalculatorService() {
        Calculator calculatorMock = Mockito.mock(Calculator.class);

        Mockito.when(calculatorMock.add(5,6)).thenReturn(11);

        CalculatorService calculatorService = new CalculatorService(calculatorMock);

        int result = calculatorService.calculateSum(5,6);
        assertEquals(11 , result);

    }
}