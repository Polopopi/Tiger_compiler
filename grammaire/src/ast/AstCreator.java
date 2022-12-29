package ast;

import parser.tigerBaseVisitor;
import parser.tigerParser;

public class AstCreator extends tigerBaseVisitor<Ast>{

	//Partie 1

	@Override 
	public Ast visitProgram(tigerParser.ProgramContext ctx) { 
		Ast child = ctx.getChild(0).accept(this);
		return new Program(child);
	}



	@Override
	/* expr_affect
       : expr_or (':=' expr_or)?
       ; */
	public Ast visitExpr_affect(tigerParser.Expr_affectContext ctx) { 

		Ast idf = ctx.getChild(0).accept(this);

		if (ctx.getChildCount() == 1){
			return idf;
		}
        else{
			Ast expr = ctx.getChild(2).accept(this);
			return new Affect(idf, expr);
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
			tempNode = new Or(tempNode, right);
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
			tempNode = new And(tempNode, right);
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
					return new Equal(left,right);
				case "<>":
					return new Diff(left,right);
				case "<":
					return new Inf(left,right);
				case ">":
					return new Sup(left,right);
				case "<=":
					return new InfEqual(left,right);
				case ">=":
					return new SupEqual(left,right);
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
					tempNode = new Plus(tempNode,right);
					break;
				case "-":
					tempNode = new Minus(tempNode,right);
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
                    tempNode = new Mult(tempNode,right);
                    break;
                case "/":
                    tempNode = new Divide(tempNode,right);
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
		return(new MinusExpr(expr));
	}

	@Override public Ast visitIfExpr(tigerParser.IfExprContext ctx){
		/* 'if' expr_or 'then' '(' seq_expr ')' ('else' '(' seq_expr ')' )?   */
		Ast condition = ctx.getChild(1).accept(this);
		Ast thenExpr = ctx.getChild(4).accept(this);
		Ast elseExpr;
		if (ctx.getChildCount() ==10){
			elseExpr = ctx.getChild(8).accept(this);
			return(new IfThenElse(condition,thenExpr,elseExpr));
		}
		else{
			return(new IfThen(condition,thenExpr));
		}
	}

	@Override public Ast visitLetExpr(tigerParser.LetExprContext ctx){
		Ast declarationList = ctx.getChild(1).accept(this);
		Ast seqExpr = ctx.getChild(3).accept(this);
		return(new Let(declarationList,seqExpr));
	}

	@Override public Ast visitForExpr(tigerParser.ForExprContext ctx){
		Ast id = ctx.getChild(1).accept(this); 
		Ast debut = ctx.getChild(3).accept(this);
		Ast fin = ctx.getChild(5).accept(this);
		Ast bloc = ctx.getChild(7).accept(this);
		return(new For(id,debut,fin,bloc));
	}

	@Override public Ast visitWhileExpr(tigerParser.WhileExprContext ctx){
		Ast condition = ctx.getChild(1).accept(this);
		Ast bloc = ctx.getChild(3).accept(this);
		return(new While(condition,bloc));
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
				return(new Call(lvalue,listExpr));
			case("["):
				Ast expr1 = ctx.getChild(2).accept(this);
				Ast expr2 = ctx.getChild(5).accept(this);
				return(new Array(lvalue,expr1,expr2));
			case("{"):
				Ast fieldList = ctx.getChild(2).accept(this);
				return(new LvalueRecord(lvalue, fieldList));
			default:
				return(null);
		}
	}

	@Override public Ast visitIntExpr(tigerParser.IntExprContext ctx){
		return(new IntExpr(Integer.parseInt(ctx.getChild(0).toString()))); 
	}

	@Override public Ast visitStrExpr(tigerParser.StrExprContext ctx){
		return(new StrExpr(ctx.getChild(0).toString()));
	}

	@Override public Ast visitNilExpr(tigerParser.NilExprContext ctx){
		return(new NilExpr());
	}

	@Override public Ast visitBreakExpr(tigerParser.BreakExprContext ctx){
		return(new BreakExpr());
	}

	@Override public Ast visitPrintExpr(tigerParser.PrintExprContext ctx){
		return(new Print(ctx.getChild(2).accept(this)));
	}
	
	@Override 
	public Ast visitDeclaration_list(tigerParser.Declaration_listContext ctx) {
		if (ctx.getChildCount() == 1){
			return ctx.getChild(0).accept(this);
		}

		DeclarationList declaration_list = new DeclarationList();

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


		SeqExpr expr_list = new SeqExpr();

		for (int i = 0; i<ctx.getChildCount();i=i+2){
			expr_list.addExpre(ctx.getChild(i).accept(this));
		}
		return expr_list;
	}


	@Override
	public Ast visitList_expr(tigerParser.List_exprContext ctx){
		/* (expr_or (',' expr_or)*)? */
		if (ctx.getChildCount() == 1){
			return ctx.getChild(0).accept(this);
		}


		ListExpr expr_list = new ListExpr();

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
		
		return new Type_Declaration(type_id, type); 
	}

	@Override 
	public Ast visitType_field(tigerParser.Type_fieldContext ctx) {
		Ast type_id = ctx.getChild(2).accept(this);
		Ast id = ctx.getChild(0).accept(this);

		
		return new Type_Field(type_id, id); 
	}

