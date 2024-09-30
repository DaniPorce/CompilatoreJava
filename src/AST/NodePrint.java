package AST;

import visitor.IVisitor;

/**
 * Questa classe rappresenta un nodo dell'AST che rappresenta un'istruzione di stampa.
 */


public class NodePrint extends NodeStm {

    private NodeId id;

    public NodePrint(NodeId id) {
        this.id = id;
    }

    public NodeId getId() {
        return id;
    }

    public void setId(NodeId id) {
        this.id = id;
    }

    @Override
    public String toString() {
        return "<PRINT "+id+">";
    }

	

	@Override
	public void accept(IVisitor vis) {
		// TODO Auto-generated method stub
		vis.visit(this);
	}

	
   
}
