package ast;

public class ParentVisitor implements AstVisitor<Boolean>{

    public Boolean visit(Idf affect){
        return true;
    }
    
    public Boolean visit(Print affect){
        affect.value.accept(this);
        affect.value.parent = affect;
        return true;
    }
     
    public Boolean visit(Program affect){
        affect.affect.accept(this);
        affect.affect.parent = affect;
        return true;
    }
     // S7EVEN

    // Partie 1 : // FAIT
    public Boolean visit(Affect affect){
        affect.idf.accept(this);
        affect.expr.accept(this);
        affect.idf.parent = affect;
        affect.expr.parent = affect;
        return true;
    }
    
    public Boolean visit(Or affect){
        affect.left.accept(this);
        affect.right.accept(this);
        affect.left.parent = affect;
        affect.right.parent = affect;
        return true;
    }
    
    public Boolean visit(And affect){
        affect.left.accept(this);
        affect.right.accept(this);
        affect.left.parent = affect;
        affect.right.parent = affect;
        return true;
    }
    
    public Boolean visit(Equal affect){
        affect.left.accept(this);
        affect.right.accept(this);
        affect.left.parent = affect;
        affect.right.parent = affect;
        return true;
    }
    
    public Boolean visit(Diff affect){
        affect.left.accept(this);
        affect.right.accept(this);
        affect.left.parent = affect;
        affect.right.parent = affect;
        return true;
    }
    
    public Boolean visit(Inf affect){
        affect.left.accept(this);
        affect.right.accept(this);
        affect.left.parent = affect;
        affect.right.parent = affect;
        return true;
    }
    
    public Boolean visit(Sup affect){
        affect.left.accept(this);
        affect.right.accept(this);
        affect.left.parent = affect;
        affect.right.parent = affect;
        return true;
    }
    
    public Boolean visit(InfEqual affect){
        affect.left.accept(this);
        affect.right.accept(this);
        affect.left.parent = affect;
        affect.right.parent = affect;
        return true;
    }
    
    public Boolean visit(SupEqual affect){
        affect.left.accept(this);
        affect.right.accept(this);
        affect.left.parent = affect;
        affect.right.parent = affect;
        return true;
    }
    
    public Boolean visit(Plus affect){
        affect.left.accept(this);
        affect.right.accept(this);
        affect.left.parent = affect;
        affect.right.parent = affect;
        return true;
    }
    
    public Boolean visit(Minus affect){
        affect.left.accept(this);
        affect.right.accept(this);
        affect.left.parent = affect;
        affect.right.parent = affect;
        return true;
    }
    
    public Boolean visit(Mult affect){
        affect.left.accept(this);
        affect.right.accept(this);
        affect.left.parent = affect;
        affect.right.parent = affect;
        return true;
    }
    
    public Boolean visit(Divide affect){
        affect.left.accept(this);
        affect.right.accept(this);
        affect.left.parent = affect;
        affect.right.parent = affect;
        return true;
    }
    

    // Partie 2 :
    public Boolean visit(MinusExpr affect){
        affect.expr.accept(this);
        affect.expr.parent = affect;
        return true;
    }
     // FAIT
    public Boolean visit(IfThen affect){
        affect.condition.accept(this);
        affect.thenBlock.accept(this);
        affect.condition.parent = affect;
        affect.thenBlock.parent = affect;
        return true;
    }
     // FAIT
    public Boolean visit(IfThenElse affect){
        affect.condition.accept(this);
        affect.thenBlock.accept(this);
        affect.elseBlock.accept(this);
        affect.condition.parent = affect;
        affect.thenBlock.parent = affect;
        affect.elseBlock.parent = affect;
        return true;
    }
     // FAIT
    public Boolean visit(Let affect){
        affect.declarationList.accept(this);
        affect.seqExpr.accept(this);
        affect.declarationList.parent = affect;
        affect.seqExpr.parent = affect;
        return true;
    }
     // FAIT SEVEN7
    public Boolean visit(For affect){
        affect.id.accept(this);
        affect.debut.accept(this);
        affect.fin.accept(this);
        affect.bloc.accept(this);
        affect.id.parent = affect;
        affect.debut.parent = affect;
        affect.fin.parent = affect;
        affect.bloc.parent = affect;
        return true;
    }
     // ADIRuiEN
    public Boolean visit(While affect){
        affect.condition.accept(this);
        affect.bloc.accept(this);
        affect.condition.parent = affect;
        affect.bloc.parent = affect;
        return true;
    }
     // FAIT
    

    public Boolean visit(BreakExpr affect){
        return true;
    }
     // S7EVEN
    public Boolean visit(NilExpr affect){
        return true;
    }
     // ADIRuiEN
    public Boolean visit(IntExpr affect){

        return true;
    }
     // FAIT S7EVEN
    public Boolean visit(StrExpr affect){
        return true;
    }
     // FAIT S7EVEN

