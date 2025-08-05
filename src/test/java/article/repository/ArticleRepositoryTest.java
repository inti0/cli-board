package article.repository;

import static org.junit.jupiter.api.Assertions.*;

import article.domain.Article;
import java.util.List;
import java.util.Optional;
import org.assertj.core.api.Assertions;
import org.assertj.core.api.SoftAssertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class ArticleRepositoryTest {

    private ArticleRepository articleRepository;
    @BeforeEach
    void setUp() {
        articleRepository = new ArticleRepository();
        articleRepository.addArticle("샘플 제목1", "샘플 내용1");
        articleRepository.addArticle("샘플 제목2", "샘플 내용2");
        articleRepository.addArticle("샘플 제목3", "샘플 내용3");
    }

    @Test
    @DisplayName("id로 게시글 찾기")
    void findArticleById() {
        Article article = articleRepository.findArticleById(3).get();

        SoftAssertions softly = new SoftAssertions();
        softly.assertThat(article.getId()).isEqualTo(3);
        softly.assertThat(article.getTitle()).isEqualTo("샘플 제목3");
        softly.assertThat(article.getContent()).isEqualTo("샘플 내용3");
        softly.assertAll();
    }

    @Test
    @DisplayName("id로 게시글 찾기 실패")
    void findArticleByIdFail() {
        Optional<Article> articleById = articleRepository.findArticleById(4);

        assertTrue(articleById.isEmpty());
    }

    @Test
    @DisplayName("게시글 추가")
    void addArticle() {
        articleRepository.addArticle("테스트 제목", "테스트 내용");

        Article article = articleRepository.findArticleById(4).get();

        SoftAssertions softly = new SoftAssertions();
        softly.assertThat(article.getId()).isEqualTo(4);
        softly.assertThat(article.getTitle()).isEqualTo("테스트 제목");
        softly.assertThat(article.getContent()).isEqualTo("테스트 내용");
        softly.assertAll();
    }

    @Test
    @DisplayName("게시글 수정 성공")
    void updateArticle() {
        boolean isModified = articleRepository.modifyArticle(3, "수정 제목", "수정 내용");

        assertTrue(isModified);
    }

    @Test
    @DisplayName("게시글 수정 내용 변경 확인")
    void updateArticleField() {
        Article oldArticle = articleRepository.findArticleById(3).get();
        boolean isModified = articleRepository.modifyArticle(3, "수정 제목", "수정 내용");
        Article modifiedArticle = articleRepository.findArticleById(3).get();

        SoftAssertions softly = new SoftAssertions();
        softly.assertThat(oldArticle.getId()).isEqualTo(3);
        softly.assertThat(oldArticle.getTitle()).isEqualTo("샘플 제목3");
        softly.assertThat(oldArticle.getContent()).isEqualTo("샘플 내용3");

        softly.assertThat(modifiedArticle.getId()).isEqualTo(3);
        softly.assertThat(modifiedArticle.getTitle()).isEqualTo("수정 제목");
        softly.assertThat(modifiedArticle.getContent()).isEqualTo("수정 내용");
        softly.assertAll();
    }

    @Test
    @DisplayName("게시글 수정 실패")
    void updateArticleFail() {
        boolean isModified = articleRepository.modifyArticle(0, "수정 제목", "수정 내용");

        assertFalse(isModified);
    }

    @Test
    @DisplayName("게시글 삭제")
    void removeArticle() {
        boolean isRemoved = articleRepository.removeArticleById(3);

        assertTrue(isRemoved);
    }

    @Test
    @DisplayName("게시글 삭제 실패")
    void removeArticleFail() {
        boolean isRemoved = articleRepository.removeArticleById(0);

        assertFalse(isRemoved);
    }

    @Test
    @DisplayName("게시글 중복 삭제")
    void removeArticleTwice() {
        boolean isRemoved = articleRepository.removeArticleById(3);

        SoftAssertions softly = new SoftAssertions();
        softly.assertThat(isRemoved).isTrue();
        boolean notRemovedYet = articleRepository.removeArticleById(3);
        softly.assertThat(notRemovedYet).isFalse();

        softly.assertAll();
    }

    @Test
    @DisplayName("게시글 목록")
    void getArticles() {
        List<Article> articles = articleRepository.getArticles();

        Assertions.assertThat(articles.size()).isEqualTo(3);
    }

    @Test
    @DisplayName("getArticles()는 새 리스트를 반환한다")
    void getArticlesInstance() {
        List<Article> articles = articleRepository.getArticles();

        articleRepository.removeArticleById(1);
        List<Article> articlesOneRemoved = articleRepository.getArticles();

        SoftAssertions softly = new SoftAssertions();
        softly.assertThat(articles.size()).isEqualTo(3);
        softly.assertThat(articlesOneRemoved.size()).isEqualTo(2);
        softly.assertAll();
    }
}