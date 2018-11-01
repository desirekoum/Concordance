package com.desire;

import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

public class ConcordanceDataStructure implements ConcordanceDataStructureInterface {

    private int numberOfWords;
    private LinkedList<ConcordanceDataElement>[] hashTable;
    private double  loadingFactor = 1.5;

    public ConcordanceDataStructure(int size) {
        //int pct = (int)((1.0/loadingFactor - 1)*100.0);
        this.numberOfWords = fourKPlus3(size, loadingFactor);
        //hashTable = new ConcordanceDataElement[numberOfWords];
        hashTable = new LinkedList[numberOfWords];
        for(int i = 0; i<numberOfWords; i++)
        {
            hashTable[i] = null;
        }
    }

    public ConcordanceDataStructure(String test, int size)
    {
        this.numberOfWords = size;
        //hashTable = new ConcordanceDataElement[numberOfWords];
        hashTable = new LinkedList[numberOfWords];
        //int hashCode = Math.abs(test.hashCode()%20);
        for(int i = 0; i< size; i++)
        {
            hashTable[i] = null;
        }
    }

    /**Calculate a 4k +3 prime search
     * @param size estimated number of node
     * @param pct
     * @return the size of the hash table
     */
    public static int fourKPlus3(int n, double loadfactor)
    {  boolean fkp3 = false;
        boolean aPrime = false;
        int prime, highDivisor, d;
        // double pctd = pct;

        prime = (int)(n/loadfactor);
        if(prime % 2 == 0) // if even make odd
            prime = prime +1;
        while(fkp3 == false) // not a 4k+3 prime
        {  while(aPrime == false) // not a prime
        {  highDivisor = (int)(Math.sqrt(prime) + 0.5);
            for(d = highDivisor; d > 1; d--)
            {  if(prime % d == 0)
                break; // not a prime
            }
            if(d != 1) // prime not found
                prime = prime + 2;
            else
                aPrime = true;
        } // end of the prime search loop
            if((prime - 3) % 4 == 0)
                fkp3 = true;
            else
            {  prime = prime + 2;
                aPrime = false;
            }
        } // end of 4k+3 prime search loop
        return prime;
    }

    @Override
    public int getTableSize() {
        return numberOfWords;
    }

    @Override
    public ArrayList<String> getWords(int index) {
        ArrayList<String> words = new ArrayList<>();
        //ConcordanceDataElement current = hashTable[index];
        LinkedList<ConcordanceDataElement> current = hashTable[index];
//        while(current!=null)
//        {
//            words.add(current.getWord());
//            current = current.getNext();
//        }
        for(ConcordanceDataElement e: current){
            words.add(e.getWord());
        }
        return words;
    }

    @Override
    public ArrayList<LinkedList<Integer>> getPageNumbers(int index) {
        ArrayList<LinkedList<Integer>> pageNumbers = new ArrayList<>();
        //Traversing the linkedList of data elements
        LinkedList<ConcordanceDataElement> current = hashTable[index];
        //if(current!=null)
        //{
          //  pageNumbers.add(current.getList());
            //current = current.getNext();
        //}
        for(ConcordanceDataElement e: current){
            pageNumbers.add(e.getList());
        }
        return pageNumbers;
    }

    @Override
    public void add(String wordInput, int lineNum) {
        //checking if the word is valid
        String word = wordInput.toLowerCase();
        if(word.length()<=2 || word.equalsIgnoreCase("the") || word.equalsIgnoreCase("and"))
        {
            return;
        }
        //searching for the word in the array
        int hashCode = Math.abs(word.hashCode()%numberOfWords);

        if(hashCode > hashTable.length)
        {
            hashCode = hashCode - hashTable.length;
        }

        if(hashTable[hashCode] != null)//The position is occupied by a previous word
        {
            LinkedList<ConcordanceDataElement> temp = hashTable[hashCode];

            //ConcordanceDataElement refWord = null;
            ConcordanceDataElement lookup = new ConcordanceDataElement(word);
            if(temp.contains(new ConcordanceDataElement(word))){
                //lookup.addPage(lineNum);
                int index = temp.indexOf(lookup);
                ConcordanceDataElement e = hashTable[hashCode].get(index);
                e.addPage(lineNum);
                hashTable[hashCode].set(index, e);
            }
            else {
                ConcordanceDataElement newWord = new ConcordanceDataElement(word);
                newWord.addPage(lineNum);
                hashTable[hashCode].addLast(newWord);
            }
        }
        else// A word ! exists in the list at this position
        {
            ConcordanceDataElement e = new ConcordanceDataElement(word);//e={word = "word", list=[]}
            e.addPage(lineNum);//e={word = "word", list=[2]}
            LinkedList<ConcordanceDataElement> l = new LinkedList<>();
            l.add(e);
            hashTable[hashCode] = l;// = e;//hashTable = [{word = "word", list=[2]}]
            //System.out.println("Element:::::"+e);
        }
    }


    @Override
    public ArrayList<String> showAll() {
        ArrayList<String> list = new ArrayList<>();
        for(int i = 0; i< hashTable.length; i++)
        {
            if(hashTable[i]!=null)
            {
                //ConcordanceDataElement c = hashTable[i].getNext();
                LinkedList<ConcordanceDataElement> c = hashTable[i];
                for(ConcordanceDataElement e : c){
                     list.add(e.toString());// += e.getList();// hashTable[i].getList().toString();
                }

            }
        }
        Collections.sort(list);
        return list;
    }
}
