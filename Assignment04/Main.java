package Assignment04;

import java.util.ArrayList;

public class Main {
    public static final double VAT_RATE = 0.18;
    public final String PLATFORM_NAME = "Bina.az Clone";

    public static void main(String[] args) {
        ArrayList<Property> properties = new ArrayList<>();

        // Show case of Upcasting as stated
        Property apt = new ResidentialApartment("RES-001", "Baku, Nasimi", 85.0, 250000.0, 5, 3, true);
        Property comm = new CommercialSpace("COM-102", "Baku, Narimanov", 120.0, 450000.0, "Office", true);
        Property land = new LandPlot("LND-999", "Baku, Mardakan", 500.0, 80000.0, true, "Residential");
        Property vip = new VipProperty("VIP-007", "Baku, Sabail", 200.0, 900000.0, "Top search ranking & dedicated agent", 1500.0);

        // Adding to the list
        properties.add(apt);
        properties.add(comm);
        properties.add(land);
        properties.add(vip);

        for (Property p : properties) {
            System.out.println("========");
            p.displayBasicInfo();
            
            double commission = p.calculateCommission();
            System.out.println("Commission: " + commission + " AZN");

            double baseFinalPrice = p.calculateFinalPrice();
            double finalPriceWithVat = baseFinalPrice + (baseFinalPrice * VAT_RATE);
            System.out.println("Final Price (inc. VAT & fees): " + finalPriceWithVat + " AZN");

            // Showcase of instanceof 
            if (p instanceof MortgageEligible) {
                MortgageEligible me = (MortgageEligible) p;
                System.out.println("Mortgage Eligible: Yes");
                double monthly = me.calculateMonthlyPayment(20, 0.055);
                System.out.println(String.format("Monthly Payment (20 years, 5.5%%): %.2f AZN", monthly));
            }

            if (p instanceof VipListing) {
                VipListing vl = (VipListing) p;
                System.out.println("VIP Fee: " + vl.calculateVipFee() + " AZN");
                System.out.println("VIP Benefits: " + vl.getVipBenefits());
            }
        }
    }
}