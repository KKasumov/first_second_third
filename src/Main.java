public class Main {
    public static void main(String[] args) {
        Foo foo = new Foo();

        Thread threadA = new Thread(foo::first);
        Thread threadB = new Thread(foo::second);
        Thread threadC = new Thread(foo::third);

        threadA.start();
        threadB.start();
        threadC.start();
    }
}



/*
Этот код содержит класс `Main`, в котором есть метод `main()`.


public class Main {
    public static void main(String[] args) {
Это объявление класса `Main` и его метода `main()`, который является точкой входа в программу.


        Foo foo = new Foo();
Здесь создается объект класса `Foo` с именем `foo`.


        Thread threadA = new Thread(foo::first);
        Thread threadB = new Thread(foo::second);
        Thread threadC = new Thread(foo::third);

В этих строках создаются три объекта класса `Thread` с именами `threadA`,
`threadB` и `threadC`. Конструктор `Thread` принимает ссылку на метод класса `Foo`
(`foo::first`, `foo::second` и `foo::third`) в качестве исполняемого кода для каждого потока.


        threadA.start();
        threadB.start();
        threadC.start();
Здесь запускаются три потока, вызывая метод `start()` для каждого объекта `Thread`.
Это начинает выполнение кода в каждом потоке параллельно.

Итак, в результате выполнения этого кода, три потока будут
выполнять методы `first()`, `second()` и `third()` объекта `foo` из класса `Foo` параллельно.
 */