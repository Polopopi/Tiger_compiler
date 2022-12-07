package ast;

public class TypeType implements Ast{
    public <T> T accept(AstVisitor<T> visitor){
        return visitor.visit(this);
    }

    public Ast typeCopie;
    public TypeType(Ast typeCopie){
        this.typeCopie = typeCopie;
    }
}