	@Override 
	public Ast visitType_fields(tigerParser.Type_fieldsContext ctx) {
		if (ctx.getChildCount() == 1){
			return ctx.getChild(0).accept(this);
		}

		Type_Fields TypeF_list = new Type_Fields();

		for (int i = 0; i<ctx.getChildCount();i=i+2){
			TypeF_list.addField(ctx.getChild(i).accept(this));
		}
		return TypeF_list;
	}

	@Override 
	public Ast visitTypeArray(tigerParser.TypeArrayContext ctx) {
		Ast typeArray = ctx.getChild(2).accept(this); 
		return(new TypeArray(typeArray));
	}

	@Override 
	public Ast visitTypeField(tigerParser.TypeFieldContext ctx) {
		if (ctx.getChildCount() == 2){
			return(new TypeRecordVoid());
		}
		Ast field = ctx.getChild(1).accept(this);
		return(new TypeRecord(field));
	}

	@Override 
	public Ast visitTypeID(tigerParser.TypeIDContext ctx) {
		return new TypeType(ctx.getChild(0).accept(this)); 
	}
	
	@Override
	public Ast visitField(tigerParser.FieldContext ctx){
		Ast id = ctx.getChild(0).accept(this);
		Ast expr = ctx.getChild(2).accept(this);
		return new Field(id,expr);
	}

	@Override
	public Ast visitField_list(tigerParser.Field_listContext ctx){
		FieldList node = new FieldList();

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

			return new VarDeclarationType(idf,type,exprOr);
	   }
		Ast exprOr=ctx.getChild(3).accept(this);
		return new VarDeclaration(idf,exprOr);
	}
	
	@Override public Ast visitFctDeclaration(tigerParser.FctDeclarationContext ctx) { 
		/*fctDeclaration    
       : 'function' id '(' type_fields? ')'  fct2Declaration
       ; */
		Ast idf= ctx.getChild(1).accept(this);
		String operation=ctx.getChild(3).toString();
		if (operation.equals(")")) {//pas de type-fields
			Ast fct2Declaraction=ctx.getChild(4).accept(this);
			return new ProcDeclaration(idf,fct2Declaraction);

	   	}
	  	Ast typeFields=ctx.getChild(3).accept(this);
		Ast fct2Declaraction=ctx.getChild(5).accept(this);
		return new FctDeclaration(idf,typeFields,fct2Declaraction);

	}
	
	@Override public Ast visitExprAffection(tigerParser.ExprAffectionContext ctx) {
		/*'=' expr_affect   */
		return new Fct2Declaration(ctx.getChild(1).accept(this)); 
	}
	
	@Override public Ast visitExprTypeAffection(tigerParser.ExprTypeAffectionContext ctx) { 
		/*':' type_id '=' expr_affect */
		Ast typeIdString=ctx.getChild(1).accept(this);

		return new Fct2DeclarationType(typeIdString, ctx.getChild(3).accept(this));
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
					noeudCourant=new LvalueIndex(noeudCourant,exprOr);
					i++;
					break;
				case ".":
					i++;
					Ast idf2=ctx.getChild(i).accept(this);
					noeudCourant=new LvalueField(noeudCourant, idf2);
					break;
				default:
					break;
			}
		}
		return noeudCourant; 
	}
	/*
	@Override public Ast visitArray(tigerParser.ArrayContext ctx) { 
		///    '[' expr_or ']' 'of'  expr_or 
		Ast exprOr1=ctx.getChild(1).accept(this);
		Ast exprOr2=ctx.getChild(4).accept(this);
		return new Array(exprOr1,exprOr2);
	}
	
	@Override public Ast visitRecord(tigerParser.RecordContext ctx) { 
		if (ctx.getChildCount() == 4){
			Ast id = ctx.getChild(1).accept(this);
			Ast exprOr=ctx.getChild(3).accept(this);
			return new LvalueRecord(id, exprOr);
		}

		///   '{' id '=' expr_or (',' id '=' expr_or)*  '}'//4+4i+2-1=5+4i  4+4i+4-1=7+4i
		Ast id = ctx.getChild(1).accept(this);
		Ast exprOr=ctx.getChild(3).accept(this);
		LvalueRecord record=new LvalueRecord(id, exprOr);
		RecordList recordList=new RecordList();
		recordList.addRecord(record);
		
		for (int i = 0; 4*i < ctx.getChildCount()-6; i++) {
			recordList.addRecord(new LvalueRecord(ctx.getChild(4*i+5).accept(this), ctx.getChild(4*i+7).accept(this)));
		}
		return recordList; }
	
	@Override public Ast visitCall(tigerParser.CallContext ctx) { 
		return ctx.getChild(1).accept(this); }
	*/
	@Override public Ast visitId(tigerParser.IdContext ctx) { return new Idf(ctx.getChild(0).toString()); }
	
	@Override public Ast visitType_id(tigerParser.Type_idContext ctx) { return new Idf(ctx.getChild(0).toString()); }

	@Override public Ast visitGen_id(tigerParser.Gen_idContext ctx) { return new Idf(ctx.getChild(0).toString()); }

	///////////////////////////////////////////////////////////////////////

}

