Feature: Echo demonstration
	Scenario: Echo scenario
	Given set x = "Mama myla ramu"
	When echo $x to test
	Then test == "Mama myla ramu"