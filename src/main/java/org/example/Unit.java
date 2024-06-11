package org.example;

/**
 * @brief Klasa Unit reprezentuje jednostkę w grze, która posiada unikalny identyfikator, nazwę, frakcję, symbol oraz różne statystyki.
 */
public class Unit {
    private static int idCounter = 0; /**< Licznik do generowania unikalnych identyfikatorów. */
    private int id; /**< Unikalny identyfikator jednostki. */
    private String name; /**< Nazwa jednostki. */
    private String fractionName; /**< Nazwa frakcji, do której należy jednostka. */
    private char symbol; /**< Symbol reprezentujący jednostkę. */
    private int baseStrength; /**< Podstawowa siła jednostki. */
    private int strength; /**< Aktualna siła jednostki. */
    private int health; /**< Zdrowie jednostki. */
    private int baseSpeed; /**< Podstawowa prędkość jednostki. */
    private int speed; /**< Aktualna prędkość jednostki. */

    /**
     * @brief Konstruktor klasy Unit.
     * @param name nazwa jednostki
     * @param fractionName nazwa frakcji, do której należy jednostka
     * @param symbol symbol reprezentujący jednostkę
     * @param baseStrength podstawowa siła jednostki
     * @param health zdrowie jednostki
     * @param baseSpeed podstawowa prędkość jednostki
     */
    public Unit(String name, String fractionName, char symbol, int baseStrength, int health, int baseSpeed) {
        this.id = ++idCounter;
        this.name = name;
        this.fractionName = fractionName;
        this.symbol = symbol;
        this.baseStrength = baseStrength;
        this.strength = baseStrength;
        this.health = health;
        this.baseSpeed = baseSpeed;
        this.speed = baseSpeed;
    }

    /**
     * @brief Zwraca identyfikator jednostki.
     * @return identyfikator jednostki
     */
    public int getId() {
        return id;
    }

    /**
     * @brief Zwraca nazwę jednostki.
     * @return nazwa jednostki
     */
    public String getName() {
        return name;
    }

    /**
     * @brief Zwraca nazwę frakcji, do której należy jednostka.
     * @return nazwa frakcji jednostki
     */
    public String getFractionName() {
        return fractionName;
    }

    /**
     * @brief Zwraca symbol jednostki.
     * @return symbol jednostki
     */
    public char getSymbol() {
        return symbol;
    }

    /**
     * @brief Zwraca aktualną siłę jednostki.
     * @return aktualna siła jednostki
     */
    public int getStrength() {
        return strength;
    }

    /**
     * @brief Zwraca aktualne zdrowie jednostki.
     * @return aktualne zdrowie jednostki
     */
    public int getHealth() {
        return health;
    }

    /**
     * @brief Zmniejsza zdrowie jednostki o podaną wartość.
     * @param amount ilość, o którą należy zmniejszyć zdrowie jednostki
     */
    public void decreaseHealth(int amount) {
        health -= amount;
        if (health < 0) {
            health = 0;
        }
    }

    /**
     * @brief Zwraca aktualną prędkość jednostki.
     * @return aktualna prędkość jednostki
     */
    public int getSpeed() {
        return speed;
    }

    /**
     * @brief Sprawdza, czy jednostka jest żywa (zdrowie większe od 0).
     * @return true, jeśli jednostka jest żywa, w przeciwnym razie false
     */
    public boolean isAlive() {
        return health > 0;
    }

    /**
     * @brief Aktualizuje siłę jednostki w zależności od jej lokalizacji.
     * Jednostki z frakcji "Army_of_ice" mają zwiększoną siłę na polach oznaczonych symbolem '!'.
     * @param currentAreaSymbol symbol pola, na którym znajduje się jednostka
     */
    public void updateStrength(char currentAreaSymbol) {
        if (fractionName.equals("Army_of_ice") && currentAreaSymbol == '!') {
            strength = baseStrength + 5;
        } else {
            strength = baseStrength;
        }
    }

    /**
     * @brief Aktualizuje prędkość jednostki w zależności od jej lokalizacji.
     * Jednostki z frakcji "Forest_Guards" mają zwiększoną prędkość na polach oznaczonych symbolem '*'.
     * @param currentAreaSymbol symbol pola, na którym znajduje się jednostka
     */
    public void updateSpeed(char currentAreaSymbol) {
        if (fractionName.equals("Forest_Guards") && currentAreaSymbol == '*') {
            speed = baseSpeed + 1;
        } else {
            speed = baseSpeed;
        }
    }

    /**
     * @brief Aktualizuje zdrowie jednostki w zależności od jej lokalizacji.
     * Jednostki z frakcji "Brotherhood_of_magicians" zwiększają zdrowie na polach oznaczonych symbolem '~'.
     * @param currentAreaSymbol symbol pola, na którym znajduje się jednostka
     */
    public void updateHealth(char currentAreaSymbol) {
        if (fractionName.equals("Brotherhood_of_magicians") && currentAreaSymbol == '~') {
            health++;
        }
    }
}
