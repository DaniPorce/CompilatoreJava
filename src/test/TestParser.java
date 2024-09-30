package test;
import static org.junit.jupiter.api.Assertions.*;
import java.io.FileNotFoundException;
import java.io.IOException;

// Resto del codice del test

import org.junit.jupiter.api.Test;
import AST.NodeProgram;
import eccezioni.*;
import parser.Parser;
import scanner.Scanner;



/**
 * Questa classe contiene una serie di test per il parser del linguaggio. Ogni test verifica un caso specifico, sia corretto che errato, per garantire il corretto funzionamento del parser e la gestione degli errori sintattici.
 */


class TestParser {
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
	Scanner sc11;

	{
		try {
			sc1 = new Scanner("./src/test/data/testParser/testParserCorretto1.txt");
			sc2 = new Scanner("./src/test/data/testParser/testParserCorretto2.txt");
			sc3 = new Scanner("./src/test/data/testParser/testParserEcc_0.txt");
			sc4 = new Scanner("./src/test/data/testParser/testParserEcc_1.txt");
			sc5 = new Scanner("./src/test/data/testParser/testParserEcc_2.txt");
			sc6 = new Scanner("./src/test/data/testParser/testParserEcc_3.txt");
			sc7 = new Scanner("./src/test/data/testParser/testParserEcc_4.txt");
			sc8 = new Scanner("./src/test/data/testParser/testParserEcc_5.txt");
			sc9 = new Scanner("./src/test/data/testParser/testParserEcc_6.txt");
			sc10 = new Scanner("./src/test/data/testParser/testParserEcc_7.txt");
			

		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
	}
	Parser p1 = new Parser(sc1);
	Parser p2 = new Parser(sc2);
	Parser p3 = new Parser(sc3);
	Parser p4 = new Parser(sc4);
	Parser p5 = new Parser(sc5);
	Parser p6 = new Parser(sc6);
	Parser p7 = new Parser(sc7);
	Parser p8 = new Parser(sc8);
	Parser p9 = new Parser(sc9);
	Parser p10 = new Parser(sc10);
	Parser p11 = new Parser(sc11);


	
	@Test
	void testParserCorretto1() throws IOException {
	    try {
	        NodeProgram node = p1.parse(); // Utilizzo del parser p1 invece di p2

	        // Test input
	        System.out.println("print stampa;\n" +
	                "float numberfloat;\n" +
	                "int floati;\n" +
	                "a += 5 + 3;\n" +
	                "b = a + 5;");

	        // Output formatted tree structure
	        System.out.println(node.toStringFormatted());

	        // Asserting the expected tree structure
	        assertEquals(node.toString(), "NodeProgram [decSts=[AST.NodeAssing@7674f035, AST.NodeAssing@69e153c5, AST.NodeDecl@173ed316, AST.NodeDecl@25ce9dc4, <PRINT AST.NodeId@74ea2410>]]");
	    } catch (SintatticaExeption e) {
	        e.printStackTrace();
	    }
	}
	
	@Test
	void testParserCorretto2() throws IOException {
	    try {
	        NodeProgram node = p2.parse();

	        // Test input
	        System.out.println("float num;\n" +
	                "int id = 99/2;\n" +
	                "num = 5;\n" +
	                "num = id;\n" +
	                "num *= id + 5.0;\n" +
	                "id /= 5;\n" +
	                "num = id * id;\n" +
	                "id += 5 - 8 * 6.0 / 2;\n" +
	                "num = id * 5 - 8.0 * 6 + 2;\n" +
	                "print num;\n" +
	                "print id;");

	        // Output formatted tree structure
	        System.out.println(node.toStringFormatted());
	        
	        assertEquals(node.toString(), "NodeProgram [decSts=[<PRINT AST.NodeId@17f62e33>, <PRINT AST.NodeId@76b1e9b8>, AST.NodeAssing@27406a17, AST.NodeAssing@2af004b, AST.NodeAssing@248e319b, AST.NodeAssing@5d0bf09b, AST.NodeAssing@793f29ff, AST.NodeAssing@3e8c3cb, AST.NodeAssing@563f38c4, AST.NodeDecl@543295b0, AST.NodeDecl@54422e18]]");

	       
	    } catch (SintatticaExeption e) {
	        e.printStackTrace();
	    }
	}


	@Test
	void testParserEcc_0() {
	    // errore sintattico dovuto alla presenza di un token inaspettato
	    Throwable e1 = assertThrows(SintatticaExeption.class, () -> p3.parse());
	    assertEquals(e1.getMessage(), "Aspettavo OP_ASSIGN alla riga 1 ma è SEMI.");


	}

	
	@Test
	void testParserEcc_1() {
	    // errore sintattico dovuto alla presenza di due operatori uno dopo l'altro
	    Throwable e = assertThrows(SintatticaExeption.class, () -> p4.parse());
	    assertEquals(e.getMessage(), "Il token TIMES alla riga 2 non è un Tr, dovrebbe essere ID, FLOAT o INT.");

	}


	@Test
	void testParserEcc_2() {
	    //errore sintattico dovuto alla assenza di un numero dopo un operatore con conseguente il SEMICOLON
	    Throwable e1 = assertThrows(SintatticaExeption.class, () -> p5.parse());
	    assertEquals(e1.getMessage(), "Il token INT alla riga 3 non è un DSs, dovrebbe essere TYFLOAT, TYINT, ID, PRINT o EOF.");
	}


	@Test
	void testParserEcc_3() {
	    //errore sintattico dovuto alla assenza di operazione prima del punto e virgola, quindi elimino il no-sense;
	    Throwable e1 = assertThrows(SintatticaExeption.class, () -> p6.parse());
	    assertEquals(e1.getMessage(), "Aspettavo OP_ASSIGN alla riga 2 ma è PLUS.");
	}


	@Test
	void testParserEcc_4() {
	    //errore sintattico dovuto alla assenza di operazione prima del punto e virgola, quindi elimino il no-sense;
	    Throwable e1 = assertThrows(SintatticaExeption.class, () -> p7.parse());
	    assertEquals(e1.getMessage(), "Aspettavo ID alla riga 2 ma è INT.");
	}

	
	@Test
	void testParserEcc_5() {
	    //errore sintattico dovuto alla assenza di operazione prima del punto e virgola, quindi elimino il no-sense;
	    Throwable e1 = assertThrows(SintatticaExeption.class, () -> p8.parse());
	    assertEquals(e1.getMessage(), "Aspettavo ID alla riga 3 ma è INT.");
	}

	
	@Test
	void testParserEcc_6() {
	    //errore sintattico dovuto alla assenza di operazione prima del punto e virgola, quindi elimino il no-sense;
	    Throwable e1 = assertThrows(SintatticaExeption.class, () -> p9.parse());
	    assertEquals(e1.getMessage(), "Aspettavo ID alla riga 4 ma è TYFLOAT.");
	}

	
	@Test
	void testParserEcc_7() {
		//errore sintattico dovuto alla assenza di operazione prima del punto e virgola, quindi elimino il no-sense;
		Throwable e1 = assertThrows(SintatticaExeption.class,() -> p10.parse());
		assertEquals(e1.getMessage(),"Aspettavo ID alla riga 2 ma è OP_ASSIGN.");
	}
	
	
	          


}
