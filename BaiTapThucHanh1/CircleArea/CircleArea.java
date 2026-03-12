import java.util.Random;
import java.util.Scanner;

public class CircleArea {
    public static double approximateArea(double r) {
        int totalPoints = 1_000_000;
        int insideCircle = 0;
        Random random = new Random();

        for (int i = 0; i < totalPoints; i++) {
            double x = (random.nextDouble() * 2 - 1) * r;
            double y = (random.nextDouble() * 2 - 1) * r;

            if (x * y + y * y <= r * r) {
                insideCircle++;
            }
        }

        double squareArea = 4 * r * r;
        return squareArea * ((double) insideCircle / totalPoints);
    }

    public static void main(String[] args) {
        Scanner input = new Scanner(System.in);
        System.out.print("Nhap ban kinh: ");
        double r = input.nextDouble();
        double area = approximateArea(r);
        input.close();
        System.out.printf("Ban kinh r = %.1f%n", r);
        System.out.printf("Dien tich xap xi = %.4f%n", area);
        System.out.printf("Dien tich thuc te = %.4f%n", Math.PI * r * r);
    }
}
