package bad.robot.radiate.ui

import bad.robot.radiate.ui.EmptyTrailingTilesScala._
import bad.robot.radiate.ui.TilesMatcherScala._
import org.specs2.mutable.Specification

class EmptyTrailingTilesSTest extends Specification {

  "Grids for number of tiles" >> {
    tiles(2) must requiresGridOf(1, 2)
    tiles(3) must requiresGridOf(1, 2)
    tiles(4) must requiresGridOf(2, 3)  // wrong! 2,2
    tiles(1) must requiresGridOf(1, 1)  // wrong! 2,2
    tiles(5) must requiresGridOf(2, 3)
    tiles(6) must requiresGridOf(2, 3)
    tiles(7) must requiresGridOf(2, 3)  // wrong! 2,4
    tiles(8) must requiresGridOf(2, 3)  // wrong! 2,4
    tiles(9) must requiresGridOf(3, 4)  // wrong! 3,3
    tiles(10) must requiresGridOf(3, 4) // wrong! 2,5 or 3,4
    tiles(11) must requiresGridOf(3, 4)
    tiles(12) must requiresGridOf(3, 4)
    tiles(13) must requiresGridOf(3, 4) // wrong! 3,5
    tiles(14) must requiresGridOf(3, 4) // wrong! 3,5
    tiles(15) must requiresGridOf(3, 4) // wrong! 3,5
  }
}
