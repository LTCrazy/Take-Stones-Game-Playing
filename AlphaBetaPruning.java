public class AlphaBetaPruning {
	
	private int action;
	private double utility;
	private int numOfVisited;
	private int numOfValued;
	private int maxDepth;
	private int currentDepth;
	private int specifiedDepth;
	private double avgBranching;
	private int numOfBranch;
	private boolean searchForever;
	private boolean isMaxTurn;

	
    public AlphaBetaPruning() {
    	numOfVisited = 0;
    	numOfValued = 0;
    	numOfBranch = 0;
    	searchForever = false;
    }

    /**
     * This function will print out the information to the terminal,
     * as specified in the homework description.
     */
    public void printStats() {
        // TODO Add your code here
    	System.out.print("Move: " + action + "\n"
    			+ "Value: " + utility + "\n"
    			+ "Number of Nodes Visited: " + numOfVisited + "\n"
    			+ "Number of Nodes Evaluated: " + numOfValued + "\n"
    			+ "Max Depth Reached: " + maxDepth + "\n"
    			+ "Avg Effective Branching Factor: " + avgBranching);
    }

    /**
     * This function will start the alpha-beta search
     * @param state This is the current game state
     * @param depth This is the specified search depth
     */
    public void run(GameState state, int depth) {
        // TODO Add your code here
    	if (depth == 0) {
    		searchForever = true;
    	}
    	currentDepth = depth;
    	isMaxTurn = state.isMaxTurn();
    	specifiedDepth = depth;
    	utility = this.alphabeta(state, depth, Double.NEGATIVE_INFINITY, Double.POSITIVE_INFINITY, isMaxTurn);
//    	System.out.print("numOfBranch "+numOfBranch+"\n");
    	avgBranching = Math.round((double)numOfBranch/(numOfVisited-numOfValued)*10.0)/10.0;
    	maxDepth = depth - currentDepth;
    	
    }

    /**
     * This method is used to implement alpha-beta pruning for both 2 players
     * @param state This is the current game state
     * @param depth Current depth of search
     * @param alpha Current Alpha value
     * @param beta Current Beta value
     * @param maxPlayer True if player is Max Player; Otherwise, false
     * @return int This is the number indicating score of the best next move
     */
    private double alphabeta(GameState state, int depth, double alpha, double beta, boolean maxPlayer) {
        // TODO Add your code here
    	numOfVisited ++;
    	// If it's Max's turn
    	double v;
    	if (maxPlayer) {
//    		System.out.print("it's Max turn \n");
    		if (searchForever==false && (this.terminalTest(state) || depth == 0)) {
    			numOfValued ++;
    			currentDepth = Math.min(currentDepth, depth);
//    			System.out.print("terminate "+numOfValued+" "+state.evaluate()+"\n");
    			return state.evaluate();
    		}
    		else {
        		v = Double.NEGATIVE_INFINITY;
//    			System.out.print("max moves "+state.getMoves() +"\n");
    			// Iterate through successors
    			for (int i = 0; i < state.getMoves().size(); i++) {
//    				System.out.print("Max takes "+state.getMoves().get(i)+"\n");
    				double successor = alphabeta(state.getSuccessors().get(i), depth - 1, alpha, beta, false);
//    				System.out.print(successor+"\n");
    				numOfBranch ++;
    				if (v < successor) {
    					v = successor;
    					if (depth == specifiedDepth) {
        					action = state.getMoves().get(i);    						
    					}
//    					System.out.print ("Max Action -> "+state.getMoves().get(i)+"\n");
    				}
    				// pruning
    				if (v >= beta) {
    					return v;
    				}
    				alpha = Math.max(v, alpha);
    			}
    		}
    	}
    	
    	// If it's Min's turn
    	else {
//    		System.out.print("it's Min turn \n");
    		if (searchForever==false && (this.terminalTest(state) || depth == 0)) {
    			numOfValued ++;
    			currentDepth = Math.min(currentDepth, depth);
//    			System.out.print("terminate "+numOfValued+" "+state.evaluate()+"\n");
    			return state.evaluate();
    		}
    		else {
        		v = Double.POSITIVE_INFINITY;
//    			System.out.print("min moves "+state.getMoves()+"\n");
    			// Iterate through successors
    			for (int i = 0; i < state.getMoves().size(); i++) {
//    				System.out.print("Min takes "+state.getMoves().get(i)+"\n");
    				double successor = alphabeta(state.getSuccessors().get(i), depth - 1, alpha, beta, true);
    				numOfBranch ++;
    				
    				if (v > successor) {
    					v = successor;
    					if (depth == specifiedDepth) {
        					action = state.getMoves().get(i);    						
    					}
//    					System.out.print ("Min Action -> "+state.getMoves().get(i)+"\n");
    				}
    				// pruning
    				if (v <= alpha) {
    					return v;
    				}
        			beta = Math.min(v, beta);
    			}
    		}
    	} 
        return v;
    }
    
    private boolean terminalTest (GameState state) {
    	if (state.getMoves().isEmpty()) {
    		return true;
    	}
    	return false;
    }
}
