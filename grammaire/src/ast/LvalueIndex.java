package ast;

public class LvalueIndex extends Ast {
    public <T> T accept(AstVisitor<T> visitor){
        return visitor.visit(this);
    }

    public Ast exprOr;
    public Ast left;

    /* gen_id('['expr_or']')* ('.' id ('[' expr_or ']')*)* */
    public LvalueIndex(Ast left, Ast exprOr){
        this.left=left;
        this.exprOr=exprOr;
    }
}
