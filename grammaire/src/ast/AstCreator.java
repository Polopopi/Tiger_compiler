package ast;

import parser.tigerBaseVisitor;
import parser.tigerParser;

public class AstCreator extends tigerBaseVisitor<Ast>{

	///////////////////////////////////////////////////////////////////////

	//Partie 3:
	@Override 
	public Ast visitDeclaration(tigerParser.DeclarationContext ctx) {
		return ctx.getChild(0).accept(this); 
	}

	@Override 
	public Ast visitType_Declaration(tigerParser.Type_DeclarationContext ctx) {
		Ast type_id = ctx.getChild(1).accept(this);
		Ast type = ctx.getChild(3).accept(this);
		
		return new Type_Declaration(type_id, type); 
	}

	@Override 
	public Ast visitType_Field(tigerParser.Type_FieldContext ctx) {
		Ast type_id = ctx.getChild(2).accept(this);
		Ast id = ctx.getChild(0).accept(this);
		
		return new Type_Field(type_id, id); 
	}

	@Override 
	public Ast visitType_Fields(tigerParser.Type_FieldsContext ctx) {
		Ast type_fields2 = ctx.getChild(1).accept(this);
		Ast type_field = ctx.getChild(0).accept(this);
		
		return new Type_Fields(type_field, type_fields2); 
	}

	@Override 
	public Ast visitTypeArray(tigerParser.TypeArrayContext ctx) {
		return ctx.getChild(2).accept(this); 
	}

	@Override 
	public Ast visitTypeField(tigerParser.TypeFieldContext ctx) {
		return ctx.getChild(1).accept(this); 
	}

	@Override 
	public Ast visitTypeID(tigerParser.TypeIDContext ctx) {
		return ctx.getChild(0).accept(this); 
	}
	
	///////////////////////////////////////////////////////////////////////

	@Override public Ast visitVarDeclaration(tigerParser.VarDeclarationContext ctx) {

		String idString=ctx.getChild(1).toString();
		Idf idf=new Idf(idString);

		/*varDeclaration
       : 'var' id (':' type_id)? ':=' expr_or   
       ; */
	   String operation=ctx.getChild(2).toString();
	   if (operation==":") {
			String typeString=ctx.getChild(3).toString();
			Ast exprOr=ctx.getChild(5).accept(this);

			Idf type=new Idf(typeString);
			return new VarDeclaration(idf,type,exprOr);
	   }
		Ast exprOr=ctx.getChild(3).accept(this);
		 return new VarDeclaration(idf,null,exprOr);
	}
	
	@Override public Ast visitFctDeclaration(tigerParser.FctDeclarationContext ctx) { 
		/*fctDeclaration    
       : 'function' id '(' type_fields? ')'  fct2Declaration
       ; */
		Idf idf=new Idf(ctx.getChild(1).toString());
		String operation=ctx.getChild(3).toString();
		if (operation==")") {//pas de type-fields
			Ast fct2Declaraction=ctx.getChild(4).accept(this);
			return new FctDeclaration(idf,null,fct2Declaraction);

	   	}
	  	Ast typeFields=ctx.getChild(3).accept(this);
		Ast fct2Declaraction=ctx.getChild(5).accept(this);
		return new FctDeclaration(idf,typeFields,fct2Declaraction);

	}
	
	@Override public Ast visitExprAffection(tigerParser.ExprAffectionContext ctx) {//c'est pas un noeud bizzare ici!!!!!!!!!!
		/*'=' expr_affect   */
		return new Fct2Declaration(null,ctx.getChild(1).accept(this)); 
	}
	
	@Override public Ast visitExprTypeAffection(tigerParser.ExprTypeAffectionContext ctx) { 
		/*':' type_id '=' expr_affect */
		String typeIdString=ctx.getChild(1).toString();

		return new Fct2Declaration(typeIdString, ctx.getChild(3).accept(this));
	}
	
	@Override public Ast visitLvalue(tigerParser.LvalueContext ctx) {
		/*   gen_id('['expr_or']')* ('.' id ('[' expr_or ']')*)* */
		Idf idf=new Idf(ctx.getChild(0).toString());
		Ast noeudCourant=ctx.getChild(0).accept(this);
		for(int i=0;i<ctx.getChildCount()-1;i++){
			String operation=ctx.getChild(i).toString();
			switch (operation) {
				case "[":
					Ast exprOr=ctx.getChild(i+1).accept(this);
					noeudCourant=new Lvalue(noeudCourant,exprOr);
					break;
				case ".":
					Idf idf2=new Idf(ctx.getChild(i+1).toString());
					noeudCourant=new Lvalue(noeudCourant, idf2);
					break;
				default:
					break;
			}
		}
		return noeudCourant; 
	}
	
	@Override public Ast visitArray(tigerParser.ArrayContext ctx) { 
		///    '[' expr_or ']' 'of'  expr_or 
		Ast exprOr1=ctx.getChild(1).accept(this);
		Ast exprOr2=ctx.getChild(4).accept(this);
		return new Array(exprOr1,exprOr2);
	}
	
	@Override public Ast visitRecord(tigerParser.RecordContext ctx) { 
		///   '{' id '=' expr_or (',' id '=' expr_or)*  '}'//4+4i+2-1=5+4i  4+4i+4-1=7+4i
		Idf id=new Idf(ctx.getChild(1).toString());
		Ast exprOr=ctx.getChild(3).accept(this);
		Record record=new Record(id, exprOr);
		RecordList recordList=new RecordList();
		recordList.addRecord(record);
		
		for (int i = 0; 4*i < ctx.getChildCount()-6; i++) {
			recordList.addRecord(new Record(new Idf(ctx.getChild(4*i+5).toString()), ctx.getChild(4*i+7).accept(this)));
		}
		return recordList; }
	
	@Override public Ast visitCall(tigerParser.CallContext ctx) { 
		return ctx.getChild(1).accept(this); }
	
	@Override public Ast visitId(tigerParser.IdContext ctx) { return new Idf(ctx.getChild(0).toString()); }
	
	@Override public Ast visitType_id(tigerParser.Type_idContext ctx) { return new Idf(ctx.getChild(0).toString()); }

	@Override public Ast visitGen_id(tigerParser.Gen_idContext ctx) { return new Idf(ctx.getChild(0).toString()); }
}
