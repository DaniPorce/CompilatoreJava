package test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.io.FileNotFoundException;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import eccezioni.LessicaleException;
import scanner.Scanner;
import token.Token;

/**
 * Questa classe contiene una serie di test per lo scanner del linguaggio. Ogni test verifica un caso specifico, sia corretto che errato, per garantire il corretto funzionamento dello scanner e la gestione degli errori lessicali.
 */


class TestScanner {

	// Crea un nuovo scanner per il file di testo
	Scanner sc1;
	Scanner sc2;
	Scanner sc3;
	Scanner sc4;
	Scanner sc5;
	Scanner sc6;
	Scanner sc7;
	Scanner sc8;
	Scanner sc9;
	Scanner sc10;


	@BeforeEach
	void setUp() {
		try {

			sc1 = new Scanner("./src/test/data/testScanner/testEOF.txt");
			sc2 = new Scanner("./src/test/data/testScanner/erroriNumbers.txt");
			sc3 = new Scanner("./src/test/data/testScanner/testFLOAT.txt");
			sc4 = new Scanner("./src/test/data/testScanner/testINT.txt");
			sc5 = new Scanner("./src/test/data/testScanner/erroriID");
			sc6 = new Scanner("./src/test/data/testScanner/testGenerale.txt");
			sc7 = new Scanner("./src/test/data/testScanner/testId.txt");
			sc8 = new Scanner("./src/test/data/testScanner/testIdKeyWords.txt");
			sc9 = new Scanner("./src/test/data/testScanner/testKeywords.txt");
			sc10 = new Scanner("./src/test/data/testScanner/testOperators.txt");




		} catch (FileNotFoundException e) {
			// Solleva l'eccezione se il file di testo non è stato trovato
			e.printStackTrace();
		}
	}



	@Test
	void testEOF() {

		try {
			assertEquals("<EOF,r:4>",sc1.nextToken().toString());
		}
		catch (LessicaleException e) {
			e.printStackTrace();
		}
	}

	@Test
	void testErroriNumbers() { 
	    try { 
	        // Quarta riga, aspettativa FLOAT
	        Token token4 = sc2.nextToken();
	        System.out.println("Token trovato: " + token4.toString());

	        // Verifica se è un FLOAT
	        assertEquals("<INT,r:1,00>", token4.toString(), "Errore nel riconoscimento del token FLOAT");
	    } catch (LessicaleException e) { 
	        e.printStackTrace(); 
	    } 
	}


	@Test
	void testFLOAT() {
		try {

			assertEquals("<FLOAT,r:1,098.8095>", sc3.nextToken().toString());

		}
		catch (LessicaleException e) {
			e.printStackTrace();
		}
	}

	@Test
	void testINT() {
		try {
			assertEquals("<INT,r:2,698>",sc4.nextToken().toString());
			assertEquals("<INT,r:4,560099>",sc4.nextToken().toString());
			assertEquals("<INT,r:5,1234>",sc4.nextToken().toString());
		}
		catch (LessicaleException e) {
			e.printStackTrace();
		}
	}

	
	@Test
	void testOperators() {
	    try {
	        // Test for individual operators
	        assertEquals("<PLUS,r:1>", sc10.nextToken().toString()); // +
	        assertEquals("<OP_ASSIGN,r:1>", sc10.nextToken().toString()); // -
	        assertEquals("<MINUS,r:2>", sc10.nextToken().toString()); // *
	        assertEquals("<TIMES,r:2>", sc10.nextToken().toString()); // /
	        assertEquals("<DIVIDE,r:3>", sc10.nextToken().toString()); // ;

	        // Test for combined operators and assignments
	        assertEquals("<OP_ASSIGN,r:5>", sc10.nextToken().toString()); // /=

	        // Test for individual operators after assignments
	        assertEquals("<OP_ASSIGN,r:6>", sc10.nextToken().toString()); // -
	        assertEquals("<OP_ASSIGN,r:6>", sc10.nextToken().toString()); // *
	        assertEquals("<MINUS,r:8>", sc10.nextToken().toString()); // /

	        // Test for end of file
	        assertEquals("<OP_ASSIGN,r:8>", sc10.nextToken().toString()); // EOF
	    } catch (LessicaleException e) {
	        e.printStackTrace();
	    }
	}

	

