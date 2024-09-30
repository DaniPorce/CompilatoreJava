package visitor;


/**
 * Questo package contiene l'interfaccia IVisitor, che definisce i metodi di visita per i diversi
 * tipi di nodi presenti nell'AST (Abstract Syntax Tree) del linguaggio.
 * 
 * IVisitor fornisce una serie di metodi astratti per visitare i nodi dell'AST e svolgere operazioni
 * specifiche su di essi. Questi metodi devono essere implementati da classi che desiderano agire
 * come visitor per l'AST.
 * 
 * I metodi definiti in questa interfaccia includono:
 * - Metodi di visita per i nodi di programma, identificatore, dichiarazione, assegnamento,
 *   espressione binaria, costante, dereferenziazione, istruzione di stampa e conversione.
 * 
 * Questa interfaccia è fondamentale per implementare algoritmi di trasformazione, analisi o generazione
 * di codice che operano sull'AST del linguaggio, fornendo un meccanismo standard per attraversare e
 * manipolare la struttura dell'albero sintattico astratto.
 */


import AST.NodeAssing;
import AST.NodeBinOp;
import AST.NodeConvert;
import AST.NodeCost;
import AST.NodeDecl;
import AST.NodeDeref;
import AST.NodeId;
import AST.NodePrint;
import AST.NodeProgram;

public interface IVisitor {
	
	public abstract void visit (NodeProgram node);
	public abstract void visit (NodeId node);
	public abstract void visit(NodeDecl node);
	public abstract void visit(NodeAssing node);
	public abstract void visit(NodeBinOp node);
	public abstract void visit(NodeCost node);
	public abstract void visit(NodeDeref node);
	public abstract void visit(NodePrint node);
	public abstract void visit(NodeConvert node);
}
