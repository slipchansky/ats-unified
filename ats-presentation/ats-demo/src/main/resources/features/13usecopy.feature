Feature: Data manipulations demonstration
	Scenario: scenario 1
	    Given set json2 = {"x" : "mama", "y" : {"a" : "A", "z" : 3}, "z" : ["x", "y", "z"]}
	    When set json3 = $json2 
	       And set $json2.x = "papa"
	       And set $json3.x = "baba"
	   Then print $json2 title "Json2 (1)"
	   And print $json3 title "Json3 (1, set)"  
	    
	    
	Scenario: scenario 2
	    Given set json2 = {"x" : "mama", "y" : {"a" : "A", "z" : 3}, "z" : ["x", "y", "z"]}
	    When copy json3 = $json2 
	     And set $json2.x = "papa"
	     And set $json3.x = "baba"
	   Then print $json2 title "Json2 (2)"
	   And print $json3 title "Json3 (2, copy)"  
	    	    