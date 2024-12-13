import java.util.concurrent.*;

// клас для перевірки чисел на простоту
class PrimeChecker implements Callable<Boolean> {
    private final int number;

    // конструктор для встановлення числа
    public PrimeChecker(int number) {
        this.number = number;
    }

    @Override
    public Boolean call() {
        if (number < 2) return false; // числа менше 2 не є простими
        for (int i = 2; i <= Math.sqrt(number); i++) {
            if (number % i == 0) {
                return false; // знайдено дільник, число не є простим
            }
        }
        return true; // число просте
    }

    public int getNumber() {
        return number;
    }
}

public class PrimeNumberExecutor {
    public static void main(String[] args) {
        // створюємо пул потоків із 4 потоками
        ExecutorService executor = Executors.newFixedThreadPool(4);

        // масив чисел для перевірки
        int[] numbers = {29, 15, 7, 23, 12, 17, 9, 4, 19, 11};

        // потокобезпечна колекція для збереження результатів
        ConcurrentHashMap<Integer, Boolean> results = new ConcurrentHashMap<>();

        // список завдань для виконання
        for (int number : numbers) {
            PrimeChecker task = new PrimeChecker(number);
            executor.submit(() -> {
                boolean isPrime = task.call();
                results.put(task.getNumber(), isPrime); // зберігаємо результат у колекцію
            });
        }

        // завершуємо роботу пулу потоків
        executor.shutdown();
        try {
            // очікуємо завершення виконання всіх завдань
            if (!executor.awaitTermination(10, TimeUnit.SECONDS)) {
                executor.shutdownNow();
            }
        } catch (InterruptedException e) {
            executor.shutdownNow();
        }

        // виводимо результати
        System.out.println("Результати перевірки на простоту:");
        results.forEach((number, isPrime) -> {
            System.out.println(number + " - " + (isPrime ? "просте" : "непросте"));
        });
    }
}
