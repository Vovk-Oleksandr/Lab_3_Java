import java.util.concurrent.*;
import java.util.*;git init
// клас для обчислення факторіалу
class FactorialTask implements Callable<Long> {
    private final int number;

    // конструктор для передачі числа
    public FactorialTask(int number) {
        this.number = number;
    }

    @Override
    public Long call() throws Exception {
        long result = 1;
        for (int i = 2; i <= number; i++) {
            result *= i; // обчислення факторіалу
        }
        System.out.println("Факторіал " + number + " обчислено: " + result);
        return result;
    }
}

public class FactorialCalculator {
    public static void main(String[] args) {
        // створюємо пул потоків
        ExecutorService executor = Executors.newFixedThreadPool(4);

        // масив чисел, для яких обчислюємо факторіали
        int[] numbers = {5, 7, 10, 12, 15};

        // список для збереження Future об'єктів
        List<Future<Long>> futures = new ArrayList<>();

        // передаємо задачі у пул потоків
        for (int number : numbers) {
            FactorialTask task = new FactorialTask(number);
            Future<Long> future = executor.submit(task); // передаємо задачу
            futures.add(future); // додаємо Future до списку
        }

        // отримуємо результати обчислень
        System.out.println("Результати факторіалів:");
        for (int i = 0; i < numbers.length; i++) {
            try {
                Long result = futures.get(i).get(); // очікуємо завершення задачі
                System.out.println(numbers[i] + "! = " + result);
            } catch (InterruptedException | ExecutionException e) {
                System.out.println("Помилка під час обчислення факторіалу для " + numbers[i]);
            }
        }

        // завершуємо роботу пулу потоків
        executor.shutdown();
    }
}