    public Boolean visit(SeqExpr affect){
        for (Ast ast:affect.listExpr){
            ast.accept(this);
            ast.parent = affect;
            return true;
        }
        return true;
    }
     // FAIT
    public Boolean visit (DeclarationList affect){
        for (Ast ast:affect.listAst){
            ast.accept(this);
            ast.parent = affect;
            return true;
        }
        return true;
    }
     // ADIrUIeNH
    public Boolean visit (ListExpr affect){
        for (Ast ast:affect.listExpr){
            ast.accept(this);
            ast.parent = affect;
            return true;
        }
    
        return true;
    }
     // ADIRuiEN
    
    // Partie 3 : xXEeolCXx eCole
    public Boolean visit(Type_Declaration affect){
        affect.type_id.accept(this);
        affect.type.accept(this);
        affect.type_id.parent = affect;
        affect.type.parent = affect;
        return true;
    }
    
    public Boolean visit(Type_Fields affect){
        for (Ast ast:affect.listAst){
            ast.accept(this);
            ast.parent = affect;
            return true;
        }
        return true;
    }
    
    public Boolean visit(Type_Field affect){
        affect.id.accept(this);
        affect.type_id.accept(this);
        affect.id.parent = affect;
        affect.type_id.parent = affect;
        return true;
    }
    
    public Boolean visit(TypeType affect){
        affect.typeCopie.accept(this);
        affect.typeCopie.parent = affect;
        return true;
    }
    
    public Boolean visit(TypeRecord affect){
        affect.typeRecord.accept(this);
        affect.typeRecord.parent = affect;
        return true;
    }
    
    public Boolean visit(TypeRecordVoid affect){
        return true;
    }
    
    public Boolean visit(TypeArray affect){
        affect.typeArray.accept(this);
        affect.typeArray.parent = affect;
        return true;
    }
    
    public Boolean visit(Field affect){
        affect.id.accept(this);
        affect.expr.accept(this);
        affect.id.parent = affect;
        affect.expr.parent = affect;
        return true;
    }
    
    public Boolean visit(FieldList affect){
        for (Ast ast:affect.listAst){
            ast.accept(this);
            ast.parent = affect;
            return true;
        }
        return true;
    }
    

    // Partie 4 : WENJIENCE
    public Boolean visit(VarDeclaration affect){
        affect.idf.accept(this);
        affect.expr.accept(this);
        affect.idf.parent = affect;
        affect.expr.parent = affect;
        return true;
    }
    
    public Boolean visit(FctDeclaration affect){
        affect.fonctionID.accept(this);
        affect.typeField.accept(this);
        affect.fct2Declaration.accept(this);
        affect.fonctionID.parent = affect;
        affect.typeField.parent = affect;
        affect.fct2Declaration.parent = affect;
        return true;
    }
    
    public Boolean visit(ProcDeclaration affect){
        affect.fonctionID.accept(this);
        affect.fct2Declaration.accept(this);
        affect.fonctionID.parent = affect;
        affect.fct2Declaration.parent = affect;
        return true;
    }
    
    public Boolean visit(Fct2Declaration affect){
        affect.exprAffect.accept(this);
        affect.exprAffect.parent = affect;
        return true;
    }
    
    public Boolean visit(Fct2DeclarationType affect){
        affect.typeID.accept(this);
        affect.exprAffect.accept(this);
        affect.typeID.parent = affect;
        affect.exprAffect.parent = affect;
        return true;
    }
    
    public Boolean visit(LvalueField affect){
        affect.id.accept(this);
        affect.left.accept(this);
        affect.id.parent = affect;
        affect.left.parent = affect;
        return true;
    }
    
    public Boolean visit(LvalueIndex affect){
        affect.exprOr.accept(this);
        affect.left.accept(this);
        affect.exprOr.parent = affect;
        affect.left.parent = affect;
        return true;
    }
    
    public Boolean visit(Array affect){
        affect.id.accept(this);
        affect.exprOr1.accept(this);
        affect.exprOr2.accept(this);
        affect.id.parent = affect;
        affect.exprOr1.parent = affect;
        affect.exprOr2.parent = affect;
        return true;
    }
    
    public Boolean visit(LvalueRecord affect){
        affect.id.accept(this);
        affect.fieldList.accept(this);
        affect.id.parent = affect;
        affect.fieldList.parent = affect;
        return true;
    }
    
    public Boolean visit(Call affect){
        affect.id.accept(this);
        affect.listExpr.accept(this);
        affect.id.parent = affect;
        affect.listExpr.parent = affect;
        return true;
    }    

    public Boolean visit(VarDeclarationType affect){
        affect.idf.accept(this);
        affect.type.accept(this);
        affect.expr.accept(this);
        affect.idf.parent = affect;
        affect.type.parent = affect;
        affect.expr.parent = affect;
        return true;
    }

}

