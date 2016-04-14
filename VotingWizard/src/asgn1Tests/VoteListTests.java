/**
 * 
 */
package asgn1Tests;

import static org.junit.Assert.*;
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
		assertFalse(vtList.addPref(7));
	}
	/**
	 * Test method for {@link asgn1Election.VoteList#addPref(int)}.
	 */
	@Test
	public void testAddPrefUnderflow() {
		assertFalse(vtList.addPref(-1));
	}

	/**
	 * Test method for {@link asgn1Election.VoteList#copyVote()}.
	 */
	@Test
	public void testCopyVote() {
		fail("Not yet implemented");
	}

	/**
	 * Test method for {@link asgn1Election.VoteList#getPreference(int)}.
	 */
	@Test
	public void testGetPreference() {
		fail("Not yet implemented");
	}

	/**
	 * Test method for {@link asgn1Election.VoteList#invertVote()}.
	 */
	@Test
	public void testInvertVote() {
		fail("Not yet implemented");
	}

	/**
	 * Test method for {@link asgn1Election.VoteList#iterator()}.
	 */
	@Test
	public void testIterator() {
		fail("Not yet implemented");
	}

	/**
	 * Test method for {@link asgn1Election.VoteList#toString()}.
	 */
	@Test
	public void testToString() {
		fail("Not yet implemented");
	}

	/**
	 * Test method for {@link asgn1Election.VoteList#get_numOfCandidates()}.
	 */
	@Test
	public void testGet_numOfCandidates() {
		fail("Not yet implemented");
	}

}
