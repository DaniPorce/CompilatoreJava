package AST;

/**
 * Questa classe rappresenta un nodo dell'AST (Abstract Syntax Tree).
 */

import visitor.IVisitor;

public abstract class NodeAST {
    
	
	private TypeDescriptor resType;
	public String code;
	private String log = "";
	
    public abstract void accept(IVisitor vis);

	public TypeDescriptor getResType() {
		return resType;
	}

	public void setResType(TypeDescriptor resType) {
		this.resType = resType;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}
}