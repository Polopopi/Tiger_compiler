package ast;

public class Field extends Ast{
    public <T> T accept(AstVisitor<T> visitor){
        return visitor.visit(this);
    }

    public Ast id;
    public Ast expr;
    
    public Field(Ast id, Ast expr){
        this.id = id;
        this.expr = expr;
    }
}
