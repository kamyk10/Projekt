package org.example;

/**
 * @brief Klasa Fractions odpowiedzialna za tworzenie jednostek dla poszczególnych frakcji.
 */
public class Fractions {

    /**
     * @brief Tworzy jednostkę dla określonego typu jednostki.
     * @param unitType typ jednostki do utworzenia
     * @return obiekt klasy Unit reprezentujący utworzoną jednostkę
     * @throws IllegalArgumentException jeśli podano niewłaściwy typ jednostki
     */
    public static Unit createUnit(String unitType) {
        switch (unitType) {
            case "K":
                return new Unit("Ice_King", "Army_of_ice", 'K', 15, 200, 1);
            case "R":
                return new Unit("Ice_Rider", "Army_of_ice", 'R', 8, 60, 2);
            case "W":
                return new Unit("Ice_Warrior", "Army_of_ice", 'W', 5, 50, 1);
            case "E":
                return new Unit("Elf", "Forest_Guards", 'E', 8, 60, 2);
            case "D":
                return new Unit("Druid", "Forest_Guards", 'D', 2, 120, 1);
            case "G":
                return new Unit("Dwarf", "Forest_Guards", 'G', 10, 70, 1);
            case "A":
                return new Unit("Archmage", "Brotherhood_of_magicians", 'A', 16, 180, 30);
            case "F":
                return new Unit("Mage_of_fire", "Brotherhood_of_magicians", 'F', 10, 65, 1);
            case "M":
                return new Unit("Adept_of_magic", "Brotherhood_of_magicians", 'M', 6, 50, 1);
            default:
                throw new IllegalArgumentException("Wrong type of unit: " + unitType);
        }
    }

    /**
     * @brief Pobiera nazwę jednostki dla określonego typu jednostki.
     * @param unitType typ jednostki
     * @return nazwa jednostki jako String
     * @throws IllegalArgumentException jeśli podano niewłaściwy typ jednostki
     */
    public static String getUnitName(String unitType) {
        switch (unitType) {
            case "K":
                return "Ice_King";
            case "R":
                return "Ice_Rider";
            case "W":
                return "Ice_Warrior";
            case "E":
                return "Elf";
            case "D":
                return "Druid";
            case "G":
                return "Dwarf";
            case "A":
                return "Archmage";
            case "F":
                return "Mage_of_fire";
            case "M":
                return "Adept_of_magic";
            default:
                throw new IllegalArgumentException("Wrong type of unit: " + unitType);
        }
    }

}
