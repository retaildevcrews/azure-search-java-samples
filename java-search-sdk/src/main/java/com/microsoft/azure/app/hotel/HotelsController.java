package com.microsoft.azure.app.hotel;

import com.azure.core.http.rest.PagedIterableBase;
import com.azure.search.Document;
import com.azure.search.SearchPagedResponse;
import com.azure.search.models.FacetResult;
import com.azure.search.models.SearchResult;
import com.microsoft.azure.app.hotel.wrapper.FacetResponseWrapper;
import com.microsoft.azure.app.hotel.wrapper.ResponseWrapper;
import io.swagger.annotations.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.*;

/**
 * MovieController
 */

@RestController
@Api(tags = "searchhotels", value = "search hotels based on given criteria")
public class HotelsController {


    @Autowired
    private HotelsService service;

    @GetMapping("/api/hotel/docs/count")
    @ApiOperation(value = "Get count of hotels", notes = "Retrieve count of hotels in the index")
    @ApiResponses(value = { @ApiResponse(code = 200, message = "Count of hotels in the index") })
    public ResponseEntity<Long> getDocumentCount() {
        long docCount = service.getDocumentCount();
        return new ResponseEntity<>(docCount, HttpStatus.OK);
    }

    @GetMapping("/api/hotel/docs/{id}")
    @ApiOperation(value = "Retrieve hotel with requested id", notes = "Retrieve hotel with requested id")
    @ApiResponses(value = { @ApiResponse(code = 200, message = "Searched Hotel with requested id") })
    public ResponseEntity<Document> getSingleDocument(@PathVariable("id") @ApiParam(value = "id", example = "39")  String docId) {
        Document doc = service.getSingleDocument(docId);
        return new ResponseEntity<>(doc, HttpStatus.OK);
    }

    @PostMapping("/api/hotel/docs/search-with-wrapper")
    @ApiOperation(value = "Search hotels with provided criteria", notes = "Search hotels with provided criteria")
    @ApiResponses(value = { @ApiResponse(code = 200, message = "Searched Results with provided criteria") })
    public ResponseEntity<ResponseWrapper> retrieveSearchWithWrapper(@RequestBody SearchCriteria searchCriteria ) {

        ResponseWrapper responseWrapper = new ResponseWrapper();
        PagedIterableBase<SearchResult, SearchPagedResponse> results = service.search(searchCriteria);
        Iterator<SearchPagedResponse> response = results.iterableByPage().iterator();
        if (response.hasNext()) {
            SearchPagedResponse searchPagedResponse = response.next();
            // since setIncludeTotalResultCount was set to true, each page contains the total results count
            Long count = searchPagedResponse.getCount();
            System.out.println("Result count :" + count);

            List<Document> documents = new ArrayList<>();
            searchPagedResponse.getValue().forEach(r ->
                    documents.add(r.getDocument()));

            responseWrapper.setCount(count);
            responseWrapper.setValue(documents);
        }
        return new ResponseEntity<>(responseWrapper, HttpStatus.OK);
    }

    @PostMapping("/api/hotel/docs/search")
    @ApiOperation(value = "Search hotels with provided criteria", notes = "Search hotels with provided criteria")
    @ApiResponses(value = { @ApiResponse(code = 200, message = "Searched Results with provided criteria") })
    public ResponseEntity<PagedIterableBase<SearchResult, SearchPagedResponse>>retrieveSearch(@RequestBody SearchCriteria searchCriteria ) {

        PagedIterableBase<SearchResult, SearchPagedResponse> results =  service.search(searchCriteria);
        return new ResponseEntity<>(results, HttpStatus.OK);
    }

    @GetMapping("/api/hotel/docs/facets")
    @ApiOperation(value = "Search hotels with facets", notes = "Search hotels with facets")
    @ApiResponses(value = { @ApiResponse(code = 200, message = "Searched Results with provided criteria") })
   public ResponseEntity<Map<String, List<FacetResult>>> getFacets(@RequestParam("q") @ApiParam(value = "q", example = "Category")  String facets) {

        PagedIterableBase<SearchResult, SearchPagedResponse> results =   service.searchFacets(facets);
        Iterator<SearchPagedResponse> response = results.iterableByPage().iterator();
        Map<String, List<FacetResult>> searchResults = response.next().getFacets();
        List<FacetResult> facetResults = new ArrayList<>();
        for (String key : searchResults.keySet()) {
            facetResults = searchResults.get(key);
            facetResults.forEach((value) -> {
                System.out.println("Key = " + key + ",  Value = " + value.getDocument().toString() + " count = " + value.getCount());
            });
        }

        System.out.println("returning search results "+searchResults.toString());
        return new ResponseEntity<Map<String, List<FacetResult>>>(searchResults, HttpStatus.OK);
    }

   @GetMapping("/api/hotel/docs/facets-with-wrapper")
   @ApiOperation(value = "Search hotels with facets", notes = "Search hotels with facets")
    @ApiResponses(value = { @ApiResponse(code = 200, message = "Searched Results with provided criteria") })
    public ResponseEntity<FacetResponseWrapper>getFacetsWithWrapper(@RequestParam("q") @ApiParam(value = "q", example = "Category") String facets) {
        PagedIterableBase<SearchResult, SearchPagedResponse> results =   service.searchFacets(facets);
        Iterator<SearchPagedResponse> response = results.iterableByPage().iterator();
        Map<String, List<FacetResult>> searchResults = response.next().getFacets();

        List<Map<String, Long >> lists = new ArrayList<>();
        Map<String, Long> facet = new HashMap<>();

        List<FacetResult> facetResults = new ArrayList<>();
        for (String key : searchResults.keySet()) {
            facetResults = searchResults.get(key);
            facetResults.forEach((value) -> {
                facet.put(value.getDocument().get("value").toString(), value.getCount());
            });
            lists.add(facet);
        }
        FacetResponseWrapper wrapper = new FacetResponseWrapper();
        wrapper.setFacet(facets);
        wrapper.setResults(lists);
         return new ResponseEntity<>(wrapper, HttpStatus.OK);
    }


}