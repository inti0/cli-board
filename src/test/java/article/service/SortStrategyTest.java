package article.service;

import static org.junit.jupiter.api.Assertions.*;

import article.domain.Article;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import org.assertj.core.api.SoftAssertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class SortStrategyTest {

    @Test
    @DisplayName("번호순 오름차순 정렬")
    void idAscendantTest() {
        Article article1 = new Article(11, "테스트 제목", "테스트 내용");
        Article article2 = new Article(5, "테스트 제목", "테스트 내용");
        Article article3 = new Article(9, "테스트 제목", "테스트 내용");

        Comparator<Article> comparator = SortStrategy.getComparator("번호순", "오름차순");
        List<Article> articles = new ArrayList<>();
        articles.add(article1);
        articles.add(article2);
        articles.add(article3);
        articles.sort(comparator);

        SoftAssertions softly = new SoftAssertions();
        softly.assertThat(articles.get(0)).isEqualTo(article2);
        softly.assertThat(articles.get(1)).isEqualTo(article3);
        softly.assertThat(articles.get(2)).isEqualTo(article1);
        softly.assertAll();
    }

    @Test
    @DisplayName("번호순 내림차순 정렬")
    void idDescendantTest() {
        Article article1 = new Article(9, "테스트 제목", "테스트 내용");
        Article article2 = new Article(11, "테스트 제목", "테스트 내용");
        Article article3 = new Article(5, "테스트 제목", "테스트 내용");

        Comparator<Article> comparator = SortStrategy.getComparator("번호순", "오름차순");
        List<Article> articles = new ArrayList<>();
        articles.add(article1);
        articles.add(article2);
        articles.add(article3);
        articles.sort(comparator);

        SoftAssertions softly = new SoftAssertions();
        softly.assertThat(articles.get(0)).isEqualTo(article3);
        softly.assertThat(articles.get(1)).isEqualTo(article1);
        softly.assertThat(articles.get(2)).isEqualTo(article2);
        softly.assertAll();
    }

    @Test
    @DisplayName("시간순 오름차순 정렬")
    void regDateAscendantTest() throws InterruptedException {
        Article article1 = new Article(9, "테스트 제목", "테스트 내용");
        Thread.sleep(10);
        Article article2 = new Article(11, "테스트 제목", "테스트 내용");
        Thread.sleep(10);
        Article article3 = new Article(5, "테스트 제목", "테스트 내용");

        Comparator<Article> comparator = SortStrategy.getComparator("시간순", "오름차순");
        List<Article> articles = new ArrayList<>();
        articles.add(article3);
        articles.add(article1);
        articles.add(article2);
        articles.sort(comparator);

        SoftAssertions softly = new SoftAssertions();
        softly.assertThat(articles.get(0)).isEqualTo(article1);
        softly.assertThat(articles.get(1)).isEqualTo(article2);
        softly.assertThat(articles.get(2)).isEqualTo(article3);
        softly.assertAll();
    }

    @Test
    @DisplayName("시간순 내림차순 정렬")
    void regDateDescendantTest() throws InterruptedException {
        Article article1 = new Article(9, "테스트 제목", "테스트 내용");
        Thread.sleep(10);
        Article article2 = new Article(11, "테스트 제목", "테스트 내용");
        Thread.sleep(10);
        Article article3 = new Article(5, "테스트 제목", "테스트 내용");

        Comparator<Article> comparator = SortStrategy.getComparator("시간순", "내림차순");
        List<Article> articles = new ArrayList<>();
        articles.add(article2);
        articles.add(article1);
        articles.add(article3);
        articles.sort(comparator);

        SoftAssertions softly = new SoftAssertions();
        softly.assertThat(articles.get(0)).isEqualTo(article3);
        softly.assertThat(articles.get(1)).isEqualTo(article2);
        softly.assertThat(articles.get(2)).isEqualTo(article1);
        softly.assertAll();
    }
}