package parser;

/**
 * Questo package contiene la classe Parser, che implementa un parser per il linguaggio specificato.
 * Il parser converte il codice sorgente del linguaggio in un AST (Abstract Syntax Tree) che rappresenta
 * la struttura del programma.
 * 
 * Parser offre il metodo parse() che avvia il processo di parsing, e metodi ausiliari per analizzare
 * i diversi elementi del linguaggio, come dichiarazioni, istruzioni, espressioni, etc.
 * 
 * Il parser si basa sullo scanner (Scanner) per ottenere i token dal codice sorgente.
 * 
 * Questo parser è responsabile della gestione delle eccezioni sintattiche e degli errori di parsing,
 * segnalando eventuali errori nel codice sorgente e interrompendo il processo di parsing in caso di
 * incongruenze o errori.
 */



import java.io.IOException;
import java.util.ArrayList;

import AST.LangOper;
import AST.LangType;
import AST.NodeAssing;
import AST.NodeBinOp;
import AST.NodeCost;
import AST.NodeDecSt;
import AST.NodeDecl;
import AST.NodeDeref;
import AST.NodeExpr;
import AST.NodeId;
import AST.NodePrint;
import AST.NodeProgram;
import AST.NodeStm;
import eccezioni.LessicaleException;
import eccezioni.SintatticaExeption;
import scanner.Scanner;
import token.Token;
import token.TokenType;


public class Parser {

	private Scanner scanner;

	public Parser(Scanner scanner) {
		this.scanner = scanner;
	}

	public NodeProgram parse() throws SintatticaExeption, IOException  {
		return this.parsePrg();
	}

	private Token match(TokenType tipo) throws IOException, SintatticaExeption  {
		Token t;
		try {
			t = scanner.peekToken();
		} catch (LessicaleException e) {
			throw new SintatticaExeption(e.getMessage());
		}

		if (tipo.equals(t.getType())) {
			try {
				return scanner.nextToken();
			} catch (LessicaleException e) {
				throw new SintatticaExeption(e.getMessage());
			}
		} else {
			throw new SintatticaExeption(
					"Aspettavo " + tipo.toString() + " alla riga " + t.getRiga() + " ma è " + t.getType() + ".");
		}
	}

	private NodeProgram parsePrg() throws SintatticaExeption, IOException {
		Token t = null;

		try {
			t = scanner.peekToken();
		} catch (LessicaleException e) {
			throw new SintatticaExeption(e.getMessage());
		}

		switch (t.getType()) {
		// Prg -> DSs $
		case TYFLOAT, TYINT, ID, PRINT, EOF -> {
			ArrayList<NodeDecSt> prg = parseDSs();
			match(TokenType.EOF);
			return new NodeProgram(prg);
		}
		default -> {
			throw new SintatticaExeption(
					"Il token " + t.getType() + " alla riga " + t.getRiga() + " non è l'inizio del programma.");
		}
		}
	}

	private ArrayList<NodeDecSt> parseDSs() throws SintatticaExeption, IOException {
		Token t;

		try {
			t = scanner.peekToken();
		} catch (LessicaleException e) {
			throw new SintatticaExeption(e.getMessage());
		}

		switch (t.getType()) {
		// DSs -> Dcl DSs
		case TYFLOAT, TYINT -> {
			NodeDecl decl = parseDcl();
			ArrayList<NodeDecSt> decSts = parseDSs();
			decSts.add(decl);
			return decSts;
		}
		// DSs -> Stm DSs
		case ID, PRINT -> {
			NodeStm stm = parseStm();
			ArrayList<NodeDecSt> decSts = parseDSs();
			decSts.add(stm);
			return decSts;
		}
		// DSs -> ϵ
		case EOF -> {
			return new ArrayList<NodeDecSt>();
		}
		default -> {
			throw new SintatticaExeption("Il token " + t.getType() + " alla riga " + t.getRiga()
					+ " non è un DSs, dovrebbe essere TYFLOAT, TYINT, ID, PRINT o EOF.");
		}
		}
	}

