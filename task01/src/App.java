package sdf.task01.src;

import java.io.Console;
import java.text.DecimalFormat;
import java.util.Scanner;

public class App 
{
    public static void main( String[] args )
    {
        System.out.println("Welcome!");

        Console console = System.console();

        String firstInput = "";
        double $last = 0;

        while (!firstInput.equals("exit")) {

            // scan input
            String input = console.readLine(">").replaceAll("[\\p{Punct}&&[^-+*/$.]]", firstInput);

            String[] array = input.strip().split(" ");
            if (array.length == 0 || array.length == 2 || array.length > 3) {
                System.out.println("Type input in this format: number space operator space number");
                continue;
            }

            Scanner scan = new Scanner(input);
            
            // input firstInput
            firstInput = scan.next().strip();
            if (firstInput.equals("exit")) {
                break;
            }

            // first number
            double firstNum;

            if (firstInput.equals("$last")) {
                firstNum = $last;
            } else {
                try {
                    firstNum = Double.parseDouble(firstInput);
                } catch (NumberFormatException e) {
                    System.out.println("Type input in this format: number space operator space number");
                    continue;
                }
            }

            // operator
            String operator = scan.next().strip();
            if (!operator.matches("[-+*/]")) {
                System.out.println("Type input in this format: number space operator space number");
                System.out.println("Operators can only be: - + * /");
                continue;
            }

            // second number
            String thirdInput = scan.next().strip();
            double secondNum;

            if (thirdInput.equals("$last")) {
                secondNum = $last;
            } else {
                try {
                    secondNum = Double.parseDouble(thirdInput);
                } catch (NumberFormatException e) {
                    System.out.println("Type input in this format: number space operator space number");
                    continue;
                }
            }

            // calculator
            switch (operator) {
                
                case "+":
                    $last = firstNum + secondNum;
                break;

                case "-":
                    $last = firstNum - secondNum;
                break;

                case "/":
                    $last = firstNum / secondNum;
                break;

                case "*":
                    $last = firstNum * secondNum;
                break;
            }

            DecimalFormat df = new DecimalFormat("0.###");
            System.out.printf("%s\n", df.format($last));

            scan.close();
        }

        System.out.println("Bye bye!");
    }
}
