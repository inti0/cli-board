package article.view.outputview;

import static org.assertj.core.api.Assertions.assertThat;

import article.domain.Article;
import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.time.format.DateTimeFormatter;
import java.util.List;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class ArticleOutputViewTest {

    PrintStream standardOut = System.out;
    ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
    PrintStream printStream = new PrintStream(outputStream);

    ArticleOutputView articleOutputView = new ArticleOutputView();
    ;

    @BeforeEach
    void beforeEach() {
        System.setOut(printStream);
    }

    @AfterEach
    void afterEach() {
        System.setOut(standardOut);
    }

    @Test
    @DisplayName("등록 안내 메시지")
    void writeArticleMessageTest() {
        articleOutputView.printWriteSuccessMessage();

        assertThat(outputStream.toString()).contains("=> 게시글이 등록되었습니다.");
    }

    @Test
    @DisplayName("게시글 목록 조회")
    void printArticlesTest() {
        List<Article> articles = List.of(
                new Article(1, "테스트 제목1", "테스트 내용1"),
                new Article(3, "테스트 제목22", "테스트 내용22")
        );

        articleOutputView.printArticles(articles);

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");

        assertThat(outputStream.toString())
                .contains("번호 | 제목       | 등록일")
                .contains("-----------------------------")
                .contains("1    | 테스트 제목1  | %s".formatted(formatter.format(articles.get(0).getRegDate())))
                .contains("3    | 테스트 제목22  | %s".formatted(formatter.format(articles.get(1).getRegDate())));
    }

    @Test
    @DisplayName("상세 게시글 조회")
    void printArticleDetailTest() {
        Article article = new Article(123, "산은 산이다", "계곡이 시원하기 때문이다");

        articleOutputView.printArticleDetail(article);

        assertThat(outputStream.toString())
                .contains("번호: 123")
                .contains("제목: 산은 산이다")
                .contains("내용: 계곡이 시원하기 때문이다");
    }

    @Test
    @DisplayName("삭제 안내 메시지")
    void printDeleteSuccessMessageTest() {
        articleOutputView.printDeleteSuccessMessage();

        assertThat(outputStream.toString()).contains("=> 게시글이 삭제되었습니다.");
    }
}