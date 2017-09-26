Feature: test random feature
Scenario: test random scenario
	And set x = random 0 ... 100
	And set y = random $x ... 200 	
	And set z = random $x ... $y
	And set u = random 0 ... $x
	
	And print "$x $y $z $u"  