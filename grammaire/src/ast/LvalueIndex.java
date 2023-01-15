package ast;

public class LvalueIndex extends Ast {
    public <T> T accept(AstVisitor<T> visitor){
        return visitor.visit(this);
    }

    public Ast exprOr;
    public Ast left;

    /* gen_id('['expr_or']')* ('.' id ('[' expr_or ']')*)* */
    public LvalueIndex(int lineNumber, Ast left, Ast exprOr){
        super(lineNumber);
        this.left=left;
        this.exprOr=exprOr;
    }
}
