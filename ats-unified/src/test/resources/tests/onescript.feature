Feature: onescript

Scenario: Snippets for indexing GKC documents
	And start
	###########################################################################
	And new snippet "random content": 
	"""
	Prepares random content for knowledge: 
	Exposes:
	- id - random document id
	- title - random document title
	- content - random textual content of 3-5 senteces 
	- summary - random textual content of 3-5 senteces
	"""
		And begin
			And set questions = [ "What Is Genesys Knowledge Center?", "What components are included in Genesys Knowledge Center?", "What is the difference between the Knowledge Cluster and the Knowledge Server?", "How do I configure two Knowledge Servers into a cluster?", "Why do I need the Genesys Knowledge Center CMS?", "Can I use my own CMS system instead of the Genesys Knowledge Center CMS?", "What is a knowledge base?", "Can I restrict access to the knowledge base to my agents only?", "What are custom fields?", "How do I import data into my knowledge base?", "How do I create a new knowledge base in the Genesys Knowledge Center Server?", "Why do I need to configure two communication ports for the Genesys Knowledge Center cluster?", "What are alternative questions?", "How are attachments used in finding the right answer to a question?", "How does Genesys Knowledge Center find the right answers to my questions?", "How should I ask questions?", "What is the Sample UI?", "How many clusters are required in my environment?", "How many nodes can be added to the cluster?", "Why do I need the Administrator plugin?", "What reporting data is available in the Pulse plugin?", "What types of events does Genesys Knowledge Center record in the historical database?", "How long is information stored in the historical database?", "Do the Knowledge Center CMS and the Knowledge Center Server use the same data?", "Why is the correct answer shown as the second item in the list of answers?", "How does my feedback help Genesys Knowledge Center?", "What happens when I indicate that my question wasn't answered?", "Why aren't any results returned from my query?", "Can agents add new documents to the knowledge base?", "Why do I need the Workspace plugin?", "How can I proactively offer a chat when no answers are found?", "Can I offer a chat session when negative feedback is provided by the user?", "Can I prevent Knowledge Center from answering certain questions?", "Can I store knowledge articles in different languages?", "Can I publish the articles authored in my CMS on my website?", "The FAQs I have are publicly available. Can I provide links to them?", "Can I prevent customers from seeing certain knowledge base articles?", "Is agent feedback treated differently from customer feedback?", "Can I search for knowledge base articles in French or other languages?", "Should I let Genesys Knowledge Center Server know how many answers a user viewed?", "Where does the CMS store the articles?", "Can I have my CMS store content in a database or other data store?", "Are there limitations on what and how many sources the CMS can index information from?", "Can I do Search Engine Optimization for articles created in the Genesys Knowledge Center CMS?", "How can I add search capability to a custom agent desktop?", "Does Genesys Knowledge Center know how much time a person spends reading each article?", "How can I archive older knowledge base articles?", "How can I set access control for my administrators and managers?", "How can I assign Genesys Knowledge Center Plugins to my agents?", "Can I configure my knowledge article approval workflow and lifecycle?", "Can I use Genesys Knowledge Center as a virtual assistant?" ]
			And set sentences = [ "Genesys Knowledge Center allows you to make the best use of your enterprise knowledge by capturing, storing, and distributing it wherever it is needed", "Built by Genesys, this product seamlessly integrates to various Genesys products to provide configuration via Genesys Administrator, reporting and basic analytics via Pulse and agent desktop integration to Workspace Desktop Edition Knowledge Center is made up of several elements, all of which work together to convert knowledge into answers", " It includes the following components: - Knowledge Center Server -- the heart of Genesys Knowledge Center, this server indexes all of your knowledge and uses it to answer your questions", " - Knowledge Center CMS -- this optional Content Management System gives you more control over the organization and maintenance of your stored knowledge", " It offers trouble-free integration with other Genesys components", " - Knowledge Center Plugin for Workspace Desktop Edition -- lets your agents use the power of Genesys Knowledge Center from within their Genesys Workspace", " - Knowledge Center Plugin for Pulse -- helps you stay up to date on what knowledge is being used and how it is being used within your enterprise", " - Knowledge Center Plugin for Administrator -- makes it easy to manage the knowledge bases contained in your Genesys Knowledge Center Server", " Each Genesys Knowledge Center Server represents a single node in the Genesys Knowledge Cluster", " You can group multiple nodes or servers into a cluster to perform better against higher traffic volume", " You can add a Genesys Knowledge Center Server to a Knowledge Center Cluster by configuring it with a connection to the cluster", " Every server within a cluster uses the same set of knowledge bases and is able to handle any question directed to these knowledge bases", " For information on how to configure the servers into a cluster, please refer to our online Deployment Guide", " The Genesys Knowledge Center CMS is an effortless way to author your content, providing access management, versioning, and tagging", " It is easy to use and provides centralized storage of your content", " The Genesys Knowledge Center CMS is optional", " Although it provides effortless integration with the other components of Knowledge Center, and includes many standard CMS functionalities, we know that you may have invested a lot in tuning your corporate CMS so that it perfectly suits your needs", " That is why we have exposed a RESTful Management API as an easy way for you to index information from your CMS system directly into your Knowledge Center Server", " This will allow you to use all of the knowledge retrieval capabilities delivered by Genesys Knowledge Center while preserving the content within your existing CMS system", " A knowledge base is an organized collection of related information that includes taxonomic data, allowing users to find the information they need by asking questions", " Yes, a knowledge base can be declared as private, in which case it will be accessible to agents only", " Information on how to declare a knowledge base as private can be found in the Knowledge Center Plugin for Administrator section of the Genesys Knowledge Center User's Guide", " Custom fields can be used to add as many types of additional information to the document as needed", " You can find information on how to configure custom fields in the Knowledge Center Plugin for Administrator and Knowledge Center CMS sections of the Genesys Knowledge Center User's Guide", " There are a number of ways to do this", " If you have decided to use the Genesys Knowledge Center CMS there is an import action available for every knowledge base", " Refer to the online User's guide for a step-by-step procedure", " If you want to import knowledge directly into the Knowledge Center Server, you can use the Indexer tool that is shipped with the product (see the Indexer topic in the Deployment Guide) or you can use the Management API to push knowledge from its existing location (see the API Reference)", " To create a new knowledge base, use the Genesys Knowledge Center configuration, which is accessible from within Genesys Administrator", " The online User's Guide provides detailed instructions on how to do this", " Genesys Knowledge Center Server carries out two different types of communication", " The HTTP/default port enables client access to the server and can be exposed to the outside world to allow integration with public websites", " The transport port, on the other hand, is intended for internal communication between Knowledge Center Servers that reside within the same Knowledge Center Cluster", " Since there are many ways to ask a question, it is helpful to allow for alternative questions--that is, several alternative formulations of the same general question", " This allows you to capture the most common rephrasings of that question, which thereby makes it easier for Knowledge Server to find the best match to a user query", " Any attachments to a document are converted to text format and evaluated by the search algorithm in the same way as the questions and answers", " Thats our intellectual property, but if you insist on knowing, we use a variety of techniques like word matching, machine learning and semantic analysis", " You can ask questions in whatever way is most convenient for you", " Genesys Knowledge Center is designed to answer real questions from real people, so it doesn't require any special effort on the part of your agents or your customers", " The Sample UI is a working sample of the application that expose most of the functionality of the Knowledge API", " It allows you to browse your knowledge base, ask questions, and provide feedback, as well as supporting knowledge sessions and being ready for easy integration with Genesys Web Engagement", " In most cases one cluster will be enough, as there is no limit to the number of knowledge bases that can be created in a single cluster", " The minimal configuration is one node per cluster", " Although there is no limit to the maximum number of nodes in a cluster, please consult a Genesys Solutions Architect for optimal configuration to meet you needs", " In short, adding additional nodes to the Genesys Knowledge Center Cluster is the simplest way to handle increasing load", " Genesys Knowledge Center Administrator Plugin provides you an easy-to-use UI to create and manage knowledge bases in your knowledge cluster", " Please refer to the User's Guide to learn more about features provided by this plugin The Pulse plugin allows you to monitor the performance of Genesys Knowledge Center and your knowledge bases", " It provides a visual representation of your summary data and basic drill down capabilities to analyze the information in more detail", " You can also choose to visualize data for various time frames that you are interested in and filter based on knowledge base, type of event, and so on", " The historical database stores all agent and customer activities that are related to the knowledge base (including search requests, documents viewed and liked or disliked, queries that the system was not able to find answers for, and so on)", " The default depth of the historical database is 14 days", " This value is a configurable option in the reporting section of the Genesys Knowledge Center Cluster application", " The Genesys Knowledge Center CMS and Genesys Knowledge Center Server have separate data storage", " Data is stored in the CMS and indexed to the Knowledge Center Server during the CMS export procedure", " Genesys Knowledge Center decides on the relevance of a particular document for a particular query based on a number of factors", " If the top answer is not ideal, people can provide feedback to the system using the like/dislike functionality, thereby allowing Knowledge Server to return better results on subsequent queries", " User feedback helps Genesys Knowledge Center improve its understanding of the various questions people need answers to", " The more feedback we get, the smarter our answers can be", " When you tell Genesys Knowledge Center that your question hasn't been answered, you are providing the kind of feedback that is the main source of improvements to the knowledge base", " The knowledge administrators can export a list of unanswered questions to either the Knowledge Center CMS or a third-party CMS system", " This list will help the knowledge base authors understand exactly what kind of content needs to be added", " Genesys Knowledge Center is designed to provide a response only if it is confident that its answers match the question", " While standard search engines sometimes overload the user with irrelevant information that has one or two words that match the initial query, Knowledge Center is designed to answer your questions", " And if it can't do that, it doesn't pretend that it can", " Absolutely! Agents are the subject matter experts in most cases and therefore they can suggest new document to be added to the knowledge base", " However, before these suggestions are incoporated into the knowledge base these documents are automatically imported into the CMS for review, and approval but an authorized administrator", " For a detailed description of the procedure for this, refer to the Genesys Knowledge Center User's Guide", " The Workspace Desktop Edition Plugin allows your agents to view the history of a customer's searches, answers viewed and their feedback , while also letting them ask their own questions as they search for the best answer", " Genesys Knowledge Center can be tightly integrated with Genesys Web Engagement (GWE), allowing GWE to consume events that are generated by Knowledge Center", " Refer to the Knowledge Center Developer's Guide for more information on how to integrate Knowledge Center with GWE", " That is one of the most common integration use case between Genesys Knowledge Center and Genesys Web Engagements", " We have this extensively documented in our online Developer's Guide", " Today, Knowledge Center tries to find the best answer from the knowledge base no matter what questions have been asked", " We will be adding more intelligence to Knowledge Center in the upcoming releases to make such intelligent decisions", " The current release of Genesys Knowledge Center is only available in English", " Our A-team is working very hard to support more languages at the earliest opportunity", " The Genesys Knowledge Center CMS exposes the content of its knowledge bases via HTTP/s in both XML and JSON format", " Websites that have the appropriate import capabilities can publish this content as if it was native to the site", " If you are using the Genesys Knowledge Center CMS you can use its publication functionality to refer to a document from external sources", " You could also use the browsing functionality of the Knowledge API", " Please refer to the Developer's Guide for more information", " Articles that you do not want to be visible to your customers (such as internal procedures) must be stored in a private knowledge base that is only accessible to agents", " Public knowledge bases are designed to be publicly available and must therefore only contain content that is appropriate for public consumption", " Both agent and customer feedback allow Knowledge Center to improve the quality of its responses", " However, in most cases, agent feedback is considered to be more reliable", " There are also many ways in which agents can help create and improve knowledge via the Workspace plugin and the Knowledge Center CMS", " Refer to the Knowledge Center User's Guide for more information", " The v8", "5 of Genesys Knowledge Center is only available in English", " The team is working very hard to support more languages at the earliest opportunity", " This is a good idea, since feedback (also including likes, dislikes, marking queries as unanswered, and so on) is the main way to improve Knowledge Center results", " The Genesys Knowledge Center CMS can use different types of data storage in addition to your existing data storage", " After installation it uses your Knowledge Center Server's file system to store information", " Refer to the Knowledge Center Deployment Guide for information on how to configure various types of data storage", " By default, the Genesys Knowledge Center CMS stores all of its information in the file system", " But you can easily configure it to use another type of storage", " The Knowledge Center CMS supports Microsoft SQL Server, Oracle, and other types of data storage", " Genesys Knowledge Center Server has a REST API that allows you to have as many types of data integration as you want", " Genesys recommends that you have one source of information for your knowledge base", " This means that all of your information is collected and edited in one place", " It also prevents duplication of your information", " However, there is no restriction on the number or types of sources that you use to assemble this information", " You can store as much additional data as you want in the knowledge base -- including SEO information -- by configuring the appropriate custom fields", " Knowledge Center Server exposes all of its functionality through a REST API", " You can use this API to add search capabilities to your custom desktop", " No", " Genesys Knowledge Center focuses on feedback that users provide about each document and on keeping track of which documents they have viewed, as this information is much more valuable than measuring their reading speed", " However, if you are interested in creating implicit feedback (such as that a user dislikes a document if they have spent less then 5 seconds on its page) you can refer to the Knowledge Center Developer's Guide for information on how to integrate Genesys Knowledge Center and Genesys Web Engagement", " The only archival functionality supported by the current release of Knowledge Center is history archiving, Both Knowledge Center Server and CMS have their own sets of administrative privileges to allow agent to become administrators of these products", " Refer to the Knowledge Center Deployment Guide for more information on these privileges and how to use them", " To provide access to Genesys Knowledge Center functionality selectively to your agents, ensure that they have the Knowledge Worker role", " Knowledge Center also supports other sets of privileges", " For example, roles within Genesys Workspace Desktop Edition can also be configured to provide access to the plugin The current version of the Knowledge Center CMS only supports a predefined approval workflow, as described in the User's Guide", " Future versions of Knowledge Center will allow you to configure your own workflow", " Genesys Knowledge Center will be information source for virtual assistants", " But it is not a virtual assistant itself", " It can help an existing or 3rd Party virtual assistant find the best answer to a question" ]
			
			And set nsentences = random 3 ... 5
			And set questionIndex = random 0 ... $questions.size()
			And set title = $questions[questionIndex]
			And set summary = ""
			
			And set content = ""
			And set id = "" 
			And repeat i $nsentences times
				And set sentenceIndex = random 0 ... $sentences.size()
				And set sentence = $sentences[$sentenceIndex]
				And set content = "${content} ${sentence}."
				And set id = "${id}${i}${sentenceIndex}"
				And set sentenceIndex = random 0 ... $sentences.size()
				And set sentence = $sentences[$sentenceIndex]
				And set summary = "${summary} ${sentence}."
			And next
			And set id = "id${id}"  
			And set content = $content.trim()
			And set summary = $content.trim()
			And expose id, title, content, summary
		And end
	And save snippet
	
	###########################################################################
	And new snippet "random document":
	"""
	Prepares documents with random content for indexing. 
	By default create document of type "ARTICLE" with template "ARTICLE"
	"""
		And begin
		And random content;
		And def id = $.id
		And def type = "ARTICLE"
		And def templateId = "ARTICLE"
		And def title = $.title
		And def content = $.content
		And def summary = $.summary
		And def url = "http://url/$id"
		And def categories = []
		And def tags = []
		And def media = []
		And def categories = []
		And ifndef $contentSectionId
			And if $templateId == "ARTICLE"
				Then set contentSectionId = "description"
			And endif
			And if $templateId == "FAQ"
				Then set contentSectionId = "answer"
				And set summary = null
			And endif	
		And endif
		
		And set document:
		"""
		{   "id" : $id,
		    "type" : $type,
		    "templateId" : $templateId,
		    "title" : $title,
		    "url" : $url,
		    "tags" : $tags,
		    "media" : $media,
		    "content" : {
		      "$contentSectionId" : $content
		    },
		    "summary" : $summary,
		    "categories" : $categories
		}
		"""
		And expose document
		And end
	And save snippet	


	###########################################################################
	And new snippet "random indexing request":
	"""
	Prepares request for indexing $count=10 documents of type $type="ARTICLE" and $templateId = "ARTICLE"
	"""
		And begin
			And def count = 10
			And set documents = []
			And repeat i $count times
				And random document;
				And add $.document to $documents 
			And next
			And set request = { "documents" : $documents }
			And expose request
		And end
	And save snippet
	
	And perform "random indexing request" with
		And set type = "FAQ"
		And set templateId = "FAQ"
	And go
	And print $.request
	 
	
	
	
