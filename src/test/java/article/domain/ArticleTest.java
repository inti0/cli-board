package article.domain;

import static org.assertj.core.api.Assertions.*;

import org.assertj.core.api.Assertions;
import org.assertj.core.api.SoftAssertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class ArticleTest {

    @Test
    @DisplayName("Article 생성 테스트")
    void ArticleGenerateTest() {
        Article article = new Article(3, "나의 집", "나의 집은 비어있다");

        SoftAssertions softly = new SoftAssertions();
        softly.assertThat(article.getId()).isEqualTo(3);
        softly.assertThat(article.getTitle()).isEqualTo("나의 집");
        softly.assertThat(article.getContent()).isEqualTo("나의 집은 비어있다");

        softly.assertAll();
    }

    @Test
    @DisplayName("viewCount 증가 테스트")
    void viewCountTest() {
        Article article = new Article(1, "밥 먹자", "배고프다");

        SoftAssertions softly = new SoftAssertions();
        softly.assertThat(article.getViewCount()).isEqualTo(0);

        article.increaseViewCount();
        softly.assertThat(article.getViewCount()).isEqualTo(1);
        softly.assertAll();
    }
}