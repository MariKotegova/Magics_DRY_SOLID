public class BascetProducts {
    int quantity;
    Products products;

    public BascetProducts(Products pr, int quantity) {
        this.products = pr;
        this.quantity = quantity;
    }

    @Override
    public String toString() {
        return products.name + " - " + quantity + " шт. цена за единицу: "
                + products.price + "руб.  итого: " + (quantity * products.price) + " руб.";
    }


}
