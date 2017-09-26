Feature: samples

##################################################################################	
Scenario: "Hello Cucumber!" using "print"
	Given start
	Then  print "Hello Cucumber!"
	
	#prints value with title (for much clarity)
	And   print "Hello Cucumber!" title "Unified cucumber says Hello"


##################################################################################	
Scenario: Storing values to Local context (exposing directlu referred data. see also "copy")
	 Given start
	 
	 #Setting object value from JSON directly
	  And set obj:
	  """
	  {
	  	"a" : "field a (X)",
	  	"b" : "field b (Y)"
	  }
	  """
	  And set x = $obj
	  And set y = $x.a
	  And set z = $x.b
	  And print $obj title "object"
	  And print $x title "value x"
	  And print $y title "value y"
	  
##################################################################################	
Scenario: Storing values to Memories context
	 Given start
	  Then set lang = "en_US"
	  
	  #stores local variable into Memories context using the same name
	  Then remember $lang
	  
	  #creates new memories variable with name of default_lang
	  Then remember default_lang = $lang 
	  And print $default_lang title "default language"
	  

##################################################################################	
Scenario: Retrieving environment and settings variables
	Given environment
	And set x = $JAVA_HOME
	And print $x title "environment variable value of $JAVA_HOME"
	And print $base.url title "base url value"

	  
##################################################################################	
Scenario: "dump to log" and "dump to <file>" demonstration
    Given start
	Then set x = 2  
	And set a:
	"""
	{
		"a" : 1,
		"b" : 4
	}
	"""
	Then set lang = "en_US"
	Then remember $lang
	Then remember default_lang = $lang 
	
	And dump to log
	#And dump to "d:/dump.txt"
	
##################################################################################	
Scenario: Using content of dump as actual context 
    Given start
	And using dump "d:/dump1.txt"
	And print $default_lang
	And print $a.b
	And dump to log
	#And dump to "d:/dump.txt" 

##################################################################################	
Scenario: Disabling and enabling testing for ommitting test activity  
    Given start
    And disable testing!
#
#   Skipped testing steps. All test steps between "disable testing!" and "enable testing!" will be omitted for executing.    
#    
	And enable testing!
	And using dump "d:/dump1.txt"
	And print $default_lang
	And print $a.b
	And dump to log
	#And dump to "d:/dump.txt" 

##################################################################################	
Scenario: On the fly substitution of $ variables
    Given start
	Then set x = 2  
	And set a:
	"""
	{
		"a" : 1,
		"b" : $x
	}
	"""
	Then print $a title "object a"
	Then print $a.b title "value of field a.b"
	Then print "Tesult of substitution during print is a.a = $a.a and a.b = $a.b" title "print with substitution"   
	

##################################################################################	
Scenario: Demonstrates accessing to array in java - List way   
    Given start
	And set allCategories:
	"""
	[ "gbank_category_1", "gbank_category_2", "gbank_category_3", "gbank_category_4", "gbank_category_5", "gbank_category_6", "gbank_category_7", "gbank_category_3" ]
	"""
	And set x = $allCategories.size()
	And set y = $allCategories[0]
	And set index = 2
	And set z = $allCategories[$index]
	And print "x = $x\ny = $y\nz = $z"
	  

##################################################################################	
Scenario: Comparison of arrays intersections, assertion that collection contains value  
	Given environment
	And set a1:
	"""
	[3, 2, 1]
	"""
	And set a2:
	"""
	[1, 2, 3]
	"""

	Then expected that 	

	And set x = 1
	#assertion expressions
	
	# equals of arrays mean equivalence of arrays independently to order of their elements 
	Then $a1 == $a2
	
	And $a1 contains all of $a2
	And $a1 contains any of $a2
	 
	 # the same as $a1 contains any of $a2
	 And $a1 contains $a2
	 
	 #assertion that collection contains value
	 And $a1 contains $x
	 And $a1 contains 1
	 

	Then set a1:
	"""
	[3, 2, 1]
	"""
	And set a2:
	"""
	[4, 5]
	"""
	 And $a1 does not contain any of $a2
	 And $a1 does not contain all of $a2
	 And $a1 does not contain 0
	
	#just demonstrate that the same conditions are implemented in if-style 
	And if $a1 does not contain any of $a2
	Then print "a1 does not contain any of a2 is implemented"
	And end
	
	#check if collection contains value
	And if $a1 contains 1
	Then print "a1 contains value is implemented"
	And end  
	
	And if $a1 does not contain 0
	Then print "a1 does not contain value implemented"
	And end


