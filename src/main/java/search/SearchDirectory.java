package search;

import index.IndexableDirectory;
import java.util.Arrays;
import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Locale;
import java.util.Map;
import java.util.Set;

public class SearchDirectory {

    private static final int MAX_MATCHED_FILES_LIST_SIZE = 10;
    private static final String WORD_SEPARATOR = " ";

    private IndexableDirectory indexableDirectory;

    public SearchDirectory(IndexableDirectory indexableDirectory) {
        this.indexableDirectory = indexableDirectory;
    }

    public void searchText(String inputToSearch) {
        final Map<String, Float> matchedFiles = new HashMap<>();
        final Set<String> inputWords = new HashSet<>(Arrays.asList(inputToSearch.split(WORD_SEPARATOR)));

        float searchWeightPerWord = 100f / inputWords.size();

        final Map<String, Map<String, String>> filesWordMap = indexableDirectory.getFilesWordMap();
        filesWordMap.forEach((fileName, words) ->
            matchedFiles.putAll(searchWordsInFile(inputWords, searchWeightPerWord, fileName, words)));

        if (matchedFiles.isEmpty()) {
            System.out.println("no matches found");
        } else {
            matchedFiles.entrySet().stream()
                .sorted(Map.Entry.comparingByKey())
                .sorted(Map.Entry.comparingByValue(Comparator.reverseOrder()))
                .limit(MAX_MATCHED_FILES_LIST_SIZE)
                .forEach((i) -> System.out
                    .println(String.format(Locale.ROOT, "%s:%.2f%%", i.getKey(), i.getValue())));
        }
    }

    private Map<String, Float> searchWordsInFile(Set<String> inputWords, float valuePerWord, String fileName,
        Map<String, String> wordsMap) {
        final Map<String, Float> matchedFiles = new HashMap<>();

        for (String inputWord : inputWords) {
            if (wordsMap.get(inputWord) != null) {
                matchedFiles.compute(fileName, (k, v) -> v == null ? valuePerWord : v + valuePerWord);
            }
        }
        return matchedFiles;
    }
}
