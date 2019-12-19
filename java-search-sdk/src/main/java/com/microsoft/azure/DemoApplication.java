package com.microsoft.azure;

import com.azure.core.http.rest.PagedIterableBase;
import com.azure.search.Document;
import com.azure.search.SearchPagedResponse;
import com.azure.search.models.FacetResult;
import com.azure.search.models.SearchResult;
import com.microsoft.azure.app.hotel.HotelsService;
import com.microsoft.azure.app.hotel.SearchCriteria;
import com.microsoft.azure.app.hotel.wrapper.FacetResponseWrapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import java.util.*;

/**
 * Hello world!
 *
 */
@SpringBootApplication
@EnableSwagger2
public class DemoApplication implements CommandLineRunner
{
    @Autowired
    public HotelsService hotelsService;
    public static void main(String[] args) {
        SpringApplication.run(DemoApplication.class, args);
    }

    @Override
    public void run(String... args) throws Exception {
        runTests();
    }

    public void runTests(){
        /*******************************************************************************************/
        System.out.println("Numbers of Documents in Hotel Index "+ hotelsService.getDocumentCount());

        /*****************************************************************************************/
        System.out.println("Search Document with Id 39"+ hotelsService.getSingleDocument("39"));

        /*****************************************************************************************/
        System.out.println("Search hotels with Parking Included " );
        PagedIterableBase<SearchResult, SearchPagedResponse> searchResultsWithParkingIncluded = hotelsService.searchHotelsWithParkingIncluded();
        Iterator<SearchPagedResponse> iterator = searchResultsWithParkingIncluded.iterableByPage().iterator();
        if (iterator.hasNext()) {
            SearchPagedResponse searchPagedResponse = iterator.next();
            // since setIncludeTotalResultCount was set to true, each page will contain the total count of results
            System.out.println("Result count : Hotels with parking included :" + searchPagedResponse.getCount());
            searchPagedResponse.getValue().forEach(r -> System.out.println(r.getDocument().get("HotelName")));
        }

        //&$filter=Rooms/any(room: room/BaseRate lt 200.0) and Rating ge 4&facet=Rating,values:1|2|3|4|5&$count=true

        /************************************GENERIC SEARCH*****************************************************/

        String facet = "";
        String filter = "Rooms/any(room: room/BaseRate lt 200.0) and Rating ge 4";
        String search = "beach";
        String highLightFields = "";
        String highLightPostTag = "";
        String highLightPreTag = "";
        Double minimumCoverage = 80.0;
        String orderBy = "Rating desc";
        String selectFields = "HotelId, HotelName, Description,Rooms/BaseRate ";
        String searchFields = "";
        String searchMode = "ANY";
        String scoringParameters = "";
        String scoringProfile = "";
        Integer skip = 0;
        int top = 10;


        SearchCriteria searchCriteria = new SearchCriteria(facet, filter, highLightFields, highLightPostTag, highLightPreTag, minimumCoverage,
                orderBy, scoringParameters, scoringProfile,  search, searchFields, searchMode, selectFields, skip, top);

        System.out.println("Search Results for " +search );
        PagedIterableBase<SearchResult, SearchPagedResponse> searchResults =
                hotelsService.search(searchCriteria);

        Iterator<SearchPagedResponse> iteratorGen = searchResults.iterableByPage().iterator();
        if (iteratorGen.hasNext()) {
            SearchPagedResponse searchPagedResponse = iteratorGen.next();
            // since setIncludeTotalResultCount was set to true, each page will contain the total count of results
            System.out.println("Result count :" + searchPagedResponse.getCount());
            searchPagedResponse.getValue().forEach(r -> System.out.println(r.getDocument()));
        }

        /*****************************************************************************************/

        /*********************************SEARCH FACETS********************************************************/

        //String facets = "Rooms/BaseRate,values:5|8|10, LastRenovationDate,values:2000-01-01T00:00:00Z";
        String facets = "Category";
        System.out.println("***********************SEARCH FACETS***********************************" + facets );
        PagedIterableBase<SearchResult, SearchPagedResponse> results =   hotelsService.searchFacets(facets);
        Iterator<SearchPagedResponse> response = results.iterableByPage().iterator();
        if (response.hasNext()) {
            Map<String, List<FacetResult>> searchFacetResults = response.next().getFacets();
            System.out.println("searchFacetResults " + searchFacetResults);
            FacetResponseWrapper facetResponseWrapper = new FacetResponseWrapper();
            List<FacetResult> facetResults = new ArrayList<>();
            List<Document> documents = new ArrayList<>();

            List<Map<String, Long>> lists = new ArrayList<>();
            Map<String, Long> result = new HashMap<>();

            for (String key : searchFacetResults.keySet()) {
                facetResults = searchFacetResults.get(key);
                facetResults.forEach((value) -> {
                    result.put(value.getDocument().get("value").toString(), value.getCount());
                    System.out.println("Key = " + key + ",  Value = " + value.getDocument().get("value") + " count = " + value.getCount());
                });
                lists.add(result);
            }
        }
    }

}
