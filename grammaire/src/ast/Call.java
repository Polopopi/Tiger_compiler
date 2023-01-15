package ast;

public class Call extends Ast{
    public <T> T accept(AstVisitor<T> visitor){
        return visitor.visit(this);
    }

    public Ast id;
    public Ast listExpr;

    public Call(int lineNumber, Ast id,Ast listExpr){
        super(lineNumber);
        this.id=id;
        this.listExpr=listExpr;
    }
}
