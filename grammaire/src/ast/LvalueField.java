package ast;

public class LvalueField extends AbstractIdf {
    public <T> T accept(AstVisitor<T> visitor){
        return visitor.visit(this);
    }

    public Ast id;
    public Ast left;

    /* gen_id('['expr_or']')* ('.' id ('[' expr_or ']')*)* */
    public LvalueField(int lineNumber, Ast left, Ast id){
        super(lineNumber);
        this.left=left;
        this.id=id;
    }

    public boolean isAffectable(){
        return true;
    }
}
