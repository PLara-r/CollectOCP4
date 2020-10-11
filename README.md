# CollectOCP4

1)Сбор с использованием группировки, разбиения (пример в репо)
Теперь предположим, что мы хотим получить группы имен по их длине. Мы можем сделать это, сказав, что мы хотим сгруппировать по длине:
Stream<String> ohMy = Stream.of("lions", "tigers", "bears");
Map<Integer, List<String>> map = ohMy.collect( 
  Collectors.groupingBy(String::length));
System.out.println(map); // {5=[lions, bears], 6=[tigers]}
groupingBy()Коллектор говорит , collect()что он должен сгруппировать все элементы потока в списки, организуя их функции при условии. Это делает ключи на карте значением функции, а значения - результатом функции.

Разделение - это особый случай группировки. С разделением есть только две возможные группы - истина и ложь. Partitioningэто как разбить список на две части.
Предположим, что мы делаем знак, чтобы выставить вне выставки каждого животного. У нас есть два размера знаков. Можно разместить имена с пятью или менее символами. Другой нужен для более длинных имен. Мы можем разбить список по признаку, который нам нужен:
Stream<String> ohMy = Stream.of("lions", "tigers", "bears");
Map<Boolean, List<String>> map = ohMy.collect(  
 Collectors.partitioningBy(s -> s.length() <= 5));
System.out.println(map); // {false=[tigers], true=[lions, bears]}
Здесь мы передали Predicateлогику, для которой группе принадлежит каждое имя животного. Теперь предположим, что мы выяснили, как использовать другой шрифт, и теперь семь символов могут поместиться на меньшем знаке. Не беспокойся. Мы просто изменим Predicate:
Stream<String> ohMy = Stream.of("lions", "tigers", "bears");
Map<Boolean, List<String>> map = ohMy.collect(
   Collectors.partitioningBy(s -> s.length() <= 7));
System.out.println(map); // {false=[], true=[lions, tigers, bears]}
Обратите внимание, что на карте все еще есть два ключа - по одному для каждого booleanзначения. Случается, что одно из значений является пустым списком, но оно все еще там. Как и в случае groupingBy(), мы можем изменить тип Listна что-то другое:


2)Сбор результатов

 Последняя тема основана на том, что вы узнали до сих пор, чтобы сгруппировать результаты. В начале главы вы видели работу collect()терминала. Существует много предопределенных сборщиков, в том числе показанных в таблице 4.11 . Мы рассмотрим различные типы коллекторов в следующих разделах.
Таблица 4.11. Примеры группирования / разбиения коллекторов
Коллектор
Collector	Описание	Возвращаемое значение при передаче в collect
averagingDouble(ToDoubleFunction f)
 averagingInt(ToIntFunction f)
averagingLong(ToLongFunction f)	Рассчитывает среднее для наших трех основных типов примитивов	Double
counting()	Подсчитывает количество элементов	Long
groupingBy(Function f)
groupingBy(Function f,  Collector dc)
 groupingBy(Function f,  Supplier s, Collector dc)	Создает группировку карт по указанной функции с необязательным типом и необязательным(optional ) нижестоящим коллектором(collector ) 	Map<K, List<T>>
joining()
 joining(CharSequence cs)	Создает одиночное String использование csв качестве разделителя между элементами, если он указан	String
maxBy(Comparator c)
minBy(Comparator c)	Находит самые большие / самые маленькие элементы	Optional<T>
mapping(Function f,  Collector dc)	Добавляет еще один уровень collectors	Collector
partitioningBy(Predicate p)
partitioningBy(Predicate p,  Collector dc)	Создает группу карт по указанному предикату с необязательным дополнительным нижестоящим коллектором(collector )	Map<Boolean, List<T>>
summarizingDouble(ToDoubleFunction f) 
summarizingInt(ToIntFunction f)
summarizingLong(ToLongFunction f)	Вычисляет среднее, минимальное, максимальное и т. Д.	DoubleSummaryStatistics 
IntSummaryStatistics 
LongSummaryStatistics
toList() toSet()	Создает произвольный тип списка или набора
(list or set )	Double 
Integer
 Long
toCollection(Supplier s)	Создает Collection указанный тип	Collection
toMap(Function k, Function v)
toMap(Function k, Function v, BinaryOperator m)
 toMap(Function k, Function v, BinaryOperator m,  Supplier s)	Создает карту, используя функции для сопоставления ключей, значений, необязательной функции слияния (optional merge function )и необязательного(optional) типа.	Map
		

Сбор с использованием основных коллекционеров

К счастью, многие из этих коллекционеров работают одинаково. Давайте посмотрим на пример:
Stream<String> ohMy = Stream.of("lions", "tigers", "bears");
String result = ohMy.collect(Collectors.joining(", "));
System.out.println(result); // lions, tigers, bears
Обратите внимание, что предопределенные сборщики находятся в Collectorsклассе, а не в Collectorклассе. Это общая тема, которую вы видели с Collectionпротив Collections. Мы передаем предопределенный joining()коллектор в collect()метод. Все элементы потока затем объединяются вString
Это очень важно , чтобы пройти Collectorк collectметоду. Он существует, чтобы помочь собирать элементы. А Collector ничего не делает сам по себе.
Давайте попробуем еще один. Какова средняя длина трех названий животных?

