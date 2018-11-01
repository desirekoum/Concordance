package com.desire.test;
import com.desire.ConcordanceDataManager;
import com.desire.ConcordanceDataManagerInterface;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Scanner;

import static org.junit.Assert.*;

/**
 * This is the test file for the ConcordanceDataManager
 * which is implemented from the ConcordanceDataManagerInterface
 *
 * @author Professor Kartchner
 *
 */
public class ConcordanceDataManagerTest {
    private ConcordanceDataManagerInterface concordanceManager = new ConcordanceDataManager();
    private File inputFile, outputFile;
    private String text;

    /**
     * Create an instance of ConcordanceDataManager
     * Create a string for testing
     * @throws Exception
     */
    @Before
    public void setUp() throws Exception {
        concordanceManager = new ConcordanceDataManager();
        text = "Now is the time\nfor all good men\n"+
                "to come to the aid\nof their country";
    }

    /**
     * Set concordanceManager reference to null
     * @throws Exception
     */
    @After
    public void tearDown() throws Exception {
        concordanceManager = null;
    }

    /**
     * Test for the createConcordanceArray method
     * Use the String text created in setUp()
     */
    @Test
    public void testCreateConcordanceArray() {
        ArrayList<String> words = concordanceManager.createConcordanceArray(text);
        System.out.println("Words:::"+words.size());
        System.out.println("Words:::"+words);
        assertEquals("aid: 3", words.get(0).trim());
        assertEquals("all: 2", words.get(1).trim());
        assertEquals("come: 3", words.get(2).trim());
        assertEquals("country: 4", words.get(3).trim());
        assertEquals("for: 2", words.get(4).trim());
        assertEquals("good: 2", words.get(5).trim());
        assertEquals("men: 2", words.get(6).trim());
        assertEquals("now: 1", words.get(7).trim());
        assertEquals("their: 4", words.get(8).trim());
        assertEquals("time: 1", words.get(9).trim());
    }

    /**
     * Test for createConcordanceFile method
     * This is intended to be used with the test file:
     * Now_is_the_time.txt
     */
    @Test
    public void testCreateConcordanceFileA() {
        ArrayList<String> words = new ArrayList<String>();
        try {
            inputFile = new File("Test1.txt");
            PrintWriter inFile = new PrintWriter(inputFile);
            inFile.print("Now is the time\n" +
                    "for all good men\n" +
                    "to come to the aid\n" +
                    "of their country\n");

            inFile.close();
            outputFile = new File("Test1Out.txt");
            PrintWriter outFile = new PrintWriter(outputFile);
            outFile.print(" ");

            concordanceManager.createConcordanceFile(inputFile, outputFile);
            Scanner scan = new Scanner(outputFile);
            while (scan.hasNext())
            {
                words.add(scan.nextLine());
            }

            scan.close();
            outFile.close();

            System.out.println(words);
            assertEquals("aid: 3", words.get(0).trim());
            assertEquals( "all: 2", words.get(1).trim());
            assertEquals("come: 3", words.get(2).trim());
            assertEquals("country: 4", words.get(3).trim());
            assertEquals("for: 2", words.get(4).trim());
            assertEquals("good: 2", words.get(5).trim());
            assertEquals("men: 2", words.get(6).trim());
            assertEquals("now: 1", words.get(7).trim());
            assertEquals("their: 4", words.get(8).trim());
            assertEquals("time: 1", words.get(9).trim());
        } catch (FileNotFoundException e) {
            fail("This should not have caused an FileNotFoundException");
        } catch (Exception e) {
            fail("This should not have caused an Exception");
        }
    }

    /**
     * Test for createConcordanceFile method
     * Create an inputFile "Test.txt"
     * and an outputFile "TestOut.txt"
     */

    @Test
    public void testCreateConcordanceFileB() {
        ArrayList<String> words = new ArrayList<String>();
        try {
            inputFile = new File("Tes1t.txt");
            PrintWriter inFile = new PrintWriter(inputFile);
            inFile.print("Applets are Java programs that are usually part of a\n" +
                    "Web site. They are stored on a Web server along with the site's\n" +
                    "Web pages. When a remote user accesses a Web page with his or\n" +
                    "her browser, any applets associated with the Web page are\n" +
                    "transmitted over the Internet from the server to the remote\n" +
                    "user's system\n");
            inFile.close();
            outputFile = new File("TestOut1.txt");
            PrintWriter outFile = new PrintWriter(outputFile);

            concordanceManager.createConcordanceFile(inputFile, outputFile);
            Scanner scan = new Scanner(outputFile);
            while (scan.hasNext())
            {
                words.add(scan.nextLine().toLowerCase());

            }

            scan.close();
            outFile.close();
            //for(int i=0; i<words.size(); i++)
            System.out.println("::::"+words);
            assertEquals("accesses: 3", words.get(0).trim());
            assertEquals("applets: 1, 4", words.get(3).trim());
            assertEquals("are: 1, 2, 4", words.get(4).trim());
            assertEquals("page: 3, 4", words.get(13).trim());
            assertEquals("pages: 3", words.get(14).trim());
            assertEquals("remote: 3, 5", words.get(17).trim());
            assertEquals("server: 2, 5", words.get(18).trim());
            assertEquals("site's: 2", words.get(19).trim());
            assertEquals("user's: 6", words.get(26).trim());
            assertEquals("web: 2, 3, 4", words.get(29).trim());
            assertEquals("with: 2, 3, 4", words.get(31).trim());
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Test
    public void testCreateConcordanceFileC() {
        try {
            inputFile = new File("Test2.txt");
            inputFile.setReadable(false);
            outputFile = new File("Test2Out.txt");
            PrintWriter outFile = new PrintWriter(outputFile);
            outFile.print(" ");

            concordanceManager.createConcordanceFile(inputFile, outputFile);
            assertTrue("This should have raised an exception", false);
            outFile.close();

        } catch (FileNotFoundException e) {
            assertTrue("This should have raised a FileNotFoundexception", true);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Test
    public void testCreateConcordanceFileD() throws IOException {
        try {
            inputFile = new File("Test3.txt");
            outputFile = new File("Test3Out.txt");
            outputFile.setWritable(false);

            concordanceManager.createConcordanceFile(inputFile, outputFile);
            assertTrue("This should have raised an exception", false);

        } catch (FileNotFoundException e) {
            assertTrue("This should have raised a FileNotFoundException", true);
        }
    }

    /**
     * Student Test for the createConcordanceFile method
     */
    @Test
    public void testCreateConcordanceFileSTUDENT() {
        assertFalse("This test has not yet been implemented", true);
    }
}
