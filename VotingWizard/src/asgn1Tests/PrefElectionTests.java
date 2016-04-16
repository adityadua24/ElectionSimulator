/**
 * 
 */
package asgn1Tests;

import static org.junit.Assert.*;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

import org.junit.Before;
import org.junit.Test;

import asgn1Election.Collection;
import asgn1Election.Election;
import asgn1Election.ElectionException;
import asgn1Election.ElectionManager;
import asgn1Election.PrefElection;
import asgn1Election.VoteCollection;
import asgn1Util.NumbersException;

import java.lang.reflect.Field;


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
	 * Test method for {@link asgn1Election.PrefElection#findWinner()}.
	 * @throws IOException 
	 */
	@Test
	public void testFindWinner() throws IOException {
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
	public void testIsFormal() {
		fail("Not yet implemented");
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
	
	@Test
	public void testloadVotes() throws Exception, SecurityException {
		Class c = em.getElection().getClass();
		Field f = c.getDeclaredField("vc");
		f.setAccessible(true);
		Collection vcTest = (Collection) f.get(em.getElection());
		int total = vcTest.getFormalCount() + vcTest.getInformalCount();
		assertEquals(30, total);
	}
}
	