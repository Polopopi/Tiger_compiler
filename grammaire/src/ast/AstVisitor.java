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
    
    // Partie 3 :
    public T visit(Type_Declaration affect);
    public T visit(Type_Fields affect);
    public T visit(Type_Field affect);

    // Partie 4 :
    public T visit(VarDeclaration affect);
    public T visit(FctDeclaration affect);
    public T visit(Fct2Declaration affect);
    public T visit(Lvalue affect);
    public T visit(Array affect);
    public T visit(Record affect);
    public T visit(RecordList affect);






}
