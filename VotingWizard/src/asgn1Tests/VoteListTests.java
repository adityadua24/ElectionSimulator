/**
 * 
 */
package asgn1Tests;

import static org.junit.Assert.*;

import java.util.Iterator;

import org.junit.Before;
import org.junit.Test;

/**
 * @author Eddy
 *
 **/
public class VoteListTests {
	
	private asgn1Election.VoteList vtList;
	
	@Before @Test
	public void steUp() {
		vtList = new asgn1Election.VoteList(5);
	}
	
	/**
	 * Test method for {@link asgn1Election.VoteList#addPref(int)}.
	 */
	@Test
	public void testAddPrefOverFlow() {
		vtList.addPref(1);
		vtList.addPref(1);
		vtList.addPref(1);
		vtList.addPref(1);
		vtList.addPref(1);
		assertFalse(vtList.addPref(7));
	}
	@Test 
	public void testAddPref() {
		assertTrue(vtList.addPref(1));		
	}
	/**
	 * Test method for {@link asgn1Election.VoteList#copyVote()}.
	 */
	@Test
	public void testCopyVote() {
		vtList.addPref(2);
		vtList.addPref(1);
		vtList.addPref(3);
		vtList.addPref(4);
		vtList.addPref(5);
		asgn1Election.Vote testCopy = vtList.copyVote();
		assertEquals(0, vtList.toString().compareTo(testCopy.toString()));
		}

	/**
	 * Test method for {@link asgn1Election.VoteList#getPreference(int)}.
	 */
	@Test
	public void testGetPreference() {
		vtList.addPref(2);
		asgn1Election.CandidateIndex cdI = vtList.getPreference(2);
		assertEquals(0, cdI.toString().compareTo("1"));
	}

	/**
	 * Test method for {@link asgn1Election.VoteList#invertVote()}.
	 */
	@Test
	public void testInvertVote() {
		vtList.addPref(2);
		vtList.addPref(1);
		vtList.addPref(3);
		vtList.addPref(4);
		vtList.addPref(5);
		asgn1Election.Vote testInvert = vtList.invertVote();
		String str = testInvert.toString();
		String strTestWith = "2 1 3 4 5 ";
		assertEquals(0, str.compareTo(strTestWith));
	}

	/**
	 * Test method for {@link asgn1Election.VoteList#iterator()}.
	 */
	@Test
	public void testIterator() {
		Iterator<Integer> itr = vtList.iterator();
		assertTrue(itr != null);
	}

	/**
	 * Test method for {@link asgn1Election.VoteList#toString()}.
	 */
	@Test
	public void testToString() {
		vtList.addPref(2);
		vtList.addPref(1);
		vtList.addPref(3);
		vtList.addPref(4);
		vtList.addPref(5);
		String str = vtList.toString();
		String strTestWith = "2 1 3 4 5 ";
		assertEquals(0, str.compareTo(strTestWith));
	}
}
