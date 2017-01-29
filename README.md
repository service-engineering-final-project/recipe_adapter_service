# Recipe Adapter Service (REST)

The **quote adapter service** is the service that provides data to the **storage data service** through a REST interface. It allows consumers to retrieve motivational quotes in a structured format (both XML and JSON) crawled from [Quotelicious](http://www.quotelicious.com);
The **recipe adapter service** is the service that provides data to the **storage data service** through a REST interface. It allows consumers to retrieve recipes and their nutritional facts in a structured, clearer format (both XML and JSON) exploiting the [Yummly](http://www.yummly.com/) APIs (by means of an academic plan); 

| resource | link |
|----------|------|
| API documentation | http://docs.recipeadapterservice.apiary.io/ |
| Heroku base URL | https://recipe-adapter-service-ar.herokuapp.com/rest/ |

### How to run it
Since the server is already deployed on Heroku, it is only needed to go to the Heroku base URL. However, you can also deploy again the server on Heroku via ant.

**Optional**: If you want to try the server locally, you can follow the steps below:
* **Clone** the repo: `git clone https://github.com/service-engineering-final-project/recipe_adapter_service.git`;
* **Navigate** into the project folder: `cd recipe_adapter_service`;
* **Install** the packages needed: `ant install`;
* **Run** the server using ant: `ant execute.server`.