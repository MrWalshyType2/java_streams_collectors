import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.Stream;

public class Streams {

    public static void main(String[] args) {
        // Streams use declarative programming
        //  - program what you want to get, not how you want to get it

        // Streams have two categories of operation:
        //  - Intermediate operations (return a stream instance)
        //  - Terminal operations (return a non stream instance, closes the stream and executes pipeline ops)
        List<String> stringList = Arrays.asList("hello", "world", "bello", "whatcha", "yellow", "mello");

        // Using a stream to iterate over the collection, logic is done by the building blocks
        List<String> stringListWithLL = stringList.stream()
                .filter(s -> s.contains("ll")) // filter() takes a predicate, skips every non-compliant element
                .sorted(Comparator.comparing(String::length))
                .collect(Collectors.toList()); // Collector to collect the stream output into a list
        System.out.println("Original List: " + stringList);
        System.out.println("Words containing ll: " + stringListWithLL);

        List<String> stringListLimited = stringList.stream()
                .filter(s -> s.contains("o"))
                .limit(1).collect(Collectors.toList());
        System.out.println("Words containing o, limited to 1: " + stringListLimited);

        List<String> skippedTwoValuesList = stringList.stream()
                .skip(2).collect(Collectors.toList());
        System.out.println("Skipped two values: " + skippedTwoValuesList);

        // The map method can be used to get 'subsets' of info from a Stream
        //  - Accepts a function param
        //  - Stream type changes
        List<Integer> wordLengthsList = stringList.stream().map(String::length).collect(Collectors.toList());
        System.out.println("Lengths of words: " + wordLengthsList);

        // The distinct method can ensure we don't have duplicates
        List<Integer> wordLengthListNoRepeats = stringList.stream()
                .map(String::length)
                .distinct()
                .collect(Collectors.toList());
        System.out.println("Length of words, no repeats: " + wordLengthListNoRepeats);

        // The takeWhile method will a process stream elements until one fails the predicate
        //  - filter method does not stop processing when an element fails the predicate
        //  - Java 9 method
        //  - dropWhile is the opposite

        // The peek method can be used to look at a Stream element during execution without modification
        //  - non-interfering Stream operation
        List<Integer> wordLengthsListTimesTwoWithPeek = stringList.stream().map(String::length)
                .peek(x -> System.out.printf("Word length normal: %d", x))
                .map(x -> x * 2)
                .peek(x -> System.out.printf("Word length doubled: %d", x))
                .collect(Collectors.toList());
        System.out.println("\nWord lengths doubled with peek: " + wordLengthsListTimesTwoWithPeek);

        // Finding elements
        //  - findFirst and findAny return a single element from a Stream
        //  - return value is an instance of Optional as the element may not exist
        Optional<Integer> optionalSingleElementGreaterThanLengthFive = stringList.stream().map(String::length)
                .filter(x -> x > 5)
                .findFirst();
        if (optionalSingleElementGreaterThanLengthFive.isPresent()) {}
        optionalSingleElementGreaterThanLengthFive.ifPresent(System.out::println); // takes a Consumer
        optionalSingleElementGreaterThanLengthFive.get(); // NoSuchElementException if empty
        Integer otherwise = optionalSingleElementGreaterThanLengthFive.orElse(6);
        System.out.println("findFirst where element length greater than 5: " + optionalSingleElementGreaterThanLengthFive);

        // Streams can also be obtained from a file
        try {
            Stream<String> lines = Files.lines(Paths.get("test.txt"), Charset.defaultCharset());
            System.out.println(lines.collect(Collectors.joining("\n")));
        } catch (IOException e) {
            e.printStackTrace();
        }

        // Streams exist for primitive types
        //  - IntStream, DoubleStream, LongStream
        IntStream intStream = IntStream.of(1, 43, 23, 2);
        System.out.println("Int stream: " + intStream.toString());
        IntStream intRange = IntStream.range(1, 1024);

        // The forEach operation can be used to consume every element in a Stream
        //  - This is a terminal operation
        intStream.forEach(System.out::println);
    }
}
