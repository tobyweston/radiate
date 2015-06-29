package bad.robot.radiate.ui;

import javax.swing.*;
import java.util.function.Consumer;

@Deprecated
interface ScreenMode extends Consumer<JFrame> {
    @Override void accept(JFrame jFrame);
}
