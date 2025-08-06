import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;

import article.controller.ArticleController;
import article.repository.ArticleRepository;
import article.service.ArticleService;
import article.view.inputview.ArticleInputView;
import article.view.outputview.ArticleOutputView;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.util.Scanner;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

@DisplayName("App 통합 테스트")
class AppTest {

    // 표준 입출력을 테스트용 스트림으로 교체합니다.
    private final PrintStream standardOut = System.out;
    private final ByteArrayOutputStream outputStream = new ByteArrayOutputStream();

    @BeforeEach
    void setUpStreams() {
        System.setOut(new PrintStream(outputStream));
    }

    @AfterEach
    void restoreStreams() {
        System.setOut(standardOut);
    }

    @Test
    @DisplayName("CLI 게시판이 정상적으로 구동되고 종료된다.")
    void runTest_exit() {
        // given: "exit" 명령어를 입력하여 프로그램이 종료되도록 설정
        String input = "exit\n";
        System.setIn(new ByteArrayInputStream(input.getBytes()));

        // when: App.run() 호출
        App app = createApp();
        app.run();

        // then: 종료 메시지를 포함한 출력을 검증
        String output = outputStream.toString();
        assertThat(output).contains("=== CLI 게시판 구동 ===");
    }

    @Test
    @DisplayName("게시글 작성 후 목록을 조회하면 작성한 게시글이 보인다.")
    void runTest_write_and_list() {
        // given: "write", "제목1", "내용1", "list", "exit" 명령어를 순차적으로 입력
        String input = "write\n제목1\n내용1\nlist\nexit\n";
        System.setIn(new ByteArrayInputStream(input.getBytes()));

        // when: App.run() 호출
        App app = createApp();
        app.run();

        // then: 작성 성공 메시지와 목록에 작성한 글이 포함되었는지 검증
        String output = outputStream.toString();
        assertAll(
                () -> assertThat(output).contains("=> 게시글이 등록되었습니다."),
                () -> assertThat(output).contains("제목1")
        );
    }

    private App createApp() {
        ArticleRepository articleRepository = new ArticleRepository();
        ArticleService articleService = new ArticleService(articleRepository);
        ArticleInputView articleInputView = new ArticleInputView(new Scanner(System.in));
        ArticleOutputView articleOutputView = new ArticleOutputView();
        ArticleController articleController = new ArticleController(articleService, articleInputView,
                articleOutputView);

        return new App(articleController);
    }
}
