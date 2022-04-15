package Utility;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class CalculatorTest {

    Calculator calculator;

    @BeforeEach
    void setUp() {
        calculator = new Calculator();
    }

    @Test
    @DisplayName("Test checkIfGreater with a greater number, should return True")
    void testGreaterWithGreater() {
        assertTrue(calculator.checkIfGreater(1, 0), "10 > 0 should be true");
    }

    @Test
    @DisplayName("Test checkIfGreater with a greater number, should return False")
    void testGreaterWithlesser() {
        assertFalse(calculator.checkIfGreater(0, 1), "0 > 10 should be false");
    }

    @Test
    @DisplayName("Test checkIfLesser with a lesser number, should return True")
    void testLesserWithGreater() {
        assertTrue(calculator.checkIfLesser(0, 1), "0 < 10 should be true");
    }

    @Test
    @DisplayName("Test checkIfLesser with a greater number, should return False")
    void testLesserWithlesser() {
        assertFalse(calculator.checkIfLesser(1, 0), "10 < 0 should be false");
    }
}