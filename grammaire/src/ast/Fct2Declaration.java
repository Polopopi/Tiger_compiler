package ast;

public class Fct2Declaration implements Ast {
    public <T> T accept(AstVisitor<T> visitor){
        return visitor.visit(this);
    }

    public Ast exprAffect;

    public Fct2Declaration(Ast exprAffect){
        this.exprAffect=exprAffect;
    }
}
