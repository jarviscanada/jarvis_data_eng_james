package ca.jrvs.apps.grep;

import java.io.*;
import java.util.ArrayList;
import java.io.p;
import java.util.List;
import java.util.stream.Stream;
import java.nio.file.Files;
import java.nio.file.Paths;

public class JavaGrepLambdaImp extends JavaGrepImp {

    public static void main(String[] args) {
        if (args.length != 3) {
            throw new IllegalArgumentException("USAGE: regex rootPath outFile");
        }
        JavaGrepLambdaImp javaGrepLambdaImp = new JavaGrepLambdaImp();
        javaGrepLambdaImp.setRegex(args[0]);
        javaGrepLambdaImp.setRootPath(args[1]);
        javaGrepLambdaImp.setOutFile(args[2]);

        try {
            javaGrepLambdaImp.process();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    /**
     * Implement using Lambda and Stream API
     */
    @Override
    public List<String> readLines(File inputFile) {

        List<String> lines = new ArrayList<>();

        try (Stream<String> stream = Files.lines(Paths.get(inputFile.toURI()))) {

            stream.forEach(lines::add);

        } catch (IOException e) {
            e.printStackTrace();
        }

        return lines;
    }

    /**
     * Implement using Lambda and Stream API
     */
    @Override
    public List<File> listFiles(String rootDir) {

            List<File> result = walk(Paths.get(rootDir)).filter(Files::isDirectory)
                    .map(x -> new File(x.toString()) ).collect(Collectors.toList());

            result.forEach(System.out::println);


    }
}
