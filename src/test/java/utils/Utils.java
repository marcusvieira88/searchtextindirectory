package utils;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.io.OutputStream;
import java.util.Comparator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Map.Entry;
import java.util.stream.Collectors;

public class Utils {

    private static final String LINE_SEPARATOR = System.getProperty("line.separator");

    public static void assertFileSearchOutput(Map<String, Float> files, OutputStream os) {

        String logMessage = "";

        final Map<String, Float> sortedMap = files.entrySet().stream()
            .sorted(Entry.comparingByKey())
            .sorted(Entry.comparingByValue(Comparator.reverseOrder()))
            .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue,
                (oldValue, newValue) -> oldValue, LinkedHashMap::new));

        for (Map.Entry<String, Float> entry : sortedMap.entrySet()) {
            logMessage = logMessage.concat(entry.getKey()).concat(":")
                .concat(String.format(Locale.ROOT, "%.2f", entry.getValue())).concat("%").concat(LINE_SEPARATOR);
        }
        assertEquals(logMessage, os.toString());
    }

    public static void assertTextOutput(String text, OutputStream os) {
        assertEquals(text.concat(LINE_SEPARATOR), os.toString());
    }

    public static void assertTextOutput(List<String> texts, OutputStream os) {
        String logMessage = "";

        for (int i = 0; i < texts.size(); i++) {
            logMessage = logMessage.concat(texts.get(i));
            if (i < texts.size() - 1) {
                logMessage = logMessage.concat(LINE_SEPARATOR);
            }
        }
        assertEquals(logMessage, os.toString());
    }
}
