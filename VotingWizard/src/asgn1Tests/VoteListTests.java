/**
 * 
 */
package asgn1Tests;

import static org.junit.Assert.*;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Iterator;
import org.junit.Before;
import org.junit.Test;

import asgn1Election.VoteList;

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
	
	@Test
	public void testVotelist() throws Exception, Exception {
		@SuppressWarnings("rawtypes")
		Class c = VoteList.class;
		Field f = c.getDeclaredField("numCandidates");
		f.setAccessible(true);
		int candidateCount = (int) f.get(vtList);
		assertEquals(5, candidateCount);
	}
	@Test
	public void testVoteListVoteInitialisation() throws NoSuchFieldException, Exception {
		@SuppressWarnings("rawtypes")
		Class c = VoteList.class;
		Field f = c.getDeclaredField("vote");
		f.setAccessible(true);
		@SuppressWarnings("unchecked")
		ArrayList<Integer> voteTest = (ArrayList<Integer>) f.get(vtList);
		assertFalse(voteTest == null);
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
	@Test
	public void testCopyVoteEmptyVote() {
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
	@Test
	public void testGetPreferenceInvalidDataOverFlow() {
		vtList.addPref(2);
		asgn1Election.CandidateIndex cdI = vtList.getPreference(6);
		assertNull(cdI);
	}
	@Test
	public void testGetPreferenceInvalidDataZero() {
		asgn1Election.CandidateIndex cdI = vtList.getPreference(0);
		assertNull(cdI);
	}
	@Test
	public void testGetPreferenceInvalidDataFive() {
		asgn1Election.CandidateIndex cdI = vtList.getPreference(5);
		assertNull(cdI);
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
	@Test
	public void testInvertVoteNoData() {
		asgn1Election.Vote testInvert = vtList.invertVote();
		String str = testInvert.toString();
		String strTestWith = vtList.toString();
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
	@Test 
	public void testToStringEmptyVote() {
		String str = vtList.toString();
		String strtestWith = "";
		assertEquals(0, str.compareTo(strtestWith));
	}
}
