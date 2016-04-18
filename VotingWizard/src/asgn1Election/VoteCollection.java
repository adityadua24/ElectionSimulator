/**
 * 
 * This file is part of the VotingWizard Project, written as 
 * part of the assessment for CAB302, Semester 1, 2016. 
 * 
 */
package asgn1Election;


import java.util.ArrayList;
import java.util.Iterator;
import java.util.Map;
import java.util.TreeMap;
import static org.junit.Assert.*;

/**
 * 
 * <p>Class to manage a collection of <code>Vote</code>s as specified by the 
 * {@link asgn1Election.Collection} interface. This implementation is based on  
 * a <code>ArrayList<E></code> data structure to enable convenient additions to the 
 * list.</p>
 * 
 * <p>The private methods {@link #getPrimaryKey(Vote) } and 
 * {@link #getPrefthKey(Vote, TreeMap, int)} are crucial to your success. Some guidance 
 * is given for these methods through comments in situ, but this is generous, and well 
 * beyond what would be provided in real practice.</p>
 * 
 * <p>As before, please note the name clash between <code>asgn1Election.Collection</code>
 * and <code>java.util.Collection</code>. 
 * 
 * @author hogan
 *
 */
public class VoteCollection implements asgn1Election.Collection  {
	
	/** Holds all the votes in this seat */
	private ArrayList<Vote> voteList;

	/** Number of candidates competing for this seat */
	private int numCandidates;

	/** Number of formal votes read during the election and stored in the collection */
	private int formalCount;

	/** Number of invalid votes received during the election */
	private int informalCount;

	
	/**
	 * Simple Constructor for the <code>VoteCollection</code> class.
	 * Most information added through mutator methods. 
	 * 
	 * @param numCandidates <code>int</code> number of candidates competing for 
	 * this seat
	 * @throws ElectionException if <code>NOT inRange(numCandidates)</code>
	 */
	public VoteCollection(int numCandidates) throws ElectionException {
		if(!(CandidateIndex.inRange(numCandidates))) {
			throw new ElectionException("invalid candidate number passed to voteCollection Constructor");
		}
		else {
				this.numCandidates = numCandidates;
				this.voteList = new ArrayList<Vote>();
				this.formalCount = 0;
				this.informalCount = 0;
		}
	}
	
	
	/* Distributes votes of elim to next active candidate and removes elim from cds
	 * @param elim - The candidate index index whose votes are to be distributed.
	 * @param cds - Treemap storing all candidate indexes and candidates
	 * @see asgn1Election.Collection#countPrefVotes(java.util.TreeMap, asgn1Election.CandidateIndex)
	 */
	@Override
	public void countPrefVotes(TreeMap<CandidateIndex, Candidate> cds,
			CandidateIndex elim) {
		assertNotNull(cds);
		assertNotNull(elim);
		for( Vote v: voteList) {
			Vote invertedV = v.invertVote();
			Iterator<Integer> itr  = invertedV.iterator();
			while(itr.hasNext()) {
				int x = itr.next();
				CandidateIndex cdX = new CandidateIndex(x);
				boolean isActive = true;
				if(this.getOriginalObjectReference(cds, cdX) == null) {
					isActive = false;
				}
				else {
					cdX = this.getOriginalObjectReference(cds, cdX);
				}
				//Checks whether current CandidateIndex in inverted vote is active and equals elim 
				if( isActive && ((cdX.compareTo(elim)) != 0)){
					break;
				}
				else if(!isActive) {
					continue;
				}
				//When found active and equal to elim, distributes vote to next active candidate
				else if(isActive && ((cdX.compareTo(elim)) == 0)){
					int pref = Integer.parseInt((invertedV.getPreference(x)).toString());
					++pref;
					CandidateIndex cdI = getPrefthKey(v, cds, pref);
					(cds.get(cdI)).incrementVoteCount();
					break;
				}
			}
		}
		cds.remove(elim);
	}

	
	/* @param cds - Treemap storing all candidate indexes and candidates 
	 * Counts primary votes of all the candidates in a vote
	 * @see asgn1Election.Collection#countPrimaryVotes(java.util.TreeMap)
	 */
	@Override
	public void countPrimaryVotes(TreeMap<CandidateIndex, Candidate> cds) {
		for(Vote v: voteList) {
			CandidateIndex cdI_similar = this.getPrimaryKey(v);
			CandidateIndex cdI_actual = this.getOriginalObjectReference(cds, cdI_similar);
			(cds.get(cdI_actual)).incrementVoteCount();
		}
	}


