package models;

import types.Color;
import types.Coordinate;
import types.Direction;

public class Board {

    private final int ROWS = 6;
    private final int COLUMNS = 7;
    private final Coordinate[][] squares = new Coordinate[ROWS][COLUMNS];
    private Coordinate lastSquare;

    public Board() {
        for (int i = 0; i < ROWS; i++) {
            for (int j = 0; j < COLUMNS; j++) {
                squares[i][j] = new Coordinate(Color.NULL);
            }
        }
    }

    public void paint() {
        String row;
        for (int i = 0; i < ROWS; i++) {
            row = "";
            for (int j = 0; j < COLUMNS; j++) {
                row += squares[i][j].getColor().getCode() + " ";
            }
            System.out.println(row);
        }
    }

    public boolean addToken(int column, Color color) {
        if (column >= 0 && column < COLUMNS && squares[0][column].hasColor(Color.NULL)) {
            int i = ROWS - 1;
            while (!squares[i][column].hasColor(Color.NULL)) {
                i--;
            }
            squares[i][column] = new Coordinate(i, column, color);
            lastSquare = squares[i][column];
            return true;
        } else {
            System.out.println("Error. Choose other column please (1-7):");
            return false;
        }
    }

    public boolean hasFourConnected() {
        int counter = 1;
        int i = 0;
        boolean hasFour = false;
        Direction[] directions = Direction.values();
        Coordinate initialSquare = lastSquare;
        Coordinate actualSquare = lastSquare;
        while (i < directions.length && counter < 4) {
            if (actualSquare.equals(nextCoordinate(actualSquare, directions[i]))) {
                counter++;
                actualSquare = nextCoordinate(actualSquare, directions[i]);
            } else {
                if (directions[i] != Direction.EAST) {
                    counter = 1;
                }
                i++;
                actualSquare = initialSquare;
            }
        }
        if (counter == 4) {
            hasFour = true;
        }
        return hasFour;
    }

    private Coordinate nextCoordinate(Coordinate square, Direction direction) {
        Coordinate next;
        try {
            next = squares[square.getX() + direction.getX()][square.getY() + direction.getY()];
        } catch (ArrayIndexOutOfBoundsException e) {
            next = null;
        }
        return next;
    }

}