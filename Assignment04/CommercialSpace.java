package Assignment04;

public class CommercialSpace extends Property{
    private String businessType;
    private boolean hasParking;

    public CommercialSpace(String propertyId, String location, double area, double price, String businessType,
            boolean hasParking) {
        super(propertyId, location, area, price);
        this.businessType = businessType;
        this.hasParking = hasParking;
    }

    @Override
    public double calculateCommission() {
        return this.price * 0.035d;
    }

    @Override
    double calculateFinalPrice() {
        return this.price + this.calculateCommission() + 500d;
    }

    @Override
    public void displayBasicInfo() {
        super.displayBasicInfo();
        System.out.println("Business Type: " + this.businessType
            + " | Parking: " + this.hasParking 
        );
    }
}
