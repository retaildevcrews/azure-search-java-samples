package com.microsoft.azure.app.hotel.wrapper;

import com.azure.search.Document;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor

public class FacetResponseWrapper {

    String facet;
    List<Map<String, Long >> results;

    public void setFacet(String facets) {
        this.facet = facets;
    }

    public void setResults(List<Map<String, Long>> results) {
        this.results = results;
    }
}
