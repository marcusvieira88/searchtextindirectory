package search;

import index.IndexableDirectory;
import java.io.File;
import java.util.Scanner;

public class InputSearch {

    private IndexableDirectory indexableDirectory;

    public InputSearch(IndexableDirectory indexableDirectory) {
        this.indexableDirectory = indexableDirectory;
    }

    public void startSearchInDirectory(String[] args) {

        if (args == null || args.length == 0 || args[0] == null || args[0].isEmpty()) {
            throw new IllegalArgumentException("No directory given to index.");
        }

        String directoryPath = args[0];
        final File directory = new File(directoryPath);
        if (!directory.isDirectory()) {
            throw new IllegalArgumentException("The path provided is not a directory.");
        }

        final File[] files = directory.listFiles(File::isFile);
        if (files == null || files.length == 0) {
            throw new IllegalArgumentException("The directory provided doesn't have files.");
        }

        indexableDirectory.loadIndexableDirectory(files);
        System.out.println(String.format("%d files read in directory %s", files.length, directoryPath));

        try (Scanner keyboard = new Scanner(System.in)) {
            while (true) {
                System.out.print("search> ");

                final String line = keyboard.nextLine();

                if (line.equals(":quit")) {
                    System.exit(0);
                }

                if (line.isEmpty()) {
                    System.out.println("no matches found");
                } else {
                    new SearchDirectory(indexableDirectory).searchText(line);
                }
            }
        }
    }
}
