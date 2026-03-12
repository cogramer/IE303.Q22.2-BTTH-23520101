import java.util.Random;

public class PiApproximation {
    public static double approximatePi() {
        int totalPoints = 10_000_000;
        int insideCircle = 0;
        Random random = new Random();

        for (int i = 0; i < totalPoints; i++) {
            double x = random.nextDouble() * 2 - 1;
            double y = random.nextDouble() * 2 - 1;
            
            if (x * x + y * y <= 1) {
                insideCircle++;
            }
        }

        return 4.0 * insideCircle / totalPoints;
    }

    public static void main(String[] args) {
        double pi = approximatePi();
        System.out.printf("Pi xap xi = %.6f%n", pi);
        System.out.printf("Pi thuc te = %.6f%n", Math.PI);
        System.out.printf("Sai so = %.6f%n", Math.abs(pi - Math.PI));
    }
}
