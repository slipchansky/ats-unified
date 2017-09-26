Feature: Variable scope demonstration
	Scenario: Scenario1
	Given start
	Given environment
	Given new scope
	Then set a = "A"
	And remember $a
	And remember b = "B"
	And set c = "C"
	Then print "$a, $b, $c" title "a, b, c in scenario 1"
	
	Scenario: scenario2
	Given same scope
	Then print "$a, $b, $c" title "a, b, c in scenario 2"
	
	Scenario: scenario 3
	Given new scope
	Then print "$a, $b, $c" title "a, b, c in scenario 3"
	And print $c
	
	Scenario: scenario 4
	Given new scope
	And def a = "new A"
	And def c = "new C"
	Then print "$a, $b, $c" title "a, b, c in scenario 4"
	And print $c
	
	
	Scenario: scenario 5 (protected scope)
	Given new scope
		And def a = "new A"
		And def c = "new C"
		Then print "$a, $b, $c" title "a, b, c before protected scope"
		And begin
			And set a = "protected A"
			And set b = "protected B" 
			Then print "$a, $b, $c" title "a, b, c in protected scope"
		And end	
	Then print "$a, $b, $c" title "a, b, c after protected scope"
	