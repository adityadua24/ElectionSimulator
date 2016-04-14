/**
 * 
 */
package asgn1Tests;

import static org.junit.Assert.*;
import org.junit.Before;
import org.junit.Test;

import asgn1Election.ElectionException;

/**
 * @author Eddy
 *
 */
public class CandidateTests {
	
	private asgn1Election.Candidate testCand;
	
	@Before @Test public void setUp() throws asgn1Election.ElectionException{
		testCand = new asgn1Election.Candidate("Test Name", "Test party", "Test Abbrev", 0);
	}
	
	/**
	 * Test method for {@link asgn1Election.Candidate#Candidate(java.lang.String, java.lang.String, java.lang.String, int)}.
	 * @throws ElectionException 
	 */

	@Test(expected = asgn1Election.ElectionException.class)
	public void testCandidateConstructorNullName() throws ElectionException {
		testCand = new asgn1Election.Candidate("", "Party", "Abbrev", 0);
	}	
	@Test(expected = asgn1Election.ElectionException.class)
	public void testCandidateConstructorNullParty() throws ElectionException {
		testCand = new asgn1Election.Candidate("Name", "", "Abbrev", 0);
	}
	@Test(expected = asgn1Election.ElectionException.class)
	public void testCandidateConstructorNullAbbrev() throws ElectionException {
		testCand = new asgn1Election.Candidate("Name", "Party", "", 0);
	}
	@Test(expected = asgn1Election.ElectionException.class)
	public void testCandidateConstructorNegativeVoteCount() throws ElectionException {
		testCand = new asgn1Election.Candidate("", "Party", "Abbrev", -1);
	}

	/**
	 * Test method for {@link asgn1Election.Candidate#candidateListing()}.
	 * @throws ElectionException 
	 */
	@Test
	public void testCandidateListing() throws ElectionException {
		testCand = new asgn1Election.Candidate("Name", "Party", "Abbrev", 1);
		String str = testCand.candidateListing();
		String strTestWith = "Name              Party          (Abbrev)";
		assertTrue(str.equals(strTestWith));
		}

	/**
	 * Test method for {@link asgn1Election.Candidate#copy()}.
	 * @throws ElectionException 
	 */
	@Test
	public void testCopy() throws ElectionException {
		testCand = new asgn1Election.Candidate("Name", "Party", "Abbrev", 1);
		asgn1Election.Candidate testCandCopy = testCand.copy();
		assertTrue((testCand.toString()).equals(testCandCopy.toString()));
	}

	/**
	 * Test method for {@link asgn1Election.Candidate#getVoteCountString()}.
	 * @throws ElectionException 
	 */
	@Test
	public void testGetVoteCountString() throws ElectionException {
		testCand = new asgn1Election.Candidate("Name", "Party", "Abbrev", 1);
		String str = testCand.getVoteCountString();
		String strTestWith = "1";
		assertTrue(str.equals(strTestWith));
	}

	/**
	 * Test method for {@link asgn1Election.Candidate#toString()}.
	 * @throws ElectionException 
	 */
	@Test
	public void testToString() throws ElectionException {
		testCand = new asgn1Election.Candidate("Name", "Party", "Abbrev", 1);
		String str = testCand.toString();
		String strTestWith = "1";
		assertTrue(str.equals(strTestWith));		
	}

}
