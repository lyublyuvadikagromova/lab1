import java.lang.reflect.*;
import java.util.Arrays;

class FunctionNotFoundException extends Exception {
    public FunctionNotFoundException(String message) {
        super(message);
    }
}

interface Evaluatable {
    double evaluate(double x);
    double evaluate(double x, int n);
    String getExpression();
}

class TestClass implements Evaluatable {
    private double a;

    public TestClass(double a) {
        this.a = a;
    }

    public double getA() {
        return this.a;
    }

    @Override
    public double evaluate(double x) {
        return Math.exp(-Math.abs(a) * x) * Math.sin(x);
    }

    @Override
    public double evaluate(double x, int n) {
        return Math.exp(-Math.abs(a) * x) * Math.sin(x) * n;
    }

    @Override
    public String getExpression() {
        return "Exp(-|" + a + "| * x) * sin(x) * n";
    }
}

public class Main {
    public static void main(String[] args) throws Exception {
        TestClass obj = new TestClass(2.5);
        double x = 1.0;
        int n = 2;
        System.out.println("[a=" + obj.getA() + ", " + obj.getExpression() + "]");
        System.out.println(invokeMethod(obj, "evaluate", new Object[]{x}));
        System.out.println(invokeMethod(obj, "evaluate", new Object[]{x, n}));
    }

    public static Object invokeMethod(Object obj, String methodName, Object[] params) throws FunctionNotFoundException {
        try {
            Class<?>[] paramTypes = new Class[params.length];
            for (int i = 0; i < params.length; i++) {
                if (params[i] instanceof Double) {
                    paramTypes[i] = Double.TYPE;
                } else if (params[i] instanceof Integer) {
                    paramTypes[i] = Integer.TYPE;
                } else {
                    paramTypes[i] = params[i].getClass();
                }
            }
            Method method = obj.getClass().getMethod(methodName, paramTypes);
            System.out.println("Типи: " + Arrays.toString(paramTypes) + ", значення: " + Arrays.toString(params));
            Object result = method.invoke(obj, params);
            return "Результат виклику: " + result;
        } catch (NoSuchMethodException | IllegalAccessException | InvocationTargetException e) {
            throw new FunctionNotFoundException("Method " + methodName + " not found");
        }
    }
}
