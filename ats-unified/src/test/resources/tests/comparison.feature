Feature: comparisons

Scenario: assertions
	Given start
	And set x = ["a", "b"]
	And set y = ["1", "2"]
	And set z = ["2", "1"]
	
	
	And set a = 1
	And set b = 2
	And set c = 1
	
	And set ok = "fail"
	And if $a == $c
	And set ok = "OK"
	And end
	