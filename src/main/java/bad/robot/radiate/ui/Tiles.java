package bad.robot.radiate.ui;

import static java.lang.String.format;

class Tiles {

    private final int numberOfTiles;
    private int rows;
    private int columns;

    public static Tiles tiles(int numberOfTiles) {
        return new Tiles(numberOfTiles);
    }

    Tiles(int numberOfTiles) {
        this.numberOfTiles = numberOfTiles;
        for (int i = 1; i <= Math.sqrt(numberOfTiles); i++) {
            if (numberOfTiles % i == 0) {
                this.rows = i;
                this.columns = numberOfTiles / i;
            }
        }
    }

    int rows() {
        return rows;
    }

    int columns() {
        return columns;
    }

    @Override
    public String toString() {
        return format("%d tiles (%d, %d)", numberOfTiles, rows(), columns());
    }
}
