import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class GameState {
    private int size;            // The number of stones
    private boolean[] stones;    // Game state: true for available stones, false for taken ones
    private int lastMove;        // The last move

    /**
     * Class constructor specifying the number of stones.
     */
    public GameState(int size) {

        this.size = size;

        //  For convenience, we use 1-based index, and set 0 to be unavailable
        this.stones = new boolean[this.size + 1];
        this.stones[0] = false;

        // Set default state of stones to available
        for (int i = 1; i <= this.size; i++) {
            this.stones[i] = true;
        }

        // Set the last move be -1
        this.lastMove = -1;
    }

    /**
     * Copy constructor
     */
    public GameState(GameState other) {
        this.size = other.size;
        this.stones = Arrays.copyOf(other.stones, other.stones.length);
        this.lastMove = other.lastMove;
    }


    /**
     * This method is used to compute a list of legal moves
     *
     * @return This is the list of state's moves
     */
    public List<Integer> getMoves() {
        // TODO Add your code here
    	List<Integer> moves = new ArrayList<Integer>();
    	
    	// If this is the first move
    	if (lastMove == -1) {
    		for (int i = 1; i <= size/2; i++) {
    			// Find prime #
    			if(i % 2 != 0) {
    				moves.add(i);
    			}
    		}
    	}
    	
    	// Not first move
    	else {
    		for (int i = 1; i <= size; i++) {
    			if(lastMove % i == 0 || i % lastMove == 0) {
    				if (getStone(i) == true) { moves.add(i); }
    			}
    		}
    	}
        return moves;
    }


    /**
     * This method is used to generate a list of successors
     * using the getMoves() method
     *
     * @return This is the list of state's successors
     */
    public List<GameState> getSuccessors() {
        return this.getMoves().stream().map(move -> {
            var state = new GameState(this);
            state.removeStone(move);
            return state;
        }).collect(Collectors.toList());
    }


    /**
     * This method is used to evaluate a game state based on
     * the given heuristic function
     *
     * @return int This is the static score of given state
     */
    public double evaluate() {
        // TODO Add your code here
    	
    	// Max's turn
    	if (isMaxTurn()) {
    		// If at end state and Min wins
    		if (getMoves().isEmpty()) {
    			return -1.0;
    		}
    		// If stone 1 is not taken
    		else if (stones[1] == true) {
    			return 0.0;
    		}
    		// If the last move is 1
    		else if (lastMove == 1) {
    			if (getMoves().size() % 2 != 0) {
    				return 0.5;
    			}
    			else { return -0.5; }
    		}
    		// If the last move is prime #
    		else if (Helper.isPrime(lastMove)) {
    			int multiplier = 0;
    			// Find legal moves from getMoves function???
    			for (int i = 0; i < getMoves().size(); i++) {
    				// find multipliers
    				if (getMoves().get(i) % lastMove == 0) {
    					multiplier ++;
    				}
    			}
    			// odd number of legal moves
    			if (multiplier % 2 != 0) {
    				return 0.7;
    			}
    			// even
    			else { return -0.7; }
    		}
    		// If the last move is composite #
    		else if (!Helper.isPrime(lastMove)) {
    			int largestPrimeFactor = Helper.getLargestPrimeFactor(lastMove);
    			int multiplier = 0;
    			// find multipliers
    			for (int i = 0; i < getMoves().size(); i++) {
    				if (getMoves().get(i) % largestPrimeFactor == 0) {
    					multiplier ++;
    				}
    			}
    			// odd number of multipliers
    			if (multiplier % 2 != 0) {
    				return 0.6;
    			}
    			// even
    			else { return -0.6; }
    		}
    		// Fall out the options...
    		else {
    			System.out.print(" Weirddd ");
    			return 999.0;
    		}
    	}
    		
    		
        	// Min's turn
        	if (!isMaxTurn()) {
        		// If at end state and Max wins
        		if (getMoves().isEmpty()) {
        			return 1.0;
        		}
        		// If stone 1 is not taken
        		else if (stones[1] == true) {
        			return 0.0;
        		}
        		// If the last move is 1
        		else if (lastMove == 1) {
        			if (getMoves().size() % 2 != 0) {
        				return -0.5;
        			}
        			else { return 0.5; }
        		}
        		// If the last move is prime #
        		else if (Helper.isPrime(lastMove)) {
        			int multiplier = 0;
        			// Find legal moves from getMoves function???
        			for (int i = 0; i < getMoves().size(); i++) {
        				// find multipliers
        				if (getMoves().get(i) % lastMove == 0) {
        					multiplier ++;
        				}
        			}
        			// odd number of legal moves
        			if (multiplier % 2 != 0) {
        				return -0.7;
        			}
        			// even
        			else { return 0.7; }
        		}
        		// If the last move is composite #
        		else if (!Helper.isPrime(lastMove)) {
        			int largestPrimeFactor = Helper.getLargestPrimeFactor(lastMove);
        			int multiplier = 0;
        			// find multipliers
        			for (int i = 0; i < getMoves().size(); i++) {
        				if (getMoves().get(i) % largestPrimeFactor == 0) {
        					multiplier ++;
        				}
        			}
        			// odd number of multipliers
        			if (multiplier % 2 != 0) {
        				return -0.6;
        			}
        			// even
        			else { return 0.6; }
        		}
        		// Fall out the options...
        		else {
        			System.out.print(" Weirddd ");
        			return 999.0;
        		}
    	}
        	return 888.0;
    }
    	
    /**
     * This method is used to take a stone out
     *
     * @param idx Index of the taken stone
     */
    public void removeStone(int idx) {
        this.stones[idx] = false;
        this.lastMove = idx;
    }

    /**
     * These are get/set methods for a stone
     *
     * @param idx Index of the taken stone
     */
    public void setStone(int idx) {
        this.stones[idx] = true;
    }

    public boolean getStone(int idx) {
        return this.stones[idx];
    }

    /**
     * These are get/set methods for lastMove variable
     *
     * @param move Index of the taken stone
     */
    public void setLastMove(int move) {
        this.lastMove = move;
    }

    public int getLastMove() {
        return this.lastMove;
    }

    /**
     * This is get method for game size
     *
     * @return int the number of stones
     */
    public int getSize() {
        return this.size;
    }
    
    public boolean isMaxTurn() {
    	int count = 0;
    	for (int i = 1; i <= stones.length-1; i++) {
    		if (stones[i] == false) {count++ ; }
    	}
    	if (count % 2 == 0) {
    		return true;
    	}
    	else {
    		return false;
    	}
    }

}	
