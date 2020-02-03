import static utils.Utils.assertFileSearchOutput;
import static utils.Utils.assertTextOutput;

import index.InMemoryDirectory;
import index.IndexableDirectory;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.OutputStream;
import java.io.PrintStream;
import java.util.Map;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import search.SearchDirectory;

public class SearchDirectoryTest {

    private static IndexableDirectory inMemoryDirectory;
    private static OutputStream os;

    @BeforeAll
    public static void loadTestFiles() {
        File directory = new File("./files");
        final File[] files = directory.listFiles(File::isFile);
        inMemoryDirectory = new InMemoryDirectory();
        inMemoryDirectory.loadIndexableDirectory(files);
    }

    @BeforeEach
    public void redirectOutput() {
        os = new ByteArrayOutputStream();
        System.setOut(new PrintStream(os));
    }

    @Test
    public void shouldFindWordThatExistsInOneFile() {

        new SearchDirectory(inMemoryDirectory).searchText("Marcus100");

        assertFileSearchOutput(Map.of("File7.txt", 100.00f), os);
    }

    @Test
    public void shouldFindWordThatExistsInTenFiles() {

        new SearchDirectory(inMemoryDirectory).searchText("Marcus");

        assertFileSearchOutput(Map.of(
            "File1.txt", 100.00f,
            "File10.txt", 100.00f,
            "File2.txt", 100.00f,
            "File3", 100.00f,
            "File4.txt", 100.00f,
            "File5", 100.00f,
            "File6.txt", 100.00f,
            "File7.txt", 100.00f,
            "File8", 100.00f,
            "File11.txt", 100.00f), os);
    }

    @Test
    public void shouldFindDifferentWeightsOfTextInFiles() {

        new SearchDirectory(inMemoryDirectory).searchText("Marcus300 test many words");

        assertFileSearchOutput(Map.of(
            "File4.txt", 100.00f,
            "File9.txt", 100.00f,
            "File1.txt", 50.00f,
            "File11.txt", 50.00f,
            "File7.txt", 50.00f,
            "File10.txt", 25.00f), os);
    }

    @Test
    public void shouldNotFindWordThatNotExistsInFiles() {

        new SearchDirectory(inMemoryDirectory).searchText("Marcus200");

        assertTextOutput("no matches found", os);
    }
}
