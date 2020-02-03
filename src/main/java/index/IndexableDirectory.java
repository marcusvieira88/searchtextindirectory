package index;

import java.io.File;
import java.util.Map;

public interface IndexableDirectory {

    void loadIndexableDirectory(File[] files);

    Map<String, Map<String, String>> getFilesWordMap();
}
