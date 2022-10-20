public class CourierDelivery implements Delivery {
    public final double priceOneKilometer = 50;

    @Override
    public double deliveryPrice(int kilometers) {
        return priceOneKilometer * kilometers;
    }
}
