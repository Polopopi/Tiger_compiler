package ast;

public class BreakExpr extends Ast{
    public <T> T accept(AstVisitor<T> visitor){
        return visitor.visit(this);
    }
}
