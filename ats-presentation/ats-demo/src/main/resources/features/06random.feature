Feature: Demonstrates "random generator" functionality
	Scenario: generate and print randoms
	
	And repeat 100 times
	Given set x = random 2 ... 5
	Then $x >= 2
	And $x < 5
	And print $x  
	And next