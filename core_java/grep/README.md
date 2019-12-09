# Introduction
The Java Grep App is a text search application. The application can search for a text pattern recursively in a given directory, and output matched lines to a file. There are two ways of doing this: storing everything on memory and using Stream with Lambda.

# Usage
The program takes in three arguments:
~~~
regex rootPath outFile
- regex: a special text string for describing a search pattern
- rootPath: root directory path
- outFile: output file name
~~~

# Pseudocode
This is the Pseudocode code for process function
~~~
matchedLines = []
#traverse through all the file under rootDir, including sub-directory
for file in listFilesRecursively(rootDir)
  #go through each line find the matching line
  for line in readLines(file)
      # matched line will be added into matchedLines list
      if containsPattern(line)
        matchedLines.add(line)
writeToFile(matchedLines)
~~~
# Performance Issue
When using Bufferreader to read the file, we have to consider the size of the file. Bufferreader will store everything on memory, memory will run out if the application is processing large files.
The problem can be sloved by using Stream with Lambda expression.

# Improvement
1.Support multiple regex pattern

2.Provide statiscal information to user (e.g. number of line matched, number of line checked, etc)

3.Check the size of the file first and decide on which function to run.