public class FractionUntil {
    private static int ebob(int num1, int num2) {
        while (num2 != 0) {
            int remainder = num1 % num2;
            num1 = num2;
            num2 = remainder; // Why java do not have '//' operator (<3 Python)
        }
        return num1;
    }

    private static void simplify_and_print(int result_denom, int result_numer) {
        int ebob = ebob(result_denom, result_numer);

        if (ebob == 0) {
            System.out.println(result_numer + ", " + result_denom);
        } else {
            result_denom /= ebob;
            result_numer /= ebob;
            System.out.println(result_numer + ", " + result_denom);
        }
    }

    // As instruction just provided output I assume the function do not have any return value
    public static void simpleFraction(int numer1, int denom1, int numer2, int denom2) {
        int result_denom;
        int result_numer;
        
        result_numer = numer1*denom2 + numer2*denom1; // For finding unsimpled form of the final numerator
        result_denom = denom1*denom2;

        if (result_numer < 0) { // If the user inputs negative number
            System.out.print("-, ");
        }
        simplify_and_print(Math.abs(result_denom), Math.abs(result_numer));
    }

    public static void complexFraction(char sign1, char sign2, int numer1, int denom1, int numer2, int denom2) {
        if ((sign1 != '+' && sign1 != '-') || (sign2 != '+' && sign2 != '-')) {
            System.err.println("Error:\n " + "Invalid parameter exception");
            return;
        }

        int result_denom;
        int result_numer;
        int result_sign1 = (sign1 == '-')? (-1) : 1;
        int result_sign2 = (sign2 == '-')? (-1) : 1;

        result_numer = result_sign1*numer1*denom2 + result_sign2*numer2*denom1; // For finding unsimplified form of the final numerator
        result_denom = denom1*denom2;

        if (result_numer < 0) {
            System.out.print("-, ");
        }
        simplify_and_print(Math.abs(result_denom), Math.abs(result_numer));
    }
}
