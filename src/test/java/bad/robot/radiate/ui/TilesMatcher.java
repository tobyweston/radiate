package bad.robot.radiate.ui;

import org.hamcrest.Description;
import org.hamcrest.TypeSafeDiagnosingMatcher;

import static java.lang.String.format;

@Deprecated
class TilesMatcher extends TypeSafeDiagnosingMatcher<Tiles> {
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