##################################################################################	
Scenario: ifdef example
	Given environment

	And set forcheck = ""
	
	And ifdef $forcheck
	Then 	print "true"
	But  else
	Then 	print "false"
	And end


##################################################################################	
Scenario: if - then - else example
	Given environment

	And set forcheck = 0.5
	
	And if $forcheck == 0.5
	Then 	print "true"
	But else
	Then 	print "false"
	And end


##################################################################################	
Scenario: json content referring example
	Given environment
	And set x:
	"""
	{
		"document" : {
			"id" : "doc1",
			"title" : "doc title",
			"content" : {
				"description" : "document description"
			},
			"categories" : ["c1", "c2", "c3"]
		}
	}
	"""
	And set y = $x.document
	And set z = $y.content
	And set d = $z.description 
	
	Then print $x title "value of x"
	And print $y title "value of y"
	And print $z title "value of z"
	And print $d title "document content description"


	#new get array size	
	And print $x.document.categories.size title "Array size"
	
	#new get last significant array index
	And print $x.document.categories.lastIndex title "Array last significant index"
	
	#new get las array item
	And print $x.document.categories.last title "Array last item"
	
	 #new use ['field-name'] notation 
	And print $x['document'].categories.last title "Array last item using ['fiel'] notation" 
	 

		 
	
##################################################
# stas new 2017.08.16 
Scenario: Demonstrates possibility of dynamic value substitution
# 	Warning!!! This algorithm does not control recursive references!!!
# 	 
	And set propertyA = "property A" 
	And set variablea = {"property" : "$propertyA"}
	And set variablex:
	"""
	{
		"field" : "$variablea.property"
	}
	"""
	And set a = $resource:json@data/a.js
	And set b = $resource:json@data/b.js
	
	And print $variablex title "Variable X"
	And print $a title "Variable A"
	And print $b title "Variable B"
	
#	content of data/a.js:
#	{
#		"file" : "a.js",	
#		"properties" : {
#			"bar" : "foo",
#			"foo" : "bar"
#		}
#	}
#	
#	content of data/b.js:
#	{
#		"file" : "b.js",	
#		"properties" : {
#			"a" : "$resource:json@data/a.js",
#			"x" : ["$variablex.field"]
#		}	
#    }
#
# ...
#
#	Output is:
#	====================================================================================
#	Variable X
#	------------------------------------------------------------------------------------
#	{
#	  "field" : "property A"
#	}
#	====================================================================================
#	
#	
#	
#	
#	====================================================================================
#	Variable A
#	------------------------------------------------------------------------------------
#	{
#	  "file" : "a.js",
#	  "properties" : {
#	    "bar" : "foo",
#	    "foo" : "bar"
#	  }
#	}
#	====================================================================================
#	
#	
#	
#	
#	====================================================================================
#	Variable B
#	------------------------------------------------------------------------------------
#	{
#	  "file" : "b.js",
#	  "properties" : {
#	    "a" : {
#	      "file" : "a.js",
#	      "properties" : {
#	        "bar" : "foo",
#	        "foo" : "bar"
#	      }
#	    },
#	    "x" : [ "propertyA" ]
#	  }
	
##################################################################################
# deprecated. use  "set x = $resource:json@data/a.js" instead	
Scenario: Load resource into local variable 
	And load resource "data/v2/requests/bank/documents.json" as documents
	And print $documents title "retrieved form resource documents"

##################################################################################	
Scenario: Load file into local variable 
	And load file "src/main/resources/data/v2/requests/bank/documents.json" as x
	And set doc = $x.documents[1]
	And print $doc
	
	
