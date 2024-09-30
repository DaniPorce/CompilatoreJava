package scanner;

/**
 * Questo package contiene la classe Scanner, che rappresenta un analizzatore lessicale
 * per il linguaggio.
 * 
 * La classe Scanner legge un file di testo contenente il codice sorgente e restituisce
 * una sequenza di token rappresentativi degli elementi del linguaggio.
 * 
 * Scanner offre i seguenti metodi principali:
 * - Un costruttore che prende in input il nome del file sorgente e inizializza il buffer per la lettura.
 * - I metodi peekToken() e nextToken() per ottenere il token successivo senza consumarlo o consumarlo rispettivamente.
 * - Metodi di supporto per la scansione di identificatori, numeri e caratteri speciali.
 * 
 * La classe gestisce anche eccezioni LessicaleException per segnalare errori durante l'analisi lessicale.
 * 
 * L'analizzatore lessicale implementato in questa classe è fondamentale per l'interpretazione del codice sorgente
 * durante la compilazione o l'esecuzione di programmi nel linguaggio specifico.
 */



import java.io.FileReader;
import java.io.IOException;
import java.io.PushbackReader;
import java.io.FileNotFoundException;
import java.util.HashMap;
import token.Token;
import token.TokenType;
import eccezioni.LessicaleException;

public class Scanner {
    final char EOF = (char) -1;
    private int riga;
    private PushbackReader buffer;
    private Token nextToken;

    private String skpChars = " \t\n\r";
    private String letters = "abcdefghijklmnopqrstuvwxyz";
    private String digits = "0123456789";

    private HashMap<Character, TokenType> charTypeMap;
    private HashMap<Character, TokenType> opTypeMap;
    private HashMap<String, TokenType> keywordsMap;

    public Scanner(String fileName) throws FileNotFoundException {
        try {
            this.buffer = new PushbackReader(new FileReader(fileName));
            riga = 1;
            initializeMaps();
        } catch (FileNotFoundException e) {
            throw new FileNotFoundException("File not found: " + fileName);
        }
    }

    private void initializeMaps() {
        charTypeMap = new HashMap<>();
        charTypeMap.put('+', TokenType.PLUS);
        charTypeMap.put('-', TokenType.MINUS);
        charTypeMap.put('*', TokenType.TIMES);
        charTypeMap.put('/', TokenType.DIVIDE);
        charTypeMap.put(';', TokenType.SEMI);
        charTypeMap.put('=', TokenType.OP_ASSIGN);

        opTypeMap = new HashMap<>();
        opTypeMap.put('+', TokenType.PLUS);
        opTypeMap.put('-', TokenType.MINUS);
        opTypeMap.put('*', TokenType.TIMES);
        opTypeMap.put('/', TokenType.DIVIDE);

        keywordsMap = new HashMap<>();
        keywordsMap.put("print", TokenType.PRINT);
        keywordsMap.put("float", TokenType.TYFLOAT);
        keywordsMap.put("int", TokenType.TYINT);
    }

    public Token peekToken() throws LessicaleException, IOException {
        if (this.nextToken == null) {
            this.nextToken = nextToken();
        }
        return this.nextToken;
    }

    /**
     * Metodo che analizza il codice sorgente e restituisce il token successivo.
     * Se il metodo è chiamato per la prima volta o è stato chiamato peekToken() in precedenza,
     * ritorna il token precedentemente ottenuto da peekToken() senza consumare ulteriormente il carattere.
     * Altrimenti, legge il carattere successivo dal buffer di input e lo analizza per determinare il tipo di token.
     * 
     * @return Il token successivo estratto dal codice sorgente.
     * @throws LessicaleException Se si verifica un errore durante l'analisi lessicale del codice sorgente.
     */
    
    public Token nextToken() throws LessicaleException {
        try {
            char nextChar = peekChar();

         // Controllo se è già stato letto un token precedentemente senza consumare il carattere
            if (this.nextToken != null) {
                Token temp = this.nextToken;
                this.nextToken = null;
                return temp;
            }

         // Legge il carattere successivo e ignora eventuali caratteri di spaziatura
            while (skpChars.indexOf(nextChar) != -1) {
                if (nextChar == '\n') {
                    riga++;
                }
                readChar();
                nextChar = peekChar();
            }

            // Gestisce il caso di fine del file
            if (nextChar == EOF) {
                return new Token(TokenType.EOF, riga, null);
            }

         // Identifica il tipo di token in base al carattere letto
            if (letters.indexOf(nextChar) != -1) {
                return scanId();
            }

            if (charTypeMap.containsKey(nextChar)) {
                TokenType operatorType = charTypeMap.get(nextChar);
                readChar();

                // Modifica: Considera anche gli operatori di assegnazione come += senza spazi
                if ((nextChar == '+' || nextChar == '-' || nextChar == '*' || nextChar == '/')
                        && peekChar() == '=') {
                    readChar();
                    return new Token(TokenType.OP_ASSIGN, riga, String.valueOf(nextChar) + "=");
                } else {
                    return new Token(operatorType, riga, String.valueOf(nextChar));
                }
            }

            if (digits.indexOf(nextChar) != -1) {
                return scanNumber();
            }

         // Gestisce caratteri non validi generando un'eccezione lessicale
            throw new LessicaleException("Illegal character at line " + riga + ": " + nextChar);

        } catch (IOException e) {
            throw new LessicaleException("IOException occurred", e);
        }
    }

    private Token scanNumber() throws IOException, LessicaleException {
        StringBuilder number = new StringBuilder();

        while (Character.isDigit(peekChar())) {
            number.append(readChar());
        }

        if (peekChar() == '.') {
            number.append(readChar());

            while (Character.isDigit(peekChar())) {
                number.append(readChar());
            }

            return new Token(TokenType.FLOAT, riga, number.toString());
        } else {
            return new Token(TokenType.INT, riga, number.toString());
        }
    }

    private Token scanId() throws IOException {
        StringBuilder identifier = new StringBuilder();

        while (Character.isLetterOrDigit(peekChar())) {
            identifier.append(readChar());
        }

        String identifierStr = identifier.toString();
        if (keywordsMap.containsKey(identifierStr)) {
            return new Token(keywordsMap.get(identifierStr), riga, identifierStr);
        } else {
            return new Token(TokenType.ID, riga, identifierStr);
        }
    }

    private char readChar() throws IOException {
        return (char) this.buffer.read();
    }

    private char peekChar() throws IOException {
        char c = (char) buffer.read();
        buffer.unread(c);
        return c;
    }

    public Token getNextToken() {
        return nextToken;
    }
}
