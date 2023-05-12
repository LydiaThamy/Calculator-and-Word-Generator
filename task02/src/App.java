package sdf.task02.src;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.Reader;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;
import java.util.Set;

import javax.swing.text.html.HTMLDocument.Iterator;

public class App {

    public static void main(String[] args) throws IOException {
        // check if directory is given
        if (args.length != 1) {
            System.out.println("Run your program with the file directory that you want to read");
            System.exit(0);
        }

        // check if directory exists
        File dir = new File(args[0]);
        if (!dir.exists()) {
            System.out.println(args[0] + " does not exist");
            System.exit(0);
        }

        // open the files of each authors
        // File.listFiles(), returns File[] of pathnames
        File[] authors = dir.listFiles();

        // work on each files
        for (File a : authors) {

            // print name of author
            System.out.println("Author: " + a.getName() + "\n");

            // create hashmap for each author to compute currentwords
            Map<String, HashMap<String, Integer>> distributionMap = new HashMap<>();

            // open the books
            File[] books = a.listFiles();
            for (File b : books) {
                // read book
                Reader fr = new FileReader(b);
                BufferedReader br = new BufferedReader(fr);

                // create list to store each word in the book
                List<String> list = new ArrayList<>();
                String line;

                // add words in book to String text
                while ((line = br.readLine()) != null) {
                    String[] array = line.replaceAll("[']", " ").replaceAll("\\p{P}", " ").toLowerCase().split(" ");

                    for (String ar : array) {

                        // remove empty strings
                        if (ar.isEmpty()) {
                            continue;
                        }

                        list.add(ar.strip());
                    }
                }

                br.close();
                fr.close();

                // look at each word
                for (int i = 0; i < list.size(); i++) {

                    // break if it's last word on the list
                    if ((i + 1) == list.size()) {
                        break;
                    }
                    
                    String currentWord = list.get(i);
                    String nextWord = list.get(i + 1);

                    // create hashmap for each word to compute nextwords
                    HashMap<String, Integer> nextWordMap = new HashMap<>();

                    // if word does not exist in distributionMap, make new set
                    if (!distributionMap.containsKey(currentWord)) {
                        // create nextwordmap
                        nextWordMap.put(nextWord, 1);

                        // put nextwordmap in distribution
                        distributionMap.put(currentWord, nextWordMap);
                        continue;
                    }

                    // if word exists, see if the nextword exists
                    // get nextwordmap from distributionMap
                    nextWordMap = distributionMap.get(currentWord);

                    // find out if nextwordmap contains next word
                    // if next word doesn't exist, create another nextwordmap
                    if (!nextWordMap.containsKey(nextWord)) {
                        nextWordMap.put(nextWord, 1);
                        continue;
                    }

                    // if next word exists
                    // update and increase value in the nextwordmap
                    nextWordMap.put(nextWord, (nextWordMap.get(nextWord) + 1));

                    // update distributionMap
                    distributionMap.put(currentWord, nextWordMap);
                }
            } // end of book

            // work with each key in the distribution list
            Set<String> keys = distributionMap.keySet();
            for (String k : keys) {
                // print out current word
                System.out.println(k);

                // probability
                // find out the nextwordmap of the current key
                HashMap<String, Integer> newWordMap = new HashMap<>();
                newWordMap = distributionMap.get(k);
                // find out how many of next word they have
                int freqAllWords = newWordMap.size();

                // find out each next word of the current word
                Set<String> nextWordKeys = newWordMap.keySet();
                for (String n : nextWordKeys) {

                    // find out frequency of each next word
                    int freqNextWord = newWordMap.get(n);

                    // find out probability
                    double freq1 = freqNextWord;
                    double freq2 = freqAllWords;
                    double probability = freq1 / freq2;

                    // print out probability
                    DecimalFormat df = new DecimalFormat("0.###");
                    System.out.printf("     %s     %s\n", n, df.format(probability));

                } // end of nextword list

            } // end of distribution list
            System.out.println("\n");
        } // end of author
    } // end of main method
} // end of class
