package cardgame;

public interface Loggable {
	
	String getLogFileName();         	// e.g. "player1_output.txt"
    
	String getLogInitialState();	
    
	String getLogFinalState();
}
