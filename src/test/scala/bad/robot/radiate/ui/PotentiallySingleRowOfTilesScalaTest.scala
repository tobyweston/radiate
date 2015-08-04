package bad.robot.radiate.ui

import bad.robot.radiate.ui.PotentiallySingleRowOfTilesScala.tiles
import bad.robot.radiate.ui.TilesMatcherScala.requiresGridOf
import org.specs2.mutable.Specification

class PotentiallySingleRowOfTilesScalaTest extends Specification {

  "Grids from number of tiles" >> {
    tiles(1) must requiresGridOf(1, 1)
    tiles(2) must requiresGridOf(1, 2)
    tiles(3) must requiresGridOf(1, 3) // yuk
    tiles(4) must requiresGridOf(2, 2)
    tiles(5) must requiresGridOf(1, 5) // yuk
    tiles(6) must requiresGridOf(2, 3)
    tiles(7) must requiresGridOf(1, 7) // yuk
    tiles(8) must requiresGridOf(2, 4)
    tiles(9) must requiresGridOf(3, 3)
    tiles(10) must requiresGridOf(2, 5)
    tiles(11) must requiresGridOf(1, 11) // yuk
    tiles(12) must requiresGridOf(3, 4)
    tiles(13) must requiresGridOf(1, 13) // yuk
    tiles(14) must requiresGridOf(2, 7)
    tiles(15) must requiresGridOf(3, 5)
  }

}
