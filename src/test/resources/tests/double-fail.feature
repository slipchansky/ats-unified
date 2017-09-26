Feature: double fail
	Scenario: scenario1
	And start
	And print "scenario 1"
	And set a = 1
#	And $a == 2
	And print "scenario 1 inaccessible point"
	
	Scenario: scenario2
	And start
	And print "scenario 2"
	And set a = 2
#	And $a == 3
	And print "scenario 2 inaccessible point"
	