##################################################################################	
Scenario: Demonstrates use of select [distinct] ... go clause 
    Given start
	And load resource "data/v2/requests/bank/documents.json" as "documentsRequest"
	
	And select distinct "documents[*].categories" from $documentsRequest
	And go
	
	And set distinctCategories = $
	
	And select all "documents[*].categories" from $documentsRequest
	And go
	
	And set allCategories = $
	And print "distinctCategories=$distinctCategories\nall categories=$allCategories"
	  
	
##################################################################################	
Scenario: "for each" clause for iterating array
	And set array:
	"""
	[3, 2, 1]
	"""
	And for each x of $array begin
	Then print $x
	And next

##################################################################################	
Scenario: Call rest example
	Given environment

	#Just for demonstrating way to redefine rest home url 	
	Then ifdef $INSTANCE_HOME
	#check if either environment or java contains value of variable INSTANCE_HOME.    
	Then set rest_home = $INSTANCE_HOME
	And endif
	
	Then ifndef $rest_home
	#if rest_home is not known yet, let rest_home be base.url (from configuration) 
	Then set rest_home = $base.url
	And  endif
	
	#exactly REST call demonstration  
	And using REST
	#Setting of home is not mandatory. By default rest home is value of base.url from configuration. 
	And set home = $rest_home
	And url = "/v1/kbs/{kbId}"
	And param kbId = "<kbId>"
	And param lang = $default_lang
	And param tenantId = "<tenantId>"
	And method GET 
	And send
	
	#check status and save response payload if success
	And	"200" == $.status
	Then set entity = $.entity 

	
##################################################################################	
Scenario: Call REST using form spring authentication
	And using REST
	#And home = "http://gks-dep-stbl:7211/gks-cms/"
	And home = "http://ua-slypchan-lt:9000/gks-cms/"
	And url = "/tenants/1/kbs"
	And FORMAUTH username "default" password "password"
	And method GET 
	And send
	And dump all to log
	
##################################################################################	
Scenario: Execute shell command and extract data from console output  
	And prepare SHELL
	#And current directory "D:\index-app" 
	And command java -version  
	And run
	And print $
#   output looks like	
#	{
#    "output" : [ "java version \"1.8.0_65\"", "Java(TM) SE Runtime Environment (build 1.8.0_65-b17)", "Java HotSpot(TM) 64-Bit Server VM (build 25.65-b01, mixed mode)" ],
#    "exitCode" : 0
#   }
	And select all output.[?(@=~ /.*Runtime Environment.*/)] from $
	And go

	
##################################################################################	
Scenario: Creating and using snippets
	Given environment
	And new snippet "print message using snippet"
	And print "It is message from snippet" title "message"
	Then  save snippet
	
	And new snippet "one more snippet"
	And print "It is message from one more snippet" title "message1"
	Then end
	
	#execute snippet (';' in the end may mean continuation of script)
	And print message using snippet;
	
	#execute snippet ('.' in the end may mean ending of script)
	And one more snippet.
	
#############################################
# stas new 2017.08.15   
Scenario: Object modification on the fly     
	And remember readerS1 = "readers1" 
	And remember kbId = "skilled_faq"
	
	And set x = {"a":[]}
	
	# add element to array at index
	And set $x.a[0] = $kbId
	And set $x.a[1] = "mama"
	
	# add element to tail of array
	And add $readerS2 to $x.a

	# clear array 	
	And remove all from $x.a
	
	# clear object (remove all fields)
	And set object = {"a" : "b"}
	And remove all from $object 
	
	And set a = {}
	And set $a.x = [1, "s"]
	And print $a title "Complex object before removing data"
	
	And remove "s" from $a.x
	And print $a title "element 's' removed from array by value"
	
	And remove 0 from $a.x
	And print $a title "element 1 was removed from array by index"
	
	And remove "x" from $a
	And print $a title "nested object a removed from object by field name"
	
	
	And set a = {}
	# set null as field value 
	And set $a.x = null
	
	
