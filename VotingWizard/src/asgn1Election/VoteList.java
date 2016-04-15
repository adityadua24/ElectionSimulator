/**
 * 
 * This file is part of the VotingWizard Project, written as 
 * part of the assessment for CAB302, Semester 1, 2016. 
 * 
 */
package asgn1Election;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * 
 * <p>Implementing class for the {@link asgn1Election.Vote} interface. <code>Vote</code> 
 * should be implemented as some sort of <code>List</code>, with 
 * <code>ArrayList<Integer></code> the default choice.</p>
 * 
 * @author hogan
 * 
 */
public class VoteList implements Vote {
	/** Holds the information that comprises a single vote */
	private List<Integer> vote;

	/** Number of candidates in the election */
	private int numCandidates;

	/**
	 * <p>Simple Constructor for the <code>VoteList</code> class. <code>numCandidates</code> 
	 * is known to be in range through check on <code>VoteCollection</code>. 
	 * 
	 * @param numCandidates <code>int</code> number of candidates competing for 
	 * this seat. 
	 */
	public VoteList(int numCandidates) {
			//Check function call flow, if numCandidates is checked thru VoteCollection before passing here, then no need to check Range on this again
		this.vote = new ArrayList<Integer>();
		this.numCandidates = numCandidates;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see asgn1Election.Vote#addPref(asgn1Election.CandidateIndex)
	 */
	@Override
	public boolean addPref(int index) {	
		if(vote.size() < numCandidates) {
			vote.add(index);
			return true;
		}
		else 
			return false;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see asgn1Election.Vote#copyVote()
	 */
	@Override
	public Vote copyVote() {
		VoteList copiedVote = new VoteList(numCandidates);
		Iterator<Integer> itr = this.iterator();
		while(itr.hasNext()){
			copiedVote.vote.add( (Integer) itr.next() );
		}		
		return copiedVote;			
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see asgn1Election.Vote#getPreference(int)
	 */
	@Override
	public CandidateIndex getPreference(int cand) {
		if((cand > 0) && (cand<=this.numCandidates)) {
			return  new CandidateIndex(((this.vote.indexOf((Integer) cand) ) +1));
		}
		else {
			return null;
		}
		//Remember : Note to use +1 with getPreference calls !!
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see asgn1Election.Vote#invertVote()
	 */
	@Override
	public Vote invertVote() {
		// First element in this invertedVote is First preferred Candidate and last element is last most preferred candidate
		VoteList vote_inverted = new VoteList(this.numCandidates);
		
		for(int i = 1; i <= this.vote.size(); i++) {
			vote_inverted.vote.add(((this.vote.indexOf((Integer) i))) + 1);
		}
		return vote_inverted;	
	}

	/* 
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Iterable#iterator()
	 */
	
	@Override
	public Iterator<Integer> iterator() {
		return this.vote.iterator();
		
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#toString()
	 */

	@Override
	public String toString() {
		String str = "";
		for (Integer index : this.vote) {
			str += index.intValue() + " ";
		}
		return str;
	}
}
