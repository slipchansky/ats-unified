Feature: Demonstrates creating and usage of snippets
	Scenario: Simple snippet creation
		Given start
		And new snippet "test snippet"
			And set a = "A"
			And set b = $x
		And save snippet
		
	Scenario: Call simple snippet 
		Given start
		And set x = "X"
		When test snippet;
		Then $a == "A"
		And $b == "X"  
		
	Scenario: Snippet with protection creation
		Given start
		And new snippet "protected snippet"
			And begin
				And set a = "A"
				And set b = $x
				And expose a, b
			And end	
		And save snippet
		
	Scenario: Call protected snippet 
		Given start
		And set x = "X"
		When protected snippet;
		Then $a == null
		And $b == null  
		And $.a == "A"
		And $.b == "X"
		And print $
		
	Scenario: Call simple snippet with parameters from protected clause (perform snippet)
		Given start
		And set x = "local X"
		And perform "test snippet" with
			And set x = "X"
		And go 
		Then $a == null
		And $b == null  
		And $.a == "A"
		And $.b == "X"
		And $x == "local X"
		And print $
		
	Scenario: Call protected snippet with parameters from protected clause (perform snippet)
		Given start
		And set x = "local X"
		And perform "protected snippet" with
			And set x = "X"
		And go 
		Then $a == null
		And $b == null  
		And $.a == "A"
		And $.b == "X"
		And $x == "local X"
		And print $		