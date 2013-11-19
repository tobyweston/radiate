package bad.robot.radiate;

import com.googlecode.totallylazy.Callable1;
import com.googlecode.totallylazy.Pair;

public class Functions {
    public static <A, B> Callable1<Pair<A, B>, A> first() {
        return new Callable1<Pair<A, B>, A>() {
            @Override
            public A call(Pair<A, B> pair) throws Exception {
                return pair.first();
            }
        };
    }

    public static <A, B> Callable1<Pair<A, B>, B> second() {
        return new Callable1<Pair<A, B>, B>() {
            @Override
            public B call(Pair<A, B> pair) throws Exception {
                return pair.second();
            }
        };
    }

    public static <T> Callable1<T, String> asString() {
        return new Callable1<T, String>() {
            @Override
            public String call(T object) throws Exception {
                return object.toString();
            }
        };
    }
}
