package AST;

import visitor.IVisitor;

/**
 * Questa classe rappresenta un nodo dell'AST che rappresenta una dichiarazione di variabile.
 */

public class NodeDecl extends NodeDecSt {
    
    private NodeId id;
    private LangType type;
    private NodeExpr init;

    public NodeDecl(NodeId nodeId, LangType tyfloat, NodeExpr dclP) {
        this.id = nodeId;
        this.type = tyfloat;
        this.init = dclP;
    }

    public NodeId getId() {
        return id;
    }

    public void setId(NodeId id) {
        this.id = id;
    }

    public LangType getType() {
        return type;
    }

    public void setType(LangType type) {
        this.type = type;
    }

    public NodeExpr getInit() {
        return init;
    }

    public void setInit(NodeExpr init) {
        this.init = init;
    }


    @Override
    public void accept(IVisitor vis) {
        vis.visit(this);
    }

	
}
