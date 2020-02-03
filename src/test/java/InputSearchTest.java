import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import static utils.Utils.assertTextOutput;

import index.InMemoryDirectory;
import index.IndexableDirectory;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.OutputStream;
import java.io.PrintStream;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.NoSuchElementException;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import search.InputSearch;

public class InputSearchTest {

    private static IndexableDirectory indexableDirectory;
    private ByteArrayInputStream in;
    private static OutputStream os;

    @BeforeAll
    public static void mockSearchFiles() {

        Map<String, Map<String, String>> filesWordMap = new HashMap<>();

        filesWordMap.put("file1.txt", Map.of("word1", "word1",
            "word2", "word2",
            "word3", "word3"));
        filesWordMap.put("file2.txt", Map.of("word1", "word1", "word3", "word3"));
        filesWordMap.put("file3.txt", Map.of("word3", "word3", "word4", "word4"));

        indexableDirectory = mock(InMemoryDirectory.class);
        when(indexableDirectory.getFilesWordMap()).thenReturn(filesWordMap);
    }

    @BeforeEach
    public void redirectOutput() {
        os = new ByteArrayOutputStream();
        System.setOut(new PrintStream(os));
    }

    @Test
    public void shouldThrowIExceptionWhenDirectoryPathNull() {
        assertThrows(IllegalArgumentException.class,
            () -> new InputSearch(indexableDirectory).startSearchInDirectory(null));
    }

    @Test
    public void shouldThrowIExceptionWhenDirectoryEmptyPath() {
        assertThrows(IllegalArgumentException.class,
            () -> new InputSearch(indexableDirectory).startSearchInDirectory(new String[]{}));
    }

    @Test
    public void shouldThrowIExceptionWhenEmptyDirectory() {
        assertThrows(IllegalArgumentException.class,
            () -> new InputSearch(indexableDirectory).startSearchInDirectory(new String[]{"./files/emptyDirectory"}));
    }

    @Test
    public void shouldFindWordThatExistsInFiles() {
        provideInputParam("word1");
        assertThrows(NoSuchElementException.class,
            () -> new InputSearch(indexableDirectory).startSearchInDirectory(new String[]{"./files"}));

        List<String> texts = List.of(
            "11 files read in directory ./files",
            "search> file1.txt:100.00%",
            "file2.txt:100.00%",
            "search> ");
        assertTextOutput(texts, os);
    }

    @Test
    public void shouldNotFindWordThatNotExistsInFiles() {
        provideInputParam("word1676");
        assertThrows(NoSuchElementException.class,
            () -> new InputSearch(indexableDirectory).startSearchInDirectory(new String[]{"./files"}));

        List<String> texts = List.of(
            "11 files read in directory ./files",
            "search> no matches found",
            "search> ");
        assertTextOutput(texts, os);
    }

    private void provideInputParam(String value) {
        in = new ByteArrayInputStream(value.getBytes());
        System.setIn(in);
    }
}
