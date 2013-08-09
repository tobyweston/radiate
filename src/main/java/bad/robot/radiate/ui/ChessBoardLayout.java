package bad.robot.radiate.ui;

import java.awt.*;
import java.util.Collection;

class ChessboardLayout extends GridLayout {

    private final Collection<?> tiles;

    public ChessboardLayout(Collection<?> tiles) {
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

}
