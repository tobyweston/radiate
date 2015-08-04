package bad.robot.radiate

import org.specs2.mutable.Specification

class ProgressTest extends Specification {
  
  "String representation" >> {
   new Progress(20, 100).toString() must_== "20%"
   new Progress(30, 200).toString() must_== "15%"
   new Progress(40, 200).toString() must_== "20%"
   new Progress(40, 120).toString() must_== "33%"
  }

  "Adding" >> {
    val progress = new Progress(10, 100).add(new Progress(20, 100));
    progress.toString() must_== "15%"
  }

  
  "Adding two builds (running and complete builds)" >> {
    val complete = new Progress(100, 100);
    val running = new Progress(20, 100);
    complete.add(running).toString() must_== "60%"
  }

  "Show the number of things the progress is over" >> {
    new Progress(0, 200).numberOfBuilds must_== 1
    new Progress(0, 100).add(new Progress(50, 300)).numberOfBuilds must_== 2
    new Progress(0, 100).add(new Progress(20, 100)).add(new Progress(50, 300)).numberOfBuilds must_== 3
  }

  "Ignore number of things when null object is used" >> {
    new Progress(0, 0).numberOfBuilds must_== 1
    new Progress(0, 0).add(new Progress(0, 0)).numberOfBuilds must_== 2
    new Progress(0, 0).add(new NullProgress()).numberOfBuilds must_== 1
    new Progress(0, 0).add(new NullProgress()).add(new NullProgress()).numberOfBuilds must_== 1
  }

  "Less than" >> {
    val sixtyPercent = Progress(60, 100);
    val fiftyPercent = new Progress(100, 200);
    fiftyPercent < sixtyPercent must_== true
  }

  "Greater than" >> {
    val sixtyPercent = new Progress(60, 100);
    val fiftyPercent = new Progress(100, 200);
    sixtyPercent > fiftyPercent must_== true
  }
}
