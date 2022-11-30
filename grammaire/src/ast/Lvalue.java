package ast;

public class Lvalue implements Ast {
    public <T> T accept(AstVisitor<T> visitor){
        return visitor.visit(this);
    }
    public Idf genId;
    public Ast exprOr;
    public Ast left;

    /* gen_id('['expr_or']')* ('.' id ('[' expr_or ']')*)* */
    public Lvalue(Ast left, Idf genID){
        this.left=left;
        this.genId=genID;
    }    
    public Lvalue(Ast left, Ast exprOr){
        this.left=left;
        this.exprOr=exprOr;
    }
}
