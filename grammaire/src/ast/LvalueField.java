package ast;

public class LvalueField extends Ast {
    public <T> T accept(AstVisitor<T> visitor){
        return visitor.visit(this);
    }

    public Ast id;
    public Ast left;

    /* gen_id('['expr_or']')* ('.' id ('[' expr_or ']')*)* */
    public LvalueField(Ast left, Ast id){
        this.left=left;
        this.id=id;
    }
}
