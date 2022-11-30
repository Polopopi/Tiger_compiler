package ast;

import parser.tigerBaseVisitor;
import parser.tigerParser;

public class AstCreator extends tigerBaseVisitor<Ast>{

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
	public Ast visitType_Fields_Full(tigerParser.Type_Fields_FullContext ctx) {
		Ast type_fields2 = ctx.getChild(1).accept(this);
		Ast type_field = ctx.getChild(0).accept(this);
		
		return new Type_Fields_Full(type_field, type_fields2); 
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
}
