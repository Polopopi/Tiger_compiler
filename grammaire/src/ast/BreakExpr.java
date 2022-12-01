package ast;

public class BreakExpr implements Ast{
    public <T> T accept(AstVisitor<T> visitor){
        return visitor.visit(this);
    }
    String str;
    public BreakExpr(){
        this.str="break";
    }
}
