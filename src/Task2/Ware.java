// клас, що представляє склад
class Warehouse {
    private int stock;

    // конструктор для встановлення початкової кількості товару
    public Warehouse(int stock) {
        this.stock = stock;
    }

    // синхронізований метод для продажу товарів
    public synchronized void sell(String cashierName) {
        if (stock > 0) {
            System.out.println(cashierName + " почав продаж товару. Залишок на складі: " + stock);
            stock--;
            try {
                // моделюємо час продажу
                Thread.sleep((long) (Math.random() * 2000 + 1000)); // 1-3 секунди
            } catch (InterruptedException e) {
                System.out.println(cashierName + " був перерваний під час продажу.");
            }
            System.out.println(cashierName + " завершив продаж. Залишок на складі: " + stock);
        } else {
            System.out.println(cashierName + " не зміг продати товар. Склад порожній.");
        }
    }
}

// клас, що представляє касира
class Seller implements Runnable {
    private Warehouse warehouse;
    private String cashierName;

    // конструктор для встановлення складу та імені касира
    public Seller(Warehouse warehouse, String cashierName) {
        this.warehouse = warehouse;
        this.cashierName = cashierName;
    }

    @Override
    public void run() {
        for (int i = 0; i < 5; i++) { // кожен касир намагається продати 5 товарів
            warehouse.sell(cashierName);
        }
    }
}

public class Ware {
    public static void main(String[] args) {
        // створюємо склад із 10 товарами
        Warehouse warehouse = new Warehouse(10);

        // створюємо потоки для трьох касирів
        Thread seller1 = new Thread(new Seller(warehouse, "Касир 1"));
        Thread seller2 = new Thread(new Seller(warehouse, "Касир 2"));
        Thread seller3 = new Thread(new Seller(warehouse, "Касир 3"));

        // запускаємо потоки
        seller1.start();
        seller2.start();
        seller3.start();
    }
}
