package Assignment04;

public interface MortgageEligible {
    boolean isEligibleForMortgage();

    double calculateMonthlyPayment(int years, double interestRate);
}
