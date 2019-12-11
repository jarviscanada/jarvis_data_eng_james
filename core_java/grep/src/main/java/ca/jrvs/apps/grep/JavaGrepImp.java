package ca.jrvs.apps.grep;

import java.io.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class JavaGrepImp  implements JavaGrep {

    private String regex;
    private String rootPath;
    private String outFile;

    public static void main(String[] args) {
        if (args.length != 3) {
            throw new IllegalArgumentException("USAGE: regex rootPath outFile");
        }
        JavaGrepImp javaGrepImp = new JavaGrepImp();
        javaGrepImp.setRegex(args[0]);
        javaGrepImp.setRootPath(args[1]);
        javaGrepImp.setOutFile(args[2]);

        try {
            javaGrepImp.process();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }
    @Override
    public void process() throws IOException {

        List<File> flist = this.listFiles(rootPath);
        List<String> matched = new ArrayList<>();

        for (File file : flist) {
            List<String> temp_string = this.readLines(file);

            for(String i : temp_string) {
                //check if match
                if (containsPattern(i))
                    matched.add(i);
            }
        }

        //write to file
        this.writeToFile(matched);
    }

    @Override
    public List<File> listFiles(String rootDir) {

        //Directory of the given path
        File dir = new File(rootDir);
        //get the name of the file contain in the directory
        File[] files = dir.listFiles();

        //if the dir is empty return null
        if (files == null) return null;

        List<File> result = new ArrayList<>();

        //Traverse through the files including sub-_directory
        for (File i : files) {
            //if is file add to the list, else is a directory recursion
            if (i.isFile()) {
                result.add(i);
            } else {
                //recursion
                List<File> tmp = this.listFiles(i.getAbsolutePath());
                result.addAll(tmp);
            }
        }

        return result;
    }

    @Override
    public List<String> readLines(File inputFile) {

        List<String> result = new ArrayList<>();

        //read from file
        BufferedReader reader;
        try {
            reader = new BufferedReader(new FileReader(inputFile));
            String line = reader.readLine();

            while (line != null) {
                result.add(line);
                line = reader.readLine();
            }
            reader.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return result;
    }

    @Override
    public boolean containsPattern(String line) {
        return line.matches(regex);
    }

    @Override
    public void writeToFile(List<String> lines) throws IOException {

        //write to file
        BufferedWriter writer;
        try {
            File file = new File(outFile);
            //create the file if not exist
            if (!file.exists()) file.createNewFile();

            writer = new BufferedWriter(new FileWriter(file));
            for (String i : lines) {
                writer.write(i + "\n");
            }
            writer.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    @Override
    public String getRegex() {
        return regex;
    }

    @Override
    public void setRegex(String regex) {
        this.regex = regex;
    }

    @Override
    public String getRootPath() {
        return rootPath;
    }

    @Override
    public void setRootPath(String rootPath) {
        this.rootPath = rootPath;
    }

    @Override
    public String getOutFile() {
        return outFile;
    }

    @Override
    public void setOutFile(String outFile) {
        this.outFile = outFile;
    }
}
