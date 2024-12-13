// клас, що представляє потік каси
class Cashier implements Runnable {
    private String cashierName;

    // конструктор для встановлення імені касира
    public Cashier(String cashierName) {
        this.cashierName = cashierName;
    }

    @Override
    public void run() {
        System.out.println(cashierName + " почав обслуговування клієнта.");
        try {
            // моделюємо час обслуговування клієнта
            Thread.sleep((long) (Math.random() * 2000 + 1000)); // 1-3 секунди
        } catch (InterruptedException e) {
            System.out.println(cashierName + " був перерваний.");
        }
        System.out.println(cashierName + " завершив обслуговування клієнта.");
    }
}

public class Supermarket {
    public static void main(String[] args) {
        // створюємо кілька потоків для кас
        Thread cashier1 = new Thread(new Cashier("Каса 1"));
        Thread cashier2 = new Thread(new Cashier("Каса 2"));
        Thread cashier3 = new Thread(new Cashier("Каса 3"));

        // запускаємо потоки
        cashier1.start();
        cashier2.start();
        cashier3.start();
    }
}
