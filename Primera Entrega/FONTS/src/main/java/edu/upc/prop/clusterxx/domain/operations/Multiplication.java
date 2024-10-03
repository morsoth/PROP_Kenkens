package edu.upc.prop.clusterxx.domain.operations;

import edu.upc.prop.clusterxx.domain.Box;

public class Multiplication extends Operation {
    public Multiplication() {
        super();
    }
    
    @Override 
    public int calculate(Box[] boxes) {
        int result = 1;
        for (int i = 0; i < boxes.length; ++i) {
            result *= boxes[i].getValue();
        }
        return result;
    }
}

// Class created by Pau Zaragoza Gallardo