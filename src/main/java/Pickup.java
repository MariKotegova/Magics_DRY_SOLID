public class Pickup implements Delivery {
    public final double pricePickup = 50;

    @Override
    public double deliveryPrice(int kilometers) {
        return pricePickup;
    }
}
