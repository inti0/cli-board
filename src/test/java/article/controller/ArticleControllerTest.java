package article.controller;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;
import static org.mockito.Mockito.when;

import article.service.ArticleService;
import article.view.WriteArticleDTO;
import article.view.inputview.ArticleInputView;
import article.view.outputview.ArticleOutputView;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

class ArticleControllerTest {

    // 컨트롤러의 의존 객체들을 Mock 객체로 생성합니다.
    @Mock
    private ArticleService articleService;

    @Mock
    private ArticleInputView inputView;

    @Mock
    private ArticleOutputView outputView;

    // 테스트 대상인 ArticleController에 Mock 객체들을 주입합니다.
    @InjectMocks
    private ArticleController articleController;

    // 각 테스트 메서드가 실행되기 전에 Mockito를 초기화합니다.
    // 이 메서드는 각 테스트가 독립적으로 실행될 수 있도록 보장합니다.
    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    @DisplayName("exit 명령어를 입력하면 프로그램이 종료된다.")
    void testExitCommand() {
        // given: `readCommand()`가 "exit"을 반환하도록 설정합니다.
        // Mockito의 `when`을 사용하여 가짜 객체의 동작을 정의합니다.
        when(inputView.readCommand()).thenReturn("exit");

        // when: execute() 메서드를 호출합니다.
        articleController.execute();

        // then: `readCommand()`가 한 번만 호출되었는지 검증합니다.
        // `verify`를 사용하여 Mock 객체의 특정 메서드가 호출되었는지 확인합니다.
        verify(inputView, times(1)).readCommand();

        // 다른 메서드는 호출되지 않았는지 검증합니다.
        verifyNoMoreInteractions(articleService, outputView);
    }

    @Test
    @DisplayName("write 명령어를 입력하면 게시글 작성 로직이 실행된다.")
    void testWriteCommand() {
        // given: `readCommand()`가 "write"를, `readWriteArticle()`이 DTO를 반환하도록 설정합니다.
        when(inputView.readCommand()).thenReturn("write", "exit");
        when(inputView.readWriteArticle()).thenReturn(new WriteArticleDTO("테스트 제목", "테스트 내용"));

        // when: execute() 메서드를 호출합니다.
        articleController.execute();

        // then: `articleService.writeArticle()`와 `outputView.printWriteSuccessMessage()`가
        // 한 번씩 호출되었는지 검증합니다.
        verify(articleService, times(1)).writeArticle(any(WriteArticleDTO.class));
        verify(outputView, times(1)).printWriteSuccessMessage();

        verifyNoMoreInteractions(articleService, outputView);
    }

    @Test
    @DisplayName("유효하지 않은 명령어를 입력하면 에러 메시지가 출력된다.")
    void testInvalidCommand() {
        // given: `readCommand()`가 유효하지 않은 명령어와 "exit"을 반환하도록 설정합니다.
        when(inputView.readCommand()).thenReturn("invalid-command", "exit");

        // when: execute() 메서드를 호출합니다.
        articleController.execute();

        // then: `outputView.printErrorMessage`와 `outputView.printCommandList`가
        // 각각 한 번씩 호출되었는지 검증합니다.
        verify(outputView, times(1)).printErrorMessage(any(IllegalArgumentException.class));
        verify(outputView, times(1)).printCommandList();

        verifyNoMoreInteractions(articleService, outputView);

    }

}