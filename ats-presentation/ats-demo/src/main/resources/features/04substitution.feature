Feature: Substitution on the fly demonstration
Scenario: Substitutions
    Given set mama = "mama"
	And set y = {"a" : "A", "z" : 3}
	And set z0 = 0
	And set json2 = { "x" : "$mama", "y" : $y, "z" : [$z0, 1, 2] }
	And set dereferencedY = $json2.y
	And set dereferencedZ = $json2.z[1]
	And print $dereferencedY
	And print $dereferencedZ

	
Scenario: Textual substitutions
    Given set language = "en"
    And set localesuffix = "US"
    And set locale = "${language}_${localesuffix}"
    And print $locale 
	