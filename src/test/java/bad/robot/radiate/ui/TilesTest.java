package bad.robot.radiate.ui;

import org.hamcrest.Description;
import org.hamcrest.TypeSafeDiagnosingMatcher;
import org.junit.Test;

import static bad.robot.radiate.ui.Tiles.tiles;
import static bad.robot.radiate.ui.TilesTest.TilesMatcher.requiresGridOf;
import static java.lang.String.format;
import static org.hamcrest.MatcherAssert.assertThat;

public class TilesTest {

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


    static class TilesMatcher extends TypeSafeDiagnosingMatcher<Tiles> {
        private final int rows;
        private final int columns;

        TilesMatcher(int rows, int columns) {
            this.rows = rows;
            this.columns = columns;
        }

        public static TilesMatcher requiresGridOf(int rows, int columns) {
            return new TilesMatcher(rows, columns);
        }

        @Override
        protected boolean matchesSafely(Tiles tiles, Description description) {
            if (tiles.rows() == rows && tiles.columns() == columns)
                return true;
            description.appendText(format("got (%d,%d)", tiles.rows(), tiles.columns()));
            return false;
        }

        @Override
        public void describeTo(Description description) {
            description.appendText(format("to require a grid of (%d,%d)", rows, columns));
        }
    }
}

