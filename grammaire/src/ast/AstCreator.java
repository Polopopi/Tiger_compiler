package ast;

import parser.tigerBaseVisitor;
import parser.tigerParser;

public class AstCreator extends tigerBaseVisitor<Ast>{

	//Partie 1

	@Override 
	public Ast visitProgram(tigerParser.ProgramContext ctx) { 
		Ast child = ctx.getChild(0).accept(this);
		return new Program(ctx.start.getLine(), child);
	}



	@Override
	/* expr_affect
       : expr_or (':=' expr_or)?
       ;	
   */
	public Ast visitExpr_affect(tigerParser.Expr_affectContext ctx) { 

		Ast idf = ctx.getChild(0).accept(this);

		if (ctx.getChildCount() == 1){
			return idf;
		}
        else{
			Ast expr = ctx.getChild(2).accept(this);
			return new Affect(ctx.start.getLine(),idf, expr);
		}
	}



	@Override 
	/* expr_or 
       : expr_and ('|' expr_and)*
       ; */
	public Ast visitExpr_or(tigerParser.Expr_orContext ctx) { 

		Ast tempNode = ctx.getChild(0).accept(this);

		for (int i = 0; 2*(i+1) < ctx.getChildCount(); i++){
			Ast right = ctx.getChild(2*(i+1)).accept(this);
			tempNode = new Or(ctx.start.getLine(),tempNode, right);
		}
		
		return tempNode;
	}



	@Override 
	/* expr_and
       : expr_test ('&' expr_test)*
       ; */
	public Ast visitExpr_and(tigerParser.Expr_andContext ctx) { 

		Ast tempNode = ctx.getChild(0).accept(this);

		for (int i = 0; 2*(i+1) < ctx.getChildCount(); i++){
			Ast right = ctx.getChild(2*(i+1)).accept(this);
			tempNode = new And(ctx.start.getLine(),tempNode, right);
		}

		return tempNode;
	}



	@Override 
	/* expr_test
       : expr_plus (('='|'<>'|'<'|'>'|'<='|'>=') expr_plus)?
       ; */
	public Ast visitExpr_test(tigerParser.Expr_testContext ctx) {

		Ast left = ctx.getChild(0).accept(this);

		if (ctx.getChildCount() == 1){
			return left;
		}
        else{
			String operation = ctx.getChild(1).toString();
			Ast right = ctx.getChild(2).accept(this);

			switch (operation) {
				case "=":
					return new Equal(ctx.start.getLine(),left,right);
				case "<>":
					return new Diff(ctx.start.getLine(),left,right);
				case "<":
					return new Inf(ctx.start.getLine(),left,right);
				case ">":
					return new Sup(ctx.start.getLine(),left,right);
				case "<=":
					return new InfEqual(ctx.start.getLine(),left,right);
				case ">=":
					return new SupEqual(ctx.start.getLine(),left,right);
				default:
					break;
			}
		}

		return left;
	}



	@Override 
	/* expr_plus
       : expr_mult (('+'|'-') expr_mult)*
       ; */
	public Ast visitExpr_plus(tigerParser.Expr_plusContext ctx) { 

		Ast tempNode = ctx.getChild(0).accept(this);

		for (int i=0; 2*(i+1)<ctx.getChildCount(); i++){
			
			String operation = ctx.getChild(2*i+1).toString();
			Ast right = ctx.getChild(2*(i+1)).accept(this);

			switch (operation) {
				case "+":
					tempNode = new Plus(ctx.start.getLine(),tempNode,right);
					break;
				case "-":
					tempNode = new Minus(ctx.start.getLine(),tempNode,right);
					break;
				default:
					break;
			}
		}    

        return tempNode;
	}



