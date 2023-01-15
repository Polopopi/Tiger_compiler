package ast;

public class TypeType extends Ast{
    public <T> T accept(AstVisitor<T> visitor){
        return visitor.visit(this);
    }

    public Ast typeCopie;

    public TypeType(int lineNumber, Ast typeCopie){
        super(lineNumber);
        this.typeCopie = typeCopie;
    }
}
