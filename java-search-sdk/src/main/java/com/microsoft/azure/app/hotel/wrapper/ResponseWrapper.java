package com.microsoft.azure.app.hotel.wrapper;

import com.azure.search.Document;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor

public class ResponseWrapper {

    Long count;
    List<Document> value;

    public void setCount(Long count) {
        this.count = count;
    }

    public void setValue(List<Document> documents) {
        this.value = documents;
    }
}
