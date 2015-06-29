package bad.robot.radiate.ui;

import static java.lang.String.format;

@Deprecated
class EmptyTrailingTiles implements Tiles {

    private final int numberOfTiles;

    public static EmptyTrailingTiles tiles(int numberOfTiles) {
        return new EmptyTrailingTiles(numberOfTiles);
    }

    EmptyTrailingTiles(int numberOfTiles) {
        this.numberOfTiles = numberOfTiles;
    }

    @Override
    public int rows() {
        return (int) Math.floor(Math.sqrt(numberOfTiles));
    }

    @Override
    public int columns() {
        int rows = rows();
        return rows + (numberOfTiles == 1 ? 0 : 1);
    }

    @Override
    public String toString() {
        return format("%d tiles (%d, %d)", numberOfTiles, rows(), columns());
    }
}
