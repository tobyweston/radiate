package bad.robot.radiate.monitor;

import bad.robot.radiate.NullProgress;
import bad.robot.radiate.Progress;
import bad.robot.radiate.Status;
import com.googlecode.totallylazy.Lists;

import java.util.List;

import static bad.robot.radiate.Activity.*;
import static bad.robot.radiate.monitor.RandomStatus.randomProgress;
import static bad.robot.radiate.monitor.RandomStatus.randomStatus;
import static java.lang.String.format;

class Demo extends ThreadSafeObservable implements MonitoringTasksFactory {

    @Override
    public List<MonitoringTask> create() {
        return Lists.<MonitoringTask>list(new DemoMonitoringTask());
    }

    private static class DemoMonitoringTask extends ThreadSafeObservable implements MonitoringTask {

        private DemonstrativeMonitor monitor = new DemonstrativeMonitor.BusyMonitorExample();

        @Override
        public void run() {
            monitor = monitor.notify(this);
        }

        @Override
        public String toString() {
            return "demonstration";
        }
    }

    private static interface DemonstrativeMonitor {
        DemonstrativeMonitor notify(Observable observable);

        static class BusyMonitorExample implements DemonstrativeMonitor {
            public DemonstrativeMonitor notify(Observable observable) {
                observable.notifyObservers(Busy, new NullProgress());
                Status status = randomStatus();
                observable.notifyObservers(status);
                observable.notifyObservers(new Information(format("Example busy monitor with random status %s", status)));
                return new IdleMonitorExample();
            }
        }

        static class IdleMonitorExample implements DemonstrativeMonitor {
            public DemonstrativeMonitor notify(Observable observable) {
                observable.notifyObservers(Idle, new NullProgress());
                Status status = randomStatus();
                observable.notifyObservers(status);
                observable.notifyObservers(new Information(format("Example of an idle monitor with random status of %s", status)));
                return new ProgressingMonitorExample();
            }
        }

        static class ProgressingMonitorExample implements DemonstrativeMonitor {
            public DemonstrativeMonitor notify(Observable observable) {
                observable.notifyObservers(Progressing, randomProgress());
                Status status = randomStatus();
                observable.notifyObservers(status);
                observable.notifyObservers(new Information(format("Example of a progressing monitor with random status %s", status)));
                return new OvertimeMonitorExample();
            }
        }

        static class OvertimeMonitorExample implements DemonstrativeMonitor {
            public DemonstrativeMonitor notify(Observable observable) {
                observable.notifyObservers(Progressing, new Complete());
                Status status = randomStatus();
                observable.notifyObservers(status);
                observable.notifyObservers(new Information(format("Example of an overtime monitor with random status of %s", status)));
                return new ErrorExample();
            }

            private static class Complete extends Progress {
                public Complete() {
                    super(100, 100);
                }
            }
        }

        // back to the beginning
        static class ErrorExample implements DemonstrativeMonitor {
            public DemonstrativeMonitor notify(Observable observable) {
                observable.notifyObservers(Busy, new NullProgress());
                observable.notifyObservers(new RuntimeException("An exception exception"));
                Status status = randomStatus();
                observable.notifyObservers(status);
                observable.notifyObservers(new Information(format("Example of an error with random status of %s", status)));
                return new IdleMonitorExample();
            }
        }
    }

}
