package bad.robot.radiate.ui

import java.awt.{Container, GridLayout}


class ChessboardLayoutS(tiles: List[_]) extends GridLayout {

  override def layoutContainer(parent: Container) {
    val tiles = EmptyTrailingTiles.tiles(this.tiles.size)
    setRows(tiles.rows)
    setColumns(tiles.columns)
    super.layoutContainer(parent)
  }
}