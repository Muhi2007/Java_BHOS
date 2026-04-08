package Assignment04;

public class LandPlot extends Property{
    private boolean isZonedForConstruction;
    private String landCategory;

    public LandPlot(String propertyId, String location, double area, double price, boolean isZonedForConstruction,
            String landCategory) {
        super(propertyId, location, area, price);
        this.isZonedForConstruction = isZonedForConstruction;
        this.landCategory = landCategory;
    }

    @Override
    public double calculateCommission() {
        return this.price * 0.015d;
    }

    @Override
    double calculateFinalPrice() {
        return this.price + this.calculateCommission();
    }

    
}
