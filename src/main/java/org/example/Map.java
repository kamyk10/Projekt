package org.example;

import java.util.*;
import java.util.Map.Entry;

/**
 * @brief Klasa Map reprezentuje mapę, na której odbywa się symulacja.
 */
public class Map {
    /** @brief Rozmiar mapy. */
    public static final int MAP_SIZE = 20;
    /** @brief Dwuwymiarowa tablica reprezentująca mapę. */
    private char[][] map;
    /** @brief Mapa przechowująca pozycje jednostek. */
    private HashMap<Unit, int[]> unitPositions;
    /** @brief Mapa przechowująca symbole jednostek. */
    private HashMap<Unit, Character> unitSymbols;

    /**
     * @brief Konstruktor klasy Map inicjalizujący mapę oraz pozycje jednostek.
     */
    public Map() {
        this.map = new char[MAP_SIZE][MAP_SIZE];
        this.unitPositions = new HashMap<>();
        this.unitSymbols = new HashMap<>();
        generateMap();
    }

    /**
     * @brief Generowanie mapy.
     */
    private void generateMap() {
        for (int i = 0; i < MAP_SIZE; i++) {
            for (int j = 0; j < MAP_SIZE; j++) {
                map[i][j] = getAreaSymbol(i, j);
            }
        }
    }

    /**
     * @brief Pobieranie symbolu z pola [x,y] w trakcie rozgrywki.
     * @param x współrzędna x
     * @param y współrzędna y
     * @return symbol obszaru
     */
    private char getAreaSymbol(int x, int y) {
        char symbol;
        int areaCount = (int) Math.ceil(MAP_SIZE * MAP_SIZE / 3.0);
        if (x * MAP_SIZE + y < areaCount) {
            symbol = '!';
        } else if (x * MAP_SIZE + y < 2 * areaCount) {
            symbol = '~';
        } else {
            symbol = '*';
        }
        return symbol;
    }

    /**
     * @brief Wypisywanie mapy.
     */
    public void printMap() {
        for (int i = 0; i < MAP_SIZE; i++) {
            for (int j = 0; j < MAP_SIZE; j++) {
                System.out.print(map[i][j] + " ");
            }
            System.out.println();
        }
    }

    /**
     * @brief Rozmieszczanie jednostek w trakcie symulacji.
     * @param unit jednostka
     * @param x współrzędna x
     * @param y współrzędna y
     */
    public void placeUnit(Unit unit, int x, int y) {
        if (x >= 0 && x < MAP_SIZE && y >= 0 && y < MAP_SIZE) {
            char symbol = unit.getSymbol();
            if (map[x][y] == '*' || map[x][y] == '~' || map[x][y] == '!') {
                map[x][y] = symbol;
                unitPositions.put(unit, new int[]{x, y});
                unitSymbols.put(unit, symbol);
            } else {
                System.out.println("Position (" + x + ", " + y + ") is already occupied.");
            }
        } else {
            System.out.println("Wrong position on map.");
        }
    }

    /**
     * @brief Pobieranie symbolu przy pierwszym rozmieszczaniu jednostek.
     * @param x współrzędna x
     * @param y współrzędna y
     * @return symbol obszaru
     */
    public char getSymbolAt(int x, int y) {
        if (x >= 0 && x < MAP_SIZE && y >= 0 && y < MAP_SIZE) {
            return map[x][y];
        } else {
            throw new IllegalArgumentException("Wrong coordinations on map: (" + x + ", " + y + ")");
        }
    }

    /**
     * @brief Wypisywanie jednostek i ich statystyk.
     */
    public void printUnits() {
        unitPositions.entrySet().stream()
                .sorted(Comparator.comparingInt(entry -> entry.getValue()[0] * MAP_SIZE + entry.getValue()[1]))
                .map(Entry::getKey)
                .forEach(unit -> {
                    int[] position = unitPositions.get(unit);
                    System.out.println("- " + unit.getName() + " (Health - " + unit.getHealth() +
                            ", Strength - " + unit.getStrength() + ", Speed - " + unit.getSpeed() +
                            ") at position [" + position[0] + "][" + position[1] + "].");
                });
    }

