package bad.robot.radiate.ui

import math._

object EmptyTrailingTiles {
  def tiles(numberOfTiles: Int): EmptyTrailingTiles = new EmptyTrailingTiles(numberOfTiles)
}

class EmptyTrailingTiles(numberOfTiles: Int) extends Tiles {

  val rows = floor(sqrt(numberOfTiles)).toInt

  val columns = rows + (if (numberOfTiles == 1) 0 else 1)

  override def toString = s"$numberOfTiles tiles ($rows, $columns)"
}