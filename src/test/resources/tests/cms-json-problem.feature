Feature: CMS JSOn feature
	Scenario: -
	    Given set id = "94c97cf3-fba2-45d2-820f-bcc30f3bb1b5_en" 
	    And set json:
	    """
	    {
		  "Status" : [ "APPROVED" ],
		  "TenantId" : [ 1 ],
		  "Type" : [ 1 ],
		  "Description" : [ "" ],
		  "OwnerId" : [ 1 ],
		  "Lang" : [ "English" ],
		  "Id" : [ "testucs@en@1" ],
		  "ModifiedDate" : [ "2017-09-04T15:44:48.000Z" ],
		  "ChildrenCategories" : [ {
		    "ctgName1" : [ {
		      "Status" : [ "Approved" ],
		      "TenantId" : [ 1 ],
		      "Type" : [ 1 ],
		      "Description" : [ "" ],
		      "OwnerId" : [ 1 ],
		      "ChildrenStdResponses" : [ {
		        "srName11" : [ {
		          "CategoryId" : [ "ctgName1" ],
		          "Status" : [ "NotApproved" ],
		          "TenantId" : [ 1 ],
		          "AckUsageType" : [ "Automatic" ],
		          "StandardResponseId" : [ "srName11" ],
		          "AutoRespUsageType" : [ "Not Used" ],
		          "Lang" : [ "English" ],
		          "TheName" : [ "srName11" ],
		          "AgentDesktopUsageType" : [ "Manual" ]
		        } ]
		      } ],
		      "Lang" : [ "English" ],
		      "Id" : [ "ctgName1" ],
		      "ModifiedDate" : [ "2017-09-04T15:44:49.401Z" ],
		      "ChildrenCategories" : [ {
		        "ctgName11" : [ {
		          "Status" : [ "Approved" ],
		          "TenantId" : [ 1 ],
		          "Type" : [ 1 ],
		          "Description" : [ "" ],
		          "OwnerId" : [ 1 ],
		          "ChildrenStdResponses" : [ {
		            "srName111" : [ {
		              "CategoryId" : [ "ctgName11" ],
		              "Status" : [ "NotApproved" ],
		              "TenantId" : [ 1 ],
		              "AckUsageType" : [ "Automatic" ],
		              "StandardResponseId" : [ "srName111" ],
		              "AutoRespUsageType" : [ "Not Used" ],
		              "Lang" : [ "English" ],
		              "TheName" : [ "srName111" ],
		              "AgentDesktopUsageType" : [ "Manual" ]
		            } ]
		          } ],
		          "Lang" : [ "English" ],
		          "Id" : [ "ctgName11" ],
		          "ModifiedDate" : [ "2017-09-04T15:44:52.927Z" ],
		          "ChildrenCategories" : [ { } ],
		          "Name" : [ "ctgName11" ]
		        } ]
		      } ],
		      "Name" : [ "ctgName1" ]
		    } ],
		    "94c97cf3-fba2-45d2-820f-bcc30f3bb1b5_en" : [ {
		      "Status" : [ "NotApproved" ],
		      "TenantId" : [ 1 ],
		      "Type" : [ 1 ],
		      "Description" : [ "" ],
		      "OwnerId" : [ 1 ],
		      "ChildrenStdResponses" : [ {
		        "eb427932-092e-41c4-9cf9-ba099354cfa3_en" : [ {
		          "CategoryId" : [ "94c97cf3-fba2-45d2-820f-bcc30f3bb1b5_en" ],
		          "Status" : [ "NotApproved" ],
		          "TenantId" : [ 1 ],
		          "AckUsageType" : [ "Automatic" ],
		          "StandardResponseId" : [ "eb427932-092e-41c4-9cf9-ba099354cfa3_en" ],
		          "AutoRespUsageType" : [ "Not Used" ],
		          "Lang" : [ "English" ],
		          "TheName" : [ "srName21" ],
		          "AgentDesktopUsageType" : [ "Manual" ]
		        } ]
		      } ],
		      "Lang" : [ "English" ],
		      "Id" : [ "94c97cf3-fba2-45d2-820f-bcc30f3bb1b5_en" ],
		      "ModifiedDate" : [ "2017-09-04T15:44:49.886Z" ],
		      "ChildrenCategories" : [ {
		        "d2ee7ead-5b38-4345-ad61-e9a021aa0a5f_en" : [ {
		          "Status" : [ "NotApproved" ],
		          "TenantId" : [ 1 ],
		          "Type" : [ 1 ],
		          "Description" : [ "" ],
		          "OwnerId" : [ 1 ],
		          "ChildrenStdResponses" : [ {
		            "c4d4999d-ddb2-4a24-9fd2-9fd7a27fc7d4_en" : [ {
		              "CategoryId" : [ "d2ee7ead-5b38-4345-ad61-e9a021aa0a5f_en" ],
		              "Status" : [ "NotApproved" ],
		              "TenantId" : [ 1 ],
		              "AckUsageType" : [ "Automatic" ],
		              "StandardResponseId" : [ "c4d4999d-ddb2-4a24-9fd2-9fd7a27fc7d4_en" ],
		              "AutoRespUsageType" : [ "Not Used" ],
		              "Lang" : [ "English" ],
		              "TheName" : [ "srName211" ],
		              "AgentDesktopUsageType" : [ "Manual" ]
		            } ]
		          } ],
		          "Lang" : [ "English" ],
		          "Id" : [ "d2ee7ead-5b38-4345-ad61-e9a021aa0a5f_en" ],
		          "ModifiedDate" : [ "2017-09-04T15:44:53.497Z" ],
		          "ChildrenCategories" : [ { } ],
		          "Name" : [ "ctgName21" ]
		        } ]
		      } ],
		      "Name" : [ "ctgName2" ]
		    } ]
		  } ],
		  "Name" : [ "testUCS(English)" ]
		}
			    
	    """
	    And set tenantId = 1
	    And set x = all ChildrenCategories..${id} from $json
	    And $tenantId == $x[0].TenantId[0]
	    