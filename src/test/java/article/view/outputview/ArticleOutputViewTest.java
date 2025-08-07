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

    static DateTimeFormatter DATE_FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd");

    @BeforeEach
    void beforeEach() {
        System.setOut(printStream);
    }

    @AfterEach
    void afterEach() {
        System.setOut(standardOut);
    }

    @Test
    @DisplayName("등록 성공 메시지")
    void writeArticleMessageTest() {
        articleOutputView.printWriteSuccessMessage();

        assertThat(outputStream.toString()).isEqualToIgnoringWhitespace("=> 게시글이 등록되었습니다.");
    }

    @Test
    @DisplayName("게시글 목록 조회")
    void printArticlesTest() {
        List<Article> articles = List.of(
                new Article(1, "테스트 제목1", "테스트 내용1"),
                new Article(3, "테스트 제목22", "테스트 내용22")
        );

        articleOutputView.printArticles(articles);

        String expectedOutput = new StringBuilder()
                .append("번호 | 제목       | 등록일\n")
                .append("-----------------------------\n")
                .append("1    | 테스트 제목1  | %s\n".formatted(DATE_FORMATTER.format(articles.get(0).getRegDate())))
                .append("3    | 테스트 제목22  | %s\n".formatted(DATE_FORMATTER.format(articles.get(1).getRegDate())))
                .toString();

        assertThat(outputStream.toString()).isEqualToIgnoringWhitespace(expectedOutput);
    }

    @Test
    @DisplayName("상세 게시글 조회")
    void printArticleDetailTest() {
        Article article = new Article(123, "산은 산이다", "계곡이 시원하기 때문이다");

        articleOutputView.printArticleDetail(article);

        String expectedOutput = new StringBuilder()
                .append("번호: 123\n")
                .append("제목: 산은 산이다\n")
                .append("내용: 계곡이 시원하기 때문이다\n")
                .append("조회수: 0\n")
                .append("등록일: ").append(DATE_FORMATTER.format(article.getRegDate())).toString();

        assertThat(outputStream.toString()).isEqualToIgnoringWhitespace(expectedOutput);
    }

    @Test
    @DisplayName("삭제 성공 메시지")
    void printDeleteSuccessMessageTest() {
        articleOutputView.printDeleteSuccessMessage();

        assertThat(outputStream.toString()).contains("=> 게시글이 삭제되었습니다.");
    }

    @Test
    @DisplayName("수정 성공 메시지")
    void printUpdateSuccessMessageTest() {
        articleOutputView.printUpdateSuccessMessage();

        assertThat(outputStream.toString()).isEqualToIgnoringWhitespace("=> 게시글이 수정되었습니다.");
    }

    @Test
    @DisplayName("명령어 목록을 출력한다.")
    void printCommandListTest() {
        articleOutputView.printCommandList();

        String expectedOutput = "명령어를 다시 입력해주세요.\n"
                + "명령어 리스트) write, list, detail [id], update [id], delete [id], search";

        assertThat(outputStream.toString()).isEqualToIgnoringWhitespace(expectedOutput);
    }

    @Test
    @DisplayName("에러 메시지를 출력한다.")
    void printErrorMessageTest() {
        IllegalArgumentException e = new IllegalArgumentException("테스트 에러 메시지");
        articleOutputView.printErrorMessage(e);

        assertThat(outputStream.toString()).isEqualToIgnoringWhitespace("[ERROR] : 테스트 에러 메시지");
    }
}