
import java.util.Comparator;
import java.util.List;
import java.util.Map;

import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static java.util.stream.Collectors.groupingBy;


public class Main {
    public static void main(String[] args) {
        //Сбор с использованием группировки, разбиения 
        //Теперь предположим, что мы хотим получить группы имен по их длине.
        // Мы можем сделать это, сказав, что мы хотим сгруппировать по длине:
    Stream<String> ohMy = Stream.of("lions", "tigers", "bears");
    Map<Integer, List<String>> map = ohMy.collect(
            groupingBy(String::length));
System.out.println(map); // {5=[lions, bears], 6=[tigers]}

        //groupingBy()Коллектор говорит , collect()что он должен сгруппировать
        // все элементы потока в списки, организуя их функции при условии.
        // Это делает ключи на карте значением функции, а значения - результатом функции.


        //Разделение - это особый случай группировки. С разделением есть только две возможные группы - истина и ложь.
        // Partitioningэто как разбить список на две части.
        //Предположим, что мы делаем знак, чтобы выставить вне выставки каждого животного.
        // У нас есть два размера знаков. Можно разместить имена с пятью или менее символами.
        // Другой нужен для более длинных имен. Мы можем разбить список по признаку, который нам нужен:
        //Здесь мы передали Predicateлогику, для которой группе принадлежит каждое имя животного.
        Stream<String> ohMy1 = Stream.of("lions", "tigers", "bears");
        Map<Boolean, List<String>> map1 = ohMy1.collect(
                Collectors.partitioningBy(s -> s.length() <= 5));
        System.out.println(map1); // {false=[tigers], true=[lions, bears]}



    }
}
