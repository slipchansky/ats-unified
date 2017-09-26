Feature: protected in snippet

Scenario: Using protected context
	
	Given start
	Then new snippet "protected snippet"
		And begin
			And set y = 3
			And set back = $param
			And remember z = 4
			And $x == 2
			And $y == 3
			And $z == 4
		And end
	And save snippet

	
	Then new snippet "unprotected snippet"
			And set u = 1
			And set x = 4 
			And set b = "B"
			And set a = "A"
			And set callback = $param
	And save snippet


	And set x = 1
	And perform "protected snippet" with
		And set x = 2
		Given set param = "paramvalue"
	And go	
	
	
		
#	Data modifications accomplished in protected context does not affect state of main context.
#	After execution of protexted context its state is returned to main context under name of protected context, or $

	And $x == 1
	And $y == null
	
#  Here is state of protected context "TEST". If we would use noname context we could call its state using just $. For instance $.x, $.y   
	And $.x == 2
	And $.y == 3
	And $z == 4
	
	And set b = "A"
	And set a = "B"
	And perform "unprotected snippet" with
		And set x = 2
		Given set param = "other value"
	And go
	And $x == 1
	And $.x == 4	
	And $a == "B"
	And $.a == "A"
	And $b == "A"
	And $.b == "B"
	And $.callback == "other value"
	And $.u == 1
	And $u == null
