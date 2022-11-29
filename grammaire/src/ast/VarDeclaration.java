package ast;

public class VarDeclaration implements Ast {
    public <T> T accept(AstVisitor<T> visitor){
        return visitor.visit(this);
    }
    public Idf idf;
    public Idf type;
    public Ast expr;
    public VarDeclaration(Idf idf,Idf type,Ast expr){
        this.idf=idf;
        this.type=type;
        this.expr=expr;
    }
}
