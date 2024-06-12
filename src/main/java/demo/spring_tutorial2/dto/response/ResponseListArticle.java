package demo.spring_tutorial2.dto.response;

import org.springframework.data.domain.Sort;

import java.util.List;

public record ResponseListArticle<T>(
        Long totalCount,
        int totalPage,
        String sortKey,
        Sort.Direction direction,
        List<T> data
) {
    public static <T> ResponseListArticle<T> of(Long totalCount, int totalPage, Sort sort, List<T> data) {
        String sortKey = "";
        Sort.Direction direction = null;

        if (sort.isSorted()) {
            for (Sort.Order order : sort) {
                sortKey = order.getProperty();
                direction = order.getDirection();
            }
        }

        return new ResponseListArticle<>(totalCount, totalPage, sortKey, direction, data);
    }
}
