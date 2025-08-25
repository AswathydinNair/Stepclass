
import java.util.Scanner;

public class BMIcalculator {

    public static String[][] calculateBMIAndStatus(double[][] data) {
        String[][] result = new String[10][4];

        for (int i = 0; i < 10; i++) {
            double weight = data[i][0];
            double heightCm = data[i][1];
            double heightM = heightCm / 100.0;
            double bmi = weight / (heightM * heightM);
            String status;

            if (bmi <= 18.4) {
                status = "Underweight";
            } else if (bmi <= 24.9) {
                status = "Normal";
            } else if (bmi <= 39.9) {
                status = "Overweight";
            } else {
                status = "Obese";
            }

            result[i][0] = String.format("%.1f", heightCm);
            result[i][1] = String.format("%.1f", weight);
            result[i][2] = String.format("%.2f", bmi);
            result[i][3] = status;
        }
        return result;
    }

    public static void displayBMIData(String[][] result) {
        System.out.println("Person\tHeight(cm)\tWeight(kg)\tBMI\t\tStatus");
        for (int i = 0; i < result.length; i++) {
            System.out.printf("%d\t\t%s\t\t%s\t\t%s\t%s\n", 
                (i + 1), result[i][0], result[i][1], result[i][2], result[i][3]);
        }
    }

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        double[][] heightWeightData = new double[10][2];

        for (int i = 0; i < 10; i++) {
            System.out.printf("Person %d:\n", i + 1);
            System.out.print("  Weight (kg): ");
            heightWeightData[i][0] = scanner.nextDouble();
            System.out.print("  Height (cm): ");
            heightWeightData[i][1] = scanner.nextDouble();
        }

        String[][] bmiResults = calculateBMIAndStatus(heightWeightData);
        displayBMIData(bmiResults);
    }
}
