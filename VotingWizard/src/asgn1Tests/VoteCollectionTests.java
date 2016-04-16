/**
 * 
 */
package asgn1Tests;

import static org.junit.Assert.*;

import java.util.TreeMap;

import org.junit.Before;
import org.junit.Test;

import asgn1Election.Candidate;
import asgn1Election.CandidateIndex;
import asgn1Election.ElectionException;

/**
 * @author Eddy
 *
 */
public class VoteCollectionTests {

	private asgn1Election.VoteCollection vc;
	private TreeMap<CandidateIndex, Candidate> cds;
	private asgn1Election.CandidateIndex cdI1, cdI2, cdI3, cdI4, cdI5;
	private asgn1Election.Candidate cd1, cd2, cd3, cd4, cd5;
	
	
	@Before @Test 
	public void setUp() throws ElectionException {
		vc = new asgn1Election.VoteCollection(4);			
		cds = new TreeMap<CandidateIndex, Candidate>();
		cd1 = new asgn1Election.Candidate("Name1", "Party", "abbrev", 0);
		cd2 = new asgn1Election.Candidate("Name2", "Party", "abbrev", 0);
		cd3 = new asgn1Election.Candidate("Name3", "Party", "abbrev", 0);
		cd4 = new asgn1Election.Candidate("Name4", "Party", "abbrev", 0);
		cd5 = new asgn1Election.Candidate("Name5", "Party", "abbrev", 0);
		cdI1 = new asgn1Election.CandidateIndex(1);
		cdI2 = new asgn1Election.CandidateIndex(2);
		cdI3 = new asgn1Election.CandidateIndex(3);
		cdI4 = new asgn1Election.CandidateIndex(4);
		cdI5 = new asgn1Election.CandidateIndex(5);
		cds.put(cdI1, cd1);
		cds.put(cdI2, cd2);
		cds.put(cdI3, cd3);
		cds.put(cdI4, cd4);
		cds.put(cdI5, cd5);		
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
	public void testVoteCollectionValidInitalisation() throws ElectionException {
		vc = new asgn1Election.VoteCollection(4);
		assertEquals(0, vc.getFormalCount());
		assertEquals(0, vc.getInformalCount());
		
	}

	/**
	 * Test method for {@link asgn1Election.VoteCollection#countPrefVotes(java.util.TreeMap, asgn1Election.CandidateIndex)}.
	 */
	@Test
	public void testCountPrefVotes() {
		asgn1Election.VoteList v1 = new asgn1Election.VoteList(5);
		asgn1Election.VoteList v2 = new asgn1Election.VoteList(5);
		asgn1Election.VoteList v3 = new asgn1Election.VoteList(5);
		asgn1Election.VoteList v4 = new asgn1Election.VoteList(5);
		asgn1Election.VoteList v5 = new asgn1Election.VoteList(5);
		v1.addPref(5); v1.addPref(1); v1.addPref(4); v1.addPref(3); v1.addPref(2);
		v2.addPref(2); v2.addPref(3); v2.addPref(1); v2.addPref(5); v2.addPref(4);
		v3.addPref(1); v3.addPref(4); v3.addPref(3); v3.addPref(5); v3.addPref(2);
		v4.addPref(3); v4.addPref(2); v4.addPref(1); v4.addPref(5); v4.addPref(4);
		v5.addPref(4); v5.addPref(5); v5.addPref(1); v5.addPref(2); v5.addPref(3);
		vc.includeFormalVote(v1);
		vc.includeFormalVote(v2);
		vc.includeFormalVote(v3);
		vc.includeFormalVote(v4);
		vc.includeFormalVote(v5);
		vc.countPrimaryVotes(cds);
		vc.countPrefVotes(cds, cdI3);
		vc.countPrefVotes(cds, cdI4);
		int i1 = cds.get(cdI1).getVoteCount();
		int i2 = cds.get(cdI2).getVoteCount();
		Candidate c3 = cds.get(cdI3);
		Candidate c4 = cds.get(cdI4);
		int i5 = cds.get(cdI5).getVoteCount();
		assertEquals(2, i1);
		assertEquals(2, i2);
		assertTrue(c3 == null);
		assertTrue(c4 == null);
		assertEquals(1, i5);
	}

	/**
	 * Test method for {@link asgn1Election.VoteCollection#countPrimaryVotes(java.util.TreeMap)}.
	 */
	@Test
	public void testCountPrimaryVotes() {
		asgn1Election.VoteList v = new asgn1Election.VoteList(5);
		asgn1Election.VoteList v2 = new asgn1Election.VoteList(5);
		v.addPref(5); v.addPref(1); v.addPref(4); v.addPref(3); v.addPref(2);
		v2.addPref(2); v2.addPref(3); v2.addPref(1); v2.addPref(5); v2.addPref(4);
		vc.includeFormalVote(v);
		vc.includeFormalVote(v2);
		vc.countPrimaryVotes(cds);
		
		int i1 = cds.get(cdI1).getVoteCount();
		int i2 = cds.get(cdI2).getVoteCount();
		int i3 = cds.get(cdI3).getVoteCount();
		int i4 = cds.get(cdI4).getVoteCount();
		int i5 = cds.get(cdI5).getVoteCount();
		
		assertEquals(0, i1);
		assertEquals(1, i2);
		assertEquals(1, i3);
		assertEquals(0, i4);
		assertEquals(0, i5);
	}
	/**
	 * Test method for {@link asgn1Election.VoteCollection#emptyTheCollection()}.
	 */
	@Test
	public void testEmptyTheCollection() {
		asgn1Election.VoteList v = new asgn1Election.VoteList(5);
		asgn1Election.VoteList v2 = new asgn1Election.VoteList(5);
		v.addPref(5); v.addPref(1); v.addPref(4); v.addPref(3); v.addPref(2);
		v2.addPref(2); v2.addPref(3); v2.addPref(1); v2.addPref(5); v2.addPref(4);
		vc.includeFormalVote(v);
		vc.includeFormalVote(v2);
		vc.countPrimaryVotes(cds);
		
		int i1 = cds.get(cdI2).getVoteCount();
		int i2 = cds.get(cdI3).getVoteCount();
		
		assertEquals(1, i1);
		assertEquals(1, i2);
		
		vc.emptyTheCollection();
		vc.countPrimaryVotes(cds);
		
		int i3 = cds.get(cdI2).getVoteCount();
		int i4 = cds.get(cdI3).getVoteCount();
		
		assertEquals(1, i3);
		assertEquals(1, i4);
		
	}

	/**
	 * Test method for {@link asgn1Election.VoteCollection#includeFormalVote(asgn1Election.Vote)}.
	 */
	@Test
	public void testIncludeFormalVote() {
		asgn1Election.VoteList v = new asgn1Election.VoteList(5);
		asgn1Election.VoteList v2 = new asgn1Election.VoteList(5);
		v.addPref(5); v.addPref(1); v.addPref(4); v.addPref(3); v.addPref(2);
		v2.addPref(2); v2.addPref(3); v2.addPref(1); v2.addPref(5); v2.addPref(4);
		vc.includeFormalVote(v);
		vc.includeFormalVote(v2);
		vc.countPrimaryVotes(cds);
		
		int i1 = cds.get(cdI2).getVoteCount();
		int i2 = cds.get(cdI3).getVoteCount();
		
		assertEquals(1, i1);
		assertEquals(1, i2);
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
