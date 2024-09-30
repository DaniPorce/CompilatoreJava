package AST;

/**
 * Questa classe rappresenta un'istruzione di assegnamento nel programma.
 * 
 */


import visitor.IVisitor;

public class NodeAssing extends NodeStm{

	private NodeId id;
	private NodeExpr expr;
	
	public NodeAssing(NodeId nodeId, NodeExpr n) {
		this.id = nodeId;
		this.expr = n;
	}
	public NodeId getId() {
		return id;
	}
	public void setId(NodeId id) {
		this.id = id;
	}
	public NodeExpr getExpr() {
		return expr;
	}
	public void setExpr(NodeExpr expr) {
		this.expr = expr;
	}
	
	@Override
	public void accept(IVisitor vis) {
		vis.visit(this);
		
	}
	
	

	
	
	
}
