package com.desire;
import java.util.LinkedList;

/**This class is the data element class for the ConcordanceDataStructure It consists of a word (String)
 * and a list of page numbers (LinkedList)
 * @author desir
 *
 */
public class ConcordanceDataElement implements Comparable<ConcordanceDataElement>{

    private String word;
    private LinkedList<Integer> list = new LinkedList<>();

    /**Default constructor
     *
     */
    public ConcordanceDataElement() {
        word = null;
        //list = new LinkedList<>();
    }

    /**Parameterized Constructor
     * @param word
     */
    public ConcordanceDataElement(String word) {
        this.word = word.toLowerCase().trim();
        //list = new LinkedList<>();
    }

    /** Add a line number to the linked list if the number doesn't exist in the list
     * @param lineNum line number
     */
    public  void addPage(Integer lineNum)
    {
        //transversing the list to find if the line number is in there
        //if not, then we add it into a the end.
        if(list.contains(lineNum))
        {
            return;
        }
        else
        {
            list.addLast(lineNum);
        }
    }

    /**Returns the linked list of integers that represent the line numbers
     * @return
     */
    public LinkedList<Integer> getList()
    {
        return list;
    }

    /** Return the word portion of the Concordance Data Element
     * @return
     */
    public String getWord()
    {
        return word;
    }

    /** Returns the hashCode.
     */
    public int hashCode()
    {
        return word.hashCode();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        ConcordanceDataElement that = (ConcordanceDataElement) o;

        return word.equals(that.word);
    }

    @Override
    public int compareTo(ConcordanceDataElement o) {

        //return this.word.compareToIgnoreCase(o.word);
        return word.replaceAll("'","").compareTo(o.word.replaceAll("'",""));
    }

    public String toString()
    {
        String lines = "";
        if(list.size()==1){
            lines = lines+list.getFirst();
        }
        else{
            for(Integer i : list){
                lines += i+", ";
            }
            lines = lines.trim();
            lines = lines.substring(0, lines.length()-1);
        }
        System.out.println(lines);
        return this.word + ": "+ lines;
    }

}
