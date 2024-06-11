package org.example;

import java.util.ArrayList;
import java.util.List;

/**
 * @brief Klasa Fraction reprezentuje frakcję, która posiada swoją nazwę oraz listę jednostek.
 */
public class Fraction {
    private String name; /**< Nazwa frakcji. */
    private List<Unit> units; /**< Lista jednostek należących do frakcji. */

    /**
     * @brief Konstruktor klasy Fraction.
     * @param name nazwa frakcji
     */
    public Fraction(String name) {
        this.name = name;
        this.units = new ArrayList<>();
    }

    /**
     * @brief Pobiera nazwę frakcji.
     * @return nazwa frakcji
     */
    public String getName() {
        return name;
    }

    /**
     * @brief Dodaje jednostkę do frakcji.
     * @param unit jednostka do dodania
     */
    public void addUnit(Unit unit) {
        units.add(unit);
    }

    /**
     * @brief Pobiera listę jednostek należących do frakcji.
     * @return lista jednostek
     */
    public List<Unit> getUnits() {
        return units;
    }
}
