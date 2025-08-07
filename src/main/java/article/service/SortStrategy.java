package article.service;

import article.domain.Article;
import java.util.Arrays;
import java.util.Comparator;

public enum SortStrategy {
    REG_DATE_ASCENDANT("시간순", "오름차순", Comparator.comparing(Article::getRegDate)),
    REG_DATE_DESCENDENT("시간순", "내림차순", REG_DATE_ASCENDANT.comparator.reversed()),
    ID_ASCENDANT("번호순", "오름차순", Comparator.comparing(Article::getId)),
    ID_DESCENDENT("번호순", "내림차순", ID_ASCENDANT.comparator.reversed());

    private final String orderName;
    private final String direction;
    private final Comparator<Article> comparator;

    SortStrategy(String orderName, String direction, Comparator<Article> comparator) {
        this.orderName = orderName;
        this.direction = direction;
        this.comparator = comparator;
    }

    public static Comparator<Article> getComparator(String orderName, String direction) {
        return Arrays.stream(values())
                .filter(o -> o.orderName.equals(orderName) && o.direction.equals(direction))
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException(
                        "정렬법입력 : %s, 정렬방향입력 : %s은 올바르지 않은 입력입니다.".formatted(orderName, direction)))
                .comparator;
    }
}
