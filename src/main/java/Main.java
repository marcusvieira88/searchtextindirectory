import index.InMemoryDirectory;
import search.InputSearch;

public class Main {

    public static void main(String[] args) {

        new InputSearch(new InMemoryDirectory()).startSearchInDirectory(args);
    }
}
