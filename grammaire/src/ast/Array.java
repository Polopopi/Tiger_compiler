package ast;

public class Array extends Ast{

    public <T> T accept(AstVisitor<T> visitor){
        return visitor.visit(this);
    }

    public Ast id;
    public Ast exprOr1;
    public Ast exprOr2;

    public Array(int lineNumber, Ast id, Ast exprOr1, Ast expreOr2){
        super(lineNumber);
        this.id = id;
        this.exprOr1=exprOr1;
        this.exprOr2=expreOr2;
    }
    

}
