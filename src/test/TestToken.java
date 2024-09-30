package test;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

import token.Token;
import token.TokenType;

/**
 * Questa classe contiene una serie di test per la classe Token che rappresenta i token del linguaggio.
 */



class TestToken {

	@Test
	void test() {
		Token tok3  = new Token(TokenType.FLOAT,1,"1.0"); // test token numero float
		Token tok5  = new Token(TokenType.INT,1,"1");     // test token int
		Token tok6  = new Token(TokenType.MINUS,1);			//test token operatore meno
		Token tok7  = new Token(TokenType.PLUS,1);				//test token operatore piu
		Token tok1  = new Token(TokenType.DIVIDE,1); 				//test token operatore diviso
		Token tok10 = new Token(TokenType.TIMES,1);			// test token operatore moltiplicazione
		Token tok9  = new Token(TokenType.SEMI,1);				// test token  puntovirgola
		Token tok2  = new Token(TokenType.EOF,5);				// test token  puntovirgola
		assertEquals("<DIVIDE,r:1>",tok1.toString());
		assertEquals("<EOF,r:5>",tok2.toString());
		assertEquals("<FLOAT,r:1,1.0>",tok3.toString());
		assertEquals("<INT,r:1,1>",tok5.toString());
		assertEquals("<MINUS,r:1>",tok6.toString());
		assertEquals("<PLUS,r:1>",tok7.toString());
		assertEquals("<SEMI,r:1>",tok9.toString());
		assertEquals("<TIMES,r:1>",tok10.toString());
	}

}