    /**
     * @brief Usuwanie jednostek, które mają 0 życia.
     */
    public void removeDeadUnits() {
        Iterator<Entry<Unit, int[]>> iterator = unitPositions.entrySet().iterator();
        while (iterator.hasNext()) {
            Entry<Unit, int[]> entry = iterator.next();
            Unit unit = entry.getKey();
            if (!unit.isAlive()) {
                int[] position = entry.getValue();
                map[position[0]][position[1]] = getAreaSymbol(position[0], position[1]);
                iterator.remove();
                unitSymbols.remove(unit);
            }
        }
    }

    /**
     * @brief Przemieszczanie się jednostek.
     */
    public void moveUnits() {
        Random rand = new Random();
        char[][] newMap = new char[MAP_SIZE][MAP_SIZE];
        for (int i = 0; i < MAP_SIZE; i++) {
            for (int j = 0; j < MAP_SIZE; j++) {
                newMap[i][j] = getAreaSymbol(i, j);
            }
        }

        HashMap<Unit, int[]> newPositions = new HashMap<>();
        for (Entry<Unit, int[]> entry : unitPositions.entrySet()) {
            Unit unit = entry.getKey();
            int[] position = entry.getValue();
            int newX = position[0];
            int newY = position[1];

            for (int step = 0; step < unit.getSpeed(); step++) {
                int direction = rand.nextInt(4);
                switch (direction) {
                    case 0:
                        newX = Math.max(0, newX - 1);
                        break;
                    case 1:
                        newX = Math.min(MAP_SIZE - 1, newX + 1);
                        break;
                    case 2:
                        newY = Math.max(0, newY - 1);
                        break;
                    case 3:
                        newY = Math.min(MAP_SIZE - 1, newY + 1);
                        break;
                }
            }

            char currentAreaSymbol = newMap[newX][newY];
            if (currentAreaSymbol == '*' || currentAreaSymbol == '~' || currentAreaSymbol == '!') {
                newMap[newX][newY] = unit.getSymbol();
            } else {
                newMap[position[0]][position[1]] = unit.getSymbol();
            }
            newPositions.put(unit, new int[]{newX, newY});
            unit.updateStrength(currentAreaSymbol);
            unit.updateSpeed(currentAreaSymbol);
            unit.updateHealth(currentAreaSymbol);
        }

        map = newMap;
        unitPositions = newPositions;
    }

    /**
     * @brief Pobieranie nazw frakcji, które pozostały na mapie.
     * @return lista nazw frakcji
     */
    public List<String> getRemainingFractions() {
        Set<String> remainingFractions = new HashSet<>();
        for (Unit unit : unitPositions.keySet()) {
            remainingFractions.add(unit.getFractionName());
        }
        return new ArrayList<>(remainingFractions);
    }

    /**
     * @brief Pobieranie jednostki na danej pozycji.
     * @param x współrzędna x
     * @param y współrzędna y
     * @return jednostka na danej pozycji lub null jeśli brak
     */
    public Unit getUnitAtPosition(int x, int y) {
        for (Entry<Unit, int[]> entry : unitPositions.entrySet()) {
            int[] position = entry.getValue();
            if (position[0] == x && position[1] == y) {
                return entry.getKey();
            }
        }
        return null;
    }

    /**
     * @brief Pobieranie jednostek, które znajdują się wokół jednostki docelowej.
     * @param unit jednostka docelowa
     * @return lista jednostek znajdujących się wokół
     */
    public List<Unit> getAdjacentUnits(Unit unit) {
        List<Unit> adjacentUnits = new ArrayList<>();
        int[] position = unitPositions.get(unit);
        int x = position[0];
        int y = position[1];

        for (int dx = -1; dx <= 1; dx++) {
            for (int dy = -1; dy <= 1; dy++) {
                if ((dx != 0 || dy != 0) && isValidPosition(x + dx, y + dy)) {
                    Unit adjacentUnit = getUnitAtPosition(x + dx, y + dy);
                    if (adjacentUnit != null) {
                        adjacentUnits.add(adjacentUnit);
                    }
                }
            }
        }

        return adjacentUnits;
    }

    /**
     * @brief Sprawdzanie, czy współrzędne znajdują się w granicach mapy.
     * @param x współrzędna x
     * @param y współrzędna y
     * @return true jeśli współrzędne są w granicach mapy, false w przeciwnym razie
     */
    private boolean isValidPosition(int x, int y) {
        return x >= 0 && x < MAP_SIZE && y >= 0 && y < MAP_SIZE;
    }
}
