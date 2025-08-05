package article.domain;

import static org.assertj.core.api.Assertions.*;

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
}