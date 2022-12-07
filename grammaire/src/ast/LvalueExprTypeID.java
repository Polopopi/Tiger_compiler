package ast;


public class LvalueExprTypeID implements Ast {
    public <T> T accept(AstVisitor<T> visitor){
        return(visitor.visit(this));
    }

    public Ast lvalue;
    public Ast suite;

    public LvalueExprTypeID(Ast lvalue, Ast suite){
        this.lvalue = lvalue;
        this.suite = suite;
    }

}