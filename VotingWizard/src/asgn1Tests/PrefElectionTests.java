/**
 * 
 */
package asgn1Tests;

import static org.junit.Assert.*;
import org.junit.Before;
import org.junit.Test;
import asgn1Election.Collection;
import asgn1Election.Election;
import asgn1Election.ElectionException;
import asgn1Election.ElectionManager;
import asgn1Election.PrefElection;
import asgn1Util.NumbersException;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.lang.reflect.Field;
import java.util.ArrayList;


/**
 * @author Eddy
 *
 */
public class PrefElectionTests {

	private ElectionManager em;
	private ArrayList<Election> elec;
	private PrefElection pfE;
	
	
	@Before @Test
	public void setUp() {
		pfE = new PrefElection("PrefElection");
		try {
			/* Main Processing Loop */
			em = new ElectionManager();
			String strAddress = ".//data//elections.lst";
			em.getElectionsFromFile(strAddress);

			elec = em.getElectionList();
			em.setElection(elec.get(2));
		}catch (Exception e) {
			System.err.println(e.getMessage());
			System.exit(1);
		}
	}	
	
	/**
	 * Test method for {@link asgn1Election.PrefElection#findWinner()}.
	 * @throws IOException 
	 */
	@Test
	public void testFindWinner() {
		String test = "Results for election: MorgulValeEnrolment: 83483Shelob              Monster Spider Party          (MSP)Gorbag              Filthy Orc Party              (FOP)Shagrat             Stinking Orc Party            (SOP)Black Rider         Nazgul Party                  (NP)Mouth of Sauron     Whatever Sauron Says Party    "
				+ "(WSSP)Counting primary votes; 5 alternatives availablePreferential election: MorgulValeShelob (MSP)                 9Gorbag (FOP)                 5Shagrat (SOP)                4Black Rider (NP)             9Mouth of Sauron (WSSP)       "
				+ "3Informal                     0Votes Cast                  30Preferences required: distributing Mouth of Sauron: 3 votesPreferential election: MorgulValeShelob (MSP)                 9Gorbag (FOP)                 5Shagrat (SOP)                6Black Rider (NP)            10Informal                     0Votes Cast                  "
				+ "30Preferences required: distributing Gorbag: 5 votesPreferential election: MorgulValeShelob (MSP)                12Shagrat (SOP)                7Black Rider (NP)            11Informal                     "
				+ "0Votes Cast                  30Preferences required: distributing Shagrat: "
				+ "7 votesPreferential election: MorgulValeShelob (MSP)                "
				+ "14Black Rider (NP)            16Informal                     0Votes Cast                "
				+ "  30Candidate Black Rider (Nazgul Party) is the winner with 16 votes...";
		String testWith = em.manageCount();
		test = test.replace("\n", "").replace("\r", "").replace(" ", "").trim();
		testWith = testWith.replace("\n", "").replace("\r", "").replace(" ", "").trim();
		String testWith2 = testWith.replace("\n", "").replace("\r", "").replace(" ", "").trim();
		
		
		assertEquals(0, test.compareTo(testWith2));
	}

	/**
	 * Test method for {@link asgn1Election.PrefElection#isFormal(asgn1Election.Vote)}.
	 */
	@Test
	public void testIsFormalValidData() {
		asgn1Election.Vote v1 = new asgn1Election.VoteList(5);
		v1.addPref(1); v1.addPref(2); v1.addPref(3); v1.addPref(4); v1.addPref(5);
		boolean isFormal = em.getElection().isFormal(v1);
		assertTrue(isFormal);
	}
	@Test
	public void testIsFormalInValidData() {
		asgn1Election.Vote v1 = new asgn1Election.VoteList(5);
		v1.addPref(1); v1.addPref(2); v1.addPref(3); v1.addPref(6); v1.addPref(5);
		boolean isFormal = em.getElection().isFormal(v1);
		assertFalse(isFormal);
	}
	@Test
	public void testIsFormalDuplicatePrefrence() {
		asgn1Election.Vote v1 = new asgn1Election.VoteList(5);
		v1.addPref(1); v1.addPref(2); v1.addPref(1); v1.addPref(4); v1.addPref(5);
		boolean isFormal = em.getElection().isFormal(v1);
		assertFalse(isFormal);
	}
	/**
	 * Test method for {@link asgn1Election.PrefElection#PrefElection(java.lang.String)}.
	 */
	@Test
	public void testPrefElection() {
		pfE = new PrefElection("testingPrefElection");
		assertEquals(0, (pfE.getName()).compareTo("testingPrefElection"));
	}
	@Test
	public void testPrefElectionTypeSetting() {
		pfE = new PrefElection("testingPrefElection");
		assertEquals(1, pfE.getType());
	}
	/**
	 * Test method for {@link asgn1Election.PrefElection#toString()}.
	 */
	@Test
	public void testToString() {
		pfE = new PrefElection("testingPrefElection");
		String str = pfE.toString();
		String strExpected = "testingPrefElection - Preferential Voting";
		assertEquals(0, str.compareTo(strExpected));
	}
	@SuppressWarnings("rawtypes")
	@Test
	public void testloadVotes() throws Exception, SecurityException {
		
		Class c = Election.class;
		Field f = c.getDeclaredField("vc");
		f.setAccessible(true);
		Collection vcTest = (Collection) f.get(em.getElection());
		int total = vcTest.getFormalCount() + vcTest.getInformalCount();
		assertEquals(30, total);
		
	}
	
	@SuppressWarnings("rawtypes")
	@Test
	public void testLoadDefs() throws NoSuchFieldException, Exception {
		Class c1 = Election.class;
		
		Field f1 = c1.getDeclaredField("name");
		Field f2 = c1.getDeclaredField("enrolment");
		Field f3 = c1.getDeclaredField("numCandidates");
		
		f1.setAccessible(true);
		String nameE = (String) f1.get(em.getElection());
		
		
		f2.setAccessible(true);
		int enrolment = (int) f2.get(em.getElection());
		
		
		f3.setAccessible(true);
		int candidateCount = (int) f3.get(em.getElection());
		
		assertEquals(0, nameE.compareTo("MorgulVale"));
		assertEquals(enrolment, 83483);
		assertEquals(candidateCount, 5);
		
	}
	@Test(expected = FileNotFoundException.class)
	public void testLoadDefsExpectedException() throws FileNotFoundException, IOException, ElectionException, NumbersException {
		
			em = new ElectionManager();
			String strAddress = ".//data//PrefElectionsTests.lst";
			em.getElectionsFromFile(strAddress);

			elec = em.getElectionList();
			
			Election el = elec.get(0);
			el.loadDefs();		
	}
	@SuppressWarnings("rawtypes")
	@Test(expected = FileNotFoundException.class)
	public void testLoadVotesExpectedException() throws FileNotFoundException, IOException, ElectionException, NumbersException, NoSuchFieldException, SecurityException, IllegalArgumentException, IllegalAccessException {
		em = new ElectionManager();
		String strAddress = ".//data//elections.lst";
		em.getElectionsFromFile(strAddress);

		elec = em.getElectionList();
		
		Election el = elec.get(2);
		el.loadDefs();
		Class c1 = Election.class;
		Field f1 = c1.getDeclaredField("name");
		f1.setAccessible(true);
		f1.set(el, "testLoadVotes");
		el.loadVotes();
	}
}
	