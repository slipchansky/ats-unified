Feature: Demonstration of n-times repetition cycle
	Scenario: array iteration
	Given start 
	And set array = [1, 2, 3, 4, 5, 6, 7, 8, 9]
	And for each x of $array begin
		And print $x
	And next
	
	Scenario: no iterator (default i iterator) repetition
	Given start 
	And set object = {"x" : "X", "y":"Y", "z" : "Z"}
	And for each x of $object begin
		And print "$x.key -> $x.value"
	And next
