package AST;


/**
 * Questa classe rappresenta un nodo dell'AST che rappresenta la dereferenziazione di un puntatore.
 */

import visitor.IVisitor;

public class NodeDeref extends NodeExpr{
	
	private NodeId id;

	public NodeDeref(NodeId nodeId) {
		this.id = nodeId;
	}

	public NodeId getId() {
		return id;
	}

	public void setId(NodeId id) {
		this.id = id;
	}

	

	@Override
	public void accept(IVisitor vis) {
		// TODO Auto-generated method stub
		vis.visit(this);
	}

	
	
	
	
	
	

}
