Feature: protected

Scenario: Using protected context
	And set x = 1
	
#   Let's open new protected context with name of TEXT (context with no defined name is opening as $)
	And begin TEST
		And set x = 2
		And set y = 3
		And remember z = 4
		And print $x title "protected X must be 2"
		And print $y title "protected Y must be 3"
		And print $z title "remembered Z must be 4"
		And $x == 2
		And $y == 3
	And end
#	Data modifications accomplished in protected context does not affect state of main context.
#	After execution of protexted context its state is returned to main context under name of protected context, or $
	And print $x title "untouched X after protected must be 1"
	And print $y title "There must be no Y after protected clause"
	
	# remember operation in protexted code affects global context state
	And print $z title "remembered Z must be 4"
	
	And $x == 1
	And $y == null
	
#  Here is state of protected context "TEST". If we would use noname context we could call its state using just $. For instance $.x, $.y   
	And print $TEST title "Protected context state"
	And $TEST.x == 2
	And $TEST.y == 3
	And $z == 4
