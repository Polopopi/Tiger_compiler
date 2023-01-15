package ast;

public class BreakExpr extends Ast{

    public BreakExpr(int lineNumber){
        super(lineNumber);
    }

    public <T> T accept(AstVisitor<T> visitor){
        return visitor.visit(this);
    }
}
