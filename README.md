# Search text in directory

This project provides a command line text search engine developed in Java 11.

It searches an input text in a directory file.

# Unit Tests 
```
mvn test
```

# Build 
```
mvn package
```

# Start 
In the project target path, execute:
```
java -jar search-text-in-directory-1.0-SNAPSHOT.jar <pathToDirectoryContainingTextFiles>
```

# Execution example
```
java -jar search-text-in-directory-1.0-SNAPSHOT.jar /Users/marcus/Documents/projects/searchtextindirectory/files
11 files read in directory /Users/marcus/Documents/projects/searchtextindirectory/files
search> Marcus test many words 10000
File4.txt:80.00%
File9.txt:60.00%
File1.txt:40.00%
File10.txt:40.00%
File11.txt:40.00%
File7.txt:40.00%
File2.txt:20.00%
File3:20.00%
File5:20.00%
File6.txt:20.00%
search> 
```
For exiting the command line application, execute:
```
search> :quit
```

## Next Steps

Here I left some ideas for improvements in our project:
- Provide the amount of found files as parameters (now it is hardcoded in 10).
- If necessary implement the text search in sub directories.