package Assignment04;

public class VipProperty extends Property implements VipListing {
    private String vipBenefits;
    private double vipFee;

    public VipProperty(String propertyId, String location, double area, double price, String vipBenefits, double vipFee) {
        // Constructor chaining using super
        super(propertyId, location, area, price);
        this.vipBenefits = vipBenefits;
        this.vipFee = vipFee;
    }

    @Override
    public double calculateCommission() {
        return this.price * 0.05; // Example: 5% commission for VIP properties
    }

    @Override
    public double calculateFinalPrice() {
        return this.price + calculateCommission() + calculateVipFee();
    }

    @Override
    public double calculateVipFee() {
        return this.vipFee;
    }

    @Override
    public String getVipBenefits() {
        return this.vipBenefits;
    }

    @Override
    public void displayBasicInfo() {
        super.displayBasicInfo();
        System.out.println("VIP Benefits: " + getVipBenefits() + " | VIP Fee: " + calculateVipFee() + " AZN");
    }
}