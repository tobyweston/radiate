package bad.robot.radiate.ui;

import org.junit.Test;

import static bad.robot.radiate.ui.EmptyTrailingTiles.tiles;
import static bad.robot.radiate.ui.TilesMatcher.requiresGridOf;
import static org.hamcrest.MatcherAssert.assertThat;

public class EmptyTrailingTilesTest {

    @Test
    public void gridsForNumberOfTiles() {
        assertThat(tiles(1), requiresGridOf(1, 1));
        assertThat(tiles(2), requiresGridOf(1, 2));
        assertThat(tiles(3), requiresGridOf(1, 2)); // wrong! 2,2
        assertThat(tiles(4), requiresGridOf(2, 3)); // wrong! 2,2
        assertThat(tiles(5), requiresGridOf(2, 3));
        assertThat(tiles(6), requiresGridOf(2, 3));
        assertThat(tiles(7), requiresGridOf(2, 3)); // wrong! 2,4
        assertThat(tiles(8), requiresGridOf(2, 3)); // wrong! 2,4
        assertThat(tiles(9), requiresGridOf(3, 4)); // wrong! 3,3
        assertThat(tiles(10), requiresGridOf(3, 4)); // wrong! 2,5 or 3,4
        assertThat(tiles(11), requiresGridOf(3, 4));
        assertThat(tiles(12), requiresGridOf(3, 4));
        assertThat(tiles(13), requiresGridOf(3, 4)); // wrong! 3,5
        assertThat(tiles(14), requiresGridOf(3, 4)); // wrong! 3,5
        assertThat(tiles(15), requiresGridOf(3, 4)); // wrong! 3,5
    }

}