##################################################################################
# deprecated. use "Object modification on the fly" instead  	
Scenario: Modifying of context objects
	And set kbCfg:
	"""
	{
    "name" : "knowledgefaq",
    "description" : "knowledgefaq",
    "type" : [ "UNKNOWN", "ARTICLE", "FAQ" ],
    }
    """
	And modify $kbCfg begin
	And update name = "test knowledge base"
	And update description = "test knowledge base description"
	And end
	
	And modify $kbCfg.type begin
	And remove 0
	And remove "FAQ"
	And add "NEWTYPE"
	And end

##################################################################################	
Scenario: Retrieving, updating and deleting knowledge base configurations
	And get configuration of knowledge base "knowledgefaq" in tenant = 1 to kbCfg
	And save configuration of knowledge base "test852simple" in tenant = 1 from $kbCfg
	And get configuration of all knowledge bases in tenant = 1 to allKbs
	And delete configuration of knowledge base "test852simple" in tenant = 1

	#copy all knowledge base definitions from tenant 1 to tenant 103 
	And get configuration of all knowledge bases in tenant = 1 to allKbs
	And for each kb of $allKbs begin
	And  set id = $kb.id
	And  save knowledge base configuration $kb in tenant = 103 
	And print $id title "Knowledge base id"
	And next

##################################################################################	
Scenario: Demonstrates extracting, modifying and updating Application Configuration (Just Options for a while)   
    Given start
    And get configuration of application "GKS_Cluster_Stas_2" in tenant = 1 to clusterCfg
    And modify $clusterCfg.options['general'] begin
    And update esReadOnly = "true"
    And end
    
    And modify $clusterCfg.options['cms.index'] begin
    And update enable = "false"
    And end 
    
	And print $clusterCfg.options.general title "General"
	And print $clusterCfg.options['cms.index'] title "CMS.INDEX"
	
	And save applications options $clusterCfg 
	
	And dump to log

##################################################################################	
Scenario: Demonstrates possibilities of locales management in tenant   
    Given start
    And get all locales of tenant 1 to langs
    And print $langs title "All locales metadata"
    
    And add locale "en_MU" to tenant 1
    And get all locales of tenant 1 to langs
    And print $langs.enabled title "Enabled locales after adding locale en_MU"

    And disable locale "en_MU" in tenant 1
    And get all locales of tenant 1 to langs
    And print $langs.disabled.locale title "Disabled locales after disabling locale en_MU"
    And print $langs.enabled.locale title "Enabled locales after disabling locale en_MU"
    
    And enable locale "en_MU" in tenant 1
    And get all locales of tenant 1 to langs
    And print $langs.disabled.locale title "Disabled locales after enable locale en_MU"
    And print $langs.enabled.locale title "Enabled locales after enable locale en_MU"
    
    And delete locale "en_MU" in tenant 1
    And get all locales of tenant 1 to langs
    And print $langs.all.locale title "All locales after deleting locale en_MU"
	
##################################################################################	
Scenario: Retrieving locales from two separate tenants   
    Given start
    And set t1 = 1
    And set t2 = 103
    And get all locales of tenant $t1 to tenant_1
    #And get all locales of tenant 1 to tenant_1
    And get all locales of tenant $t2 to tenant_103
    #And get all locales of tenant 103 to tenant_103
    And print "tenant1:\n$tenant_1.all.locale\n$tenant_103.all.locale" title "available locales"

##################################################################################
#new	
Scenario: Creating new role from JSON
    Given start
    And set stasRole:
    """
    {
	  	"name" : "GK_STAS",
	  	"description" : "stas description",
	  	"active" : true,
	  	"annex" : {
	    	"CfgReservedApplication6" : [ "Knowledge.REPORTING" ],
	    	"CfgReservedApplication7" : [ "Knowledge.CMS.Administrator", "Knowledge.CMS.Approver" ]
	  	},
	  	"persons" : [ "gkc_new", "gks_cms_appr"],
	  	"accessGroups" : [ "test_access_group" ]
	}
    """
    And save role $stasRole in tenant = 1
    And print $ title "save status"
    
    And get role "GK_STAS" in tenant = 1 to r
    And print $r title "Retrieved new role"
    
    
