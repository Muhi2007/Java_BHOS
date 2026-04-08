package Assignment04;

// Implenets keyword added as the example output is showing mortgage eligibility
public class ResidentialApartment extends Property implements MortgageEligible{
    private int floor;
    private int numberOfRooms;
    private boolean hasElevator;

    public ResidentialApartment(String propertyId, String location, double area, double price, 
            int floor, int numberOfRooms, boolean hasElevator) {
        super(propertyId, location, area, price);
        this.floor = floor;
        this.numberOfRooms = numberOfRooms;
        this.hasElevator = hasElevator;
    }

    @Override
    public double calculateCommission() {
        return this.price * 0.02d;
    }

    @Override
    public double calculateFinalPrice() {
        return this.price + this.calculateCommission();
    }

    @Override
    public void displayBasicInfo() {
        super.displayBasicInfo();
        System.out.println("Floor: " + this.floor +" | Room: " + this.numberOfRooms + " | Elevator: " + this.hasElevator);
    }

    @Override
    public boolean isEligibleForMortgage() {
        return true;
    }

    @Override
    public double calculateMonthlyPayment(int years, double interestRate) {
        if (this.isEligibleForMortgage()) {
            // The rest of the formulas are determined by AI as I'm not an expert in this field
            double principal = this.calculateFinalPrice();
            double monthlyInterestRate = interestRate / 12.0;
            int totalMonths = years * 12;

            double mathPower = Math.pow(1 + monthlyInterestRate, totalMonths);
            double monthlyPayment = (principal * monthlyInterestRate * mathPower) / (mathPower - 1);

            return monthlyPayment;


        } else {
            return 0d;
        }
    }
}
