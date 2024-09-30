package visitor;


/**
 * Questo package contiene la classe TypeCheckingVisitor, che implementa un visitor per eseguire
 * il controllo dei tipi sull'AST (Abstract Syntax Tree) del linguaggio.
 * 
 * TypeCheckingVisitor implementa l'interfaccia IVisitor e visita i nodi dell'AST per eseguire
 * il controllo dei tipi durante la compilazione del programma.
 * 
 * La classe offre i seguenti metodi:
 * - Un costruttore che inizializza il visitor e la Symbol Table.
 * - Il metodo getLog() per ottenere il log degli errori di tipo generati durante il controllo.
 * - Metodi di visita per i diversi tipi di nodi presenti nell'AST, come la visita dei nodi di assegnamento,
 *   delle espressioni binarie, delle costanti, delle dichiarazioni, etc.
 * 
 * TypeCheckingVisitor utilizza la Symbol Table per gestire gli identificatori e i loro tipi durante
 * il controllo dei tipi.
 * 
 * Questo visitor è fondamentale per garantire la correttezza del programma in termini di tipi durante
 * il processo di compilazione, identificando errori di tipo e segnalando eventuali inconsistenze
 * nel codice sorgente.
 */


import AST.NodeAssing;
import AST.NodeBinOp;
import AST.NodeConvert;
import AST.NodeCost;
import AST.NodeDecSt;
import AST.NodeDecl;
import AST.NodeDeref;
import AST.NodeExpr;
import AST.NodeId;
import AST.NodePrint;
import AST.NodeProgram;
import AST.TypeDescriptor;
import SymbolTable.Attributes;
import SymbolTable.SymbolTable;


public class TypeCheckingVisitor implements IVisitor {

	private StringBuilder log;

	public TypeCheckingVisitor() {
		this.log = new StringBuilder();
		SymbolTable.init();
	}

	public String getLog() {
		return this.log.toString();
	}

	private void setLog(String string) {
		if (!this.log.toString().equals(""))
			this.log.append(", ");
		this.log.append(string);
	}

	private NodeExpr convert(NodeExpr node) {
		NodeExpr expr = new NodeConvert(node);
		expr.setResType(TypeDescriptor.FLOAT);

		return expr;
	}

	@Override
	public void visit(NodeAssing node) {
		NodeId id = node.getId();
		NodeExpr expr = node.getExpr();

		id.accept(this);
		expr.accept(this);

		if (id.getResType().equals(expr.getResType())) {
			node.setResType(TypeDescriptor.OK);
			return;
		}

		if ((id.getResType() == TypeDescriptor.FLOAT && expr.getResType() == TypeDescriptor.INT)) {
			node.setExpr(this.convert(expr));
			node.setResType(TypeDescriptor.OK);
			return;
		}

		node.setResType(TypeDescriptor.ERROR);

		if (id.getResType() == TypeDescriptor.INT && expr.getResType() == TypeDescriptor.FLOAT)
			setLog("NodeAssign: l'ID '" + id.toString() + "' di tipo '" + id.getResType()
			+ "' non é compatibile con l'Expr '" + expr.toString() + "'");
	}

	@Override
	public void visit(NodeBinOp node) {
		NodeExpr exprLeft = node.getLeft();
		NodeExpr exprRight = node.getRight();

		exprLeft.accept(this);
		exprRight.accept(this);

		if (exprLeft.getResType().equals(exprRight.getResType())) {
			node.setResType(exprLeft.getResType());
			return;
		}

		if (exprLeft.getResType() == TypeDescriptor.INT && exprRight.getResType() == TypeDescriptor.FLOAT) {
			node.setLeft(this.convert(exprLeft));
			node.setResType(TypeDescriptor.FLOAT);
			return;
		}

		if (exprLeft.getResType() == TypeDescriptor.FLOAT && exprRight.getResType() == TypeDescriptor.INT) {
			node.setRight(this.convert(exprRight));
			node.setResType(TypeDescriptor.FLOAT);
			return;
		}

		node.setResType(TypeDescriptor.ERROR);
	}

