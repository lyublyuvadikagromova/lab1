import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.util.Arrays;


interface Evaluatable {
    double evaluate(double x);
    String getExpression();
}


class Function1 implements Evaluatable {
    private double a;

    public Function1(double a) {
        this.a = a;
    }

    @Override
    public double evaluate(double x) {
        return Math.exp(-Math.abs(a * x)) * Math.sin(x);
    }

    @Override
    public String getExpression() {
        return "Exp(-|" + a + "| * x) * sin(x)";
    }
}


class Function2 implements Evaluatable {
    @Override
    public double evaluate(double x) {
        return x * x;
    }

    @Override
    public String getExpression() {
        return "x^2";
    }
}

public class Z5 {
    public static void main(String[] args) {
        Evaluatable function1 = new Function1(2.0);
        Evaluatable proxyFunction1 = createProxy(function1);

        Evaluatable function2 = new Function2();
        Evaluatable proxyFunction2 = createProxy(function2);

        double result1 = proxyFunction1.evaluate(1.5);
        double result2 = proxyFunction2.evaluate(3.0);
    }

    private static Evaluatable createProxy(Evaluatable target) {
        return (Evaluatable) java.lang.reflect.Proxy.newProxyInstance(
                Evaluatable.class.getClassLoader(),
                new Class<?>[]{Evaluatable.class},
                new FunctionInvocationHandler(target)
        );
    }
}

class FunctionInvocationHandler implements InvocationHandler {
    private final Evaluatable target;

    public FunctionInvocationHandler(Evaluatable target) {
        this.target = target;
    }

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        long startTime = System.nanoTime();
        System.out.println("Функция: " + ((Evaluatable) target).getExpression());
        System.out.println("Имя метода: " + method.getName());
        System.out.println("Параметры метода: " + Arrays.toString(args));
        Object result = method.invoke(target, args);

        long endTime = System.nanoTime();
        long duration = endTime - startTime;

        System.out.println("Метод " + method.getName() + " выполнен за " + duration + " наносекунд");
        System.out.println("Результат вызова метода: " + result);

        return result;
    }
}
