Feature: test set

Scenario: test storing data into local memory
	Given start
	And set object:
	"""
	{
		"key" : "value"
	}
	"""
	And set array = [1, 2, 3]
	And print $object title "Object content"
	And print "Object is $object" title "Object translations"
	