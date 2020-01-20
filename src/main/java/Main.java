import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class Main {
    public static void main(String[] args) throws FileNotFoundException {
        BufferedReader reader = new BufferedReader(new FileReader(new File("src/words.txt")));
        Map<Character, List<String>> wordsMap = reader.lines()
                .filter(s -> !s.isEmpty())
                .collect(Collectors.groupingBy(s -> s.charAt(0)));
        List<String> collect = wordsMap.values().stream()
                .flatMap(List::stream)
                .distinct()
                .filter(s -> isConcatenated(s, wordsMap))
                .sorted(Comparator.comparingInt(String::length).reversed())
                .collect(Collectors.toList());
        System.out.println(collect.get(0));
        System.out.println(collect.get(1));
        System.out.println(collect.size());
    }

    private static boolean isConcatenated(String word, Map<Character, List<String>> wordsMap) {
        return getWords(word, wordsMap).stream()
                .anyMatch(prefix -> !word.equals(prefix) && word.startsWith(prefix)
                        && isConcatenatedInner(word.substring(prefix.length()), wordsMap));
    }

    private static boolean isConcatenatedInner(String word, Map<Character, List<String>> wordsMap) {
        return getWords(word, wordsMap).stream()
                .anyMatch(prefix -> prefix.equals(word) || word.startsWith(prefix)
                        && isConcatenatedInner(word.substring(prefix.length()), wordsMap));
    }

    private static List<String> getWords(String word, Map<Character, List<String>> wordsMap) {
        return wordsMap.getOrDefault(word.charAt(0), Collections.emptyList());
    }
}
