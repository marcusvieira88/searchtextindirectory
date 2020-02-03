import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;

import index.InMemoryDirectory;
import index.IndexableDirectory;
import java.io.File;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

public class InMemoryDirectoryTest {

    private static IndexableDirectory inMemoryDirectory;

    @BeforeAll
    public static void loadTestFiles() {
        File directory = new File("./files");
        final File[] files = directory.listFiles(File::isFile);
        inMemoryDirectory = new InMemoryDirectory();
        inMemoryDirectory.loadIndexableDirectory(files);
    }

    @Test
    public void shouldLoadTestFiles() {
        assertEquals(11, inMemoryDirectory.getFilesWordMap().size());
    }

    @Test
    public void shouldFindFilesData() {
        assertNotNull(inMemoryDirectory.getFilesWordMap().get("File5").get("volutpat"));
        assertNotNull(inMemoryDirectory.getFilesWordMap().get("File9.txt").get("iaculis"));
    }

    @Test
    public void shouldNotFindNonExistentFilesData() {
        assertNull(inMemoryDirectory.getFilesWordMap().get("File2.txt").get("testNull"));
        assertNull(inMemoryDirectory.getFilesWordMap().get("File10.txt").get("anotherWord"));
    }
}
