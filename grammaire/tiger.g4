grammar tiger;
 
@header{
package parser;
}


 
program : expr_or EOF ;


// OPERATIONS
expr_or 
       : expr_and ('|' expr_or)?
       ;

expr_and
       : expr_test ('&' expr_and)?
       ;

expr_test
       : expr_plus (('='|'<>'|'<'|'>'|'<='|'>=') expr_plus)?
       ;

expr_plus
       : expr_fois (('+'|'-') expr_plus)?
       ;

expr_fois
       : expr (('*'|'/') expr_fois)?
       ;


// "RACINE"
expr   
       : '-' expr
       | 'if' expr_or 'then' expr_or ('else' expr_or)?
       | 'let' declaration* 'in' expr_or (';' expr_or)* 'end'
       | 'for' id ':=' expr_or 'to' expr_or 'do' expr_or
       | 'while' expr_or 'do' expr_or
       | gen_id('['expr_or']')? (call|lvalue|record|array)
       | '('(expr_or (';' expr_or)*)?')'
       | STR
       | INT
       | 'break'
       | 'nil'
       ;


// DECLARATIONS
declaration 
       : type_declaration
       | var_declaration
       | fct_declaration
       ;


// DECLARATION TYPES
type_declaration 
       : 'type' type_id '='type 
       ;

type
       :type_id
       |'{' type_fields? '}'
       | 'array' 'of' type_id
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


// DECLARATION VARIABLES
var_declaration
       : 'var' id (':' type_id)? ':=' expr_or
       ;


// DECLARATION FONCTIONS
fct_declaration    
       : 'function' id '(' type_fields? ')'  fct2_declaration
       ;

fct2_declaration   
       : '=' expr_or
       | ':' type_id '=' expr_or
       ;




// VARIABLES ET AFFECTATIONS
lvalue 
       : ('.' id ('[' expr_or ']')*)* affect?
       ;

affect 
       : ':=' expr_or
       ;

array
       : '[' expr_or ']' 'of'  expr_or
       ;

record
       : '{' id '=' expr_or (',' id '=' expr_or)*  '}'
       ;

call 
       : '(' expr_or* ')'
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


/*
operator
       : plus
       | expr op_egal         
       ;
*/

/*
op_egal 
       : ('='|'<>') expr                  // pour faire = (==) et <> (!=)
       | ('<'|'>'|'<='|'>=') expr         // pour faire <, >, >=, <=
       ;

plus    
       : mult(('+'|'/') mult)*
       ;

mult   
       : value (('*'|'/')value)*
       ;


value  
       : INT
       | id
       | '(' expr ')'
       ;
*/



// Les terminaux (def des exp régulières reconnaissant les tokens)
 
ID     
       : ('a'..'z'|'A'..'Z')('a'..'z'|'A'..'Z'|'0'..'9'|'_')*
       ;

INT    
       : ('0'..'9')+
       ;
 
STR    
       : '"' ('a'..'z'|'A'..'Z'|'0'..'9'|'?'|'!'|'-'|'_'|'.'|':'|';'|','|' '|'\n'|'('|')')* '"'
	;
 
WS     
       : [ \n\t\r]+ ->skip
       ;
 
COM
       : '/*' .*? '*/' -> skip
       ;
