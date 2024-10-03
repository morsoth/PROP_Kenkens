package edu.upc.prop.clusterxx.domain.operations;

import edu.upc.prop.clusterxx.domain.Box;

public class Substraction extends Operation {
    public Substraction() {
        super();
    }
    
    @Override 
    public int calculate(Box[] boxes) {
        if (boxes.length != 2) return -1;

        int bigNum = Math.max(boxes[0].getValue(), boxes[1].getValue());
        int smallNum = Math.min(boxes[0].getValue(), boxes[1].getValue());
        int result = bigNum - smallNum;
        return result;
    }
}

// Class created by Pau Zaragoza Gallardo