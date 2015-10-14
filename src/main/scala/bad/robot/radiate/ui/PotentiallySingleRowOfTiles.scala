package bad.robot.radiate.ui

import scala.math._

object PotentiallySingleRowOfTiles {
  def tiles(numberOfTiles: Int) = new PotentiallySingleRowOfTiles(numberOfTiles)
}

case class PotentiallySingleRowOfTiles(numberOfTiles: Int) extends Tiles {
  private var _rows = 0
  private var _columns = 0

  for (i <- 1 to sqrt(numberOfTiles).toInt) {
    if (numberOfTiles % i == 0) {
      _rows = i
      _columns = numberOfTiles / i
    }
  }

  val rows = _rows
  val columns = _columns
  
  override def toString: String = {
    s"$numberOfTiles tiles ($rows, $columns)"
  }
}