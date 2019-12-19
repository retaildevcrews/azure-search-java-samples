package com.microsoft.azure.app.hotel;

import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor

public class SearchCriteria {

    @ApiModelProperty(example=" " , notes="facets")
    String facets;
    @ApiModelProperty(example="Rooms/any(room: room/BaseRate ge 50 and room/BaseRate lt 70)" , notes = "filter")
    String filter;
    @ApiModelProperty(example=" " ,notes = "highlight")
    String highlight;
    @ApiModelProperty(example=" ", notes = "highlightposttag", required = false)
    String highLightPostTag;
    @ApiModelProperty(example=" ", notes = "highlightpretag", required = false)
    String highLightPreTag;
    @ApiModelProperty(example= "80.0", notes = "minimumcoverage")
    Double minimumCoverage;
    @ApiModelProperty(example= "Rating desc",  notes = "orderby")
    String orderBy;
    @ApiModelProperty(example=" ", notes = "scoringparameters" , required = false)
    String scoringParameters;
    @ApiModelProperty(example=" ", notes = "scoringprofile", required = false)
    String scoringProfile;
    @ApiModelProperty(example= "*" , notes = "search")
    String search;
    @ApiModelProperty(example=" ", notes = "searchfields")
    String searchFields;
    @ApiModelProperty(example="any", notes = "searchmode")
    String searchMode;
    @ApiModelProperty(example="HotelId, HotelName, Description,Rooms/BaseRate" , notes = "select")
    String select;
    @ApiModelProperty(example=" ", notes = "skip")
    Integer skip;
    @ApiModelProperty(example="100", notes = "top")
    Integer top;

    public SearchCriteria(String facet, String filter, String highLightFields, String highLightPostTag,
                          String highLightPreTag, Double minimumCoverage, String orderBy,
                          String scoringParameters, String scoringProfile, String search,
                          String searchFields, String searchMode, String selectFields,
                          Integer skip, int top) {
        this.facets = facet;
        this.filter = filter;
        this.highlight = highLightFields;
        this.highLightPostTag = highLightPostTag;
        this.highLightPreTag = highLightPreTag;
        this.minimumCoverage = minimumCoverage;
        this.orderBy = orderBy;
        this.scoringParameters = scoringParameters;
        this.scoringProfile = scoringProfile;
        this.search = search;
        this.searchFields = searchFields;
        this.searchMode = searchMode;
        this.select = selectFields;
        this.skip = skip;
        this.top = top;
    }
}
