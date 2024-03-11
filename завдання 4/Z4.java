import java.lang.reflect.Array;
import java.util.Arrays;
import java.util.Random;

public class Z4 {

    private static final Random random = new Random();

    public static <T> T[] createArray(Class<T> type, int size) {
        T[] array = (T[]) Array.newInstance(type, size);
        return array;
    }

    public static <T> T[][] createMatrix(Class<T> type, int rows, int cols) {
        T[][] matrix = (T[][]) Array.newInstance(type, rows, cols);
        return matrix;
    }

    public static <T> String arrayToString(T[] array) {
        return Arrays.toString(array);
    }

    public static <T> String matrixToString(T[][] matrix) {
        StringBuilder result = new StringBuilder("{");
        for (T[] row : matrix) {
            if (result.length() > 1) {
                result.append(", ");
            }
            result.append(arrayToString(row));
        }
        result.append("}");
        return result.toString();
    }

    public static <T> T[] resizeArray(T[] array, int newSize) {
        T[] newArray = Arrays.copyOf(array, newSize);
        Arrays.fill(newArray, array.length, newSize, generateRandomValue(array.getClass().getComponentType()));
        return newArray;
    }

    public static <T> T[][] resizeMatrix(T[][] matrix, int newRows, int newCols) {
        T[][] newMatrix = Arrays.copyOf(matrix, newRows);
        for (int i = matrix.length; i < newRows; i++) {
            newMatrix[i] = createArray((Class<T>) matrix.getClass().getComponentType().getComponentType(), newCols);
            Arrays.fill(newMatrix[i], 0);
        }
        for (int i = 0; i < Math.min(matrix.length, newRows); i++) {
            newMatrix[i] = Arrays.copyOf(matrix[i], newCols);
            if (newCols > matrix[i].length) {
                Arrays.fill(newMatrix[i], matrix[i].length, newCols, (T) Integer.valueOf(0));
            }
        }
        return newMatrix;
    }

    private static <T> T generateRandomValue(Class<T> type) {
        if (type.equals(int.class) || type.equals(Integer.class)) {
            return (T) Integer.valueOf(random.nextInt(100));
        } else if (type.equals(double.class) || type.equals(Double.class)) {
            return (T) Double.valueOf(random.nextDouble() * 100);
        } else {
            return null;
        }
    }


    public static void main(String[] args) {
        

        String[] strArray1D = createArray(String.class, 3);
        System.out.println("java.lang.String[3] = " + arrayToString(strArray1D));
        Integer[] intArray1D = createArray(Integer.class, 5);
        System.out.println("java.lang.String[5] = " + arrayToString(intArray1D));

        Integer[][] intMatrix1 = createMatrix(Integer.class, 3, 5);
        for (int i = 0; i < intMatrix1.length; i++) {
            for (int j = 0; j < intMatrix1[i].length; j++) {
                intMatrix1[i][j] = generateRandomValue(Integer.class);
            }
        }
        System.out.println("int[3][5] = " + matrixToString(intMatrix1));

        Integer[][] intMatrix2 = resizeMatrix(intMatrix1, 4, 6);
        System.out.println("int[4][6] = " + matrixToString(intMatrix2));

        Integer[][] intMatrix3 = resizeMatrix(intMatrix1, 3, 7);
        System.out.println("int[3][7] = " + matrixToString(intMatrix3));

        Integer[][] intMatrix4 = resizeMatrix(intMatrix1, 2, 3);
        System.out.println("int[2][3] = " + matrixToString(intMatrix4));
    }
}























