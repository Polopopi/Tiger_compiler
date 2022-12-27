package ast;

public class MinusExpr implements Ast{

    public <T> T accept(AstVisitor<T> visitor){
        return visitor.visit(this);
    }

    public Ast expr;
    
    public MinusExpr(Ast expr){
        this.expr = expr;
    }
    
}
