/**
 * 
 * This file is part of the VotingWizard Project, written as 
 * part of the assessment for CAB302, Semester 1, 2016. 
 * 
 */
package asgn1Election;

//import java.util.Collection;
import java.util.Map;

import asgn1Util.Strings;

/**
 * 
 * Subclass of <code>Election</code>, specialised to simple, first past the post voting
 * 
 * @author hogan
 * 
 */
public class SimpleElection extends Election {

	/**
	 * Simple Constructor for <code>SimpleElection</code>, takes name and also sets the 
	 * election type internally. 
	 * 
	 * @param name <code>String</code> containing the Election name
	 */
	public SimpleElection(String name) {
		super(name);
		this.type = 0;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see asgn1Election.Election#findWinner()
	 */
	@Override
	public String findWinner() {
		this.vc.countPrimaryVotes(cds);
		return this.reportCountResult();		
	}

	/* 
	 * (non-Javadoc)
	 * @see asgn1Election.Election#isFormal(asgn1Election.Vote)
	 */
	@Override
	public boolean isFormal(Vote v) {
		while(v.iterator().hasNext()) {
			if((v.iterator().next() > this.numCandidates)) {
				return false;
			}
			else {
				continue;
			}
		}
		return true;
	}
		

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		String str = this.name + " - Simple Voting";
		return str;
	}
	
	// Protected and Private/helper methods below///

	/*
	 * (non-Javadoc)
	 * 
	 * @see asgn1Election.Election#clearWinner(int)
	 */
	@Override
	protected Candidate clearWinner(int wVotes) {
		
		Candidate potentialWinner = (cds.get(cds.firstKey()));	//gets first candidate
		Candidate potentialWinner2 = null;
		int skipFirstIteration = 0;
		for(Map.Entry<CandidateIndex , Candidate> entry : cds.entrySet()) {
			if(skipFirstIteration == 0) {
				skipFirstIteration++;
				continue;
			}
			Candidate cd = entry.getValue();
			if((cd.getVoteCount()) > (potentialWinner.getVoteCount())) {
				potentialWinner = cd;
			}
			else if((cd.getVoteCount()) == (potentialWinner.getVoteCount())) {
				potentialWinner2 = cd;
			}
			else {
				continue;
			}
		}
		if(potentialWinner2 == null) {
			return potentialWinner;
		}
		else {
			int random = (int) ( Math.random() * 10);
			if( (random % 2) == 1 ) {
					return potentialWinner;
			}
			else {
				return potentialWinner2;
			}
		}
		
	}

	/**
	 * Helper method to create a string reporting the count result
	 * 
	 * @return <code>String</code> containing summary of the count
	 */
	private String reportCountResult() {
		String str = "\nSimple election: " + this.name + "\n\n"
				+ candidateVoteSummary() + "\n";
		String inf = "Informal";
		String voteStr = "" + this.vc.getInformalCount();
		int length = ElectionManager.DisplayFieldWidth - inf.length()
				- voteStr.length();
		str += inf + Strings.createPadding(' ', length) + voteStr + "\n\n";

		String cast = "Votes Cast";
		voteStr = "" + this.numVotes;
		length = ElectionManager.DisplayFieldWidth - cast.length()
				- voteStr.length();
		str += cast + Strings.createPadding(' ', length) + voteStr + "\n\n";
		return str;
	}
}