##################################################################################
#new	
Scenario: Retrieving all roles and modify one of existing role using JSON modifications
    Given start
    And get all roles of tenant 1 to roles
    
    
    And set roleId = $roles.all.last
    And print $roleId 
    And set role = $roles.config[$roleId]
    And print $role title "Role instance before modifications"
    
    And modify $role.annex begin
    And update CfgReservedApplication7:
    """
    [ "Knowledge.CMS.Administrator", "Knowledge.CMS.Approver" ]
    """
    And end
    
    And print $role title "annex modified"
    
    And modify $role.persons begin
    And remove "gks_cms_appr"
    And end
    
    And modify $role.accessGroups begin
    And add "test_access_group"
    And end
    
    And print $role title "access group added"
    
    And save role $role in tenant = 1
    And get role $roleId in tenant = 1 to role
    And print $role title "Retrieved role instance after save modifications"

##################################################################################
#new	
Scenario: Key modifications of role without role retrieving
    Given start
	And get all roles of tenant 1 to roles
    And set roleId = $roles.all.last
    
    And get role $roleId in tenant = 1 to role
    
    #save role for restoring it at the end 
    And set savedRole = $role
    
    And print $role title "Retrieved role before modifications"
    
    And add person "gks_cms_doc" to role $roleId in tenant = 1
    
    And get role $roleId in tenant = 1 to role
    And print $role title "Retrieved role after adding person"
    
    And remove person "gks_cms_doc" from role $roleId in tenant = 1
    And get role $roleId in tenant = 1 to role
    And print $role title "Retrieved role after removing person"
    

    And add access group "test_access_group" to role $roleId in tenant = 1
    And get role $roleId in tenant = 1 to role
    And print $role title "Retrieved role after adding access group"
    
    And remove access group "test_access_group" from role $roleId in tenant = 1
    And get role $roleId in tenant = 1 to role
    And print $role title "Retrieved role after removing access group"
    
    And disable role $roleId in tenant = 1
    And get role $roleId in tenant = 1 to role
    And print $role title "Retrieved role after disabling"
    
    And enable role $roleId in tenant = 1
    And get role $roleId in tenant = 1 to role
    And print $role title "Retrieved role after enabling"
    
    And delete role $roleId in tenant = 1
    And print $ title "status of delete role"
    
    #restore deleted role to previous state
    And save role $savedRole in tenant = 1


##################################################################################
#new
Scenario: Test person modification operations
	Given start
	Then set tenantId = 1
	Then set newPersonName = "test_new_person"
	And save person $newPersonName in tenant = $tenantId
	And get person $newPersonName in tenant = $tenantId to newPerson
	And print $newPerson title "Retrieved recently created person"

	Then modify $newPerson begin
	And update firstName = $newPersonName
	And end
	And save person $newPerson in tenant = $tenantId
	And get person $newPersonName in tenant = $tenantId to updatedPerson
	And print $updatedPerson title "Retrieved person after updating firstName"

	Then modify $newPerson begin
	And set modifiedPersonName = "modified_test_new_person"
	And update username = $modifiedPersonName
	And end
	And save person $newPerson in tenant = $tenantId
	And get person $newPersonName in tenant = $tenantId to person
	And print $person title "Retrieved old person after saving with new username"
	And get person $modifiedPersonName in tenant = $tenantId to personWithModifiedName
	And print $personWithModifiedName title "Retrieved new person after saving with new username"

	Then set skillGroupon = "skill_groupon"
	And set skillPhp = "php"
	And set level = 0
	And add skill "karateSkill" with level 0 to person $newPersonName in tenant = $tenantId
	And add skill $skillGroupon with level $level to person $newPersonName in tenant = $tenantId
	And add skill $skillPhp to person $newPersonName in tenant = $tenantId
	And get person $newPersonName in tenant = $tenantId to personWithNewSkills
	And print $personWithNewSkills title "Retrieved person after adding new skills"

	Then remove skill "karateSkill" from person $newPersonName in tenant = $tenantId
	And remove skill $skillGroupon from person $newPersonName in tenant = $tenantId
	Then remove skill $skillPhp from person $newPersonName in tenant = $tenantId
	And get person $newPersonName in tenant = $tenantId to personWithoutAddedSkills
	And print $personWithoutAddedSkills title "Retrieved person after removing added skills"

	Then delete person $newPersonName in tenant = $tenantId
	And delete person $modifiedPersonName in tenant = $tenantId
	And get all persons of tenant = $tenantId to allPersons
	And $allPersons does not contain $newPersonName
	And $allPersons does not contain $modifiedPersonName
	
