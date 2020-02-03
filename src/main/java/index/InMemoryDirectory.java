package index;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class InMemoryDirectory implements IndexableDirectory {

    private static final String WORD_SEPARATOR = " ";
    private static int READ_CHUNK_SIZE = 1024;

    private Map<String, Map<String, String>> filesWordMap = new HashMap<>();

    public void loadIndexableDirectory(File[] files) {

        for (File file : files) {
            try (FileReader reader = new FileReader(file);
                BufferedReader br = new BufferedReader(reader, READ_CHUNK_SIZE)) {
                Map<String, String> wordsMap = new HashMap<>();
                String text;
                while ((text = br.readLine()) != null) {
                    final String[] words = text.split(WORD_SEPARATOR);
                    for (String word : words) {
                        wordsMap.put(word.replaceAll("\\s+", ""), word);
                    }
                }
                filesWordMap.put(file.getName(), wordsMap);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public Map<String, Map<String, String>> getFilesWordMap() {
        return filesWordMap;
    }
}
