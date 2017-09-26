Feature: Demonstrates creating and usage of snippets
	Scenario: perform imports
	Given import "20snippets.f"
	 
	Scenario: Call simple snippet 
		Given start
		And set x = "X"
		When test snippet;
		Then $a == "A"
		And $b == "X"  
		
		
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