##################################################################################
#stas new
Scenario: Skills management 
	Given start
	#retrieve all available skills to variable
    And get all skills from tenant 1 to skills
    And print $skills title "skills"
	
	#get Id of particular skill
	And get id of skill "football" in tenant = 1 to skillId
    And print $skillId title "skill id"
    
    #save skill and get its id
    And save skill "stasTest" in tenant 1
    And set skillId = $.id
    And print $skillId title "skills"
    
    #delete skill
    And delete skill "stasTest" in tenant 1
    And print $    
    

##################################################################################
#stas new
Scenario: "copy" versus "set" exposes copy of referred data. Visibility: local scope.  
	And set sample:
	"""
	{
		"id" : 1,
		"name" : "sample"
	}
	"""
	And copy x = $sample
	And copy y = $sample
	
	And modify $x begin
	And update id = "2" 
	And end
	
	And modify $y begin
	And update name = "y object" 
	And end
	
	And print $sample title "sample"
	And print $x title "x"
	And print $y title "y"
    
##################################################################################
#stas new
Scenario: continues new scenario in scope of previous scenario.  
    Given same scope
    #Given continue
    #Given same environment
      And print $y title "y"
      
##################################################################################
#stas new
Scenario: Executing methods at "set", "copy" expressions 
    Given start
	And set sample:
	"""
	{
	"testucs@en@1" : [ {} ]
	}
	"""
	And set keys = $sample.keySet()
	And print $keys title "keys"
	And set x = $keys[0]
	And print $x title "key"
      
##################################################################################
#stas new
Scenario: Iterating Map
    Given start
 	And set sample:
 	"""
 	{
 	"testucs@en@1" : [ {} ]
 	}
 	"""
 	And for each e of $sample begin
 	And print $e.key title "key"
 	And print $e.value title "value"
 	And next

##################################################################################
#stas new
	Scenario: Accessing data using evaluated data path
		Given start
		And remember response:
    """
    {
	  	"Categories" : [ {
		    "testucs@en@1" : [ {
		      "Status" : [ "APPROVED" ],
		      "TenantId" : [ 1 ],
		      "Type" : [ 1 ],
		      "Description" : [ "" ],
		      "OwnerId" : [ 1 ],
		      "Lang" : [ "English" ],
		      "Id" : [ "testucs@en@1" ],
		      "ModifiedDate" : [ "2017-08-08T17:59:53.000Z" ],
		      "ChildrenCategories" : [ { } ],
		      "Name" : [ "testUCS(English)" ]
		    } ]
	  	} ]
	}
    """
		And set y:
    """
    [ "APPROVED" ]
    """
		And set key = "testucs@en@1"

		# next line stores content whose path is referred relatively to value of 'key'
		And set x = $response.Categories[0]['$key'][0].Status

		# in the same way we could refer data for assertion
		And $response.Categories[0]['$key'][0].Status[0] == "APPROVED"

		# in the same way we could refer data for printing
		And print $response.Categories[0]['$key'][0].Status title "X"
		
