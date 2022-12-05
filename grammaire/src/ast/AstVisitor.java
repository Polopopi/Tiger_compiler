package ast;

public interface AstVisitor<T>  {

    public T visit(Idf affect);
    public T visit(InstrList affect);
    public T visit(Print affect);
    public T visit(Program affect);

    // Partie 1 :
    public T visit(Affect affect);
    public T visit(Or affect);
    public T visit(And affect);
    public T visit(Equal affect);
    public T visit(Diff affect);
    public T visit(Inf affect);
    public T visit(Sup affect);
    public T visit(InfEqual affect);
    public T visit(SupEqual affect);
    public T visit(Plus affect);
    public T visit(Minus affect);
    public T visit(Mult affect);
    public T visit(Divide affect);

    // Partie 2 :
    public T visit(MinusExpr affect);
    public T visit(IfThen affect);
    public T visit(IfThenElse affect);
    public T visit(Let affect);
    public T visit(For affect);
    public T visit(While affect);
    public T visit(LvalueExpr affect);
    public T visit(BreakExpr affect);
    public T visit(NilExpr affect);
    public T visit(IntExpr affect);
    public T visit(StrExpr affect);

    public T visit(SeqExpr affect);
    public T visit (DeclarationList affect);
    public T visit (ListExpr affect);
    
    // Partie 3 :
    public T visit(Type_Declaration affect);
    public T visit(Type_Fields affect);
    public T visit(Type_Field affect);

    // Partie 4 :
    public T visit(VarDeclaration affect);
    public T visit(FctDeclaration affect);
    public T visit(ProcDeclaration affect);
    public T visit(Fct2Declaration affect);
    public T visit(Fct2DeclarationType affect);
    public T visit(LvalueField affect);
    public T visit(LvalueIndex affect);
    public T visit(Array affect);
    public T visit(LvalueRecord affect);
    public T visit(RecordList affect);
    public T visit(VarDeclarationType affect);
}
