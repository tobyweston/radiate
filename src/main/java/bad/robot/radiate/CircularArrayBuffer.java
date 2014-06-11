package bad.robot.radiate;

import java.util.function.Supplier;

public class CircularArrayBuffer<T> {

    private final Supplier<T>[] values;
    private int index;

    public CircularArrayBuffer(Supplier<T>... values) {
        if (values.length == 0)
            throw new IllegalArgumentException();
        this.values = values;
    }

    public T next() {
        if (index == values.length)
            index = 0;
        return values[index++].get();
    }
}
