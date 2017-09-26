Feature: Data manipulations demonstration
	Scenario: Reading json from file 
	    Given set json2 = $resource:json@data/file.js
	    #Given set json2 = $json@data/file.js
	    Then print $json2  
	    
	Scenario: Reading json from file and make substitutions on the fly
	    Given set reference = {"field1" : "value1", "field2" : "value 2"}
	    And set name = "otherfield"
	    And set name1 = "onemorefield"
	    And set value1 = "some text"
	    And set json2 = $json@data/file1.js
	    Then print $json2  
	    
	Scenario: All from / distinct from, data from file
	    Given set documents = $json@data/documents.js
	    When set distinct = distinct documents[*].categories.* from $documents
	    And set all = all documents[*].categories.* from $documents
	    Then print $distinct
	    And print $all	    