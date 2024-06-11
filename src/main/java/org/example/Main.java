package org.example;

import java.util.*;

/**
 * @brief Główna klasa programu odpowiedzialna za inicjalizację i uruchomienie symulacji.
 */
public class Main {
    /**
     * @brief Główna metoda programu, odpowiedzialna za uruchomienie symulacji.
     * @param args argumenty wiersza poleceń
     */
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        Fraction armyOfIce = new Fraction("Army_of_ice");
        Fraction forestGuards = new Fraction("Forest_Guards");
        Fraction brotherhoodOfMagicians = new Fraction("Brotherhood_of_magicians");

        displayUnitAndFractionInfo();

        System.out.println("--------------------------------------------------------------------------------------------------------");
        System.out.println("ARMY_OF_ICE");
        addUnitsForFraction(scanner, armyOfIce, new String[]{"K", "R", "W"});
        System.out.println("--------------------------------------------------------------------------------------------------------");
        System.out.println("BROTHERHOOD_OF_MAGICIANS");
        addUnitsForFraction(scanner, brotherhoodOfMagicians, new String[]{"A", "F", "M"});
        System.out.println("--------------------------------------------------------------------------------------------------------");
        System.out.println("FOREST_GUARDS");
        addUnitsForFraction(scanner, forestGuards, new String[]{"E", "D", "G"});
        System.out.println("--------------------------------------------------------------------------------------------------------");

        for (int i = 0; i < 10; i++) {
            System.out.println();
        }

        System.out.println("Beggining of the simulation: ");
        System.out.println();
        Map map = new Map();
        placeUnitsOnMap(armyOfIce, map);
        placeUnitsOnMap(forestGuards, map);
        placeUnitsOnMap(brotherhoodOfMagicians, map);

        map.printMap();

        System.out.println();

        map.printUnits();

        System.out.println();
        System.out.println("Choose simulation mode:");
        System.out.println("1. Automatic simulation (one round after another)");
        System.out.println("2. Step-by-step simulation (single round after pressing 'n')");
        int simulationMode = 0;
        boolean validInput = false;
        while (!validInput) {
            if (scanner.hasNextInt()) {
                simulationMode = scanner.nextInt();
                if (simulationMode == 1 || simulationMode == 2) {
                    validInput = true;
                } else {
                    System.out.println("Please choose 1 or 2.");
                }
            } else {
                System.out.println("Please choose 1 or 2.");
                scanner.next();
            }
        }

        if (simulationMode == 1) {
            Round.simulateRound(map);
        } else {
            Round.simulateRoundStepByStep(map, scanner);
        }

        scanner.close();
    }

    /**
     * @brief Wypisanie informacji początkowych o jednostkach i frakcjach.
     */
    private static void displayUnitAndFractionInfo() {
        System.out.println();
        System.out.println("         UNITS AND FRACTIONS");
        System.out.println("------------------------------------");
        System.out.println("ARMY_OF_ICE:                        |");
        System.out.println("K - Ice_King (1)                    |");
        System.out.println("R - Ice_Rider (5 - 10)              |");
        System.out.println("W - Ice_Warrior (5 - 10)            |");
        System.out.println("                                    |");
        System.out.println("BROTHERHOOD_OF_MAGICIANS:           |");
        System.out.println("A - Archmage (1)                    |");
        System.out.println("F - Mage_of_fire (5 - 10)           |");
        System.out.println("M - Adept_of_magic (5 - 10)         |");
        System.out.println("                                    |");
        System.out.println("FOREST_GUARDS:                      |");
        System.out.println("E - Elf (5 - 10)                    |");
        System.out.println("D - Druid (5 - 10)                  |");
        System.out.println("G - Dwarf (5 - 10)                  |");
        System.out.println("------------------------------------");
        System.out.println();
        System.out.println("         AREAS OF FRACTIONS");
        System.out.println("------------------------------------");
        System.out.println("! - Army_of_ice                     |");
        System.out.println("~ - Brotherhood_of_magicians        |");
        System.out.println("* - Forest_guards                   |");
        System.out.println("------------------------------------");
        System.out.println();
        System.out.println("             STATISTICS");
        System.out.println("------------------------------------");
        System.out.println("-Strength                           |");
        System.out.println("-Speed                              |");
        System.out.println("-Health                             |");
        System.out.println("------------------------------------");
        System.out.println();
        System.out.println();
        System.out.println();
    }

    /**
     * @brief Dodanie jednostek do frakcji przez użytkownika.
     * @param scanner obiekt Scanner do odczytu danych wejściowych
     * @param fraction frakcja, do której mają być dodane jednostki
     * @param unitTypes typy jednostek, które mają być dodane
     */
    private static void addUnitsForFraction(Scanner scanner, Fraction fraction, String[] unitTypes) {
        for (String unitType : unitTypes) {
            if (!unitType.equals("K") && !unitType.equals("A")) {
                System.out.println("How many units of " + Fractions.getUnitName(unitType) + " (5-10) do you want to add to " + fraction.getName() + "?");
                int numUnits = 0;
                boolean validInput = false;
                while (!validInput) {
                    if (scanner.hasNextInt()) {
                        numUnits = scanner.nextInt();
                        if (numUnits >= 5 && numUnits <= 10) {
                            validInput = true;
                        } else {
                            System.out.println("Please enter a number between 5 and 10.");
                        }
                    } else {
                        System.out.println("Please enter a valid number between 5 and 10.");
                        scanner.next();
                    }
                }

                for (int i = 0; i < numUnits; i++) {
                    try {
                        fraction.addUnit(Fractions.createUnit(unitType));
                    } catch (IllegalArgumentException e) {
                        System.out.println(e.getMessage());
                        break;
                    }
                }
            } else {
                fraction.addUnit(Fractions.createUnit(unitType));
            }
        }
    }

    /**
     * @brief Rozmieszczanie jednostek na mapie przed rozpoczęciem symulacji.
     * @param fraction frakcja, której jednostki mają być rozmieszczone
     * @param map mapa, na której jednostki mają być rozmieszczone
     */
    private static void placeUnitsOnMap(Fraction fraction, Map map) {
        char symbol;
        switch (fraction.getName()) {
            case "Army_of_ice":
                symbol = '!';
                break;
            case "Forest_Guards":
                symbol = '*';
                break;
            case "Brotherhood_of_magicians":
                symbol = '~';
                break;
            default:
                throw new IllegalArgumentException("Unknown fraction: " + fraction.getName());
        }

        for (Unit unit : fraction.getUnits()) {
            int x, y;
            do {
                x = (int) (Math.random() * Map.MAP_SIZE);
                y = (int) (Math.random() * Map.MAP_SIZE);
            } while (map.getSymbolAt(x, y) != symbol);

            map.placeUnit(unit, x, y);
        }
    }

}
