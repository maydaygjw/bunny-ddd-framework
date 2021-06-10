package xyz.mayday.tools.bunny.ddd.schema.query;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.google.common.collect.Streams;
import io.swagger.annotations.ApiParam;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.With;
import org.springframework.data.domain.Sort;
import org.springframework.data.util.Pair;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Data
@With
@AllArgsConstructor
@NoArgsConstructor
public class CommonQueryParam {

    Integer currentPage;

    Integer pageSize;

    List<String> sortField = new ArrayList<>();

    List<String> sortOrder = new ArrayList<>();

    @JsonIgnore
    public List<Sort.Order> collectSortOrders() {
        return Streams.zip(sortField.stream(), sortOrder.stream(), Pair::of).map(pair -> new Sort.Order(Sort.Direction.fromString(pair.getSecond()), pair.getFirst())).collect(Collectors.toList());
    }

}
