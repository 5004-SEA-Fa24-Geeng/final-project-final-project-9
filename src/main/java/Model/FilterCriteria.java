package Model;

import Controller.Operation;

public class FilterCriteria {
    private Operation operation;
    private String value;

    public FilterCriteria(Operation operation, String value) {
        this.operation = operation;
        this.value = value;
    }

    public Operation getOperation() {
        return operation;
    }

    public String getValue() {
        return value;
    }
} 