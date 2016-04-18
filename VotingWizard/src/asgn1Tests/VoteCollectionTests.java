/**
 * 
 */
package asgn1Tests;

import static org.junit.Assert.*;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Map;
import java.util.TreeMap;

import org.junit.Before;
import org.junit.Test;

import asgn1Election.Candidate;
import asgn1Election.CandidateIndex;
import asgn1Election.Collection;
import asgn1Election.Election;
import asgn1Election.ElectionException;
import asgn1Election.ElectionManager;
import asgn1Election.Vote;
import asgn1Election.VoteCollection;

/**
 * @author Eddy
 *
 */
public class VoteCollectionTests {

	private VoteCollection vc;
	private ElectionManager em;
	private ArrayList<Election> elec;
	
	@Before @Test 
	public void setUp() throws ElectionException {
		vc = new VoteCollection(5);
		try {
			/* Main Processing Loop */
			em = new ElectionManager();
			String strAddress = ".//data//PrefElectionsTests.lst";
			em.getElectionsFromFile(strAddress);

			elec = em.getElectionList();
			em.setElection(elec.get(2));
		}catch (Exception e) {
			System.err.println(e.getMessage());
			System.exit(1);
		}
	}
	
	/**
	 * Test method for {@link asgn1Election.VoteCollection#VoteCollection(int)}.
	 * @throws ElectionException 
	 */
	@Test(expected = asgn1Election.ElectionException.class)
	public void testVoteCollectionUnderFlow() throws ElectionException {
		vc = new asgn1Election.VoteCollection(0);
	}

	@Test(expected = asgn1Election.ElectionException.class)
	public void testVoteCollectionOverFlow() throws ElectionException {
		vc = new asgn1Election.VoteCollection(16);
	}
	@Test
	public void testVoteCollectionValidInput() {
		try{
			vc = new asgn1Election.VoteCollection(4);
		}catch(asgn1Election.ElectionException e) {
			fail("No exception should have been thrown");
		}
	}
	@Test 
	public void testVoteCollectionValidInitalisation() throws NoSuchFieldException, SecurityException, IllegalArgumentException, IllegalAccessException  {
		
		assertEquals(0, vc.getFormalCount());
		assertEquals(0, vc.getInformalCount());
		
		@SuppressWarnings("rawtypes")
		Class c = VoteCollection.class;
		Field f = c.getDeclaredField("numCandidates");
		f.setAccessible(true);
		int numCandidates = (Integer) f.get(vc);
		assertEquals(numCandidates, 5);
	}
	
	@SuppressWarnings("unchecked")
	@Test 
	public void testVoteCollectionValidInitalisationVoteList() throws NoSuchFieldException, SecurityException, IllegalArgumentException, IllegalAccessException  {
		
		@SuppressWarnings("rawtypes")
		Class c2 = VoteCollection.class;
		Field f2 = c2.getDeclaredField("voteList");
		f2.setAccessible(true);
		ArrayList<Vote> voteListTest; 
		voteListTest = (ArrayList<Vote>) f2.get(vc);
		assertFalse(voteListTest == null);
		
	}

	/**
	 * Test method for {@link asgn1Election.VoteCollection#countPrefVotes(java.util.TreeMap, asgn1Election.CandidateIndex)}.
	 * @throws SecurityException 
	 * @throws NoSuchFieldException 
	 * @throws IllegalAccessException 
	 * @throws IllegalArgumentException 
	 * @throws NoSuchMethodException 
	 * @throws InvocationTargetException 
	 */
	@SuppressWarnings({"unused" , "rawtypes", "unchecked" })
	@Test
	public void testCountPrefVotes() throws NoSuchFieldException, SecurityException, IllegalArgumentException, IllegalAccessException, NoSuchMethodException, InvocationTargetException {
		
		String str = em.manageCount();
		
		Class c = Election.class;
		Field f2 = c.getDeclaredField("vc");
		f2.setAccessible(true);
		Collection vcCopy = (Collection) f2.get(em.getElection());
		int formalVotes = vcCopy.getFormalCount();
		int winVotes = formalVotes/2;
		
		
		Class c4 = (em.getElection()).getClass();
		
		
		Method m = c4.getDeclaredMethod("clearWinner", int.class);
		m.setAccessible(true);
		
		Candidate cdWinner = (Candidate) m.invoke(em.getElection(), winVotes );
		int votes = cdWinner.getVoteCount();
		assertEquals(16, votes);
	}
	