	/* Simple method to empty a treemap 
	 * @see asgn1Election.Collection#emptyTheVoteList()
	 */
	@Override
	public void emptyTheCollection() {
		voteList.clear();
	}

	
	/* Getter method to get formal vote count in a vote collection
	 * @see asgn1Election.Collection#getFormalCount()
	 */
	@Override
	public int getFormalCount() {
		return this.formalCount;
	}

	/*
	Getter method to get formal vote count in a vote collection
	 * @see asgn1Election.Collection#getInformalCount()
	 */
	@Override
	public int getInformalCount() {
		return informalCount;
	}

	
	/* @param v - vote whose formality needs to be tested 
	 * Checks formality of v
	 * increments formal vote count
	 * @see asgn1Election.Collection#includeFormalVote(asgn1Election.Vote)
	 */
	@Override
	public void includeFormalVote(Vote v) {
		this.voteList.add(v);
		++this.formalCount;
	}

	
	/* Simple method to increment informal vote count
	 * @see asgn1Election.Collection#updateInformalCount()
	 */
	@Override
	public void updateInformalCount() {
		++this.informalCount;
	}
	
	
	/**
	 * 
	 * <p>Important helper method to find the candidate in the current vote 
	 * corresponding to a given preference. Ideally we seek the candidate who is 
	 * preference <emphasis>pref</emphasis>, but often some of the higher preferences
	 * for a given vote may already have been eliminated. So this method finds not 
	 * the <emphasis>pref-th</emphasis> candidate, but the pref-th 
	 * <emphasis>active</emphasis> candidate</p>
	 * 
	 * <p>You must therefore find a way to deal with the candidate set, to work out 
	 * which ones are still active and which aren't. This method is quite specific 
	 * to the preferential election and so does not get used for the simple ballot.</p>
	 * 
	 * @param v <code>Vote</code> to be examined to find the pref-th active candidate
	 * @param cds <code>TreeMap</code> set of all active candidates
	 * @param pref <code>int</code> specifies the preference we are looking for
	 * @return <code>(key = prefth preference still active) OR null</code>
	 * 
	 */
	private CandidateIndex getPrefthKey(Vote v,TreeMap<CandidateIndex, Candidate> cds, int pref) {
		while(pref <= this.numCandidates) {
			CandidateIndex cdI_similar = v.getPreference(pref);
			CandidateIndex cdI_actual = this.getOriginalObjectReference(cds, cdI_similar);
			if(cdI_actual == null) {
				pref++;
				continue;
			}
			else if(cds.containsKey(cdI_actual)) {
				return cdI_actual;
			}
			else {
				pref++;
				continue;
			}
		}
		return null;
	}

	
	/**
	 * <p>Important helper method to find the first choice candidate in the current 
	 * vote. This is always undertaken prior to distribution of preferences and so it 
	 * is not necessary to test whether the candidate remains in the set.</p>
	 * 
	 * @param v <code>Vote</code> from which first pref is to be obtained
	 * @return <code>CandidateIndex</code> of the first preference candidate
	 */
	private CandidateIndex getPrimaryKey(Vote v) {
        return v.getPreference((Integer) 1);
    }
	
	/**
	 * This method returns the reference of original object present in TreeMap cds and passes it to wherever needed
	 * This enables it to fetch values off the cds
	 * @param cds variable name of TreeMap data type
	 * @param cdI CandidateIndex variable which is identical to one of the candidateIndexs in cds 
	 * @return	returns the reference of original object 
	 */
	private CandidateIndex getOriginalObjectReference(TreeMap<CandidateIndex, Candidate> cds, CandidateIndex cdI) {
		for(Map.Entry<CandidateIndex, Candidate> entry : cds.entrySet()) {
			if((cdI.compareTo(entry.getKey())) == 0) {
				return entry.getKey();
			}
		}
		return null;
	}
}
