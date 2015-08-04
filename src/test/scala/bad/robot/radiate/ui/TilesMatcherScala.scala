package bad.robot.radiate.ui

import org.specs2.matcher.{MatchResult, Expectable, Matcher}

object TilesMatcherScala {
  def requiresGridOf(rows: Int, columns: Int) = new TilesMatcherScala(rows, columns)
}

class TilesMatcherScala(rows: Int, columns: Int) extends Matcher[TilesScala] {
  def apply[S <: TilesScala](actual: Expectable[S]): MatchResult[S] = {
    result(actual.value.rows == rows && actual.value.columns == columns,
      actual.description + s" has size of ($rows, $columns)",
      actual.description + s" does not have size of ($rows, $columns), it's (${actual.value.rows}, ${actual.value.columns})",
      actual
    )
  }
}
