package bad.robot.radiate.ui;

import bad.robot.radiate.Status;

public interface Ui {
    void start();

    void update(Status status);

    void update(Exception exception);
}
