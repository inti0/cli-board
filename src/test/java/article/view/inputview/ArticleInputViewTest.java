package article.view.inputview;

import article.view.UpdateArticleDTO;
import article.view.WriteArticleDTO;
import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.util.Scanner;
import org.assertj.core.api.SoftAssertions;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class ArticleInputViewTest {

    //기존 스트림 백업
    private final PrintStream standardOut = System.out;
    private final ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
    private ArticleInputView articleInputView;

    @BeforeEach
    void beforeEach() {
        System.setOut(new PrintStream(outputStream));
    }

    @AfterEach
    void afterEach() {
        System.setOut(standardOut);
    }

    @Test
    @DisplayName("명령어를 읽는다")
    void readCommandTest() {
        String input = "write\n";
        articleInputView = new ArticleInputView(new Scanner(input));

        String command = articleInputView.readCommand();

        SoftAssertions softly = new SoftAssertions();
        softly.assertThat(command).isEqualTo("write");
        softly.assertThat(outputStream.toString()).contains("명령어:");
        softly.assertAll();
    }

    @Test
    @DisplayName("게시글 등록을 위한 제목과 내용을 입력받아 DTO로 반환한다")
    void readWriteArticleTest() {
        String input = "테스트 제목\n테스트 내용\n";
        articleInputView = new ArticleInputView(new Scanner(input));

        WriteArticleDTO dto = articleInputView.readWriteArticle();

        String output = outputStream.toString();
        SoftAssertions softly = new SoftAssertions();
        softly.assertThat(dto.title()).isEqualTo("테스트 제목");
        softly.assertThat(dto.content()).isEqualTo("테스트 내용");
        softly.assertThat(output).contains("제목:");
        softly.assertThat(output).contains("내용:");
        softly.assertAll();
    }

    @Test
    @DisplayName("게시글 수정을 위한 제목과 내용을 입력받아 DTO로 반환한다")
    void readUpdateArticleTest() {
        UpdateArticleDTO oldDto = new UpdateArticleDTO(1, "기존 제목 바다", "기존 내용 바다와 상어");
        String input = "새 제목\n새 내용\n";
        articleInputView = new ArticleInputView(new Scanner(input));

        UpdateArticleDTO newDto = articleInputView.readUpdateArticle(oldDto);

        String output = outputStream.toString();
        SoftAssertions softly = new SoftAssertions();
        softly.assertThat(newDto.id()).isEqualTo(oldDto.id());
        softly.assertThat(newDto.title()).isEqualTo("새 제목");
        softly.assertThat(newDto.content()).isEqualTo("새 내용");
        softly.assertThat(output).contains("제목 (현재: 기존 제목 바다): ");
        softly.assertThat(output).contains("내용 (현재: 기존 내용 바다와 상어): ");
        softly.assertAll();

    }
}
