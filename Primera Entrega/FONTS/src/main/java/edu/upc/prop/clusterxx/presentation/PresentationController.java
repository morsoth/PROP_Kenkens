package edu.upc.prop.clusterxx.presentation;

import java.util.Scanner;
import edu.upc.prop.clusterxx.domain.DomainController;
import edu.upc.prop.clusterxx.exceptions.*;

public class PresentationController {
    private DomainController DomainCntrl;

    public PresentationController() {
        DomainCntrl = new DomainController();
    }

    public PresentationController(DomainController DomainCntrl) {
        this.DomainCntrl = DomainCntrl;
    }

    public boolean readKenken() {  // imports kenken from terminal
        Scanner in = new Scanner(System.in);
	    System.out.println("Enter kenken");
        while(!in.hasNextLine());
        int N = in.nextInt(); int R = in.nextInt(); in.nextLine();
        String[] kenken = new String[R+1];
        kenken[0] = String.valueOf(N) + " " + String.valueOf(R);
        for (int i = 1; i <= R; ++i) {
            while(!in.hasNextLine());
            kenken[i] = in.nextLine();
        }
        
        boolean ret = false;

        try {
            ret = DomainCntrl.createKenken(kenken);
        
            if (ret) {
                ret = DomainCntrl.playGame();
            }
        }
        catch (NumberFormatException e) {
            System.out.println("Exception: You have introduced a value that is not an integer.");
            System.out.println("*** getMessage(): " + e.getMessage());
        }
        catch (ArrayIndexOutOfBoundsException e) {
            System.out.println("Exception: You missed a box in one of your regions.");
            System.out.println("*** getMessage(): " + e.getMessage());
        }
        catch (InvalidOperationIdException e) {
            System.out.println("Exception: You have introduced an invalid kenken operation id.");
            System.out.println("*** getMessage(): " + e.getMessage());
        }
        catch (NullKenkenException e) {
            System.out.println("Exception: There is currently no kenken to solve.");
            System.out.println("*** getMessage(): " + e.getMessage());
        }

        return ret;        
    }

    public boolean showKenken() {
        int[][] kenken = DomainCntrl.getCurrentKenken();
        if (kenken == null) {
            System.out.println("There is currently no kenken to solve.");
            return false;
        }
        else {
            System.out.println("Solution:");
            int N = kenken.length;
            for (int i = 0; i < N; ++i) {
                for (int j = 0; j < N; ++j) {
                    if (kenken[i][j] != -1) System.out.print(kenken[i][j]);
                    else System.out.print(" ");
                    System.out.print(" ");
                }
                System.out.println("");
            }
            System.out.println("**Note that this may not be the only solution.");
        }
        return true;
    }

    public boolean solve() {
        boolean solution = false;

        try {
            solution = DomainCntrl.solveKenken();

            if (!solution) System.out.println("No solution for current kenken.");
            else showKenken();
        }
        catch (NullKenkenException e) {
            System.out.println("Exception: There is currently no kenken to solve.");
            System.out.println("*** getMessage(): " + e.getMessage());
        }
        catch (InvalidGameStateException e) {
            System.out.println("Exception: You are trying to solve a kenken that can not be solved at this state.");
            System.out.println("*** getMessage(): " + e.getMessage());
        }

        return solution;
    }
}

/* Class created by Guillem Nieto */