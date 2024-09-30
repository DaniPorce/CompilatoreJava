// File: NodeConvert.java
package AST;

/**
 * Questa classe rappresenta un nodo dell'AST che rappresenta una conversione di tipo.
 */

import visitor.IVisitor;

public class NodeConvert extends NodeExpr {
    private NodeExpr expression;

    public NodeConvert(NodeExpr expression) {
        this.expression = expression;
    }

    public NodeExpr getExpression() {
        return expression;
    }

    @Override
    public void accept(IVisitor vis) {
        vis.visit(this);
    }

	
}
