Feature:  set

Scenario Outline: scenario description
	And set cat1Id = "catIdentifier"
	And set cat2Id = "catIdentifier"
	Given set catId = "<catId>"
	And print $catId
	And $catId == "catIdentifier"
    Examples:
    |catId  |result  |
    |$cat1Id|[$cat11Id] |
    |$cat2Id|["cat21", "cat22"]|
