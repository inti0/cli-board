package article.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import article.domain.Article;
import article.repository.ArticleRepository;
import article.view.UpdateArticleDTO;
import article.view.WriteArticleDTO;
import java.util.Comparator;
import java.util.List;
import org.assertj.core.api.Assertions;
import org.assertj.core.api.SoftAssertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class ArticleServiceTest {

    ArticleRepository articleRepository;
    ArticleService articleService;

    @BeforeEach
    public void beforeEach() {
        articleRepository = new ArticleRepository();
        articleService = new ArticleService(articleRepository);
        articleService.writeArticle(new WriteArticleDTO("샘플 제목1", "샘플 내용1"));
        articleService.writeArticle(new WriteArticleDTO("샘플 제목2", "샘플 내용2"));
        articleService.writeArticle(new WriteArticleDTO("샘플 제목3", "샘플 내용3"));
    }

    @Test
    @DisplayName("게시글 작성 테스트")
    void writeTest() {
        WriteArticleDTO writeArticleDTO = new WriteArticleDTO("테스트 제목", "테스트 내용");
        articleService.writeArticle(writeArticleDTO);

        Article writtenArticle = articleService.findArticleWithId(4);

        SoftAssertions softly = new SoftAssertions();
        softly.assertThat(writtenArticle.getId()).isEqualTo(4);
        softly.assertThat(writtenArticle.getTitle()).isEqualTo("테스트 제목");
        softly.assertThat(writtenArticle.getContent()).isEqualTo("테스트 내용");
        softly.assertAll();
    }

    @Test
    @DisplayName("게시글 목록 테스트 : 추가")
    void listTest() {
        List<Article> articles = articleService.listArticles();

        SoftAssertions softly = new SoftAssertions();
        assertThat(articles.size()).isEqualTo(3);

        articleService.writeArticle(new WriteArticleDTO("테스트 제목", "테스트 내용"));
        List<Article> afterWrite = articleService.listArticles();
        assertThat(afterWrite.size()).isEqualTo(4);

        softly.assertAll();
    }

    @Test
    @DisplayName("게시글 목록 테스트 : 삭제")
    void listTestAfterDelete() {
        List<Article> articles = articleService.listArticles();

        SoftAssertions softly = new SoftAssertions();
        assertThat(articles.size()).isEqualTo(3);

        articleService.writeArticle(new WriteArticleDTO("테스트 제목", "테스트 내용"));
        List<Article> afterWrite = articleService.listArticles();
        assertThat(afterWrite.size()).isEqualTo(4);

        softly.assertAll();
    }

    @Test
    @DisplayName("게시글 찾기 테스트")
    void findByIdTest() {
        Article foundArticle = articleService.findArticleWithId(3);

        assertThat(foundArticle.getId()).isNotNull();
    }

    @Test
    @DisplayName("게시글 찾기 실패 테스트 : 존재하지 않는 Id를 찾을 경우 예외가 발생한다.")
    void findByIdTestFail() {
        assertThatThrownBy(() -> articleService.findArticleWithId(4)).isInstanceOf(IllegalArgumentException.class);
    }

    @Test
    @DisplayName("게시글 수정 테스트")
    void updateTest() {
        UpdateArticleDTO updateArticleDTO = new UpdateArticleDTO(2, "테스트 제목", "테스트 내용");

        Article oldArticle = articleService.findArticleWithId(2);
        articleService.updateArticle(updateArticleDTO);
        Article updatedArticle = articleService.findArticleWithId(2);

        SoftAssertions softly = new SoftAssertions();
        softly.assertThat(updatedArticle.getId()).isEqualTo(oldArticle.getId());
        softly.assertThat(oldArticle.getTitle()).isEqualTo("샘플 제목2");
        softly.assertThat(oldArticle.getContent()).isEqualTo("샘플 내용2");

        softly.assertThat(updatedArticle.getTitle()).isEqualTo("테스트 제목");
        softly.assertThat(updatedArticle.getContent()).isEqualTo("테스트 내용");
        softly.assertAll();
    }

    @Test
    @DisplayName("게시글 수정 실패 테스트 : 존재하지 않는 Id로 수정하려고 할 경우 예외가 발생한다.")
    void updateFailTest() {
        UpdateArticleDTO updateArticleDTO = new UpdateArticleDTO(0, "테스트 제목", "테스트 내용");
        assertThatThrownBy(() -> articleService.updateArticle(updateArticleDTO)).isInstanceOf(
                IllegalArgumentException.class);
    }

    @Test
    @DisplayName("게시글 삭제 테스트")
    void deleteTest() {
        Article notDeletedYet = articleService.findArticleWithId(3);

        articleService.deleteArticle(3);

        SoftAssertions softly = new SoftAssertions();
        softly.assertThatThrownBy(() -> articleService.findArticleWithId(3))
                .isInstanceOf(IllegalArgumentException.class);
        softly.assertThatThrownBy(() -> articleService.deleteArticle(3)).isInstanceOf(IllegalArgumentException.class);
    }

    @Test
    @DisplayName("게시글 삭제 실패 테스트 : 존재하지 않는 Id를 삭제하려고 할 경우 예외가 발생한다.")
    void deleteFailTest() {
        assertThatThrownBy(() -> articleService.deleteArticle(33)).isInstanceOf(IllegalArgumentException.class);
    }

    @Test
    @DisplayName("게시글 자세히보기를 하면 조회수가 오른다.")
    void showDetailTest() {
        Article article = articleService.showDetail(3);

        SoftAssertions softly = new SoftAssertions();
        softly.assertThat(article.getViewCount()).isEqualTo(1);

        articleService.showDetail(3);
        softly.assertThat(article.getViewCount()).isEqualTo(2);
        softly.assertAll();
    }

    @Test
    @DisplayName("단순히 객체가져오기를 하면 조회수가 오르지 않는다.")
    void FindAndShowDetailIsDifferent() {
        Article article = articleService.showDetail(3);

        SoftAssertions softly = new SoftAssertions();
        softly.assertThat(article.getViewCount()).isEqualTo(1);

        articleService.findArticleWithId(3);
        softly.assertThat(article.getViewCount()).isEqualTo(1);
        softly.assertAll();
    }

    @Test
    @DisplayName("게시글 제목 검색기능 테스트")
    void searchTitleTest() {
        articleService.writeArticle(new WriteArticleDTO("검색456", "테스트 내용"));
        articleService.writeArticle(new WriteArticleDTO("테스트 제목", "테스트 내용"));

        List<Article> articles = articleService.searchArticlesByKeyword("검색");

        Assertions.assertThat(articles.size()).isEqualTo(1);
    }

    @Test
    @DisplayName("게시글 내용 검색기능 테스트")
    void searchContentTest() {
        articleService.writeArticle(new WriteArticleDTO("테스트 제목", "테스트 내용"));
        articleService.writeArticle(new WriteArticleDTO("테스트 제목", "검색123"));

        List<Article> articles = articleService.searchArticlesByKeyword("검색");

        SoftAssertions softly = new SoftAssertions();
        softly.assertThat(articles.size()).isEqualTo(1);
        softly.assertAll();
    }

    @Test
    @DisplayName("게시글 제목 + 내용 검색기능 테스트")
    void searchKeywordTest() {
        articleService.writeArticle(new WriteArticleDTO("검색", "테스트 내용"));
        articleService.writeArticle(new WriteArticleDTO("테스트 제목", "검색"));
        articleService.writeArticle(new WriteArticleDTO("검색", "검색"));

        List<Article> articles = articleService.searchArticlesByKeyword("검색");

        SoftAssertions softly = new SoftAssertions();
        softly.assertThat(articles.size()).isEqualTo(3);
        softly.assertAll();
    }
}