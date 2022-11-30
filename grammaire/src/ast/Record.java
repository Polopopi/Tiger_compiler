package ast;

public class Record implements Ast {
    public <T> T accept(AstVisitor<T> visitor){
        return visitor.visit(this);
    }
    public Idf id;
    public Ast exprOr;
    public Record(Idf id,Ast exprOr){
        this.id=id;
        this.exprOr=exprOr;
    }
}
