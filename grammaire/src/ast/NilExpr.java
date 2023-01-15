package ast;

public class NilExpr extends Ast{
    public <T> T accept(AstVisitor<T> visitor){
        return visitor.visit(this);
    }

    public NilExpr(int lineNumber){
        super(lineNumber);
    }
}