	@Override
	public void visit(NodeCost node) {
		switch (node.getType()) {
		case TYINT:
			node.setResType(TypeDescriptor.INT);
			break;
		case TYFLOAT:
			node.setResType(TypeDescriptor.FLOAT);
			break;
		default:
			node.setResType(TypeDescriptor.ERROR);
		}
	}

	@Override
	public void visit(NodeDecl node) {
		NodeId id = node.getId();
		NodeExpr expr = node.getInit();

		id.accept(this);
		if (expr != null)
			expr.accept(this);

		// cerca il NodeId nella Symbol Table
		// se lo trovi assegna a resType ERRORE e aggiungi al log l’indicazione che
		// l’identificatore e’ gia’ definito
		if (id.getResType() == TypeDescriptor.INT || id.getResType() == TypeDescriptor.FLOAT) {
			node.setResType(TypeDescriptor.ERROR);
			setLog("NodeDcl: l'ID '" + id.getName() + "' é giá definito");
			return;
		}
		// altrimenti inserisci l’identificatore nella Symbol Table con associato il suo
		// tipo
		Attributes attr = new Attributes(node.getType());
		id.setDefinition(attr);

		switch (attr.getType()) {
		case TYFLOAT:
			id.setResType(TypeDescriptor.FLOAT);
			break;
		case TYINT:
			id.setResType(TypeDescriptor.INT);
		}

		SymbolTable.enter(id.getName(), attr);
		node.setResType(TypeDescriptor.OK);

		if (expr == null || (id.getResType() == expr.getResType() && id.getResType() != TypeDescriptor.ERROR))
			return;

		if (id.getResType() == TypeDescriptor.FLOAT && expr.getResType() == TypeDescriptor.INT) {
			node.setInit(this.convert(expr));
			return;
		}


		setLog("NodeDcl: l'ID '" + id.toString() + "' di tipo '" + id.getResType()
		+ "' non é compatibile con l'Expr '" + expr.toString() + "'");
	}

	@Override
	public void visit(NodeDeref node) {
		NodeId id = node.getId();

		id.accept(this);

		node.setResType(id.getResType());

		if (node.getResType() == TypeDescriptor.ERROR)
			setLog("NodeDefer: l'ID '" + id.getName() + "' non é definito");
	}

	@Override
	public void visit(NodeId node) {
		// cerca il NodeId nella Symbol Table se non lo trovi assegna a resType ERROR e
		// aggiungi al log l’indicazione che l’identificatore non e’ definito
		if (SymbolTable.lookup(node.getName()) == null) {
			node.setResType(TypeDescriptor.ERROR);
			setLog("NodeId: l'ID '" + node.getName() + "' non é definito");
		} else { // altrimenti setta resType al tipo dell’identificatore e il campo definition
			// alla entry della symbol table.
			node.setDefinition(SymbolTable.lookup(node.getName()));
			switch (SymbolTable.lookup(node.getName()).getType()) {
			case TYINT:
				node.setResType(TypeDescriptor.INT);
				break;
			case TYFLOAT:
				node.setResType(TypeDescriptor.FLOAT);
				break;
			}
		}
	}

	@Override
	public void visit(NodeProgram node) {
	    for (NodeDecSt nodeDSs : node.getDecSts()) {
	        nodeDSs.accept(this);

	        if (nodeDSs.getResType() == TypeDescriptor.ERROR) {
	            node.setResType(TypeDescriptor.ERROR);
	            return;  // Se trovi un errore, puoi interrompere il processo.
	        }
	    }

	    node.setResType(TypeDescriptor.OK);

	   
	}



	@Override
	public void visit(NodePrint node) {
		NodeId id = node.getId();

		id.accept(this);

		if (id.getResType() == AST.TypeDescriptor.ERROR) {
			node.setResType(AST.TypeDescriptor.ERROR);
			setLog("PRINT: l'ID '" + id.getName() + "' non é definito");
		} else
			node.setResType(AST.TypeDescriptor.OK);
	}

	@Override
	public void visit(NodeConvert node) {
		NodeExpr expr = node.getExpression();

		expr.accept(this);

		if (expr.getResType() == AST.TypeDescriptor.ERROR)
			node.setResType(TypeDescriptor.ERROR);
		else
			node.setResType(TypeDescriptor.FLOAT);
	}




}