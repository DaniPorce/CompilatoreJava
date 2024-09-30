package AST;

/**
 * Questa classe rappresenta un nodo dell'AST che rappresenta un'operazione binaria.
 */

import visitor.IVisitor;

public class NodeBinOp extends NodeExpr{

	private LangOper op;
	private NodeExpr left;
	private NodeExpr right;
	public NodeBinOp(LangOper plus, NodeExpr sinistro, NodeExpr destro) {
		this.op = plus;
		this.left = sinistro;
		this.right = destro;
	}
	public LangOper getOp() {
		return op;
	}
	public void setOp(LangOper op) {
		this.op = op;
	}
	public NodeExpr getLeft() {
		return left;
	}
	public void setLeft(NodeExpr left) {
		this.left = left;
	}
	public NodeExpr getRight() {
		return right;
	}
	public void setRight(NodeExpr right) {
		this.right = right;
	}
	
	
	@Override
	public void accept(IVisitor vis) {
		// TODO Auto-generated method stub
		vis.visit(this);
	}
	
	

	
	





}
