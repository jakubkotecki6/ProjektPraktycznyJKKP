package pl.sda.projektPraktyczny;

import pl.sda.projektPraktyczny.models.Category;
import pl.sda.projektPraktyczny.models.Order;
import pl.sda.projektPraktyczny.models.OrderStatus;
import pl.sda.projektPraktyczny.models.Product;
import pl.sda.projektPraktyczny.services.CategoryService;
import pl.sda.projektPraktyczny.services.OrderService;
import pl.sda.projektPraktyczny.services.ProductService;

import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

import static org.apache.commons.lang3.StringUtils.isNumeric;

public class Menu extends OrderService {
    private static final Scanner scanner = new Scanner(System.in);
    private static final OrderService orderService = new OrderService();
    private static final CategoryService categoryService = new CategoryService();
    private static final ProductService productService = new ProductService();



    public static void main(String[] args) {
        OrderService orderService = new OrderService();
        CategoryService categoryService = new CategoryService();
        ProductService productService = new ProductService();

        orderService.loadOrdersFromFile();
        categoryService.loadCategoriesFromFile();
        productService.loadProductsFromFile();

        mainMenu();
    }

    private static void mainMenu() {
        System.out.println("""
                Wybierz
                 [1] Zamówienia
                 [2] Kategorie produktów
                 [3] Produkty
                 [0] Exit""");

        switch (pickingNumber(3)) {
            case 0 -> {
                OrderService.generateOrdersList();
                CategoryService.generateCategoriesList();
                ProductService.generateProductsList();
            }
            case 1 -> orderMenu();
            case 2 -> categoryMenu();
            case 3 -> productMenu();
        }
    }

    private static void orderMenu() {
        int pick;
        do {
            System.out.println("""
                    Zamówienia
                    \t [1] Lista zamówień
                    \t [2] Konkretne zamówienie
                    \t [3] Dodaj zamówienie
                    \t [4] Usuń zamówienie
                    \t [5] Edytuj zamówienie
                    \t [6] Zmień status zamówienia changeStatus(CREATED)
                    \t [7] Pokaż status zamówienia
                    \t [8] Dodaj produkt do zamówienia addProductToOrder(productId, orderId, quantity)
                    \t [0] Cofnij""");

            pick = pickingNumber(8);
            switch (pick) {
                case 0 -> mainMenu();
                case 1 -> orderService.showAllOrders();
                case 2 -> System.out.println(orderService.showOrderByOrderNumber(getOrderNumber()));
                case 3 -> orderService.addOrder(getOrder());
                case 4 -> whichRemove();
                case 5 -> System.out.println("""
                        Pole, pole, łyse pole, ale mam już plan.
                        Pomalutku, bez pośpiechu wszystko zrobię sam.
                        Nad makietą się męczyłem ładnych parę lat,
                        Ale za to zwiedzać cudo będzie cały świat""");
                case 6 -> orderService.changeStatusByOrderNumber(getOrderNumber(), getOrderStatus());
                case 7 -> orderService.showOrderStatusByOrderNumber(getOrderNumber());
                case 8 -> addProductsToOrder();
            }
        } while (pick != 0);

    }

    private static void whichRemove() {
        int pick;
        do {
            System.out.println("""
                    Jak chcesz usunąć obiekt
                     [1] Obiekt Order
                     [2] Numer zamówienia
                     [3] ID zamówienia
                     [0] Cofnij""");

            pick = pickingNumber(3);
            switch (pick) {
                case 0 -> orderMenu();
                case 1 -> orderService.removeOrder(getOrder());
                case 2 -> orderService.removeOrderByOrderNumber(getOrderNumber());
                case 3 -> orderService.removeOrderByOrderId(getOrderId());
            }
        } while (pick != 0);

    }

    private static void categoryMenu() {
        int pick;
        do {
            System.out.println("""
                    Kategorie produktów
                    \t [1] Lista kategorii
                    \t [2] Konkretna kategoria
                    \t [3] Dodaj kategorie
                    \t [4] Usuń kategorie
                    \t [0] Cofnij""");

            pick = pickingNumber(4);
            switch (pick) {
                case 0 -> mainMenu();
                case 1 -> categoryService.showAllCategories();
                case 2 -> categoryService.showCategoryById(getCategoryId());
                case 3 -> categoryService.addCategory(getCategory());
                case 4 -> categoryService.removeCategory(getCategory());
            }
        } while (pick != 0);

    }

    private static void productMenu() {
        int pick;
        do {
            System.out.println("""
                    Produkty
                    \t [1] Lista produktów
                    \t [2] Konkretny produkt
                    \t [3] Dodaj produkt
                    \t [4] Usuń produkt
                    \t [0] Cofnij""");

            pick = pickingNumber(3);
            switch (pick) {
                case 0 -> mainMenu();
                case 1 -> productService.showAllProducts();
                case 2 -> productService.showProductById(getProductId());
                case 3 -> productService.addProduct(getProduct());
                case 4 -> productService.removeProduct(getProduct());
            }
        } while (pick != 0);

    }


