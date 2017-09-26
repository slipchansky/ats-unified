Feature: modify

Scenario: test storing data into local memory
	Given start
	And set kbCfg:
	"""
	{
    "name" : "knowledgefaq",
    "description" : "knowledgefaq",
    "test" : {
    	"field" : "value"
    },
    "type" : [ "UNKNOWN", "ARTICLE", "FAQ" ]
    }
    """
	And modify $kbCfg begin
	And update name = "modified_name"
	And update description = "modified_description"
	And end
	
	And modify $kbCfg.type begin
	And remove 0
	And remove "FAQ"
	And add "NEWTYPE"
	And end
	
	And modify $kbCfg.test begin
	And update field = "new_value"
	And end

	And print $kbCfg title "Result"
	