// TODO Single-Responsibility principle - классы доставки Pickup, CourierDelivery, AvtoDelivery
//  отвечают за доставку, классы товаров Clothes, FoodProducts предназначены для создания товаров,
//  класс корзины BascetProducts отвечает за корзину.

// TODO Liskov substitution principle - классы товаров Clothes, FoodProducts наследуются от своего логичного предка
//  класса Products

//  TODO Open-closed principle - статический метод в Main, printPriceDelivery принимает наследников интерфейса Deliweri
//   по этому можно добавлять другие разные классы доставки имплементировать интерфейс Deliweri и получать стоимость доставки

//   TODO Interface segregation principle - интерфейсы Card и SMS логически не зависимы и если мне где то в программе
//    потребуется ввести номер телефона но не надо будет привязывать к нему данные своей карты, допустим чтоб указать
//    номер телефона для курьера я смогу это сделать

//    TODO Dependency inversion principle -  создан интерфейс Loger в котором есть метод который будет отвечать за
//     логирование сообщени, а как и куда он будет его логировать указанно уже в классе ConsoleLoger и если мне надо будет
//     поменять место вывода сообщения мне достаточно будет поменять класс реализующий этот интерфейс.

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Main {

    static Scanner scanner = new Scanner(System.in);

    public static void main(String[] args) {
        Loger loger = new ConsoleLoger();
        loger.log("Добро пожаловать в интернет магазин");
        List<Products> list = new ArrayList<>();
        List<BascetProducts> bascet = new ArrayList<>();
        double priceDelivery = 0;

        list.add(new FoodProducts("Продукты", "Рыба", "Форель", 200, "Русское море"));
        list.add(new FoodProducts("Продукты", "Сыр", "Молоко, закваска", 500, "Ферма"));
        list.add(new FoodProducts("Продукты", "Хлеб", "Мука, Дрозжы", 50, "Батончик"));
        list.add(new Clothes("Одежда", "Халат", "Хлопок", 2000, "Ивановский хлопок"));
        list.add(new Clothes("Одежда", "Платье", "Лен", 3000, "Ивановский лен"));

        while (true) {
            loger.log("\nВыберите действие:\n" +
                    "1. Добавить товары в корзину\n" +
                    "2. Показать добавленные товары\n" +
                    "3. Удалить из корзины\n" +
                    "4. Расчитать стоимость доставки\n" +
                    "5. Показать итоговую цену с учетом доставки\n" +
                    "6. Оплатить товары\n");
            int number;
            try {
                String num = scanner.nextLine();
                number = Integer.parseInt(num);
            } catch (NumberFormatException e) {
                loger.log("Вы ввели буквы, а не число");
                continue;
            }

            switch (number) {
                case 1:
                    printProduct(list);
                    loger.log("Введите номер позиции и колличество, для возврата в предыдущее меню нажмите end");
                    while (true) {
                        String input = scanner.nextLine();
                        if (input.equals("end")) {
                            break;
                        }
                        String[] parst = input.split(" ");
                        int num = Integer.parseInt(parst[0]) - 1;
                        Products pr = list.get(num);
                        int quantity = Integer.parseInt(parst[1]);
                        boolean result = false;
                        if (bascet.isEmpty()) {
                            bascet.add(new BascetProducts(pr, quantity));
                        } else {
                            //TODO Магические числа
                            for (int i = 0; i < bascet.size(); i++) {
                                if (pr.name.equals(bascet.get(i).products.name)) {
                                    bascet.get(i).quantity += quantity;
                                    result = true;
                                    break;
                                }
                            }
                            if (result == false) {
                                bascet.add(new BascetProducts(pr, quantity));
                            }
                        }
                    }
                    break;
                case 2:
                    loger.log("Ваша корзина: ");
                    bascet.forEach(System.out::println);
                    //TODO DRY
                    loger.log("Сумма к оплате: " + summPriseProduct(bascet));
                    break;
                case 3:
                    loger.log("В корзину добавленны следующие элементы: \n" +
                            "Выберите позицию для удаления и укажите колличество, для возврата в предыдущее меню нажмите end ");
                    //TODO Магические числа
                    for (int i = 0; i < bascet.size(); i++) {
                        System.out.println((i + 1) + ". " + bascet.get(i).products.name + " - " + bascet.get(i).quantity + " шт. ");
                    }
                    while (true) {
                        String inp = scanner.nextLine();
                        if (inp.equals("end")) {
                            break;
                        }
                        String[] pars = inp.split(" ");
                        int num1 = Integer.parseInt(pars[0]) - 1;
                        Products pr1 = bascet.get(num1).products;
                        int quantity1 = Integer.parseInt(pars[1]);
                        //TODO Магические числа
                        for (int i = 0; i < bascet.size(); i++) {
                            if (pr1.name.equals(bascet.get(i).products.name)) {
                                bascet.get(i).quantity -= quantity1;
                            }
                            if (bascet.get(i).quantity == 0) {
                                bascet.remove(i);
                            }
                        }
                    }
                    break;
                case 4:
                    Delivery delivery;
                    loger.log("Выберете способ доставки\n" +
                            "1. Автодоставка\n" +
                            "2. Курьерская доставка\n" +
                            "3. Самовывоз");
                    String deli = scanner.nextLine();
                    int deliver = Integer.parseInt(deli);
                    if (deliver == 3) {
                        delivery = new Pickup();
                        //TODO DRY
                        priceDelivery = printPriceDelivery(delivery, "самовывоза", 0);
                    } else {
                        loger.log("Укажите растояние от нашего склада до вас в километрах");
                        String kilometers = scanner.nextLine();
                        int km = Integer.parseInt(kilometers);
                        if (deliver == 1) {
                            delivery = new AvtoDelivery();
                            //TODO DRY
                            priceDelivery = printPriceDelivery(delivery, "доставки", km);
                        } else if (deliver == 2) {
                            delivery = new CourierDelivery();
                            //TODO DRY
                            priceDelivery = printPriceDelivery(delivery, "доставки", km);
                        }
                    }
                    break;
                case 5:
                    //TODO DRY
                    loger.log("Итого к оплате с учетом доставки: " +
                            (priceDelivery + summPriseProduct(bascet)) + " руб.");
                    break;
                case 6:
                    loger.log("Введитье данные банковской карты");
                    Card card = () -> System.out.println("Идет обработка\n" +
                            "Данные карты прниняты");
                    card.dataCard();
                    loger.log("Для отправки электронного чека введите номер телефона");
                    String numberTelephon = scanner.nextLine();
                    SMS sms = (a) -> System.out.println("Чек отправлен на номер " + a);
                    sms.sms(numberTelephon);
                    loger.log("Спасибо за заказ!!!");
                    return;
                default:
                    System.out.println("Нет такого числа в списке");
            }
        }
    }

    public static void printProduct(List<Products> list) {
        System.out.println("Доступные товары: ");
        //TODO Магические числа
        for (int i = 0; i < list.size(); i++) {
            System.out.println((i + 1) + ". " + list.get(i));
        }
    }

    //TODO DRY
    public static double printPriceDelivery(Delivery delivery, String txt, int km) {
        double price = delivery.deliveryPrice(km);
        System.out.println("Цена " + txt + ": " + price + " руб.");
        return price;
    }

    //TODO DRY
    public static double summPriseProduct(List<BascetProducts> bascet) {
        double sum = 0;
        for (BascetProducts bascetProducts : bascet) {
            sum += bascetProducts.quantity * bascetProducts.products.price;
        }
        return sum;
    }
}
