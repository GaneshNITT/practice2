package com.verteil;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;
class SimpleTestTest {

    @Test
    void add(){
        SimpleTest simpleTest = new SimpleTest();

        assertEquals(45 , simpleTest.add(10,35));
    }

    @Test
    void aVoid(){
        SimpleTest simpleTest = new SimpleTest();
        assertEquals("Hello" , simpleTest.doNothing());
    }
}