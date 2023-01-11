package ast;

public interface AstVisitor<T>  {

    public T visit(Idf affect);
    public T visit(Print affect); 
    public T visit(Program affect); // S7EVEN

    // Partie 1 : // FAIT
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
    public T visit(MinusExpr affect); // FAIT
    public T visit(IfThen affect); // FAIT
    public T visit(IfThenElse affect); // FAIT
    public T visit(Let affect); // FAIT SEVEN7
    public T visit(For affect); // FAIT ADIRuiEN
    public T visit(While affect); // FAIT
    //public T visit(LvalueExpr affect);
    //public T visit(LvalueExprTypeID affect);
    public T visit(BreakExpr affect); // S7EVEN
    public T visit(NilExpr affect); // FAIT ADIRuiEN
    public T visit(IntExpr affect); // FAIT S7EVEN
    public T visit(StrExpr affect); // FAIT S7EVEN

    public T visit(SeqExpr affect); // FAIT
    public T visit (DeclarationList affect); // FAIT ADIrUIeNH
    public T visit (ListExpr affect); // ADIRuiEN
    
    // Partie 3 : xXEeolCXx eCole
    public T visit(Type_Declaration affect);
    public T visit(Type_Fields affect);
    public T visit(Type_Field affect);
    public T visit(TypeType affect);
    public T visit(TypeRecord affect);
    public T visit(TypeRecordVoid affect);
    public T visit(TypeArray affect);
    public T visit(Field affect);
    public T visit(FieldList affect);

    // Partie 4 : WENJIENCE
    public T visit(VarDeclaration affect);
    public T visit(FctDeclaration affect);
    public T visit(ProcDeclaration affect);
    public T visit(Fct2Declaration affect);
    public T visit(Fct2DeclarationType affect);
    public T visit(LvalueField affect);
    public T visit(LvalueIndex affect);
    public T visit(Array affect);
    public T visit(LvalueRecord affect);
    public T visit(Call affect);
    //public T visit(RecordList affect);
    public T visit(VarDeclarationType affect);
}