package com.microsoft.azure.app.hotel;


import com.azure.core.http.rest.PagedIterableBase;
import com.azure.search.Document;
import com.azure.search.SearchPagedResponse;
import com.azure.search.models.*;
import com.microsoft.azure.config.ClientConfiguration;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Iterator;
import java.util.List;
import java.util.Map;

/**
 * MoviesService
 */
@Service
public class HotelsService {


    @Autowired
    ClientConfiguration clientConfiguration;

    public long getDocumentCount(){
        return clientConfiguration.client().getDocumentCount();
    }

    public Document getSingleDocument(String docId){
        Document document = clientConfiguration.client().getDocument(docId);
        for (String key : document.keySet()) {
            System.out.println(key + ":" + document.get(key));
        }
        return document;
    }

    /*These  fuzzySearch (~), String proximity (\"hotel airport\"~5"), String termBooster(^) is defined in searchCriteria */
    public PagedIterableBase<SearchResult, SearchPagedResponse> basicSearch(String searchTerm) {
        System.out.println("searching... " + searchTerm);
        SearchOptions parameters = new SearchOptions()
                .setQueryType(QueryType.FULL)
                .setIncludeTotalResultCount(true);

        // pass null as the context
        PagedIterableBase<SearchResult, SearchPagedResponse> searchResults =  clientConfiguration.client().search(searchTerm, parameters, new RequestOptions(), null);

        return searchResults;
    }

    public PagedIterableBase<SearchResult, SearchPagedResponse> searchWithFilters(String searchCriteria, String filterCriteria) {
        System.out.println("searching... " + searchCriteria +" with filter " + filterCriteria);
        SearchOptions parameters = new SearchOptions()
                .setFilter(filterCriteria)
                .setQueryType(QueryType.FULL)
                .setIncludeTotalResultCount(true);

        PagedIterableBase<SearchResult, SearchPagedResponse> searchResults =  clientConfiguration.client().search(searchCriteria, parameters, new RequestOptions(), null);

        return searchResults;
    }

    public PagedIterableBase<SearchResult, SearchPagedResponse> searchWithOptions(String searchCriteria, String filterCriteria, String selectFields, String searchFields, String facets, String orderBy, Integer top) {
        System.out.println(
                "searching... " + searchCriteria
                +"with filter " + filterCriteria
                +"order by " + orderBy
                +"select " + selectFields
                +"searchfields" + searchFields
                +"facets" +facets);

        SearchOptions parameters = new SearchOptions()
                .setQueryType(QueryType.FULL)
                .setIncludeTotalResultCount(true);

        if(filterCriteria != null){
            parameters.setFilter(filterCriteria);
        }
        if(orderBy != null){
            parameters.setOrderBy(orderBy);
        }
        if(top != null){
            parameters.setTop(top);
        }
        if(selectFields != null){
            parameters.setSelect(selectFields);
        }
        if(searchFields != null){
            parameters.setSearchFields(searchFields);
        }
        if(facets != null){
            parameters.setFacets(facets);
        }

        PagedIterableBase<SearchResult, SearchPagedResponse> searchResults =  clientConfiguration.client().search(searchCriteria, parameters, new RequestOptions(), null);

        return searchResults;
    }

