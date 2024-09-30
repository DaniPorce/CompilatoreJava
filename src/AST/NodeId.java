package AST;

/**
 * Questa classe rappresenta un nodo dell'AST che rappresenta un identificatore.
 */

import SymbolTable.Attributes;
import visitor.IVisitor;

public class NodeId extends NodeAST{

	private String name;
	private Attributes definition;
	
	
	public NodeId(String name)
	{
		this.name = name;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	

	@Override
	public void accept(IVisitor vis) {
		// TODO Auto-generated method stub
		vis.visit(this);
	}

	public Attributes getDefinition() {
		return definition;
	}

	public void setDefinition(Attributes attributes) {
		this.definition = attributes;
	}

	

	

}
