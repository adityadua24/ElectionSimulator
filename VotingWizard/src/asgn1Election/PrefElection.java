/**
 * 
 * This file is part of the VotingWizard Project, written as 
 * part of the assessment for CAB302, Semester 1, 2016. 
 * 
 */
	package asgn1Election;

	
	import java.util.Iterator;
	import java.util.Map;
	import asgn1Util.Strings;

/**
 * 
 * Subclass of <code>Election</code>, specialised to preferential, but not optional
 * preferential voting.
 * 
 * @author hogan
 * 
 */
public class PrefElection extends Election {

	private int winVotes = 0;
	/**
	 * Simple Constructor for <code>PrefElection</code>, takes name and also sets the 
	 * election type internally. 
	 * 
	 * @param name <code>String</code> containing the Election name
	 */
	public PrefElection(String name) {
		super(name);
		this.type = Election.PrefVoting;
	}

	
	/* Method to:-
	 * 	Find votes required to win
	 *  Counts primary votes
	 *  Keep eliminating candidate with lowest votes until a winner is found
	 *  Builds String containing vote count summary and winner 
	 *  Return that string 
	 * @see asgn1Election.Election#findWinner()
	 */
	@Override
	public String findWinner() {
		this.winVotes = this.vc.getFormalCount() / 2;
		this.vc.countPrimaryVotes(cds);
		String str = "";
		str += this.showResultHeader();
		str += "" + "Counting primary votes; " + this.numCandidates + " alternatives available \n";
		str +=this.reportCountStatus();
		while( (this.clearWinner(winVotes)) == null) {
			CandidateIndex elim = this.selectLowestCandidate();
			Candidate elimCand = cds.get(elim);
			str += this.prefDistMessage(elimCand) +"\n";
			this.vc.countPrefVotes(cds, elim);
			str += this.reportCountStatus();
		}
		Candidate winner = this.clearWinner(winVotes);
		str = str + this.reportWinner(winner); 
		return str;
	}

	
	/* @param v Vote
	 * Checks formality of vote in context to Preferential Election 
	 * @see asgn1Election.Election#isFormal(asgn1Election.Vote)
	 */
	@Override
	public boolean isFormal(Vote v) {
			Iterator<Integer> itr = v.iterator();
			while(itr.hasNext()) {
				int x = itr.next();
				Iterator<Integer> itr2 = v.iterator();
				int repeat = 0;
				
				//Finds duplicate preferences
				while(itr2.hasNext()) {
					if(itr2.next()== x) {
						++repeat;
					}
				}
				if(repeat > 1) {
					return false;
				}
				
				//Finds invalid preferences
				if(((x > this.numCandidates)) || ( x < 1)) {
					return false;
				}
				else {
					continue;
				}
			}
			return true;
	}

	
	/* Returns String containing Election name and Type 
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
        String str = this.name + " - Preferential Voting";
		return str;
	}
	
	// Protected and Private/helper methods below///


	/* @param winVotes Int - votes required to win + 1
	 * Finds candidate with votes more than winVotes
	 * Returns candidate if found
	 * Returns null if not found
	 * @see asgn1Election.Election#clearWinner(int)
	 */
	@Override
	protected Candidate clearWinner(int winVotes) {
		
		for(Map.Entry<CandidateIndex, Candidate> entry : cds.entrySet()) {
			if(((entry.getValue()).getVoteCount()) > winVotes) {
				return entry.getValue();
			}
		}
		return null;
	}

	
	/**
	 * Helper method to create a preference distribution message for display 
	 * 
	 * @param c <code>Candidate</code> to be eliminated
	 * @return <code>String</code> containing preference distribution message 
	 */
	private String prefDistMessage(Candidate c) {
		String str = "\nPreferences required: distributing " + c.getName()
				+ ": " + c.getVoteCount() + " votes";
		return str;
	}

	
	/**
	 * Helper method to create a string reporting the count progress
	 * 
	 * @return <code>String</code> containing count status  
	 */
	private String reportCountStatus() {
		String str = "\nPreferential election: " + this.name + "\n\n"
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

	
	/** Helper method to select candidate with fewest votes 
	 * @return <code>CandidateIndex</code> of candidate with fewest votes
	 */
	private CandidateIndex selectLowestCandidate() {
		CandidateIndex elimCandIndex = cds.firstKey();
		Candidate elimCand = cds.get(elimCandIndex);
		Candidate elimCand2 = null;
		CandidateIndex elimCand2Index = null;
		int skipFirstIteration = 0;
		for(Map.Entry<CandidateIndex, Candidate> entry : cds.entrySet()) {
			if(skipFirstIteration == 0) {
				++skipFirstIteration;
				continue; //first iteration skipped
			}
			Candidate cd = entry.getValue();
			if( cd.getVoteCount() < elimCand.getVoteCount()) {
				elimCand = entry.getValue();
				elimCandIndex = entry.getKey();
			}
			
			 //Finds a second candidate with lowest votes
			else if(cd.getVoteCount() == elimCand.getVoteCount()) {
				elimCand2 = elimCand;
				elimCand = entry.getValue();
				elimCandIndex = entry.getKey();
			}
			else {
				continue;
			}
		} //exits loop
		
		if(elimCand2 == null) 
			return elimCandIndex; 
		
		else if(!(elimCand2 == null)) {
			
			//loops to find CanidateIndex of elimCand2
			for(Map.Entry<CandidateIndex, Candidate> entry : cds.entrySet()) {
				if(elimCand2.equals(entry.getValue())) {
					elimCand2Index = entry.getKey();
				}
			}
			if(elimCand.getVoteCount() < elimCand2.getVoteCount()){
				return elimCandIndex;
			}
			else if(elimCand.getVoteCount() == elimCand2.getVoteCount()){
				int random = (int) ( Math.random() * 10);
				if( (random % 2) == 1 ) {
						return elimCandIndex;
				}
				else {
					return elimCand2Index;
				}
			}
		}
		return elimCandIndex;
	}
}