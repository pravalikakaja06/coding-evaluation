package com.aa.act.interview.org;

public class EmployeeCounter {
    private static int counter = 1;

    public synchronized static int getNextEmployeeId() {
        return counter++;
    }
}
