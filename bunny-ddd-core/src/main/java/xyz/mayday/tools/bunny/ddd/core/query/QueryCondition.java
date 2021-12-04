package xyz.mayday.tools.bunny.ddd.core.query;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import xyz.mayday.tools.bunny.ddd.schema.query.SearchConjunction;
import xyz.mayday.tools.bunny.ddd.schema.query.SearchCriteria;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class QueryCondition {
    
    List<SearchCriteria> searchCriteriaList = new ArrayList<>();
    
    protected List<SearchCriteria> getOrCriteriaList() {
        return searchCriteriaList.stream().filter(criteria -> criteria.getSearchConjunction().equals(SearchConjunction.OR)).collect(Collectors.toList());
    }
    
    protected List<SearchCriteria> getAndCriteriaList() {
        return searchCriteriaList.stream().filter(criteria -> criteria.getSearchConjunction().equals(SearchConjunction.AND)).collect(Collectors.toList());
    }
    
    public void addAll(List<SearchCriteria> searchCriteriaList) {
        this.searchCriteriaList.addAll(searchCriteriaList);
    }
}
