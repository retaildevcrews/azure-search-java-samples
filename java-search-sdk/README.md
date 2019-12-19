# Azure Java Search SDK Reference Implementation

This reference implementation demonstrates the use of Azure Java Search SDK.

## Prerequisites

- Azure subscription with permissions to create:
  - Resource Group and Azure Search Service
- Java 8 or higher [download](https://jdk.java.net/)
- Maven 3.6 or higher [download](https://maven.apache.org/download.cgi)
- Visual Studio Code (or dev environment of your choice) [download](https://code.visualstudio.com/Download)

## Setup

## Create an instance of Azure Cognitive Search Service

- Create [Search Service](https://docs.microsoft.com/en-us/azure/search/search-create-service-portal)  
- Load Index data from existing hotels sample-data [data](https://docs.microsoft.com/en-us/azure/search/search-get-started-portal)
  - Note the key, url end point and index name

## Download the code

- Fork this repo and clone to your local machine
  - cd to the base directory of the repo  
- update the key and url end point and index name in the src/main/resources/application.properties

```bash

# Run locally
- cd java-search-sdk
- mvn clean package
- mvn clean spring-boot:run

# Test the application
curl http://localhost:8080/api/hotel/docs/count

```

## Swagger Docs

[localhost:8080/swagger-ui.html](http://localhost:8080/swagger-ui.html)

- All APIs other than search APIs have example values for testing specified  
- For search with wrapper and without wrapper use the following request body to test  

```json
{
     "facets": "",  
     "filter": "Rooms/any(room: room/BaseRate ge 50 and room/BaseRate lt 70)",
     "highlight": "",  
     "highlightPreTag": "",  
     "highlightPostTag": "",  
     "minimumCoverage": 80.0,  
     "orderby": "Rating desc",  
     "scoringParameters": "",  
     "scoringProfile": "",  
     "search": "*",  
     "searchFields": "",  
     "searchMode": "any",  
     "select": "HotelId, HotelName, Description,Rooms/BaseRate",  
     "skip": 0,  
     "top": 100  
}
```

## Contributing

This project welcomes contributions and suggestions.  Most contributions require you to agree to a
Contributor License Agreement (CLA) declaring that you have the right to, and actually do, grant us
the rights to use your contribution. For details, visit [cla.microsoft.com](https://cla.microsoft.com).

When you submit a pull request, a CLA-bot will automatically determine whether you need to provide
a CLA and decorate the PR appropriately (e.g., label, comment). Simply follow the instructions
provided by the bot. You will only need to do this once across all repos using our CLA.

This project has adopted the [Microsoft Open Source Code of Conduct](https://opensource.microsoft.com/codeofconduct/).
For more information see the [Code of Conduct FAQ](https://opensource.microsoft.com/codeofconduct/faq/) or
contact [opencode@microsoft.com](mailto:opencode@microsoft.com) with any additional questions or comments.
