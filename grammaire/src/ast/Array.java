package ast;

public class Array implements Ast{

    public <T> T accept(AstVisitor<T> visitor){
        return visitor.visit(this);
    }

    public Ast exprOr1;
    public Ast exprOr2;
    public Array(Ast exprOr1, Ast expreOr2){
        this.exprOr1=exprOr1;
        this.exprOr2=expreOr2;
    }
    

}
