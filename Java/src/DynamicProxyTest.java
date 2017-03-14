import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

/**
 * Created by mazhuang on 2017/3/14.
 */

interface Human {
    void eat();
    void sleep();
}

class Student implements Human {
    @Override
    public void eat() {
        System.out.println("Student eat");
    }

    @Override
    public void sleep() {
        System.out.println("Student sleep");
    }
}

class HumanProxyHandler implements InvocationHandler {

    private Human mBase;

    public HumanProxyHandler(Human base) {
        mBase = base;
    }

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {

        System.out.println("Before invoke " + method.getName());

        Object result = method.invoke(mBase, args);

        System.out.println("After invoke " + method.getName());

        return result;
    }
}

public class DynamicProxyTest {
    public static void main(String[] args) {
        Student student = new Student();
        HumanProxyHandler proxyHandler = new HumanProxyHandler(student);
        Human proxy = (Human) Proxy.newProxyInstance(
                Human.class.getClassLoader(),
                new Class[] {Human.class},
                proxyHandler);
        proxy.eat();
        proxy.sleep();
    }
}
