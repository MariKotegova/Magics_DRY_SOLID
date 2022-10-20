public class AvtoDelivery implements Delivery {
    public final double priceOneKilometer = 100;

    @Override
    public double deliveryPrice(int kilometers) {
        return priceOneKilometer * kilometers;
    }
}
