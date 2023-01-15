package ast;

public class StrExpr extends Ast {
    public <T> T accept(AstVisitor<T> visitor){
        return visitor.visit(this);
    }

    public String value;

    public StrExpr(int lineNumber, String value){
        super(lineNumber);
        this.value = value.replace("\"","");
    }
}