	private NodeDecl parseDcl() throws SintatticaExeption, IOException {
		Token t;

		try {
			t = scanner.peekToken();
		} catch (LessicaleException e) {
			throw new SintatticaExeption(e.getMessage());
		}

		switch (t.getType()) {
		// Dcl -> Ty id DclP
		case TYFLOAT, TYINT -> {
			LangType ty = parseTy();
			String id = match(TokenType.ID).getVal();
			NodeExpr dclP = parseDclP();
			return new NodeDecl(new NodeId(id), ty, dclP);
		}
		default -> {
			throw new SintatticaExeption("Il token " + t.getType() + " alla riga " + t.getRiga()
					+ " non è un Dcl, dovrebbe essere TYFLOAT o TYINT.");
		}
		}
	}

	private LangType parseTy() throws SintatticaExeption, IOException {
		Token t;

		try {
			t = scanner.peekToken();
		} catch (LessicaleException e) {
			throw new SintatticaExeption(e.getMessage());
		}

		switch (t.getType()) {
		// Ty -> float
		case TYFLOAT -> {
			match(TokenType.TYFLOAT);
			return LangType.TYFLOAT;
		}
		// Ty -> int
		case TYINT -> {
			match(TokenType.TYINT);
			return LangType.TYINT;
		}
		default -> {
			throw new SintatticaExeption("Il token " + t.getType() + " alla riga " + t.getRiga()
					+ " non è un Stm, dovrebbe essere TYFLOAT o TYINT.");
		}
		}
	}

	// TODO
	private NodeExpr parseDclP() throws SintatticaExeption, IOException {
		Token t;

		try {
			t = scanner.peekToken();
		} catch (LessicaleException e) {
			throw new SintatticaExeption(e.getMessage());
		}

		switch (t.getType()) {
		// DclP -> ;
		case SEMI -> {
			match(TokenType.SEMI);
			return null;
		}
		// DclP -> opAssign Exp ;
		case OP_ASSIGN -> {
			match(TokenType.OP_ASSIGN);
			NodeExpr exp = parseExp();
			match(TokenType.SEMI);
			return exp;
		}
		default -> {
			throw new SintatticaExeption("Il token " + t.getType() + " alla riga " + t.getRiga()
					+ " non è un DclP, dovrebbe essere SEMI o OP_ASSIGN.");
		}
		}
	}

	private NodeStm parseStm() throws SintatticaExeption, IOException {
		Token t;

		try {
			t = scanner.peekToken();
		} catch (LessicaleException e) {
			throw new SintatticaExeption(e.getMessage());
		}

		switch (t.getType()) {
		// Stm -> id opAssign Exp ;
		case ID -> {
			String id = match(TokenType.ID).getVal();
			match(TokenType.OP_ASSIGN);
			NodeExpr exp = parseExp();
			match(TokenType.SEMI);
			return new NodeAssing(new NodeId(id), exp);
		}
		// Stm -> print id ;
		case PRINT -> {
			match(TokenType.PRINT);
			String id = match(TokenType.ID).getVal();
			match(TokenType.SEMI);
			return new NodePrint(new NodeId(id));
		}
		default -> {
			throw new SintatticaExeption("Il token " + t.getType() + " alla riga " + t.getRiga()
					+ " non è un Stm, dovrebbe essere ID o PRINT.");
		}
		}
	}

	private NodeExpr parseExp() throws SintatticaExeption, IOException {
		Token t;

		try {
			t = scanner.peekToken();
		} catch (LessicaleException e) {
			throw new SintatticaExeption(e.getMessage());
		}

		switch (t.getType()) {
		// Exp -> Tr ExpP
		case ID, FLOAT, INT -> {
			NodeExpr tr = parseTr();
			NodeExpr expP = parseExpP(tr);
			return expP;
		}
		default -> {
			throw new SintatticaExeption("Il token " + t.getType() + " alla riga " + t.getRiga()
					+ " non è un Exp, dovrebbe essere ID, FLOAT o INT.");
		}
		}
	}

