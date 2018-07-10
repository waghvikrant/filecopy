package com.vikrant.files.filecopy;

import java.util.Date;

import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;

/**
 * Unit test for simple App.
 */
public class AppTest 
    extends TestCase
{
    /**
     * Create the test case
     *
     * @param testName name of the test case
     */
    public AppTest( String testName )
    {
        super( testName );
    }

    /**
     * @return the suite of tests being tested
     */
    public static Test suite()
    {
        return new TestSuite( AppTest.class );
    }

    /**
     * Rigourous Test :-)
     */
    public void testApp()
    {
    	Date start = new Date();
		App app = new App();
		String sourceDirectory = "";
		String destinationDirectory = "";
		app.copy(sourceDirectory, destinationDirectory);
		Date end = new Date();
		System.out.println("Total time taken : " + (end.getTime() - start.getTime()) + " milliseconds");
    }
}
