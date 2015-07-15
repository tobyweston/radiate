package bad.robot.radiate.ui

import java.awt.{Container, GridLayout}

class ChessboardLayoutS(tiles: Seq[_]) extends GridLayout {

  override def layoutContainer(parent: Container) {
    val tiles = EmptyTrailingTilesScala.tiles(this.tiles.size)
    setRows(tiles.rows)
    setColumns(tiles.columns)
    super.layoutContainer(parent)
  }
}