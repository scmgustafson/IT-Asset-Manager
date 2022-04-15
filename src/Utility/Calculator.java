package Utility;

import javafx.collections.ObservableList;

public class Calculator {

    public Calculator() {

    }

    public boolean checkIfGreater(int num, int num2) {
        if (num > num2) {
            return true;
        }
        else {
            return false;
        }
    }

    public boolean checkIfLesser(int num, int num2) {
        if (num > num2) {
            return false;
        }
        else {
            return true;
        }
    }
}
