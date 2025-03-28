package cardgame;

//there is a new writer attached to each deck and player

public class LogWriterFactory {
    public static LogWriterInterface getDeckLogger(Deck obj) {
            return new DeckLogWriter((Deck) obj);
    }

	public static LogWriterInterface getPlayerLogger(Player obj) {
		return new PlayerLogWriter((Player) obj);
		
	}




}


	
