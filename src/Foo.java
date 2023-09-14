import java.util.concurrent.Semaphore;

public class Foo {
    private final Semaphore sem1;
    private final Semaphore sem2;

    public Foo() {
        sem1 = new Semaphore(1);
        sem2 = new Semaphore(1);

        try {
            // Устанавливаем начальные разрешения для семафоров
            sem1.acquire();
            sem2.acquire();
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt(); // Протоколируем прерывание
            // Далее можно решить, следует ли продолжать выполнение или бросить исключение
            // Например, можно бросить исключение и передать управление выше
            throw new RuntimeException("Исключение при инициализации", e);
        }
    }

    public void first() {
        try {
            // Выполняем действия для метода first()
            System.out.print("first");
        } finally {
            // Освобождаем семафор для метода second()
            sem1.release();
        }
    }

    public void second() {
        try {
            // Ждем, пока метод first() выполнится
            sem1.acquire();
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt(); // Протоколируем прерывание
            // Далее можно решить, следует ли продолжать выполнение или бросить исключение
            // Например, можно бросить исключение и передать управление выше
            throw new RuntimeException("Исключение при выполнении second()", e);
        }

        try {
            // Выполняем действия для метода second()
            System.out.print("second");
        } finally {
            // Освобождаем семафор для метода third()
            sem2.release();
        }
    }

    public void third() {
        try {
            // Ждем, пока метод second() выполнится
            sem2.acquire();
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt(); // Протоколируем прерывание
            // Далее можно решить, следует ли продолжать выполнение или бросить исключение
            // Например, можно бросить исключение и передать управление выше
            throw new RuntimeException("Исключение при выполнении third()", e);
        }

        try {
            // Выполняем действия для метода third()
            System.out.print("third");
        } finally {
            // Освобождаем семафор для завершения
            sem1.release();
            sem2.release();
        }
    }
}


/*
Этот код представляет собой класс `Foo`, содержащий методы `first()`, `second()` и `third()`,
которые должны быть выполнены последовательно в определенном порядке.
Класс использует семафоры для синхронизации выполнения методов.

    import java.util.concurrent.Semaphore;
Этот оператор импортирует класс `Semaphore` из пакета `java.util.concurrent`.
Семафоры являются средством синхронизации потоков в Java.


    public class Foo {
Это объявление класса `Foo`, который является оберткой для трех методов.


    private final Semaphore sem1;
    private final Semaphore sem2;
Это объявление двух переменных-семафоров `sem1` и `sem2`,
которые будут использоваться для синхронизации потоков.


    public Foo() {
        sem1 = new Semaphore(1);
        sem2 = new Semaphore(1);
Этот конструктор класса инициализирует оба семафора.
Значение `1` передается в качестве аргумента конструктора, указывая,
что каждый семафор будет иметь только одно разрешение (первоначально свободно).


        try {
            sem1.acquire();
            sem2.acquire();
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            throw new RuntimeException("Исключение при инициализации", e);
        }
В блоке `try-catch` мы захватываем семафоры `sem1` и `sem2`.
Но в данном случае, так как начальный счетчик каждого семафора равен 1
и изначально семафоры захвачены, выполнение потока будет приостановлено до тех пор,
пока разрешение не будет освобождено. Если в процессе захвата семафора возникает
исключение `InterruptedException`, то прерывание потока устанавливается
и выбрасывается исключение `RuntimeException`.


        public void first() {
            try {
                System.out.print("first");
            } finally {
                sem1.release();
            }
        }
Метод `first()` выводит строку "first" и затем освобождает семафор `sem1`,
позволяя выполнить метод `second()`.


        public void second() {
            try {
                sem1.acquire();
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
                throw new RuntimeException("Исключение при выполнении second()", e);
            }

            try {
                System.out.print("second");
            } finally {
                sem2.release();
            }
        }

Метод `second()` сначала захватывает семафор `sem1`.
Если при захвате семафора возникает исключение `InterruptedException`,
то прерывание потока устанавливается и выбрасывается исключение `RuntimeException`.
Затем метод выводит строку "second" и освобождает семафор `sem2`,
позволяя выполнить метод `third()`.


        public void third() {
            try {
                sem2.acquire();
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
                throw new RuntimeException("Исключение при выполнении third()", e);
            }

            try {
                System.out.print("third");
            } finally {
                sem1.release();
                sem2.release();
            }
        }
Метод `third()` сначала захватывает семафор `sem2`. Если при захвате семафора возникает
исключение `InterruptedException`, то прерывание потока устанавливается
и выбрасывается исключение `RuntimeException`. Затем метод выводит строку "third"
и освобождает оба семафора `sem1` и `sem2`, завершая выполнение.

Подводя итог, этот код реализует класс `Foo`, который обеспечивает
последовательное выполнение методов `first()`, `second()` и `third()`
при помощи семафоров. Каждый метод будет ожидать освобождения соответствующего
семафора перед выполнением действий.
*/
