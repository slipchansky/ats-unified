Feature: unroll

Scenario: Demonstrates possibility of dynamic value substitution
# 	Warning!!! This algorithm does not control recursive references!!!
# 	 
#	And set propertyA = "property A" 
#	And set variablea = {"property" : $propertyA}
#	And set variablex:
#	"""
#	{
#		"field" : "$variablea.property",
#		"object" : $variablea
#	}
#	"""
	And set a = $resource:json@data/a.js
#	And set b = $resource:json@data/b.js
#	
#	And print $variablex title "Variable X"
#	And print $a title "Variable A"
#	And print $b title "Variable B"
#	
#	And "property A" == $variablea.property
#	And "property A" == $b.properties.x[0]
#	And "bar" == $b.properties.a.properties.foo
#	
##	content of data/a.js:
##	{
##		"file" : "a.js",	
##		"properties" : {
##			"bar" : "foo",
##			"foo" : "bar"
##		}
##	}
##	
##	content of data/b.js:
##	{
##		"file" : "b.js",	
##		"properties" : {
##			"a" : "$resource:json@data/a.js",
##			"x" : ["$variablex.field"]
##		}	
##    }
##
## ...
##
##	Output is:
##	====================================================================================
##	Variable X
##	------------------------------------------------------------------------------------
##	{
##	  "field" : "property A"
##	}
##	====================================================================================
##	
##	
##	
##	
##	====================================================================================
##	Variable A
##	------------------------------------------------------------------------------------
##	{
##	  "file" : "a.js",
##	  "properties" : {
##	    "bar" : "foo",
##	    "foo" : "bar"
##	  }
##	}
##	====================================================================================
##	
##	
##	
##	
##	====================================================================================
##	Variable B
##	------------------------------------------------------------------------------------
##	{
##	  "file" : "b.js",
##	  "properties" : {
##	    "a" : {
##	      "file" : "a.js",
##	      "properties" : {
##	        "bar" : "foo",
##	        "foo" : "bar"
##	      }
##	    },
##	    "x" : [ "propertyA" ]
##	  }
#	