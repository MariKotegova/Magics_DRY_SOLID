import java.util.List;

public abstract class Products  {
    public String type;
    public String name;
    public String structure;
    public double price;
    public String provider;
   // public int quantity;


    public String toString(){
        return "Наименование: " + name + " Цена: " + price + "руб.";
    }
}
