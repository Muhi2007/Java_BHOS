import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner scan = new Scanner(System.in);

        System.out.println("Input: ");
        String input = scan.nextLine();
        scan.close();

        String[] arr = input.split(","); // For converting the input into string array

        try{
            // strip() for letting user input as shown in the examples
            if (arr.length == 4) { // For 1st description
                int denom1 = Integer.valueOf(arr[1].strip());
                int denom2 = Integer.valueOf(arr[3].strip());
                int numer1 = Integer.valueOf(arr[0].strip());
                int numer2 = Integer.valueOf(arr[2].strip());
                FractionUntil.simpleFraction(numer1, denom1, numer2, denom2);
            } else if (arr.length == 6) { // For 2nd description
                char sign1 = arr[0].strip().charAt(0); // For matching the type as well as preventing any error
                char sign2 = arr[3].strip().charAt(0);

                int denom1 = Integer.valueOf(arr[2].strip());
                int denom2 = Integer.valueOf(arr[5].strip());
                int numer1 = Integer.valueOf(arr[1].strip());
                int numer2 = Integer.valueOf(arr[4].strip());

                FractionUntil.complexFraction(sign1, sign2, numer1, denom1, numer2, denom2);
            } else {
                System.err.println("Error:\n " + "Invalid number of arguments");
                return;
            }
        }catch(NumberFormatException e) {
            System.err.println("Error:\n " + e.getMessage());
            return;
        }
    }
}