    public PagedIterableBase<SearchResult, SearchPagedResponse> search(SearchCriteria searchCriteria) {
        String facets = "";
        String filter = "";
        String highlight = "";
        String highLightPostTag = "";
        String highLightPreTag = "";
        Double minimumCoverage = null;
        String orderBy = "";
        String scoringParameters = "";
        String scoringProfile = "";
        String search = "";
        String searchFields = "";;
        String searchMode = "";;
        String select = "";;
        Integer skip = 0;
        Integer top = 0;

        System.out.println(
                "searching... " + searchCriteria.search
                        +"with filter " + searchCriteria.filter
                        +"order by " + searchCriteria.orderBy
                        +"select " + searchCriteria.select
                        +"searchfields" + searchCriteria.searchFields
                        +"facets" + searchCriteria.facets
                        +"highlight" + searchCriteria.highlight
                        +"highLightPreTag" + searchCriteria.highLightPreTag
                        +"highLightPostTag" + searchCriteria.highLightPostTag
                        +"minumumcoverage" + searchCriteria.minimumCoverage
                        +"scoringProfile" + searchCriteria.scoringProfile
                        +"scoringParameters" +searchCriteria.scoringParameters
                        +"searchMode" +searchCriteria.searchMode
                        +"skip" +searchCriteria.skip
                        +"top" +searchCriteria.top);

        if(searchCriteria != null) {
             facets = searchCriteria.facets;
             filter = searchCriteria.filter;
             highlight = searchCriteria.highlight;
             highLightPostTag = searchCriteria.highLightPostTag;
             highLightPreTag = searchCriteria.highLightPreTag;
             minimumCoverage = searchCriteria.minimumCoverage;
             orderBy = searchCriteria.orderBy;
             scoringParameters = searchCriteria.scoringParameters;
             scoringProfile = searchCriteria.scoringProfile;
             search = searchCriteria.search;
             searchFields = searchCriteria.searchFields;
             searchMode = searchCriteria.searchMode;
             select = searchCriteria.select;
             skip = searchCriteria.skip;
             top = searchCriteria.top;
        }


        SearchOptions parameters = new SearchOptions()
                .setQueryType(QueryType.FULL)
                .setIncludeTotalResultCount(true);

        if(!isNullOrEmpty(facets) ){
            parameters.setFacets(facets);
        }

        if(!isNullOrEmpty(filter)){
            parameters.setFilter(filter);
        }

        if(!isNullOrEmpty(highlight)){
            parameters.setHighlightFields(highlight);
        }

        if(!isNullOrEmpty(highLightPostTag)){
            parameters.setHighlightPostTag(highLightPostTag);
        }

        if(!isNullOrEmpty(highLightPreTag)){
            parameters.setHighlightPreTag(highLightPreTag);
        }

        if(minimumCoverage != null){
            parameters.setMinimumCoverage(minimumCoverage);
        }
        
        if(!isNullOrEmpty(orderBy)){
            parameters.setOrderBy(orderBy);
        }

        if(!isNullOrEmpty(scoringParameters)){
            parameters.setScoringParameters(scoringParameters);
        }

        if(!isNullOrEmpty(scoringProfile)){
            parameters.setScoringProfile(scoringProfile);
        }

        if(!isNullOrEmpty(searchFields)){
            parameters.setSearchFields(searchFields);
        }

        if(!isNullOrEmpty(searchMode)){
            if(searchMode.equalsIgnoreCase("ANY"))
                parameters.setSearchMode(SearchMode.ANY);
            else
                parameters.setSearchMode(SearchMode.ALL);
        }

        if(!isNullOrEmpty(select)){
            parameters.setSelect(select);
        }

        if(skip != null){
            parameters.setSkip(skip);
        }

        if(top != null){
            parameters.setTop(top);
        }

        PagedIterableBase<SearchResult, SearchPagedResponse> searchResults =  clientConfiguration.client().search(search, parameters, new RequestOptions(), null);

        return searchResults;
    }


    public PagedIterableBase<SearchResult, SearchPagedResponse> searchWithHightlights(String searchCriteria, String highlights) {

        System.out.println("searching... " + searchCriteria +" with highlight on" + highlights);
        SearchOptions parameters = new SearchOptions()
                .setHighlightFields(highlights)
                .setQueryType(QueryType.FULL)
                .setIncludeTotalResultCount(true);

        PagedIterableBase<SearchResult, SearchPagedResponse> searchResults =  clientConfiguration.client().search(searchCriteria, parameters, new RequestOptions(), null);

        return searchResults;
    }


    public PagedIterableBase<SearchResult, SearchPagedResponse> searchFacets(String facets) {
        System.out.println("searching  facets " + facets);
        SearchOptions parameters = new SearchOptions()
                //.setFacets("Rooms/BaseRate,values:100.0|200.0|300.0","Category")
                .setFacets(facets)
                .setIncludeTotalResultCount(true);

        PagedIterableBase<SearchResult, SearchPagedResponse> searchResults =
                clientConfiguration.client().search("*", parameters, new RequestOptions(), null);

        return searchResults;
    }



    public PagedIterableBase<SearchResult, SearchPagedResponse> searchHotelsWithParkingIncluded() {
        SearchOptions parameters = new SearchOptions()
                .setFilter("ParkingIncluded eq true")
                .setQueryType(QueryType.FULL)
                .setIncludeTotalResultCount(true);

        PagedIterableBase<SearchResult, SearchPagedResponse> searchResults =  clientConfiguration.client().search("hotel", parameters, new RequestOptions(), null);


        return searchResults;
    }

    public PagedIterableBase<SearchResult, SearchPagedResponse> searchBeachHotelsWithBaseRateLt200AndRatingGe4() {

        SearchOptions parameters = new SearchOptions()
                .setFilter("Rooms/any(room: room/BaseRate lt 200.0) and Rating ge 4")
                .setQueryType(QueryType.FULL)
                .setIncludeTotalResultCount(true);

        PagedIterableBase<SearchResult, SearchPagedResponse> searchResults =  clientConfiguration.client()
                .search("beach", parameters, new RequestOptions(), null);
        return  searchResults;

    }


    public static boolean isNullOrEmpty(String str) {
        if(str != null && !str.isEmpty())
            return false;
        return true;
    }


}



