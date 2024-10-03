package edu.upc.prop.clusterxx;

import edu.upc.prop.clusterxx.presentation.PresentationController;

import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        // basic input/output

        PresentationController pc = new PresentationController();

        System.out.println("Kenken game");
        System.out.println("Made by: Raúl Gilabert, Guillem Nieto, Pau Zaragoza and David Cañadas");

        while (true) {
            System.out.println("Select an option:");
            System.out.println("1. Load a Kenken");
            System.out.println("2. Solve a Kenken");
            System.out.println("3. Exit");

            int option = 0;

            Scanner in = new Scanner(System.in);
            while (!in.hasNextLine());
            option = in.nextInt();
            in.nextLine();

            switch (option) {
                case 1:
                    try {
                        pc.readKenken();
                    } catch (Exception e) {
                        System.out.println(e.getMessage());
                    }
                    break;
                case 2:
                    try {
                        pc.solve();
                    } catch (Exception e) {
                        System.out.println(e.getMessage());
                    }
                    break;
                case 3:
                    System.out.println("Exit");
                    System.exit(0);
                    break;
                default:
                    System.out.println("Invalid option");
                    break;
            }
        }
    }
}
