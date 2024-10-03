package edu.upc.prop.clusterxx.domain.operations;

import edu.upc.prop.clusterxx.domain.Box;

public class Addition extends Operation {
    public Addition() {
        super();
    }
    
    @Override 
    public int calculate(Box[] boxes) {
        int result = 0;
        for (int i = 0; i < boxes.length; ++i) {
            result += boxes[i].getValue();
        }
        return result;
    }
}

// Class created by Pau Zaragoza Gallardo