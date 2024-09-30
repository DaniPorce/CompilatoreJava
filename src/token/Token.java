package token;

/**
 * Questo package contiene la classe Token, che rappresenta un elemento token del linguaggio.
 * Ogni token ha un tipo, una riga di origine nel codice sorgente e un valore associato (opzionale).
 * I tipi di token sono definiti nell'enumerazione TokenType.
 * 
 * La classe Token offre due costruttori:
 * - Un costruttore che inizializza un token con tipo e riga.
 * - Un secondo costruttore che permette anche di specificare un valore associato al token.
 * 
 * Sono presenti metodi per ottenere il tipo, la riga e il valore del token.
 * 
 * Il metodo toString() genera una rappresentazione testuale del token, includendo il tipo e la riga.
 * Se il token è di tipo INT o FLOAT, viene anche incluso il valore associato.
 */


public class Token {
    private int riga; // Variabile che memorizza la riga in cui si trova il token
    private TokenType tipo; // Tipo del token (enumerazione TokenType)
    private String val; // Valore associato al token (opzionale)

    public Token(TokenType tipo, int riga) {
        this.riga = riga;
        this.tipo = tipo;
    }

    public Token(TokenType tipo, int riga, String val) {
        this.riga = riga;
        this.tipo = tipo;
        this.val = val;
    }
    public int getRiga() {
        return riga;
    }

    public TokenType getType() {
        return tipo;
    }

    public String getVal() {
        return val;
    }

    @Override
    public String toString() {
        if (this.tipo == TokenType.INT || this.tipo == TokenType.FLOAT )
            return "<" + this.tipo + ",r:" + this.riga + "," + this.val + ">";
        else
            return "<" + this.tipo + ",r:" + this.riga + ">";
    }

	
		
}
