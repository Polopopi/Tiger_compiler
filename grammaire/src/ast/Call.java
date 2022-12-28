package ast;

public class Call implements Ast{
    public <T> T accept(AstVisitor<T> visitor){
        return visitor.visit(this);
    }

    public Ast id;
    public Ast listExpr;

    public Call(Ast id,Ast listExpr){
        this.id=id;
        this.listExpr=listExpr;
    }
}
