grammar tiger;
 
@header{
package parser;
}
 
program : expr EOF ;
 
expr	: STR
       | INT
       | 'nil'
       ;
/* 
       | lvalue 
       | '-' expr 
       | expr binary_operator expr 
       | lvalue ':=' expr 
       | ID '(' expr_list ')' // optional expr-list
       | '(' expr_seq ')' //; optional expre-seq
       | TYPE_ID '{' field_listopt '}' 
       | TYPE_ID '[' expr ']' 'of' expr 
       | 'if' expr 'then' expr 
       | 'if' expr 'then' expr 'else' expr 
       | 'while' expr 'do' expr 
       | 'for' ID ':=' expr 'to' expr 'do' expr 
       | 'break' 
       | 'let' declaration_list 'in' expr_seq 'end' // optional expre-seq
       ;
 
expr_seq : expr 
       | expr_seq ';' expr
       ; 
 
expr_list: expr
       | expr_list ',' expr 
       ;
 
field_list: ID '=' expr 
       | field_list ',' ID '=' expr 
       ;
 
lvalue: ID 
       | lvalue '.' ID
       | lvalue '[' expr ']'
	;
declaration_list: declaration 
       | declaration_list declaration 
       ;
 
declaration: type_declaration 
       | variable_declaration 
       | function_declaration
       ;
 
type_declaration: type  TYPE_ID '=' type 
	;
 
type:  TYPE_ID 
       | '{' type_fields '}' // optional declaration
       | 'array' 'of'  TYPE_ID 
       ;
 
type_fields: type_field 
       | type_fields ',' type_field 
       ;
 
type_field: ID ':'  TYPE_ID
	;*/
 
 
 
 
 
// Les terminaux (def des exp régulières reconnaissant les tokens)
 
ID     : ('a'..'z'|'A'..'Z')('a'..'z'|'A'..'Z'|'0'..'9'|'_')*
       ;
 
TYPE_ID     : ('a'..'z'|'A'..'Z')('a'..'z'|'A'..'Z'|'0'..'9'|'_')*
       ;

INT    : ('0'..'9')+
       ;
 
STR    : '"'('a'..'z'|'A'..'Z'|'0'..'9')*'"'
	  ;
 
WS     : [ \n\t\r]+ ->skip
       ;
 
COM    : [(‘\*’.*‘*\’)]+ ->skip
       ;
 
