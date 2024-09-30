package AST;


/**
 * Questa classe rappresenta un nodo dell'AST che contiene l'intero programma.
 * Estende la classe NodeAST.
 */

import java.util.ArrayList;

import visitor.IVisitor;
/** 
 * Questa classe crea e gestisce il nodo che rappresenterà l'intero AST del codice.
 * Tale classe estende la classe NodeAST.
 * */
public class NodeProgram extends NodeAST {
	
	private ArrayList<NodeDecSt> decSts;
	
	/** 
	 * Costruttore del NodeProgram a cui viene assegnato l'ArrayList di NodeDecSt
	 * @param decSts ArrayList che viene assegnato al NodeProgram
	 * */
	public NodeProgram(ArrayList<NodeDecSt> decSts) {
		super();
		this.decSts = decSts;
	}
	/** Getter che ritorna l'ArrayList di NodeDecSt associato al NodeProgram
	 * @return ritorna l'ArrayList di NodeDecSt associato al NodeProgram
	 * */
	public ArrayList<NodeDecSt> getDecSts() {
		return this.decSts;
	}
	
	/** 
	 * Metodo che ritorna la stringa che rappresenta il NodeProgram
	 * @return stringa che rappresenta il NodeProgram
	 * */
	@Override
	public String toString() {
		return "NodeProgram [decSts=" + decSts + "]";
	}
	/** 
	 * Metodo che ritorna la stringa che rappresenta il NodeProgram formattato.
	 * In questo toStringFormatted ogni nodo dell'AST viene indicato in una riga diversa.
	 * @return stringa che rappresenta il NodeProgram
	 * */
	public String toStringFormatted() {
		String stampa = "NodeProgram [\n\tdecSts=[\n";
		for(NodeDecSt node :decSts) {
			stampa += "\t\t"+ node.toString() + ",\n";
		}
		stampa += "\t]\n"+"]";
		return stampa;
	}
	
	@Override
	public void accept(IVisitor vis) {
		// TODO Auto-generated method stub
		vis.visit(this);
	}
	
	
	
	
}