    private static Order getOrder() {
        System.out.println("Wpisz dane do zamówienia");
        return new Order(getOrderSum(), getFirstName(), getSurname(), getAdress(), getOrderStatus(), setProductsMap());
    }

    private static double getOrderSum() {
        System.out.print("Wpisz wartość zamówienia: ");
        return getDoubleValue();
    }

    private static String getFirstName() {
        System.out.print("Wpisz imię klienta: ");
        return getStringValue();
    }

    private static String getSurname() {
        System.out.print("Wpisz nazwisko klienta: ");
        return getStringValue();
    }

    private static String getAdress() {
        System.out.print("Wpisz Adres: ");
        return getStringValue();
    }

    private static Product getProduct() {
        System.out.println("Wpisz dane produktu: ");
        return new Product(getPrice(), getProductName(), getCategory(), getAmountOfProducts());
    }

    private static double getPrice() {
        System.out.print("Wpisz cenę: ");
        return getDoubleValue();
    }

    private static String getProductName() {
        System.out.print("Wpisz nazwę produktu: ");
        return getStringValue();
    }

    private static Category getCategory() {
        System.out.print("Wpisz nazwę kategorii: ");
        return new Category(getStringValue());
    }

    private static int getAmountOfProducts() {
        System.out.print("Wpisz ile masz sztuk produktu: ");
        return getIntValue();
    }

    private static int getOrderId() {
        System.out.print("Podaj ID zamówienia: ");
        return getIntValue();
    }

    private static int getOrderNumber() {
        System.out.print("Podaj numer zamówienia: ");
        return getIntValue();
    }

    private static int getProductId() {
        System.out.print("Podaj numer produktu: ");
        return getIntValue();
    }

    private static int getCategoryId() {
        System.out.print("Podaj numer kategorii: ");
        return getIntValue();
    }

    private static Map<Product, Integer> setProductsMap() {
        Map<Product, Integer> products = new HashMap<>();
        int addOrFinish;
        do {
            System.out.println("aby dodać produkt do mapy wpisz 1 jeśli chcesz zakończyć dodawanie produktów wpisz 0");
            addOrFinish = scanner.nextInt();
            if (addOrFinish == 1) {
                products.put(getProduct(), getAmountOfProducts());
            }
            if (addOrFinish == 0) {
                return products;
            }
        } while (addOrFinish == 1);
        return products;
    }

    private static Map<Product, Integer> addProductsToOrder() {
        orderService.showOrderByOrderNumber(getOrderNumber()).setProducts(setProductsMap());
        return null;
    }

    private static OrderStatus getOrderStatus() {
        System.out.println("""
                Statusy
                \t [0] OPLACONE
                \t [1] ANULOWANE
                \t [2] WYSLANE
                \t [3] W_PRZYGOTOWANIU""");

        return switch (pickingNumber(3)) {
            case 0 -> OrderStatus.OPLACONE;
            case 1 -> OrderStatus.ANULOWANE;
            case 2 -> OrderStatus.WYSLANE;
            case 3 -> OrderStatus.W_PRZYGOTOWANIU;
            default -> null;
        };
    }

    private static int pickingNumber(int numberOfOptions) {
        int pick = -1;
        String input;
        do {
            try {
                input = scanner.next();
                if (isNumeric(input)) {
                    pick = Integer.parseInt(input);
                }
                if (Integer.parseInt(input) < 0 || Integer.parseInt(input) > numberOfOptions) {
                    System.out.println("podaj liczbę w zakresie od 0 do " + numberOfOptions);
                }
            } catch (NumberFormatException e) {
                System.out.println("podaj liczbę w zakresie od 0 do " + numberOfOptions);
            }

        } while (pick < 0 || pick > numberOfOptions);
        return pick;
    }

    private static int getIntValue() {
        int number = 0;
        String input = null;
        do {
            try {
                input = scanner.next();
                if (isNumeric(input)) {
                    number = Integer.parseInt(input);
                }
            } catch (NumberFormatException e) {
                System.out.println("podaj liczbę!");
            }

        } while (!isNumeric(input));
        return number;
    }

    private static double getDoubleValue() {
        double number = 0;
        String input = null;
        do {
            try {
                input = scanner.next();
                if (isNumeric(input)) {
                    number = Double.parseDouble(input);
                }
            } catch (NumberFormatException e) {
                System.out.println("podaj liczbę!");
            }
        } while (!isNumeric(input));
        return number;
    }

    private static String getStringValue() {
        String word = null;
        String input;
        do {
            input = scanner.next();
            if (!input.isBlank()) {
                word = input;
            }
        } while (input.isBlank());
        return word;
    }
}
