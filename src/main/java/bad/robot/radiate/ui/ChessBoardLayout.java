package bad.robot.radiate.ui;

import java.awt.*;
import java.util.Collection;

import static java.lang.String.format;

class ChessboardLayout extends GridLayout {

    private final Collection<?> tiles;

    public ChessboardLayout(Collection<?> tiles){
        this.tiles = tiles;
    }

    @Override
    public void layoutContainer(Container parent) {
        Tiles tiles = Tiles.tiles(this.tiles.size());
        setRows(tiles.rows());
        setColumns(tiles.columns());
        super.layoutContainer(parent);
        System.out.println("assertThat(tiles(" + this.tiles.size() + "), requiresGridOf(" + tiles.rows() + "," + tiles.columns() + "));");
    }

    private static class Tiles {

        private final int numberOfTiles;

        public static Tiles tiles(int numberOfTiles) {
            return new Tiles(numberOfTiles);
        }

        private Tiles(int numberOfTiles) {
            this.numberOfTiles = numberOfTiles;
        }

        int rows() {
            return (int) Math.floor(Math.sqrt(numberOfTiles));
        }

        int columns() {
            int rows = rows();
            return rows + (numberOfTiles == 1 ? 0 : 1);
        }

        @Override
        public String toString() {
            return format("%d tiles (%d, %d)", numberOfTiles, rows(), columns());
        }
    }
}
