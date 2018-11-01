package com.desire;

import java.io.*;
import java.util.ArrayList;
import java.util.Scanner;


/**
 * This program makes a concordance for a file or a String.
 * A concordance lists all the words that occur in the file or String,
 * along with all the line numbers on which each word occurs.
 * (Words of length less than 3 are omitted, "and" and "the" are omitted.)
 * Strip out all punctuation, except apostrophes that occur in the
 * middle of a word, i.e. let’s, we’d, etc.
 *
 * Uses an object that implements ConcordanceDataStructureInterface
 * @author desire Mpondo
 *
 */
public class ConcordanceDataManager implements ConcordanceDataManagerInterface{

    /**
     *
     */
    public ConcordanceDataManager() {

    }

    @Override
    public ArrayList<String> createConcordanceArray(String input) {
        int count = 0;
        int line = 1;
        ArrayList<String> list = new ArrayList<>();
        //System.out.println("Before stripping:::"+input);
        //Stripping punctuation from the String
        String newInput = input.replaceAll("[\\p{Punct}&&[^']]|(?<![a-zA-Z])'|'(?![a-zA-Z])", " ");
        //System.out.println("After stripping punctuation:::"+newInput);
        //Reading from the string
        Scanner scanner = new Scanner(newInput);
        //Data structure
        String currentLine = null;
        while(scanner.hasNextLine())
        {
            currentLine = scanner.nextLine();
            Scanner scanner2 = new Scanner(currentLine);
            while(scanner2.hasNext())
            {
                scanner2.next();
                count++;
            }
        }
        scanner.close();
        //DataStructure for the number of word just estimated
        ConcordanceDataStructure dataStructure = new ConcordanceDataStructure(count);
        scanner = new Scanner(newInput);
        String currentLine2 = null;
        while(scanner.hasNextLine())
        {
            currentLine2 = scanner.nextLine();
            Scanner scanner2 = new Scanner(currentLine2);
            while(scanner2.hasNext())
            {
                String word = scanner2.next();
                dataStructure.add(word, line);
            }
            line++;
        }
        //Exploration of the Data structure content
        //System.out.println(line+" : "+Collections.sort(dataStructure));
        return dataStructure.showAll();
    }


    @Override

    public boolean createConcordanceFile(File input, File output) throws IOException
    {
        boolean created = false;
        //Output file

            int line = 0;
            FileWriter outFile = new FileWriter(output);
            Scanner sc = new Scanner(new FileReader(input));
            //Counting the words in the input to defi
            // ne the hash table size
            int words = countWords(sc);
            //Creating the hashtable dataStructure
            ConcordanceDataStructure ds = new ConcordanceDataStructure(words);
            sc.close();
            sc= new Scanner(input);
            //Collections.sort();
            while(sc.hasNextLine())
            {
                line++;
                String currentLine = sc.nextLine();
                String newInput = currentLine.replaceAll("[\\p{Punct}&&[^']]|(?<![a-zA-Z])'|'(?![a-zA-Z])", "");
                Scanner sc2 = new Scanner(newInput);
                //treatment word by word
                while(sc2.hasNext())
                {
                    String word = sc2.next();
                    System.out.println(word);
                    ds.add(word, line);
                }
            }
            ArrayList<String> list = ds.showAll();
            for (String string : list ) {
                outFile.write(string+" \n");
            }
            outFile.close();
            sc.close();
            created = true;

        return created;
    }

    /**
     * Counts words in the scanner object
     * @param source
     */
    public int countWords(Scanner source)
    {
        int count = 0;
        while(source.hasNext() )
        {
            String input = source.nextLine().trim();
            Scanner stream = new Scanner( input );
            while ( stream.hasNext() )
            {
                count++;
                stream.next();
            }
        }
        return count;
    }
}
