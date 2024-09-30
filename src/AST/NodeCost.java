package AST;

import visitor.IVisitor;

/**
 * Questa classe rappresenta un nodo dell'AST che rappresenta una costante.
 */


public class NodeCost extends NodeExpr{

	private String value;
	private LangType type;
	public NodeCost(String val, LangType tyfloat) {
		this.value =val;
		this.type = tyfloat;
	}
	public String getValue() {
		return value;
	}
	public void setValue(String value) {
		this.value = value;
	}
	public LangType getType() {
		return type;
	}
	public void setType(LangType type) {
		this.type = type;
	}
	
	@Override
	public void accept(IVisitor vis) {
		// TODO Auto-generated method stub
		vis.visit(this);
	}
	
	
	
}