##################################################################################
Scenario Outline: Trick for declaring different expectations for cases declared in Examples of scenario outline.  
      And set expectations:
      """
      {
      "agent" : [ "publicWithSkillsKb", "notPublicKb", "knowledgefaq", "gbank", "publicKb" ],
      "skilled_agent" : [ "publicWithSkillsKb", "notPublicWithSkillsKb", "notPublicKb", "knowledgefaq", "gbank", "publicKb" ]
      }
      """ 
      And set userId = "<userId>"
      And set expected = $expectations.$userId 
      And print $expected title "Expected"
    Examples:
      |userId|
      |agent|
      |skilled_agent|		
      
##################################################################################
# stas new  Use "import" expression for importing outer scenarios into scenario.
Scenario: Using the "import" expression for import outer scenarios into the feature.
	Given import "snippet.feature"
	Then start
	And print "Now we will call snippet from import"
	And set a = []
	And set y = "a" 
	And set x = "b"
	
	# it is assumed that snippet "s2" is saved in imported "snippet.feature" 
	And s2;
	And print $a title "Result of applying used snippet to a"
      
# stas new. Now you can use if-then-else, for each, driven snippet substitutions inside of rest
Scenario: Driven rest
	And set x = 1
	And set params:
	"""
	{
	"x" : "baba",
	"y" : "deda"
	}
	""" 
	
	And using REST
	And home = "http://localhost:8080"
	And param a = "mama"
	
		And if $x == 2
			Then param b = "papa"
		But else
			Then param c = "xxx"
		And endif
		
		And for each p of $params begin
			And param $p.key = $p.value
			And header $p.value = $p.key
		And next
		
	And body:
	"""
	{
		"x" : "y"
	}
	"""
	And method POST
	And send

# stas new. 2017.08.15. Using protected context.
# In protected context you can see and modify any data values from main context but all data modifications inside 
# of protected context do no affect data of main context.  
# it is useful for snippets.
# You can open protected context using:
# And begin <name>
# And begin
Scenario: Using protected context
	And set x = 1
	
#   Let's open new protected context with name of $TEST (anonymous context is opening as $)
	And begin TEST
		And set x = 2
		And set y = 3
		And remember z = 4
		And print $x title "protected X must be 2"
		And print $y title "protected Y must be 3"
		And print $z title "remembered Z must be 4"
		And $x == 2
		And $y == 3
	And end
#	Data modifications accomplished in protected context does not affect state of main context.
#	After execution of protexted context its state is returned to main context under name of protected context, or $
	And print $x title "untouched X after protected must be 1"
	And print $y title "There must be no Y after protected clause"
	
	# remember operation in protexted code affects global context state
	And print $z title "remembered Z must be 4"
	
	And $x == 1
	And $y == null
	
#  Here is state of protected context "TEST". If we would use anonymous context we could call its state using just $. For instance $.x, $.y   
	And print $TEST title "Protected context state"
	And $TEST.x == 2
	And $TEST.y == 3
	And $z == 4
	

#	
# Create and use snippet on the fly
# new stas 2017.08.15
Scenario: Snippets on the fly

    # create and run snippet at place
	And as snippet 
	And print $agentId title "agentId from snippet"
	# saves snippet under the name printAgent and runs it immediatelly 
	And go printAgent 
	
	And as snippet 
	And print $kbId title "agentId from snippet"
	# saves snippet under the name printAgent and runs it immediatelly
	And go printKbId

	And set kbId = "MamaRama"
	# runs the snippet that was executed last again 
    And @last

    And set agentId = "stas"
    # runs named snippet in normal way 
    And @printAgent

    
	And as snippet 
	And print "Mama myla ramu" title "agentId from snippet"
	# run it immediatelly
	And go
    
    # run it again
    And @last

    
