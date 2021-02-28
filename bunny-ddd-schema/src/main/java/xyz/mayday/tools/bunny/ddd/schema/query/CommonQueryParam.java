package xyz.mayday.tools.bunny.ddd.schema.query;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.google.common.collect.Streams;
import lombok.Data;
import org.springframework.data.domain.Sort;
import org.springframework.data.util.Pair;

import java.util.List;
import java.util.stream.Collectors;

@Data
public class CommonQueryParam {

    Integer currentPage;

    Integer pageSize;

    List<String> sortField;

    List<String> sortOrder;

    @JsonIgnore
    public List<Sort.Order> getSortOrders() {
        return Streams.zip(sortField.stream(), sortOrder.stream(), Pair::of).map(pair -> new Sort.Order(Sort.Direction.fromString(pair.getSecond()), pair.getFirst())).collect(Collectors.toList());
    }

}
