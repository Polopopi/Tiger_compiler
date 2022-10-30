grammar tiger;
 
@header{
package parser;
}

// options{ backtracking = false;}
 
program : expr_or EOF ;

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



expr   
       : STR
       | INT
       | 'nil'
       | '-' expr
       | conditionnel
       | 'let' declaration* 'in' expr (';' expr)* 'end'
       | 'for' id ':=' expr 'to' expr 'do' expr
       | 'while' expr 'do' expr
       | operator
       | 'break'
       | id (lvalue|call)?
       | '('(expr (';' expr)*)?')'
       ;

operator
       : plus
       | expr op_egal         
       ;

fct_declaration    
       : 'function' id '(' 'type_fieldsopt' ')'  fct2_declaration
       ;

fct2_declaration   
       : '=' expr
       | ':' type_id '=' expr
       ;


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

var_declaration
       : 'var' id (':' type_id)? ':=' expr
       ;

lvalue 
       : ('.' id ('[' expr ']')*)* affect?
       ;

affect 
       : ':=' expr
       ;

call 
       : '(' expr* ')'
       ;

id 
       : ID
       ;

type_id 
       : ID
       ;

conditionnel 
       : 'if' expr 'then' expr ('else' expr)?
       ;


declaration 
       : type_declaration
       | var_declaration
       | fct_declaration
       ;
type_declaration 
       : type type_id '='type 
       ;
type
       :type_id
       |'{' type_fields '}'
       |'{''}'
       |'array''of'type_id
       ;

type_fields
       : (type_field type_fields2)?
       ;

type_fields2
       :(','type_field type_fields2)?
       ;
       //changé la récursivité gauche

type_field
       :id ':'type_id
       ;
// J'ai tout supprimer pour l'instant pour pas qu'on soit trop influencé, on peut s'inspirer de la grammaire du manuel,
//  mais il y a beaucoup de chose récursive gauche à traité et de factorisation à faire.
// Adrien

 
// Les terminaux (def des exp régulières reconnaissant les tokens)
 
ID     
       : ('a'..'z'|'A'..'Z')('a'..'z'|'A'..'Z'|'0'..'9'|'_')*
       ;

INT    
       : ('0'..'9')+
       ;
 
STR    
       : '"'('a'..'z'|'A'..'Z'|'0'..'9')*'"'
	;
 
WS     
       : [ \n\t\r]+ ->skip
       ;
 
COM    
       : [(‘\*’.*‘*\’)]+ ->skip
       ;
 
