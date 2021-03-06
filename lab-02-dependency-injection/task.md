# Упражнение 02 - Dependency Injection

Напишем систему по автоматическому внедрению зависимостей (Dependency Injection), будем использовать рефлексию,
загрузчики классов. Проект будем собирать билд-системой Gradle, чтобы иметь возможность подключать внешние библиотеки
и использовать фреймворк для написания юнит-тестов.

## Условие 

Теорию по паттерну Dependency Injection (далее - DI) можно начать изучать по статье из [Википедии][1].
DI даёт возможность "инвертировать управление" (inversion of control, [IoC][3]).

В Java на уровне стандартизации языка был предложен набор аннотаций, которые будут описывать как должен действовать 
DI-фреймворк - [JSR 330][2] - `@Inject`, `@Singleton`, `@Named`, ... Сама реализация DI-фреймворка не стандартизирована,
таких реализаций существует много с различными принципами работы.

Мы попробуем реализовать базовый функционал DI самостоятельно, используя runtime-рефлексию.

1. Поддержка аннотации `@Inject`. Такой аннотацией может быть помечен конструктор.
 В таком случае зависимостями класса будут являться аргументы его конструктора. И, соответственно, создаваться объекты
 данного класса с зависимостями будут через такой конструктор.

2. Поддержка аннотации `@Singleton`. Такой аннотацией может быть помечен сам класс, у которого есть 
 `@Inject`-конструктор. Если такая аннотация присутствует, то такой класс должен создаваться один раз и иметь только
 один экземпляр. Этот экземпляр должен предоставляться каждому зависимому классу, в отличие от не-`@Singleton` классов,
 которые могут создаваться на каждый отдельный запрос зависимости.

#### Сценарий работы

##### 1. Подготовка графа зависимостей
1. DI-фреймворк должен знать о всех классах, которые собираются участвовать в DI (в нашем базовом случае - это наличие 
`@Inject`-конструктора). Каким образом это будет происходить - решайте сами. 
В некоторых реализациях используется явная императивная регистрация в коде вида
```java
myDi.register(MyClass1.class);
myDi.register(MyClass2.class);
myDi.register(MyInterface.class, MyImplementation.class);
```
При таком подходе вам на вход приходит готовый `Class<?>`, с которым можно оперировать, используя Reflection API:
инспектировать его поля/методы, запрашивать аннотации и список конструкторов. Создавать его экземпляры с помощью
выбранного конструктора и т. п.

В других используется разметка в файлах ресурсов, например в XML (напр. фреймворк Spring, платформа IntelliJ). 
Файлы можно хранить как ресурсы Java программы, т. е. в директории `resources`. 
Обратиться к ним можно, например, через вызов `ClassLoader.getSystemClassLoader().getResourceAsStream("my-file.xml")`.
[Тутор по ресурсам][5].
При использовании такого подхода нам на вход будет приходить строка с полным именем класса.
Чтобы получить `Class<?>`, необходимо использовать `ClassLoader` или вызов `Class.forName()`.

2. Далее DI-фреймворк строит граф зависимостей классов друг от друга в том или ином виде,
напр. алогоритмом топологической сортировки.

##### 2. Рабочее состояние

Мы завершили построение графа (для варианта с регистрацией в коде хорошо обозначить это явно вызовом метода 
`myDi.completeRegistration()`, после которого дальнейшая регистрация не допускается). Далее мы хотим использовать 
созданные классы с предоставленными зависимостями.
Можно использовать построенный граф несколькими способами (выбрать способ или предложить и обосновать другой 
так же можете сами):

- Паттерн "Service Locator" ([почитать][4]). Можно запрашивать любой класс из графа вызовами вида 
```java
MyClass1 m1 = myDi.resolve(MyClass1.class);  // returns an instance of MyClass1
MyClass1 m2 = myDi.resolve(MyClass1.class);  // returns another instance of MyClass1, and the same if MyClass1 is @Singleton.
MyInterface i = myDi.resolve(MyInterface.class);  // returns an instance of MyImplementation
```
 Таким образом у графа есть точка входа в каждом классе.
- Явное использование точек входа. Например, в графе обязан быть единственный класс, реализующий интерфейс 
`MyDiEntryPoint`, который можно будет получить `MyDiEntryPoint entry = myDi.obtainEntryPoint(); entry.run()`. Тогда у
 графа будет одна точка входа, получить объекты других классов напрямую не получится.

### Требования и советы к реализации
- Создайте проект Gradle через IntelliJ Idea. При модификации файлов build.gradle не забывайте делать sync в IDE, чтобы
подтянуть новые библиотеки и зависимости проекта.
- Вам будет необходимо подключить внешнюю библиотеку, реализующую `JSR-330`. Для этого пропишите 
 `api 'javax.inject:javax.inject:1'` в блок `dependencies {}` в `build.gradle`. Отличие `api` от `implementation` 
 разберём отдельно позже.
- Напишите тесты используя framework JUnit4/JUnit5, обзор по нему был на паре + найдите доки в интернете. Так как мы
 пишем только фреймворк, и никак его не используем, то протестировать его как раз можно в `src/test/**`. Так же можете
 попробовать подход TDD, и написать тесты заранее. Спроектируйте интерфейс (буквально `interface`) вашего фреймворка,
 не пишите реализацию, а напишите тесты, которые будут оперировать с `interface` и ожидать корректной работы. Так вы
 сразу будете думать над тем, что вы хотите от вашей программы, а потом сядете реализовывать это. Если не получится
 следовать такому подходу - ничего страшного, ~~не так уж много у кого получается~~, но попробовать стоит.
- Внимательно обрабатывайте крайние случаи работы DI-фреймворка, например, несколько `@Inject`-конструкторов 
 (а вы поддерживаете только один), приватный `@Inject`-конструктор (а должен быть `public`), регистрация класса без 
 нужного конструктора вообще, и т. п. - нужно выдавать понятные сообщения об ошибках. Задание составлено в более 
 абстрактной форме, чем предыдущее, так что у вас больше свободы и больше ответственности. Придётся самим организовывать
 и придумывать классы.
- Важно понять идею того, что от вас хотят - написать DI-фреймворк, который мы с вами в дальнейшем сможем использовать;
 любые пробелы в задании стоит попробовать заполнить самостоятельно исходя из здравого смысла - это важный навык. Любое
 обоснованное решение будет принято.

[1]: https://en.wikipedia.org/wiki/Dependency_injection
[2]: https://jcp.org/en/jsr/detail?id=330
[3]: https://en.wikipedia.org/wiki/Inversion_of_control
[4]: https://en.wikipedia.org/wiki/Service_locator_pattern
[5]: https://www.amitph.com/java-read-file-from-resources-folder/

### Дополнительные задания aka дополнительный функционал вашего DI-фреймворка

"comin soon"