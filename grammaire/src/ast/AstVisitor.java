package ast;

public interface AstVisitor<T>  {

    public T visit(Affect affect);
    public T visit(Divide affect);
    public T visit(Idf affect);
    public T visit(IfThen affect);
    public T visit(IfThenElse affect);
    public T visit(InstrList affect);
    public T visit(IntNode affect);
    public T visit(Minus affect);
    public T visit(Mult affect);
    public T visit(Plus affect);
    public T visit(Print affect);
    public T visit(Program affect);

/////////////////////////////////////////////////////
    public T visit(VarDeclaration affect);
    public T visit(FctDeclaration affect);

}
