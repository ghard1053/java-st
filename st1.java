import java.util.*;
import java.util.stream.*;
import java.util.function.*;

public class Main {
    public static void main(String... args) {
        List<String> list = Arrays.asList("abc", "xyz", "defghi", "a");

        Stream<String> stream1 = list.stream();

        Stream<String> stream2 = stream1.filter(s -> s.contains("a"));

        Stream<String> stream3 = stream2.map(String::toUpperCase);

        stream3.forEach(System.out::println);
    }
}

public class Main2 {
    public static void main(String... args) {
        List<String> list = Arrays.asList("abc", "xyz", "defghi", "a");

        list.stream()
                .filter(s -> s.contains("a"))
                .map(String::toUpperCase)
                .forEach(System.out::Println);
}

Map<String, Integer> map;

map.entrySet().stream().forEach(e -> System.out.println(e.getKey() + ", " + e.getValue()));
map.entrySet().stream().map(Map.Entry::getValue).forEach(System.out::println);


IntSummaryStatistics stat = IntStream.range(0, 20).collect(IntSummaryStatistics::new,
                                                            IntSummaryStatistics::accept,
                                                            IntSummaryStatistics::combine);
System.out.println("%d,%d,%d,%d,%f", stat.getCount(), stat.getSum(), stat.getMin(),
                    stat.getMax(), stat.getAverage());


Stream<String> stream = Arrays.asList("zzz", "aa", "bbb", "x").parallelStream();

int min = stream.mapToInt(String::length).min().orElse(0);


List<Integer> result = stringStream
    .collect(ArrayList::new,
        (partialList, s) -> { partialList.add(s.length()); },
        ArrayList::addAll);

List<Integer> result2 = stringStream.map(String::length).collect(Collectors.toList());

