package ast;

public class Fct2Declaration extends Ast {
    public <T> T accept(AstVisitor<T> visitor){
        return visitor.visit(this);
    }

    public Ast exprAffect;

    public Fct2Declaration(int lineNumber, Ast exprAffect){
        super(lineNumber);
        this.exprAffect=exprAffect;
    }
}