	@Override 
	/* expr_mult
       : expr (('*'|'/') expr)*
       ; */
	public Ast visitExpr_mult(tigerParser.Expr_multContext ctx) {

		Ast tempNode = ctx.getChild(0).accept(this);

        for (int i=0; 2*(i+1)<ctx.getChildCount(); i++){
            
            String operation = ctx.getChild(2*i+1).toString();
            Ast right = ctx.getChild(2*(i+1)).accept(this);

            switch (operation) {
                case "*":
                    tempNode = new Mult(ctx.start.getLine(),tempNode,right);
                    break;
                case "/":
                    tempNode = new Divide(ctx.start.getLine(),tempNode,right);
                    break;
                default:
                    break;
            }
        }    

        return tempNode;
	}

	//////////////////////////////////////////////////////////////////////

	//Partie 2

	@Override public Ast visitMinusExpr(tigerParser.MinusExprContext ctx){
		/* '-' expr  */
		Ast expr = ctx.getChild(1).accept(this);
		return(new MinusExpr(ctx.start.getLine(),expr));
	}

	@Override public Ast visitIfExpr(tigerParser.IfExprContext ctx){
		/* 'if' expr_or 'then' '(' seq_expr ')' ('else' '(' seq_expr ')' )?   */
		Ast condition = ctx.getChild(1).accept(this);
		Ast thenExpr = ctx.getChild(4).accept(this);
		Ast elseExpr;
		if (ctx.getChildCount() ==10){
			elseExpr = ctx.getChild(8).accept(this);
			return(new IfThenElse(ctx.start.getLine(),condition,thenExpr,elseExpr));
		}
		else{
			return(new IfThen(ctx.start.getLine(),condition,thenExpr));
		}
	}

	@Override public Ast visitLetExpr(tigerParser.LetExprContext ctx){
		Ast declarationList = ctx.getChild(1).accept(this);
		Ast seqExpr = ctx.getChild(3).accept(this);
		return(new Let(ctx.start.getLine(),declarationList,seqExpr));
	}

	@Override public Ast visitForExpr(tigerParser.ForExprContext ctx){
		Ast id = ctx.getChild(1).accept(this); 
		Ast debut = ctx.getChild(3).accept(this);
		Ast fin = ctx.getChild(5).accept(this);
		Ast bloc = ctx.getChild(8).accept(this);
		return(new For(ctx.start.getLine(),id,debut,fin,bloc));
	}

	@Override public Ast visitWhileExpr(tigerParser.WhileExprContext ctx){
		Ast condition = ctx.getChild(1).accept(this);
		Ast bloc = ctx.getChild(4).accept(this);
		return(new While(ctx.start.getLine(),condition,bloc));
	}

	@Override public Ast visitLValueExpr(tigerParser.LValueExprContext ctx){
		Ast lvalue = ctx.getChild(0).accept(this);
		if (ctx.getChildCount() == 1){
			return(lvalue);
		}


		String operation = ctx.getChild(1).toString();
		switch(operation){
			case("("):
				Ast listExpr = ctx.getChild(2).accept(this);
				return(new Call(ctx.start.getLine(),lvalue,listExpr));
			case("["):
				Ast expr1 = ctx.getChild(2).accept(this);
				Ast expr2 = ctx.getChild(5).accept(this);
				return(new Array(ctx.start.getLine(),lvalue,expr1,expr2));
			case("{"):
				Ast fieldList = ctx.getChild(2).accept(this);
				return(new LvalueRecord(ctx.start.getLine(),lvalue, fieldList));
			default:
				return(null);
		}
	}

	@Override public Ast visitIntExpr(tigerParser.IntExprContext ctx){
		return(new IntExpr(ctx.start.getLine(),Integer.parseInt(ctx.getChild(0).toString()))); 
	}

	@Override public Ast visitStrExpr(tigerParser.StrExprContext ctx){
		return(new StrExpr(ctx.start.getLine(),ctx.getChild(0).toString()));
	}

	@Override public Ast visitNilExpr(tigerParser.NilExprContext ctx){
		return(new NilExpr(ctx.start.getLine()));
	}

