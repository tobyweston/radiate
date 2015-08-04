package bad.robot.radiate

import org.specs2.mutable.Specification

class ProgressSTest extends Specification {
  
  "String representation" >> {
   new ProgressS(20, 100).toString() must_== "20%"
   new ProgressS(30, 200).toString() must_== "15%"
   new ProgressS(40, 200).toString() must_== "20%"
   new ProgressS(40, 120).toString() must_== "33%"
  }

  "Adding" >> {
    val progress = new ProgressS(10, 100).add(new ProgressS(20, 100));
    progress.toString() must_== "15%"
  }

  
  "Adding two builds (running and complete builds)" >> {
    val complete = new ProgressS(100, 100);
    val running = new ProgressS(20, 100);
    complete.add(running).toString() must_== "60%"
  }

  "Show the number of things the progress is over" >> {
    new ProgressS(0, 200).numberOfBuilds must_== 1
    new ProgressS(0, 100).add(new ProgressS(50, 300)).numberOfBuilds must_== 2
    new ProgressS(0, 100).add(new ProgressS(20, 100)).add(new ProgressS(50, 300)).numberOfBuilds must_== 3
  }

  "Ignore number of things when null object is used" >> {
    new ProgressS(0, 0).numberOfBuilds must_== 1
    new ProgressS(0, 0).add(new ProgressS(0, 0)).numberOfBuilds must_== 2
    new ProgressS(0, 0).add(new NullProgressS()).numberOfBuilds must_== 1
    new ProgressS(0, 0).add(new NullProgressS()).add(new NullProgressS()).numberOfBuilds must_== 1
  }

  "Less than" >> {
    val sixtyPercent = ProgressS(60, 100);
    val fiftyPercent = new ProgressS(100, 200);
    fiftyPercent < sixtyPercent must_== true
  }

  "Greater than" >> {
    val sixtyPercent = new ProgressS(60, 100);
    val fiftyPercent = new ProgressS(100, 200);
    sixtyPercent > fiftyPercent must_== true
  }
}