	private NodeExpr parseExpP(NodeExpr left) throws SintatticaExeption, IOException {
		Token t;

		try {
			t = scanner.peekToken();
		} catch (LessicaleException e) {
			throw new SintatticaExeption(e.getMessage());
		}

		switch (t.getType()) {
		// Exp -> + Tr ExpP
		case PLUS -> {
			match(TokenType.PLUS);
			NodeExpr tr = parseTr();
			NodeExpr expP = parseExpP(tr);
			return new NodeBinOp(LangOper.PLUS, left, expP);
		}
		// Exp -> - Tr ExpP
		case MINUS -> {
			match(TokenType.MINUS);
			NodeExpr tr = parseTr();
			NodeExpr expP = parseExpP(tr);
			return new NodeBinOp(LangOper.MINUS, left, expP);
		}
		// Exp -> ϵ
		case SEMI -> {
			return left;
		}
		default -> {
			throw new SintatticaExeption("Il token " + t.getType() + " alla riga " + t.getRiga()
					+ " non è un ExpP, dovrebbe essere PLUS, MINUS o SEMI.");
		}
		}
	}

	private NodeExpr parseTr() throws SintatticaExeption, IOException {
		Token t;

		try {
			t = scanner.peekToken();
		} catch (LessicaleException e) {
			throw new SintatticaExeption(e.getMessage());
		}

		switch (t.getType()) {
		// Tr -> Val TrP
		case ID, FLOAT, INT -> {
			NodeExpr val = parseVal();
			NodeExpr trP = parseTrP(val);
			return trP;
		}
		default -> {
			throw new SintatticaExeption("Il token " + t.getType() + " alla riga " + t.getRiga()
					+ " non è un Tr, dovrebbe essere ID, FLOAT o INT.");
		}
		}
	}

	private NodeExpr parseTrP(NodeExpr left) throws SintatticaExeption, IOException {
		Token t;

		try {
			t = scanner.peekToken();
		} catch (LessicaleException e) {
			throw new SintatticaExeption(e.getMessage());
		}

		switch (t.getType()) {
		// TrP -> * Val TrP
		case TIMES -> {
			match(TokenType.TIMES);
			NodeExpr val = parseVal();
			NodeExpr trP = parseTrP(val);
			return new NodeBinOp(LangOper.TIMES, left, trP);
		}
		// TrP -> / Val TrP
		case DIVIDE -> {
			match(TokenType.DIVIDE);
			NodeExpr val = parseVal();
			NodeExpr trP = parseTrP(val);
			return new NodeBinOp(LangOper.DIV, left, trP);
		}
		// TrP -> ϵ
		case MINUS, PLUS, SEMI -> {
			return left;
		}
		default -> {
			throw new SintatticaExeption("Il token " + t.getType() + " alla riga " + t.getRiga()
					+ " non è un TrP, dovrebbe essere TIMES, DIVIDE, PLUS, MINUS o SEMI.");
		}
		}
	}

	private NodeExpr parseVal() throws SintatticaExeption, IOException {
		Token t;

		try {
			t = scanner.peekToken();
		} catch (LessicaleException e) {
			throw new SintatticaExeption(e.getMessage());
		}

		switch (t.getType()) {
		// Val -> intVal
		case INT -> {
			String intVal = match(TokenType.INT).getVal();
			return new NodeCost(intVal, LangType.TYINT);
		}
		// Val -> floatVal
		case FLOAT -> {
			String floatVal = match(TokenType.FLOAT).getVal();
			return new NodeCost(floatVal, LangType.TYFLOAT);
		}
		// Val -> id
		case ID -> {
			String id = match(TokenType.ID).getVal();
			return new NodeDeref(new NodeId(id));
		}
		default -> {
			throw new SintatticaExeption("Il token " + t.getType() + " alla riga " + t.getRiga()
					+ " non è un Val, dovrebbe essere INT, FLOAT o ID.");
		}
		}
	}

}