Feature: Data manipulations demonstration
	Scenario: scenario 1
	    Given set json2 = {"x" : "mama", "y" : {"a" : "A", "z" : 3}, "z" : ["x", "y", "z"]}
	    When set $json2.x = "papa"
	       And set v = "value"
	       And set $json2.y.a = $v
	       And set $json2.y.field = "field value"
	       And add "w" to $json2.z
	       And remove 0 from $json2.z
	       And remove "y" from $json2.z
	       And remove "a" from $json2.y
	       And set $json2.z[0] = "new Z"
	   Then print $json2   
	    