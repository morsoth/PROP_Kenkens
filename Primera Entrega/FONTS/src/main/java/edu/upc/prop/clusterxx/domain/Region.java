package edu.upc.prop.clusterxx.domain;

import edu.upc.prop.clusterxx.domain.operations.Operation;

public class Region {
    private Box[] boxes;
    private Kenken kenken;
    private Operation operation;
    private int resultOp;


    /* CREATORS */
    public Region(Kenken k, Box[] b, Operation op) {
        this(k,b,op,op.calculate(b));
    }

    public Region(Kenken k, Box[] b, Operation op, int r) {
        boxes = b;
        kenken = k;
        operation = op;
        resultOp = r;
    }

    /* GETTERS */
    public boolean check() {
        if (operation == null) {
            return resultOp == boxes[0].getValue();
        }
        else {
            return resultOp == operation.calculate(boxes);
        }
    }

    public int getResult() {
        return resultOp;
    }

    public Box[] getBoxList() {
        return boxes;
    }

    public Kenken getKenken() {
        return kenken;
    }

    public Operation getOperation() {
        return operation;
    }

    public int getSize() {
        return boxes.length;
    }

    public boolean isFull() {
        for(Box b : boxes) {
            if(b.isEmpty()) return false;
        }
        return true;
    }

    /* SETTERS */
}

/* Class created by Guillem Nieto */