	@Override public Ast visitBreakExpr(tigerParser.BreakExprContext ctx){
		return(new BreakExpr(ctx.start.getLine()));
	}

	@Override public Ast visitPrintExpr(tigerParser.PrintExprContext ctx){
		return(new Print(ctx.start.getLine(),ctx.getChild(2).accept(this)));
	}
	
	@Override 
	public Ast visitDeclaration_list(tigerParser.Declaration_listContext ctx) {
		DeclarationList declaration_list = new DeclarationList(ctx.start.getLine());

		for (int i = 0; i<ctx.getChildCount();i++){
			declaration_list.addDecla(ctx.getChild(i).accept(this));
		}
		return declaration_list;
	}	


	@Override public Ast visitSeqExpr(tigerParser.SeqExprContext ctx){
		return(ctx.getChild(1).accept(this));
	}


	@Override
	public Ast visitSeq_expr(tigerParser.Seq_exprContext ctx){
		if (ctx.getChildCount() == 1){
			return ctx.getChild(0).accept(this);
		}


		SeqExpr expr_list = new SeqExpr(ctx.start.getLine());

		for (int i = 0; i<ctx.getChildCount();i=i+2){
			expr_list.addExpre(ctx.getChild(i).accept(this));
		}
		return expr_list;
	}


	@Override
	public Ast visitList_expr(tigerParser.List_exprContext ctx){
		/* (expr_or (',' expr_or)*)? */
		ListExpr expr_list = new ListExpr(ctx.start.getLine());

		for (int i = 0; i<ctx.getChildCount();i=i+2){
			expr_list.addExpre(ctx.getChild(i).accept(this));
		}
		return expr_list;
	}
	//////////////////////////////////////////////////////////////////////// 

	//Partie 3:
	@Override 
	public Ast visitDeclaration(tigerParser.DeclarationContext ctx) {
		return ctx.getChild(0).accept(this); 
	}

	@Override 
	public Ast visitType_declaration(tigerParser.Type_declarationContext ctx) {
		Ast type_id = ctx.getChild(1).accept(this);
		Ast type = ctx.getChild(3).accept(this);
		
		return new Type_Declaration(ctx.start.getLine(),type_id, type); 
	}

	@Override 
	public Ast visitType_field(tigerParser.Type_fieldContext ctx) {
		Ast type_id = ctx.getChild(2).accept(this);
		Ast id = ctx.getChild(0).accept(this);

		
		return new Type_Field(ctx.start.getLine(),type_id, id); 
	}

	@Override 
	public Ast visitType_fields(tigerParser.Type_fieldsContext ctx) {
		if (ctx.getChildCount() == 1){
			return ctx.getChild(0).accept(this);
		}

		Type_Fields TypeF_list = new Type_Fields(ctx.start.getLine());

		for (int i = 0; i<ctx.getChildCount();i=i+2){
			TypeF_list.addField(ctx.getChild(i).accept(this));
		}
		return TypeF_list;
	}

	@Override 
	public Ast visitTypeArray(tigerParser.TypeArrayContext ctx) {
		Ast typeArray = ctx.getChild(2).accept(this); 
		return(new TypeArray(ctx.start.getLine(),typeArray));
	}

	@Override 
	public Ast visitTypeField(tigerParser.TypeFieldContext ctx) {
		if (ctx.getChildCount() == 2){
			return(new TypeRecordVoid(ctx.start.getLine()));
		}
		Ast field = ctx.getChild(1).accept(this);
		return(new TypeRecord(ctx.start.getLine(),field));
	}

	@Override 
	public Ast visitTypeID(tigerParser.TypeIDContext ctx) {
		return new TypeType(ctx.start.getLine(),ctx.getChild(0).accept(this)); 
	}
	
	@Override
	public Ast visitField(tigerParser.FieldContext ctx){
		Ast id = ctx.getChild(0).accept(this);
		Ast expr = ctx.getChild(2).accept(this);
		return new Field(ctx.start.getLine(),id,expr);
	}

