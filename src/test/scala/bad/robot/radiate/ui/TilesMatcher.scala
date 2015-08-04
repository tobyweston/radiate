package bad.robot.radiate.ui

import org.specs2.matcher.{MatchResult, Expectable, Matcher}

object TilesMatcher {
  def requiresGridOf(rows: Int, columns: Int) = new TilesMatcher(rows, columns)
}

class TilesMatcher(rows: Int, columns: Int) extends Matcher[Tiles] {
  def apply[S <: Tiles](actual: Expectable[S]): MatchResult[S] = {
    result(actual.value.rows == rows && actual.value.columns == columns,
      actual.description + s" has size of ($rows, $columns)",
      actual.description + s" does not have size of ($rows, $columns), it's (${actual.value.rows}, ${actual.value.columns})",
      actual
    )
  }
}
