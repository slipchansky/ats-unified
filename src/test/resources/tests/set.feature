Feature:  set

Scenario: test storing data into local memory
	Given start
	And set valname = "some"
	And set $valname = "value"
	And set a = {}
	And set $a.x = "mama"
	
	And $some == "value"
	
	And remember object:
	"""
	{
		"key" : "value",
		$valname : "valvalue"
	}
	"""
	
	And $object.some == "valvalue"
	And set array = [1, 2, 3]
	And set map = {"x":"y"} 
	And set num = 10
	
	And set reference = $object.key
	And set string = "str-${object.key}-$num"
	And set isnull = $unknown
	And set x = "X"
	And set y = "Y"
	And set z = "Z"
	And set o:
	"""
	{
	"$x" : ["$y", "$z"]
	}
	"""
	And set cat1Id = "cat1"
	And set cat2Id = "cat2"
	And set cat11Id = "cat11"
	And set cat12Id = "cat12"
	And set cat14Id = "cat14"
	And set cat21Id = "cat21"
	And set cat22Id = "cat22"
	
	And set expects:
        """
        {
        "$cat1Id" : [ "$cat11Id","$cat12Id","$cat14Id"],
        "$cat2Id" : [ "$cat21Id", "$cat22Id" ]
        }
        """
   And set catId = "cat1"
   And set result = ["cat11", "cat12", "cat14"]
   And $expects.$catId == $result
   And set xresult = [ "$cat11Id","$cat12Id","$cat14Id"]
   And $expects.$catId == $xresult
   
   Scenario Outline: scenario description
	Given set cat1Id = "cat1"
	And set cat2Id = "cat2"
	And set cat11Id = "cat11"
	And set cat12Id = "cat12"
	And set cat14Id = "cat14"
	And set cat21Id = "cat21"
	And set cat22Id = "cat22"
    And set expects:
        """
        {
        "$cat1Id" : [ "$cat11Id"],
        "$cat2Id" : [ "$cat21Id", "$cat22Id" ]
        }
        """
     And set catId = "<catId>"
     And $expects.$catId == <result>   
    Examples:
    |catId  |result  |
    |$cat1Id|[$cat11Id] |
    |$cat2Id|["cat21", "cat22"]|
    
Scenario: scenario description
	Given set cat1Id = "cat1"
	And set cat2Id = "cat2"
	And set cat11Id = "cat11"
	And set cat12Id = "cat12"
	And set cat14Id = "cat14"
	And set cat21Id = "cat21"
	And set cat22Id = "cat22"
	And set cats = [ $cat21Id, $cat22Id ]
	And print $cats
    