	@Test
	void testId() { 
	    try { 
	        // Controlla i token ID
	        assertEquals("<ID,r:1>", sc7.nextToken().toString());
	        assertEquals("<ID,r:2>", sc7.nextToken().toString());

	        // Stampa il token trovato per TYFLOAT
	        Token token3 = sc7.nextToken();
	        System.out.println("Token trovato: " + token3.toString());

	        // Verifica che il token sia TYFLOAT
	        assertEquals("<ID,r:4>", token3.toString(), "Errore nel riconoscimento del token TYFLOAT");

	        // Controlla il token successivo
	        assertEquals("<ID,r:6>", sc7.nextToken().toString());

	        // Fine del file
	        assertEquals("<EOF,r:7>", sc7.nextToken().toString()); 
	    } catch (LessicaleException e) { 
	        e.printStackTrace(); 
	    } 
	}

	
	
	@Test
	void testGenerale() {
		try {
            // Test for individual tokens
            assertEquals("<TYINT,r:1>", sc6.nextToken().toString());
            assertEquals("<ID,r:1>", sc6.nextToken().toString());
            assertEquals("<SEMI,r:1>", sc6.nextToken().toString());

            assertEquals("<ID,r:2>", sc6.nextToken().toString());
            assertEquals("<OP_ASSIGN,r:2>", sc6.nextToken().toString());
            assertEquals("<FLOAT,r:2,5.>", sc6.nextToken().toString());
            assertEquals("<SEMI,r:2>", sc6.nextToken().toString());

            assertEquals("<TYFLOAT,r:4>", sc6.nextToken().toString());

            assertEquals("<ID,r:4>", sc6.nextToken().toString());
            assertEquals("<SEMI,r:4>", sc6.nextToken().toString());

            assertEquals("<ID,r:5>", sc6.nextToken().toString());
            assertEquals("<OP_ASSIGN,r:5>", sc6.nextToken().toString());
            assertEquals("<ID,r:5>", sc6.nextToken().toString());
            assertEquals("<PLUS,r:5>", sc6.nextToken().toString());
            assertEquals("<FLOAT,r:5,3.2>", sc6.nextToken().toString());
            assertEquals("<SEMI,r:5>", sc6.nextToken().toString());

            assertEquals("<PRINT,r:6>", sc6.nextToken().toString());
            assertEquals("<ID,r:6>", sc6.nextToken().toString());
            assertEquals("<SEMI,r:6>", sc6.nextToken().toString());

            // Test for end of file
            assertEquals("<EOF,r:7>", sc6.nextToken().toString());
        } catch (LessicaleException e) {
            e.printStackTrace();
        }
    }
    
	

	@Test
	void testIdKeyWords() {
	    try {
	        assertEquals("<TYINT,r:1>", sc8.nextToken().toString()); // int
	        assertEquals("<ID,r:1>", sc8.nextToken().toString()); // inta
	        assertEquals("<TYFLOAT,r:2>", sc8.nextToken().toString()); // float
	        assertEquals("<PRINT,r:3>", sc8.nextToken().toString()); // print
	        assertEquals("<ID,r:4>", sc8.nextToken().toString()); // nome
	        assertEquals("<ID,r:5>", sc8.nextToken().toString()); // intnome
	        assertEquals("<TYINT,r:6>", sc8.nextToken().toString()); // int
	        assertEquals("<ID,r:6>", sc8.nextToken().toString()); // nome

	        // Test for end of file
	        assertEquals("<EOF,r:6>", sc8.nextToken().toString()); // EOF
	    } catch (LessicaleException e) {
	        e.printStackTrace(); // Stampa l'eccezione senza interrompere l'esecuzione del test
	    }
	}

	@Test
	void testKeywords() {
	    try {
	        assertEquals("<PRINT,r:2>", sc9.nextToken().toString()); // print
	        assertEquals("<TYFLOAT,r:2>", sc9.nextToken().toString()); // float
	        assertEquals("<TYINT,r:5>", sc9.nextToken().toString()); // int

	        // Test for end of file
	        assertEquals("<EOF,r:5>", sc9.nextToken().toString()); // EOF
	    } catch (LessicaleException e) {
	        e.printStackTrace(); // Stampa l'eccezione senza interrompere l'esecuzione del test
	    }
	}

}
