package org.example;

import java.io.FileWriter;
import java.io.IOException;
import java.util.List;
import java.util.Scanner;

/**
 * @brief Klasa Round reprezentuje przebieg rund symulacji.
 */
public class Round {

    /**
     * @brief Automatyczna symulacja, która przechodzi przez wszystkie rundy.
     * @param map obiekt klasy Map reprezentujący mapę symulacji
     */
    public static void simulateRound(Map map) {
        int round = 1;

        while (map.getRemainingFractions().size() > 1) {
            for (int i = 0; i < 20; i++) {
                System.out.println();
            }

            map.printUnits(); // Wyświetlanie jednostek

            System.out.println();
            System.out.println("Round " + round + ":");
            System.out.println();

            map.removeDeadUnits();
            map.moveUnits();
            checkCombat(map);

            System.out.println();
            map.printMap();   // Wyświetlanie mapy

            round++;

            try {
                Thread.sleep(3000); // 3-sekundowy przestój
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        List<String> remainingFractions = map.getRemainingFractions();
        if (!remainingFractions.isEmpty()) {
            String winningFraction = remainingFractions.get(0);
            System.out.println();
            System.out.println("Simulation ended. Winning fraction: " + winningFraction);
            writeSurvivingUnitsToCSV(map, "surviving_units.csv");
        } else {
            System.out.println();
            System.out.println("There are no units left on the map.");
        }
    }

    /**
     * @brief Symulacja krok po kroku.
     * @param map obiekt klasy Map reprezentujący mapę symulacji
     * @param scanner obiekt klasy Scanner do odczytu wejścia użytkownika
     */
    public static void simulateRoundStepByStep(Map map, Scanner scanner) {
        int round = 1;

        while (map.getRemainingFractions().size() > 1) {
            for (int i = 0; i < 10; i++) {
                System.out.println();
            }
            System.out.println("Round " + round + ":");
            System.out.println();
            map.removeDeadUnits();
            map.moveUnits();
            checkCombat(map);

            map.printMap();
            System.out.println();
            map.printUnits();

            System.out.println();
            if (round >= 2) {
                System.out.println("Press 'n' to continue to the next round...");
            }
            waitForN(scanner);
            round++;
        }

        List<String> remainingFractions = map.getRemainingFractions();
        if (!remainingFractions.isEmpty()) {
            String winningFraction = remainingFractions.get(0);
            System.out.println();
            System.out.println("Simulation ended. Winning fraction: " + winningFraction);
            writeSurvivingUnitsToCSV(map, "surviving_units.csv");
        } else {
            System.out.println();
            System.out.println("There are no units left on the map.");
        }
    }

    /**
     * @brief Pobieranie 'n' od użytkownika.
     * @param scanner obiekt klasy Scanner do odczytu wejścia użytkownika
     */
    public static void waitForN(Scanner scanner) {
        String input = scanner.nextLine();
        while (!input.equalsIgnoreCase("n")) {
            System.out.println("Press 'n' to continue to the next round...");
            input = scanner.nextLine();
        }
    }

    /**
     * @brief Zapisywanie jednostek, które przeżyły, do pliku CSV.
     * @param map obiekt klasy Map reprezentujący mapę symulacji
     * @param fileName nazwa pliku do zapisu
     */
    private static void writeSurvivingUnitsToCSV(Map map, String fileName) {
        try {
            FileWriter writer = new FileWriter(fileName);

            writer.append("Unit ID,Name,Fraction,Symbol,Strength,Health,Speed\n");

            for (int i = 0; i < Map.MAP_SIZE; i++) {
                for (int j = 0; j < Map.MAP_SIZE; j++) {
                    Unit unit = map.getUnitAtPosition(i, j);
                    if (unit != null && unit.isAlive()) {
                        writer.append(String.valueOf(unit.getId())).append(",");
                        writer.append(unit.getName()).append(",");
                        writer.append(unit.getFractionName()).append(",");
                        writer.append(String.valueOf(unit.getSymbol())).append(",");
                        writer.append(String.valueOf(unit.getStrength())).append(",");
                        writer.append(String.valueOf(unit.getHealth())).append(",");
                        writer.append(String.valueOf(unit.getSpeed())).append("\n");
                    }
                }
            }

            writer.flush();
            writer.close();
            System.out.println();
            System.out.println("Surviving units have been written to: " + fileName);
            System.out.println();
            System.out.println();
            System.out.println();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * @brief Przeprowadzenie walki między jednostkami.
     * @param map obiekt klasy Map reprezentujący mapę symulacji
     */
    private static void checkCombat(Map map) {
        for (int i = 0; i < Map.MAP_SIZE; i++) {
            for (int j = 0; j < Map.MAP_SIZE; j++) {
                Unit unit = map.getUnitAtPosition(i, j);
                if (unit != null) {
                    List<Unit> adjacentUnits = map.getAdjacentUnits(unit);
                    for (Unit adjacentUnit : adjacentUnits) {
                        if (!adjacentUnit.getFractionName().equals(unit.getFractionName())) {
                            int damageToUnit = adjacentUnit.getStrength();
                            int damageToAdjacentUnit = unit.getStrength();
                            unit.decreaseHealth(damageToUnit);
                            adjacentUnit.decreaseHealth(damageToAdjacentUnit);
                        }
                    }
                }
            }
        }
    }
}
