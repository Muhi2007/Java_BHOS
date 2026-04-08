package Assignment04;

public abstract class Property {
    protected String propertyId;
    protected String location;
    protected double area;
    protected double price;
    protected final String PLATFORM_NAME = "Emlak.az";

    public Property(String propertyId, String location, double area, double price) {
        this.propertyId = propertyId;
        this.location = location;
        this.area = area;
        this.price = price;
    }

    public void displayBasicInfo() {
        System.out.println("PROPERTY: " + this.propertyId
            + "\nLocation: " + this.location
            + "\nArea: " + this.area + " | Price: " + this.price
        );
    }

    public void setPrice(double price) throws Exception {
        if (price < 0) {
            throw new Exception("Error: Price cannot be negative");
        } else {
            this.price = price;
        }
    }

    public abstract double calculateCommission();

    abstract double calculateFinalPrice();
}
