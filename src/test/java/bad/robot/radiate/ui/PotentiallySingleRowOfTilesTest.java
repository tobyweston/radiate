package bad.robot.radiate.ui;

import org.junit.Test;

import static bad.robot.radiate.ui.PotentiallySingleRowOfTiles.tiles;
import static bad.robot.radiate.ui.TilesMatcher.requiresGridOf;
import static org.hamcrest.MatcherAssert.assertThat;

@Deprecated
public class PotentiallySingleRowOfTilesTest {

    @Test
    public void gridsForNumberOfTiles() {
        assertThat(tiles(1), requiresGridOf(1, 1));
        assertThat(tiles(2), requiresGridOf(1, 2));
        assertThat(tiles(3), requiresGridOf(1, 3)); // yuk
        assertThat(tiles(4), requiresGridOf(2, 2));
        assertThat(tiles(5), requiresGridOf(1, 5)); // yuk
        assertThat(tiles(6), requiresGridOf(2, 3));
        assertThat(tiles(7), requiresGridOf(1, 7)); // yuk
        assertThat(tiles(8), requiresGridOf(2, 4));
        assertThat(tiles(9), requiresGridOf(3, 3));
        assertThat(tiles(10), requiresGridOf(2, 5));
        assertThat(tiles(11), requiresGridOf(1, 11)); // yuk
        assertThat(tiles(12), requiresGridOf(3, 4));
        assertThat(tiles(13), requiresGridOf(1, 13)); // yuk
        assertThat(tiles(14), requiresGridOf(2, 7));
        assertThat(tiles(15), requiresGridOf(3, 5));
    }

}

