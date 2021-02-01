# Activeuser
	Activeuser project use by ehcache 3 for store all entries include sub system with special feature- stream +swagger mvc+ controllerAdvice and jib with helm for CICD to the cloud in K8S .

	This project contain soluton for search active user per daily .
	Project design by MVC class diagram with most popular design pattarns(entity,dto,repository,service)
	
	For simulation there is a suagger ui with three endpoint:
		- /api/v1/user/{insertDate} - show the active user per daily ,the solution of the question
		- /api/v1/user - I add them to show all the data include the grouping and special structal. there is option to refresh the cache by pass param that update him from the subsystem
		- /api/v1/cache - clear the cache

	more details in swagger UI.
	The concept is to be in Lazy load pattern and no load all params in any request only while you need.
	this concept allow the project to be more scale and effective in the runtime(O1), while the cache group the data entries to insertdate key.
	
	
	
	