	@Override
	public Ast visitField_list(tigerParser.Field_listContext ctx){
		FieldList node = new FieldList(ctx.start.getLine());

		for (int i = 0; i<ctx.getChildCount();i=i+2){
			node.addField(ctx.getChild(i).accept(this));
		}

		return(node);
	}

	/////////////////////////////////////////////////////////////////////// 

	@Override public Ast visitVarDeclaration(tigerParser.VarDeclarationContext ctx) {
		Ast idf=ctx.getChild(1).accept(this);

		/*varDeclaration
       : 'var' id (':' type_id)? ':=' expr_or   
       ; */
	   String operation=ctx.getChild(2).toString();
	   if (operation.equals(":")) {
			Ast type= ctx.getChild(3).accept(this);
			Ast exprOr=ctx.getChild(5).accept(this);

			return new VarDeclarationType(ctx.start.getLine(),idf,type,exprOr);
	   }
		Ast exprOr=ctx.getChild(3).accept(this);
		return new VarDeclaration(ctx.start.getLine(),idf,exprOr);
	}

	@Override public Ast visitFctDeclaration(tigerParser.FctDeclarationContext ctx) { 
		/*fctDeclaration    
       : 'function' id '(' type_fields? ')' (':' type_id)? '=' expr_affect
       ; */
		Ast idf= ctx.getChild(1).accept(this);
		String parameters=ctx.getChild(3).toString();
		Ast typeFields;
		Ast exprAffect;
		Ast typeId;
		int exprAffectIndex;

		if (parameters.equals(")")) {//pas de type-fields
			typeFields = new Type_Fields(ctx.start.getLine());
			exprAffectIndex = 7;
	   	}
	  	else{
			typeFields = ctx.getChild(3).accept(this);
			exprAffectIndex = 8;
		}

		if (ctx.getChild(exprAffectIndex - 3).toString().equals(":")){
			typeId = ctx.getChild(exprAffectIndex - 2).accept(this);
			exprAffect = ctx.getChild(exprAffectIndex).accept(this);
			return new FctDeclaration(ctx.start.getLine(),idf, typeFields, typeId, exprAffect);
		}
		else{
			exprAffectIndex -= 2;
			exprAffect = ctx.getChild(exprAffectIndex).accept(this);
			return new ProcDeclaration(ctx.start.getLine(),idf, typeFields, exprAffect);
		}
	}	
	
	@Override public Ast visitLvalue(tigerParser.LvalueContext ctx) {
		/*   gen_id('['expr_or']')* ('.' id ('[' expr_or ']')*)* */
		//Idf idf=new Idf(ctx.getChild(0).toString());
		Ast noeudCourant=ctx.getChild(0).accept(this);
		for(int i=1;i<ctx.getChildCount()-1;i++){
			String operation=ctx.getChild(i).toString();
			switch (operation) {
				case "[":
					i++;
					Ast exprOr=ctx.getChild(i).accept(this);
					noeudCourant=new LvalueIndex(ctx.start.getLine(),noeudCourant,exprOr);
					i++;
					break;
				case ".":
					i++;
					Ast idf2=ctx.getChild(i).accept(this);
					noeudCourant=new LvalueField(ctx.start.getLine(),noeudCourant, idf2);
					break;
				default:
					break;
			}
		}
		return noeudCourant; 
	}

	@Override public Ast visitId(tigerParser.IdContext ctx) { return new Idf(ctx.start.getLine(),ctx.getChild(0).toString()); }
	
	@Override public Ast visitType_id(tigerParser.Type_idContext ctx) { return new Idf(ctx.start.getLine(),ctx.getChild(0).toString()); }

	@Override public Ast visitGen_id(tigerParser.Gen_idContext ctx) { return new Idf(ctx.start.getLine(),ctx.getChild(0).toString()); }

	///////////////////////////////////////////////////////////////////////

}

