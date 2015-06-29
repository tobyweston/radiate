package bad.robot.radiate.ui

import math._

object EmptyTrailingTilesScala {
  def tiles(numberOfTiles: Int): EmptyTrailingTilesScala = new EmptyTrailingTilesScala(numberOfTiles)
}

class EmptyTrailingTilesScala(numberOfTiles: Int) extends TilesScala {

  val rows = floor(sqrt(numberOfTiles)).toInt

  val columns = rows + (if (numberOfTiles == 1) 0 else 1)

  override def toString = s"$numberOfTiles tiles ($rows, $columns)"
}