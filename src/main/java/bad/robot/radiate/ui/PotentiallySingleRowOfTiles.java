package bad.robot.radiate.ui;

import static java.lang.String.format;

class PotentiallySingleRowOfTiles implements Tiles {

    private final int numberOfTiles;
    private int rows;
    private int columns;

    public static PotentiallySingleRowOfTiles tiles(int numberOfTiles) {
        return new PotentiallySingleRowOfTiles(numberOfTiles);
    }

    private PotentiallySingleRowOfTiles(int numberOfTiles) {
        this.numberOfTiles = numberOfTiles;
        for (int i = 1; i <= Math.sqrt(numberOfTiles); i++) {
            if (numberOfTiles % i == 0) {
                this.rows = i;
                this.columns = numberOfTiles / i;
            }
        }
    }

    @Override
    public int rows() {
        return rows;
    }

    @Override
    public int columns() {
        return columns;
    }

    @Override
    public String toString() {
        return format("%d tiles (%d, %d)", numberOfTiles, rows(), columns());
    }
}
