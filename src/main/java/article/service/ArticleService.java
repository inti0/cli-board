package article.service;

import article.domain.Article;
import article.repository.ArticleRepository;
import article.view.UpdateArticleDTO;
import article.view.WriteArticleDTO;
import java.util.List;
import java.util.Optional;

public class ArticleService {

    private final ArticleRepository articleRepository;
    private final String NO_ARTICLE_FOUND_WITH_ID = "No article found with id: ";

    public ArticleService(ArticleRepository articleRepository) {
        this.articleRepository = articleRepository;
    }

    public void writeArticle(WriteArticleDTO writeArticleDTO) {
        articleRepository.addArticle(writeArticleDTO.title(), writeArticleDTO.content());
    }

    public List<Article> listArticles() {
        return articleRepository.getArticles();
    }

    public Article findArticleWithId(int id) {
        return articleRepository.findArticleById(id)
                .orElseThrow(() -> new IllegalArgumentException(NO_ARTICLE_FOUND_WITH_ID + id));
    }

    public void updateArticle(UpdateArticleDTO updateArticleDTO) {
        int id = updateArticleDTO.id();
        Optional<Article> optionalArticle = articleRepository.findArticleById(id);
        if (optionalArticle.isPresent()) {
            Article article = optionalArticle.get();
            articleRepository.modifyArticle(id, updateArticleDTO.title(), updateArticleDTO.content());
            return;
        }
        throw new IllegalArgumentException(NO_ARTICLE_FOUND_WITH_ID + id);
    }

    public void deleteArticle(int id) {
        boolean isNotRemoved = !articleRepository.removeArticleById(id);
        if (isNotRemoved) {
            throw new IllegalArgumentException(NO_ARTICLE_FOUND_WITH_ID + id);
        }
    }
}
