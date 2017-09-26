Feature: Separate snippets declaration feature
	Scenario: Simple snippet creation
		Given start
		And new snippet "test snippet"
			And set a = "A"
			And set b = $x
		And save snippet

	Scenario: Snippet with protection creation
		Given start
		And new snippet "protected snippet"
			And begin
				And set a = "A"
				And set b = $x
				And expose a, b
			And end	
		And save snippet
		