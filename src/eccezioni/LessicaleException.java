package eccezioni;

/**
 * Questo package contiene la classe LessicaleException, che rappresenta un'eccezione specifica
 * associata a errori durante l'analisi lessicale del linguaggio.
 * 
 * LessicaleException estende la classe Exception e offre due costruttori:
 * - Un costruttore che permette di specificare un messaggio di errore personalizzato.
 * - Un secondo costruttore che permette di specificare sia un messaggio di errore che una causa
 *   che ha causato l'eccezione, utile per l'indicazione di eccezioni annidate.
 * 
 * La classe fornisce una rappresentazione standard per gestire gli errori specifici durante l'analisi lessicale,
 * consentendo una gestione chiara e precisa degli errori che possono verificarsi in questa fase.
 */




public class LessicaleException extends Exception{
    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	public LessicaleException(String message) {
        super(message);
    }
  
    public LessicaleException(String message, Throwable cause) {
        super(message, cause);
    }
}
