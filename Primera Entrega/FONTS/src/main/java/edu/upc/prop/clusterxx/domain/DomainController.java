package edu.upc.prop.clusterxx.domain;

import edu.upc.prop.clusterxx.domain.*;
import edu.upc.prop.clusterxx.domain.operations.*;
import edu.upc.prop.clusterxx.exceptions.*;

public class DomainController {

    private User user;
    private Kenken kenken;

    public DomainController() {
        user = new User();
        kenken = null;
    }

    // Mainly for testing purposes
    public boolean assignKenken(Kenken kenken) {
        this.kenken = kenken;
        return true;
    }

    public Kenken createNewKenken(int size) throws InvalidKenkenSizeException {
        return new Kenken(size);
    }
    
    private Kenken stringToKenken(String[] kenkenData) throws NumberFormatException, ArrayIndexOutOfBoundsException, InvalidOperationIdException, InvalidKenkenSizeException {
        String[] line = kenkenData[0].split(" ");

        int size = Integer.parseInt(line[0]);
        Kenken newKenken = createNewKenken(size);
        
        int numsRegions = Integer.parseInt(line[1]);
        for (int r = 1; r <= numsRegions; ++r) {
            line = kenkenData[r].split(" ");
            int i = 0;

            int op = Integer.parseInt(line[i++]);
            Operation operation;
            switch (op) {
                case 0:
                    operation = null;
                    break;
                case 1:
                    operation = new Addition();
                    break;
                case 2:
                    operation = new Substraction();
                    break;
                case 3:
                    operation = new Multiplication();
                    break;
                case 4:
                    operation = new Division();
                    break;
                case 5:
                    operation = new Modulus();
                    break;
                case 6:
                    operation = new Power();
                    break;
                default:
                    throw new InvalidOperationIdException(op);
            }

            int result = Integer.parseInt(line[i++]);

            int nums_elem = Integer.parseInt(line[i++]);
            Box[] boxes = new Box[nums_elem];
            for (int e = 0; e < nums_elem; ++e) {
                int x = Integer.parseInt(line[i++]);
                int y = Integer.parseInt(line[i++]);

                boxes[e] = newKenken.getBox(x-1, y-1);

                if (i < line.length && line[i].charAt(0) == '[') {
                    int value = Integer.parseInt(line[i++].replace("[", "").replace("]", ""));
                    boxes[e].setValue(value);
                }
            }
            
            Region region = new Region(newKenken, boxes, operation, result);
            newKenken.addRegion(region);
        }

        return newKenken;
    }

    public boolean createKenken(String[] kenkenData) throws NumberFormatException, ArrayIndexOutOfBoundsException, InvalidOperationIdException, InvalidKenkenSizeException {
        kenken = stringToKenken(kenkenData);

        if (kenken == null) return false;

        kenken.setState(Kenken.GameState.IN_EDITION);
        kenken.setType(Kenken.GameType.NORMAL);

        return true;
    }

    public int[][] getCurrentKenken() {
        if (kenken == null) return null;

        int[][] board = new int[kenken.getSize()][kenken.getSize()];

        for (int i = 0; i < kenken.getSize(); ++i) {
            for (int j = 0; j < kenken.getSize(); ++j) {
                board[i][j] = kenken.getBox(i, j).getValue();
            }
        }
 
        return board;
    }

    public boolean playGame() throws NullKenkenException {
        if (kenken == null) throw new NullKenkenException();

        kenken.setState(Kenken.GameState.IN_GAME);

        return true;
    }

    public boolean leaveGame() {
        kenken = null;

        return true;
    }
    
    public boolean solveKenken() throws NullKenkenException, InvalidGameStateException {
        if (kenken == null) throw new NullKenkenException();
        if (kenken.getState() != Kenken.GameState.IN_GAME)
            throw new InvalidGameStateException(kenken.getState().name(), Kenken.GameState.IN_GAME.name());

        return solveKenken(0, 0);
    }

    private boolean solveKenken(int row, int col) {
        if (row == kenken.getSize()) return true;

        if (col == kenken.getSize()) return solveKenken(row + 1, 0);

        Box box = kenken.getBox(row, col);

        if (!box.isEmpty()) return solveKenken(row, col + 1);

        for (int nums = 1; nums <= kenken.getSize(); ++nums) {
            box.setValue(nums);
            if (kenken.checkBox(box)) {
                if (solveKenken(row, col + 1)) return true;
            }
            box.clearValue();
        }

        return false;
    }
}

// Class created by Pau Zaragoza Gallardo