# stas new. 2017.08.16. 
Scenario: Call snippet as function with direct referring data of snippet context (call snippet as function)
    # use syntax 
    # set var = <snippet-local-var-name> from <snippet name>
    # may be used in set/copy/remember expressions
    #  
  
    # use syntax 
    # set var = <snippet-local-var-name> from <snippet name>
    # may be used in set/copy/remember expressions
    #  
  
	And new snippet "prepare section configuration"
		And begin
			And set configuration = {"options" : ["prepared section $option config"]}
			And set moreconfig = "Something else" 
		And end
	And end

	 
	# lets define some "snippet parameter(s)"
	And set option = "option_value"
	
	
	
	#call snippet and set set snippet "configuration" variable to our "cfg" variable  
	And set cfg = $configuration.options from prepare section configuration
	
	#call snippet and set set snippet "moreconfig" variable to our "more" variable  
	And set more = $moreconfig from prepare section configuration
	
	And print $cfg title "prepared configuration"
	And print $more title "more configuration extracted as snippet return"

	# extract "moreconfig" from executed snippet context explicitly 	
	And print $.moreconfig title "more configuration extracted from executed snippet context"
	
	And set expectedCfg = { "options" : ["prepared section option_value config"] } 
	And set expectedCfgOptions = $expectedCfg.options
	 
	And $expectedCfgOptions == $cfg
	
	And $more == "Something else"
	And $.moreconfig == "Something else"
	And $expectedCfg == $.configuration 

	# ask for all local snippet variables 
	And set all = $ from prepare section configuration
	
	And $expectedCfg == $all.configuration
	And "Something else" == $all.moreconfig

# stas new. 2017.08.20 
Scenario: Explains all prepared snippets in form of:
# ==================================================================
# all documents
# ------------------------------------------------------------------
# Consumes: [$tenantId, $locale]
# Defs: [$.knowledgebases = { "knowledgebases" : ["$kbId"] }, $.agentId = gks_super]
# Produces: [$.documentids, $.documents, $.categoryids, $.success, $.entity, $.payload]
	And import "includes/common.f"
	And explain
	And abort

# stas new. 2017.08.20 
Scenario: Declares new snippet with comment (for snippet explain. Use tail colon ':' then write text into three-doublequote block)
	Given new snippet "successV2":
	"""
	Checks if Knowledge API v2 finished with success, stores response payload as $payload.  
	"""
	When "200" == $.status
		And set entity = $.entity
		And set payload = $entity.data
		And "SUCCESS" == $entity.status
	Then  save snippet

# stas new 2017.08.22
Scenario: test random scenario
	And set x = random 0 ... 100
	And set y = random $x ... 200 	
	And set z = random $x ... $y
	And set u = random 0 ... $x
	And print "$x $y $z $u"
	  	
 # stas new 2017.08.24
Scenario: test repeat scenario
	And set a = []
	And repeat i 10 times
		And add $i to $a
	And next
	And $a.size == 10
	And $a[0] == 1
	And $a[9] == 10
	And print $a
	
 # stas new 2017.08.25
Scenario: Break iteration repeat
	Given start
	And set a = []

	# break in repeat
	And repeat i
		And add $i to $a
		And print $a
		And if $i == 10
			Then break
		And endif
	And next
	
	# break in for each
	And set array = ["1", "2"]
	And for each i of $array begin
		And print $i
		And if $i == "1"
			And break
		And endif 
	And next
	And print $x
	
		
		
 # stas new 2017.08.25
 Scenario: extract substring by regexp extraction scenario (match)
	Given set str = "Java(TM) SE Runtime Environment (build 1.8.0_65-b17)"
	And set ver = $str/.* \(build (.*)\)/
	And "1.8.0_65-b17" == $ver
	
	# Treat array of strings as string for extracting substring by regexp
	And set array:
	"""
	[ 
		"java version \"1.8.0_65\"", 
		"Java(TM) SE Runtime Environment (build 1.8.0_65-b17)", 
		"Java HotSpot(TM) 64-Bit Server VM (build 25.65-b01, mixed mode)"
	]
	""" 
	And set ver = $array/.*java version "(.*)".*/
	And print $ver 
	
Scenario: Test string similarity assertion
	And set str = "Java(TM) SE Runtime Environment (build 1.8.0_65-b17)"
	And set otherStr = "mama myla ramu"
	
	
	And $str ~= /.*\(build (.*)\)/
	And $otherStr !~ /.*\(build (.*)\)/
	
	And set pattern = ".*\(build (.*)\)"
	
	And $str ~= $pattern 
	And $otherStr !~ $pattern  
		
	 	