package test;

import static org.junit.jupiter.api.Assertions.*;

import java.io.IOException;
import java.io.StringReader;

import org.junit.jupiter.api.Test;
import visitor.TypeCheckingVisitor;
import AST.NodeDecSt;
import AST.NodeProgram;
import AST.TypeDescriptor;
import SymbolTable.SymbolTable;
import parser.Parser;
import scanner.Scanner;
import eccezioni.SintatticaExeption;

/**
 * Questa classe contiene una serie di test per la classe TypeCheckingVisitor che esegue il controllo dei tipi.
 */


class TypeCheckingVisitorTest {

	@Test
	void testDuplicateDeclaration() throws IOException {
	    String inputFilePath = "./src/test/data/testTypeChecking/1_dicRipetute.txt";

	    try {
	        Scanner scanner = new Scanner(inputFilePath);
	        Parser parser = new Parser(scanner);
	        NodeProgram rootNode = parser.parse();

	        TypeCheckingVisitor typeChecker = new TypeCheckingVisitor();
	        rootNode.accept(typeChecker); // Modifica qui per visitare l'intero programma

	        // Check the overall result for the program
	        assertEquals(TypeDescriptor.ERROR, rootNode.getResType());

	    } catch (SintatticaExeption e) {
	        e.printStackTrace();
	        fail("Exception thrown during parsing");
	    }
	}
	    
	    @Test
	    void testUndeclaredVariable() throws IOException {
	        String inputFilePath = "./src/test/data/testTypeChecking/2_idNonDec.txt";

	        try {
	            Scanner scanner = new Scanner(inputFilePath);
	            Parser parser = new Parser(scanner);
	            NodeProgram rootNode = parser.parse();

	            TypeCheckingVisitor typeChecker = new TypeCheckingVisitor();
	            rootNode.accept(typeChecker);

	            // Check the log for undeclared variable
	            String log = typeChecker.getLog();
	            assertTrue(log.contains("PRINT: l'ID 'b' non é definito"));

	            // Check the overall result for the program
	            assertEquals(TypeDescriptor.ERROR, rootNode.getResType());
	        } catch (SintatticaExeption e) {
	            e.printStackTrace();
	            fail("Exception thrown during parsing");
	        }
	    }
	    
	    @Test
	    void testVariableOperations() throws IOException {
	        String inputFilePath = "./src/test/data/testTypeChecking/3_idNonDec";

	        try {
	            Scanner scanner = new Scanner(inputFilePath);
	            Parser parser = new Parser(scanner);
	            NodeProgram rootNode = parser.parse();

	            TypeCheckingVisitor typeChecker = new TypeCheckingVisitor();
	            rootNode.accept(typeChecker);

	            // Check the overall result for the program
	            assertEquals(TypeDescriptor.OK, rootNode.getResType());

	        } catch (SintatticaExeption e) {
	            e.printStackTrace();
	            fail("Exception thrown during parsing");
	        }
	    }
	    
	    @Test
	    void testCompoundAssignment() throws IOException {
	        String inputFilePath = "./src/test/data/testTypeChecking/4_tipoNonCompatibile.txt";

	        try {
	            Scanner scanner = new Scanner(inputFilePath);
	            Parser parser = new Parser(scanner);
	            NodeProgram rootNode = parser.parse();

	            TypeCheckingVisitor typeChecker = new TypeCheckingVisitor();
	            rootNode.accept(typeChecker);

	            // Verify the overall result for the program
	            assertEquals(TypeDescriptor.ERROR, rootNode.getResType());

	        } catch (SintatticaExeption e) {
	            e.printStackTrace();
	            fail("Exception thrown during parsing");
	        }
	    }
	    
	    @Test
	    void testArithmeticOperations() throws IOException {
	        String inputFilePath = "./src/test/data/testTypeChecking/5_corretto.txt";

	        try {
	            Scanner scanner = new Scanner(inputFilePath);
	            Parser parser = new Parser(scanner);
	            NodeProgram rootNode = parser.parse();

	            TypeCheckingVisitor typeChecker = new TypeCheckingVisitor();
	            rootNode.accept(typeChecker);

	            // Verify the overall result for the program
	            assertEquals(TypeDescriptor.ERROR, rootNode.getResType());

	        } catch (SintatticaExeption e) {
	            e.printStackTrace();
	            fail("Exception thrown during parsing");
	        }
	    }
	    
	    @Test
	    void testArithmeticOperations2() throws IOException {
	        String inputFilePath = "./src/test/data/testTypeChecking/6_corretto.txt";

	        try {
	            Scanner scanner = new Scanner(inputFilePath);
	            Parser parser = new Parser(scanner);
	            NodeProgram rootNode = parser.parse();

	            TypeCheckingVisitor typeChecker = new TypeCheckingVisitor();
	            rootNode.accept(typeChecker);

	            // Verify the overall result for the program
	            assertEquals(TypeDescriptor.ERROR, rootNode.getResType());

	        } catch (SintatticaExeption e) {
	            e.printStackTrace();
	            fail("Exception thrown during parsing");
	        }
	    }
	    
	    @Test
	    void testArithmeticOperations3() throws IOException {
	        String inputFilePath = "./src/test/data/testTypeChecking/7_corretto.txt";

	        try {
	            Scanner scanner = new Scanner(inputFilePath);
	            Parser parser = new Parser(scanner);
	            NodeProgram rootNode = parser.parse();

	            TypeCheckingVisitor typeChecker = new TypeCheckingVisitor();
	            rootNode.accept(typeChecker);

	            // Verify the overall result for the program
	            assertEquals(TypeDescriptor.ERROR, rootNode.getResType());

	        } catch (SintatticaExeption e) {
	            e.printStackTrace();
	            fail("Exception thrown during parsing");
	        }
	    }






}
