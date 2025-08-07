package article.controller;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;
import static org.mockito.Mockito.when;

import article.domain.Article;
import article.service.ArticleService;
import article.view.ListArticlesDto;
import article.view.UpdateArticleDTO;
import article.view.WriteArticleDTO;
import article.view.inputview.ArticleInputView;
import article.view.outputview.ArticleOutputView;
import java.util.Collections;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

class ArticleControllerTest {// 컨트롤러의 의존 객체들을 Mock 객체로 생성합니다.
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
    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    @DisplayName("exit 명령어를 입력하면 프로그램이 종료된다.")
    void testExitCommand() {
        // given: `readCommand()`가 "exit"을 반환하도록 설정합니다.
        when(inputView.readCommand()).thenReturn("exit");

        // when: execute() 메서드를 호출합니다.
        articleController.execute();

        // then: `readCommand()`가 한 번만 호출되었는지 검증합니다.
        // 이 테스트는 루프를 한 번만 돌고 종료되므로 1회 호출이 맞습니다.
        verify(inputView, times(1)).readCommand();
        verifyNoMoreInteractions(articleService, inputView, outputView);
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
        // 한 번씩 호출되었는지 검증하고, `readCommand`가 2번 호출되었는지 검증합니다.
        verify(inputView, times(2)).readCommand();
        verify(inputView, times(1)).readWriteArticle();
        verify(articleService, times(1)).writeArticle(any(WriteArticleDTO.class));
        verify(outputView, times(1)).printWriteSuccessMessage();
        verifyNoMoreInteractions(articleService, inputView, outputView);
    }

    @Test
    @DisplayName("유효하지 않은 명령어를 입력하면 에러 메시지가 출력된다.")
    void testInvalidCommand() {
        // given: `readCommand()`가 유효하지 않은 명령어와 "exit"을 반환하도록 설정합니다.
        when(inputView.readCommand()).thenReturn("invalid-command", "exit");

        // when: execute() 메서드를 호출합니다.
        articleController.execute();

        // then: `readCommand`가 2번 호출되고, 에러 메시지 관련 메서드들이 호출되었는지 검증합니다.
        verify(inputView, times(2)).readCommand();
        verify(outputView, times(1)).printErrorMessage(any(IllegalArgumentException.class));
        verify(outputView, times(1)).printCommandList();
        verifyNoMoreInteractions(articleService, inputView, outputView);
    }

    @Test
    @DisplayName("list 명령어를 입력하면 게시글 목록을 조회한다.")
    void testListCommand() {
        // given: "list", "exit" 명령어를 입력하도록 설정합니다.
        when(inputView.readCommand()).thenReturn("list", "exit");
        when(inputView.readListArticles()).thenReturn(new ListArticlesDto(true, "", ""));
        when(articleService.listArticles(any(ListArticlesDto.class))).thenReturn(Collections.emptyList());

        // when: execute() 메서드를 호출합니다.
        articleController.execute();

        // then: `readCommand`가 2번 호출되고, 관련 메서드가 한 번씩 호출되었는지 검증합니다.
        verify(inputView, times(2)).readCommand();
        verify(inputView, times(1)).readListArticles();
        verify(articleService, times(1)).listArticles(any(ListArticlesDto.class));
        verify(outputView, times(1)).printArticles(any(List.class));
        verifyNoMoreInteractions(articleService, inputView, outputView);
    }

    @Test
    @DisplayName("detail 명령어를 입력하면 특정 게시글의 상세 정보를 조회한다.")
    void testDetailCommand() {
        // given: "detail 1", "exit" 명령어를 입력하도록 설정합니다.
        when(inputView.readCommand()).thenReturn("detail 1", "exit");
        Article mockArticle = new Article(1, "제목", "내용");
        when(articleService.showDetail(1)).thenReturn(mockArticle);

        // when: execute() 메서드를 호출합니다.
        articleController.execute();

        // then: `readCommand`가 2번 호출되고, 관련 메서드가 한 번씩 호출되었는지 검증합니다.
        verify(inputView, times(2)).readCommand();
        verify(articleService, times(1)).showDetail(1);
        verify(outputView, times(1)).printArticleDetail(mockArticle);
        verifyNoMoreInteractions(articleService, inputView, outputView);
    }

    @Test
    @DisplayName("update 명령어를 입력하면 게시글을 수정한다.")
    void testUpdateCommand() {
        // given: "update 1", "exit" 명령어를 입력하도록 설정합니다.
        when(inputView.readCommand()).thenReturn("update 1", "exit");
        Article mockArticle = new Article(1, "기존 제목", "기존 내용");
        when(articleService.findArticleWithId(1)).thenReturn(mockArticle);
        when(inputView.readUpdateArticle(mockArticle)).thenReturn(new UpdateArticleDTO(1, "새 제목", "새 내용"));

        // when: execute() 메서드를 호출합니다.
        articleController.execute();

        // then: `readCommand`가 2번 호출되고, 관련 메서드가 한 번씩 호출되었는지 검증합니다.
        verify(inputView, times(2)).readCommand();
        verify(articleService, times(1)).findArticleWithId(1);
        verify(inputView, times(1)).readUpdateArticle(mockArticle);
        verify(articleService, times(1)).updateArticle(any(UpdateArticleDTO.class));
        verify(outputView, times(1)).printUpdateSuccessMessage();
        verifyNoMoreInteractions(articleService, inputView, outputView);
    }

    @Test
    @DisplayName("delete 명령어를 입력하면 게시글을 삭제한다.")
    void testDeleteCommand() {
        // given: "delete 1", "exit" 명령어를 입력하도록 설정합니다.
        when(inputView.readCommand()).thenReturn("delete 1", "exit");

        // when: execute() 메서드를 호출합니다.
        articleController.execute();

        // then: `readCommand`가 2번 호출되고, 관련 메서드가 한 번씩 호출되었는지 검증합니다.
        verify(inputView, times(2)).readCommand();
        verify(articleService, times(1)).deleteArticle(1);
        verify(outputView, times(1)).printDeleteSuccessMessage();
        verifyNoMoreInteractions(articleService, inputView, outputView);
    }

    @Test
    @DisplayName("search 명령어를 입력하면 키워드에 맞는 게시글을 검색한다.")
    void testSearchCommand() {
        // given: "search", "exit" 명령어를 입력하도록 설정합니다.
        when(inputView.readCommand()).thenReturn("search", "exit");
        when(inputView.readSearchKeyword()).thenReturn("검색어");
        when(articleService.searchArticlesByKeyword("검색어")).thenReturn(Collections.emptyList());

        // when: execute() 메서드를 호출합니다.
        articleController.execute();

        // then: `readCommand`가 2번 호출되고, 관련 메서드가 한 번씩 호출되었는지 검증합니다.
        verify(inputView, times(2)).readCommand();
        verify(inputView, times(1)).readSearchKeyword();
        verify(articleService, times(1)).searchArticlesByKeyword("검색어");
        verify(outputView, times(1)).printArticles(any(List.class));
        verifyNoMoreInteractions(articleService, inputView, outputView);
    }
}