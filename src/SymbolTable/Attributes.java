package SymbolTable;

/**
 * Questa classe rappresenta gli attributi associati a un'identificatore nella Symbol Table.
 * Gli attributi includono il tipo (LangType) dell'identificatore e un registro (char) associato
 * per scopi di generazione del codice.
 */


import AST.LangType;

public class Attributes {

		private LangType type;
		private char registro;
		
		public Attributes(LangType type) 
		{
			this.type = type;
			
		}

		public LangType getType() {
			return type;
		}

		public void setType(LangType type) {
			this.type = type;
		}

		public char getRegistro() {
			return registro;
		}

		public void setRegistro(char registro) {
			this.registro = registro;
		}
		
}
