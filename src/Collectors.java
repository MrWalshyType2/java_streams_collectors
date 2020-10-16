import java.util.*;
import java.util.stream.IntStream;
import java.util.stream.Stream;

public class Collectors {

    // Collectors transform a Streams elements into a mutable result
    //  - Elements can be accumulated into a collection
    //  - Elements can be grouped by a property
    //  - Elements can be summarised into a single value
    //  - Elements can be partitioned

    // The Collectors class has a list of factory methods for common Collector implementations
    //  - You can also write a custom Collector by implementing the Collectors interface
    public static void main(String[] args) {
        List<String> stringList = Arrays.asList("Hello", "World", "!", "Hello", "", "World");
        System.out.println("Original List: " + stringList);

        Set<String> stringSet = stringList.stream().collect(java.util.stream.Collectors.toSet());
        System.out.println("List as a Set: " + stringSet);

        TreeSet<String> stringTreeSet = stringList.stream()
                .collect(java.util.stream.Collectors.toCollection(TreeSet::new)); // toCollection takes a Supplier
        System.out.println("List as a Tree Set: " + stringTreeSet);

        // The groupingBy collector will group elements within a Stream
        Map<Integer, List<String>> stringsAndLengths = stringList.stream()
                .collect(java.util.stream.Collectors.groupingBy(String::length)); // Func passed to groupingBy defines grouping criteria
        System.out.println("Strings and lengths: " + stringsAndLengths);

        // Elements in a Stream can be concatenated to a String (CharSequence)
        //  - Can optionally provide a separator, prefix and suffix
        String listAsString = stringList.stream().map(String::toUpperCase)
                .distinct()
                .collect(java.util.stream.Collectors.joining(", ", "[", "]"));
        System.out.println("List as a String: "+ listAsString);

        // Element Partitioning is essentially groupingBy, but with two groupds
        //  - In accordance with a given Predicate
        Map<Boolean, List<String>> resultsEmptyTrueOrFalse = stringList.stream()
                .collect(java.util.stream.Collectors.partitioningBy(String::isEmpty));
        System.out.println("Partitioning: " + resultsEmptyTrueOrFalse);

        // Elements in a stream can be counted
        long count = stringList.stream().collect(java.util.stream.Collectors.counting());
        Integer totalLengthOfAllWords = stringList.stream().collect(java.util.stream.Collectors.summingInt(String::length));

        IntSummaryStatistics statistics = stringList.stream().collect(java.util.stream.Collectors.summarizingInt(String::length));
        double avg = statistics.getAverage();
        long count2 = statistics.getCount();
        int max = statistics.getMax();
        int min = statistics.getMin();

    }
}