	/**
	 * Test method for {@link asgn1Election.VoteCollection#countPrimaryVotes(java.util.TreeMap)}.
	 * @throws SecurityException 
	 * @throws NoSuchFieldException 
	 * @throws IllegalAccessException 
	 * @throws IllegalArgumentException 
	 * @throws NoSuchMethodException 
	 * @throws InvocationTargetException 
	 */
	@SuppressWarnings({ "rawtypes", "unchecked" })
	@Test
	public void testCountPrimaryVotes() throws NoSuchFieldException, SecurityException, IllegalArgumentException, IllegalAccessException, NoSuchMethodException, InvocationTargetException {
	
		Class c2 = Election.class;
		Field f = c2.getDeclaredField("cds");
		f.setAccessible(true);
		
		TreeMap<CandidateIndex, Candidate> cdsCopy = (TreeMap<CandidateIndex, Candidate>) f.get(em.getElection());
		
		Class c = Election.class;
		Field f2 = c.getDeclaredField("vc");
		f2.setAccessible(true);
		Collection vcCopy = (Collection) f2.get(em.getElection());
		vcCopy.countPrimaryVotes(cdsCopy);
		
		String str = "";
		
		for(Map.Entry<CandidateIndex, Candidate> entry: cdsCopy.entrySet()) {
			str += entry.getValue().toString();
		}
		str = str.replace("\n", "").replace("\r", "").replace(" ", "").trim();
		String testWith = "Shelob (MSP)                 9Gorbag (FOP)                 5Shagrat (SOP)                4Black Rider (NP)             9Mouth of Sauron (WSSP)       3";
	    testWith = testWith.replace("\n", "").replace("\r", "").replace(" ", "").trim();

		
	    assertEquals(0, str.compareTo(testWith));
		
	}
	/**
	 * Test method for {@link asgn1Election.VoteCollection#emptyTheCollection()}.
	 * @throws SecurityException 
	 * @throws NoSuchFieldException 
	 * @throws IllegalAccessException 
	 * @throws IllegalArgumentException 
	 */
	@SuppressWarnings({ "rawtypes", "unchecked" })
	@Test
	public void testEmptyTheCollection() throws NoSuchFieldException, SecurityException, IllegalArgumentException, IllegalAccessException {
		
		Class c = Election.class;
		Field f2 = c.getDeclaredField("vc");
		f2.setAccessible(true);
		Collection vcCopy = (Collection) f2.get(em.getElection());
		
		vcCopy.emptyTheCollection();
		
		Class c2 = VoteCollection.class;
		Field f = c2.getDeclaredField("voteList");
		f.setAccessible(true);
		ArrayList<Vote> votesCopy = (ArrayList<Vote>) f.get(vcCopy);
		
		assertTrue(votesCopy.isEmpty());
		
	}

	/**
	 * Test method for {@link asgn1Election.VoteCollection#includeFormalVote(asgn1Election.Vote)}.
	 * @throws SecurityException 
	 * @throws NoSuchFieldException 
	 * @throws IllegalAccessException 
	 * @throws IllegalArgumentException 
	 */
	@Test
	public void testIncludeFormalVote() throws NoSuchFieldException, SecurityException, IllegalArgumentException, IllegalAccessException {
		asgn1Election.VoteList v = new asgn1Election.VoteList(5);
		asgn1Election.VoteList v2 = new asgn1Election.VoteList(5);
		v.addPref(5); v.addPref(1); v.addPref(4); v.addPref(3); v.addPref(2);
		v2.addPref(2); v2.addPref(3); v2.addPref(1); v2.addPref(5); v2.addPref(4);
		vc.includeFormalVote(v);
		vc.includeFormalVote(v2);
		
		assertEquals(2, vc.getFormalCount());
		
	}

	/**
	 * Test method for {@link asgn1Election.VoteCollection#updateInformalCount()}.
	 * @throws ElectionException 
	 */
	@Test
	public void testUpdateInformalCount() throws ElectionException {
		vc = new asgn1Election.VoteCollection(5);
		vc.updateInformalCount();
		assertEquals(1, vc.getInformalCount());
	}
}
