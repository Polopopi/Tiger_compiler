grammar tiger;
 
@header{
package parser;
}


 
program : expr_affect EOF ;


// OPERATIONS

//////////////////////////////////////////////////1 Stiti

expr_affect
       : expr_or (':=' expr_or)?
       ;

expr_or 
       : expr_and ('|' expr_and)*
       ;

expr_and
       : expr_test ('&' expr_test)*
       ;

expr_test
       : expr_plus (('='|'<>'|'<'|'>'|'<='|'>=') expr_plus)?
       ;

expr_plus
       : expr_mult (('+'|'-') expr_mult)*
       ;

expr_mult
       : expr (('*'|'/') expr)*
       ;

// "RACINE"

/////////////////////////////////////////////


//////////////////////////////////////////// 2 Adiren

expr   
       : '-' expr                                                                   #MinusExpr
       | 'if' expr_or 'then' '(' seq_expr ')' ('else' '(' seq_expr ')' )?           #IfExpr
       | 'let' declaration_list 'in' seq_expr 'end'                                 #LetExpr
       | 'for' id ':=' expr_or 'to' expr_or 'do' expr_affect                        #ForExpr
       | 'while' expr_or 'do' expr_affect                                           #WhileExpr
       | lvalue (call|record|array)?                                                #LValueExpr
       | '('seq_expr')'                                                             #SeqExpr
       | STR                                                                        #StrExpr
       | INT                                                                        #IntExpr
       | 'break'                                                                    #BreakExpr
       | 'nil'                                                                      #NilExpr
       | 'print' '(' expr_or ')'                                                    #PrintExpr
       ;

//liste

seq_expr
       : (expr_affect (';' expr_affect)*)?  
       ;

list_expr
       : (expr_or (',' expr_or)*)?
       ;

///////////////////////////////////////// 
// DECLARATIONS

declaration_list
       : declaration*
       ;

///////////////////////////////////////// 3 Khloai

declaration                        //DONE
       : type_declaration
       | varDeclaration
       | fctDeclaration
       ;


// DECLARATION TYPES
type_declaration                   //DONE
       : 'type' type_id '='type 
       ;

type                               //DONE
       : type_id                                         #TypeID
       |'{' type_fields? '}'                             #TypeField
       | 'array' 'of' type_id                            #TypeArray
       ;

type_fields
       : type_field (',' type_field)*               
       ;
/*
type_fields2
       :(','type_field type_fields2)?
       ;
       //changé la récursivité gauche
*/

type_field                         //DONE
       :id ':'type_id
       ;

////////////////////////////////////////////////


///////////////////////////////////////////// 4 Wenjouille


// DECLARATION VARIABLES
varDeclaration
       : 'var' id (':' type_id)? ':=' expr_or   
       ;


// DECLARATION FONCTIONS
fctDeclaration    
       : 'function' id '(' type_fields? ')'  fct2Declaration
       ;

fct2Declaration   
       : '=' expr_affect                  #ExprAffection
       | ':' type_id '=' expr_affect      #ExprTypeAffection
       ;




// VARIABLES ET AFFECTATIONS
lvalue 
       : gen_id('['expr_or']')* ('.' id ('[' expr_or ']')*)*
       ;

array
       : '[' expr_or ']' 'of'  expr_or
       ;

record
       : '{' id '=' expr_or (',' id '=' expr_or)*  '}'
       ;

call 
       : '(' list_expr ')'
       ;

id 
       : ID
       ;

type_id 
       : ID
       ;

gen_id
       : ID
       ;



// Les terminaux (def des exp régulières reconnaissant les tokens)
 
ID     
       : ('a'..'z'|'A'..'Z')('a'..'z'|'A'..'Z'|'0'..'9'|'_')*
       ;

INT    
       : ('0'..'9')+
       ;
 
STR    
       : '"'('a'..'z'|'A'..'Z'|'0'..'9'|'?'|'!'|'-'|'_'|'.'|':'|';'|','|' '|'\\n'|'('|')')* '"'
	;
 
WS     
       : [ \n\t\r]+ ->skip
       ;
 
COM
       : '/*' .*? '*/' -> skip
       ;
