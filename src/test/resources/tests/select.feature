Feature: select feature 
Scenario: Demonstrates use of select [distinct] ... go clause 
	Given start 
	And set documentsRequest: 
		"""
		{
		  "documents" : [ {
			    "id" : "bank_of_america_221",
			    "categories" : [ "gbank_category_1" ]
			  }, {
			    "id" : "bank_of_america_222",
			    "categories" : [ "gbank_category_2" ]
			  }	
		  ]
  	}
  	"""
		
		
	And select distinct "documents[*].categories" from $documentsRequest 
	And go 
	
	And set distinctCategories = $
	And print $distinctCategories 