Stream<String> ohMy = Stream.of("lions", "tigers", "bears");
Double result = ohMy.collect(Collectors.averagingInt(String::length));
System.out.println(result); // 5.333333333333333
Шаблон тот же. Мы передаем коллектор, collect()и он выполняет среднее для нас. На этот раз нам нужно было передать функцию, чтобы сообщить коллекционеру, что нужно усреднить. Мы использовали ссылку на метод, который возвращает intпосле выполнения. В примитивных потоках результат усреднения всегда был равен a double, независимо от того, какой тип усредняется. Для коллекционеров это Doubleтак какObject
Часто вы обнаруживаете, что взаимодействуете с кодом, написанным до Java 8. Это означает, что он будет ожидать Collectionтип, а не Streamтип. Нет проблем. Вы все еще можете выразить себя, используя, Streamа затем преобразовать Collectionв конец, например:
Stream<String> ohMy = Stream.of("lions", "tigers", "bears");
TreeSet<String> result = ohMy.filter(s -> s.startsWith("t")   .collect(Collectors.toCollection(TreeSet::new));
System.out.println(result); // [tigers]
На этот раз у нас есть все три части потокового конвейера. Stream.of()является источником для потока. Промежуточная операция есть filter(). Наконец, операция терминала collect(), которая создает TreeSet. Если бы нам было все равно, какой инструмент Setмы получили, мы могли бы написатьCollectors.toSet()
На данный момент, вы должны быть в состоянии использовать все Collectorsв таблице 4.11 , за исключением groupingBy(), mapping(), partitioningBy()и toMap().
  Сбор в карты
Коллекторный код с картами может стать длинным. Мы будем строить это медленно. Убедитесь, что вы понимаете каждый пример, прежде чем переходить к следующему. Давайте начнем с простого примера создания карты из потока:
Stream<String> ohMy = Stream.of("lions", "tigers", "bears");
Map<String, Integer> map = ohMy.collect( 
  Collectors.toMap(s -> s, String::length));
System.out.println(map); // {lions=5, bears=5, tigers=6}
При создании карты необходимо указать две функции. Первая функция сообщает сборщику, как создать ключ. В нашем примере мы используем предоставленный Stringключ. Вторая функция сообщает сборщику, как создать значение. В нашем примере мы используем длинуString
Возвращение того же значения, переданного в лямбду, является обычной операцией, поэтому Java предоставляет метод для этого. Вы можете переписать s -> sкак Function.identity(). Оно не короче и может быть, а может и не быть более четким, поэтому примите решение о его использовании.
Теперь мы хотим сделать обратное и сопоставить длину имени животного с самим именем. Наша первая неправильная попытка показана здесь:
Stream<String> ohMy = Stream.of("lions", "tigers", "bears");
Map<Integer, String> map = ohMy.collect(Collectors.toMap(String::length, k -> k)); // BAD
Запуск этого дает исключение, подобное следующему:
Exception in thread "main" java.lang.IllegalStateException: Duplicate key lions  
  at java.util.stream.Collectors.lambda$throwingMerger$114(Collectors.java:133)   
 at java.util.stream.Collectors$$Lambda$3/1044036744.apply(Unknown Source)
Что не так? Два имени животных имеют одинаковую длину. Мы не говорили Java, что делать. Должен ли коллекционер выбрать первый, с которым он сталкивается? Последний, с которым он сталкивается? Объединить два? Поскольку сборщик понятия не имеет, что делать, он «решает» проблему, выбрасывая исключение и делая его нашей проблемой. Как продуманно. Давайте предположим, что наше требование - создать запятую Stringс именами животных. Мы могли бы написать это:
Stream<String> ohMy = Stream.of("lions", "tigers", "bears");
Map<Integer, String> map = ohMy.collect(Collectors.toMap(  
  String::length, k -> k, (s1, s2) -> s1 + "," + s2));
System.out.println(map);  // {5=lions,bears, 6=tigers}
System.out.println(map.getClass());  // class. java.util.HashMap
Так получилось, что Mapвозвращённым является HashMap. Такое поведение не гарантируется. Предположим, что мы хотим, чтобы код возвращал TreeMapвместо. Нет проблем. Мы просто добавим ссылку на конструктор в качестве параметра:
Stream<String> ohMy = Stream.of("lions", "tigers", "bears");
TreeMap<Integer, String> map = ohMy.collect(Collectors.toMap( 
  String::length, k -> k, (s1, s2) -> s1 + "," + s2, TreeMap::new));
System.out.println(map); // // {5=lions,bears, 6=tigers}
System.out.println(map.getClass());  // class. java.util.TreeMap
На этот раз мы получили тип, который мы указали. 
