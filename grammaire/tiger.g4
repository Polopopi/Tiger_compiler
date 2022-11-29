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
       : expr_fois (('+'|'-') expr_fois)*
       ;

expr_fois
       : expr (('*'|'/') expr)*
       ;

// "RACINE"

/////////////////////////////////////////////


//////////////////////////////////////////// 2 Adiren

expr   
       : '-' expr
       | 'if' expr_or 'then' '(' seq_expr ')' ('else' '(' seq_expr ')' )? 
       | 'let' declaration* 'in' (expr_affect (';' expr_affect)*)? 'end'
       | 'for' id ':=' expr_or 'to' expr_or 'do' expr_affect
       | 'while' expr_or 'do' expr_affect
       | lvalue (call|record|array)?
       | '('seq_expr')'
       | STR
       | INT
       | 'break'
       | 'nil'
       | 'print' '(' expr_or ')'
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

///////////////////////////////////////// 3 Khloai

declaration 
       : type_declaration                               #DeclarationType
       | var_declaration                                #DeclarationVar
       | fct_declaration                                #DeclarationFct
       ;


// DECLARATION TYPES
type_declaration 
       : 'type' type_id '='type 
       ;

type
       :type_id                                         #TypeID
       |'{' type_fields? '}'                            #TypeField
       | 'array' 'of' type_id                           #TypeArray
       ;

type_fields
       : type_field type_fields2
       ;

type_fields2
       :(','type_field type_fields2)?
       ;
       //changé la récursivité gauche

type_field
       :id ':'type_id
       ;

////////////////////////////////////////////////


///////////////////////////////////////////// 4 Wenjouille


// DECLARATION VARIABLES
var_declaration
       : 'var' id (':' type_id)? ':=' expr_or
       ;


// DECLARATION FONCTIONS
fct_declaration    
       : 'function' id '(' type_fields? ')'  fct2_declaration
       ;

fct2_declaration   
       : '=' expr_affect
       | ':' type_id '=' expr_affect
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
       : '"' ('a'..'z'|'A'..'Z'|'0'..'9'|'?'|'!'|'-'|'_'|'.'|':'|';'|','|' '|'\\n'|'('|')')* '"'
	;
 
WS     
       : [ \n\t\r]+ ->skip
       ;
 
COM
       : '/*' .*? '*/' -> skip
       ;
