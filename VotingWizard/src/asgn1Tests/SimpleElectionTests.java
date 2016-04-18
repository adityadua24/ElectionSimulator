/**
 * 
 */
package asgn1Tests;

import static org.junit.Assert.*;

import java.util.ArrayList;

import org.junit.Before;
import org.junit.Test;

import asgn1Election.Election;
import asgn1Election.ElectionManager;
import asgn1Election.SimpleElection;

/**
 * @author Eddy
 *
 */
public class SimpleElectionTests {

	private ElectionManager em;
	private ArrayList<Election> elec;
	private asgn1Election.SimpleElection se;
	
	@Before @Test
	public void setUp() {
		se = new SimpleElection("SimpleElection");
		try {
			/* Main Processing Loop */
			em = new ElectionManager();
			String strAddress = ".//data//SimpleElectionTest.lst";
			em.getElectionsFromFile(strAddress);

			elec = em.getElectionList();
			em.setElection(elec.get(2));
		}catch (Exception e) {
			System.err.println(e.getMessage());
			System.exit(1);
		}
	}	
	
	/**
	 * Test method for {@link asgn1Election.SimpleElection#findWinner()}.
	 */
	@Test
	public void testFindWinner() {
		String test = "Results for election: MorgulVale\n"
				+ "Enrolment: 83483\n\n"
				+"Shelob              Monster Spider Party          (MSP)\n"
				+"Gorbag              Filthy Orc Party              (FOP)\n"
				+"Shagrat             Stinking Orc Party            (SOP)\n"
				+"Black Rider         Nazgul Party                  (NP)\n"
				+"Mouth of Sauron     Whatever Sauron Says Party    (WSSP)\n"
				+"\n\n"
				+"Counting primary votes; 5 alternatives available \n\n"
				+"Simple election: MorgulVale\n"
				+"\n"
				+"Shelob (MSP)                 9\n"
				+"Gorbag (FOP)                 5\n"
				+"Shagrat (SOP)                4\n"
				+"Black Rider (NP)             9\n"
				+"Mouth of Sauron (WSSP)       3\n"
				+"\n"
				+"Informal                     0\n"
				+"\n"
				+"Votes Cast                  30\n"
				+"\n\n"
				+"Candidate Shelob (Monster Spider Party) is the winner with 9 votes...\n";
		String testWith = em.manageCount();
		
		

		test = test.replace("\n", "").replace("\r", "").replace(" ", "").trim();
		testWith = testWith.replace("\n", "").replace("\r", "").replace(" ", "").trim();
		String testWith2 = testWith.replace("\n", "").replace("\r", "").replace(" ", "").trim();
		
		assertEquals(0, test.compareTo(testWith2));
	}

	/**
	 * Test method for {@link asgn1Election.SimpleElection#isFormal(asgn1Election.Vote)}.
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
		asgn1Election.VoteList v1 = new asgn1Election.VoteList(5);
		v1.addPref(2); v1.addPref(1); v1.addPref(7); v1.addPref(4); v1.addPref(3);
		boolean isFormal = em.getElection().isFormal(v1);
		assertFalse(isFormal);
	}

	/**
	 * Test method for {@link asgn1Election.SimpleElection#SimpleElection(java.lang.String)}.
	 */
	@Test
	public void testSimpleElection() {
		assertEquals(0, (se.getName()).compareTo("SimpleElection"));
	}
	@Test
	public void testSimpleElectionTypeSetting() {
		assertEquals(0, se.getType());
	}
	/**
	 * Test method for {@link asgn1Election.SimpleElection#toString()}.
	 */
	@Test
	public void testToString() {
		assertEquals(0, se.toString().compareTo("SimpleElection"+" - Simple Voting"));
	}

}
