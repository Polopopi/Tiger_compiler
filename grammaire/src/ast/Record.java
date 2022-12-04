package ast;

public class Record implements Ast {
    public <T> T accept(AstVisitor<T> visitor){
        return visitor.visit(this);
    }
    public Ast id;
    public Ast exprOr;
    public Record(Ast id,Ast exprOr){
        this.id=id;
        this.exprOr=exprOr;
    }
}
