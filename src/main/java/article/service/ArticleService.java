package article.service;

import article.domain.Article;
import article.repository.ArticleRepository;
import article.view.UpdateArticleDTO;
import article.view.WriteArticleDTO;
import article.view.ListArticlesDto;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;

public class ArticleService {

    private final ArticleRepository articleRepository;
    private final String NO_ARTICLE_FOUND_WITH_ID = "해당하는 ID의 게시글을 찾을 수 없음 id : ";

    public ArticleService(ArticleRepository articleRepository) {
        this.articleRepository = articleRepository;
    }

    public void writeArticle(WriteArticleDTO writeArticleDTO) {
        articleRepository.addArticle(writeArticleDTO.title(), writeArticleDTO.content());
    }

    public List<Article> listArticles() {
        return articleRepository.getArticles();
    }

    public List<Article> listArticles(ListArticlesDto listArticlesDto) {
        boolean isBasicStrategy = listArticlesDto.isBasicStrategy();
        if (isBasicStrategy) {
            return listArticles();
        }
        String orderName = listArticlesDto.orderName();
        String direction = listArticlesDto.direction();
        Comparator<Article> comparator = SortStrategy.getComparator(orderName, direction);

        return articleRepository.getArticles(comparator);
    }

    public Article findArticleWithId(int id) {
        return articleRepository.findArticleById(id)
                .orElseThrow(() -> new IllegalArgumentException(NO_ARTICLE_FOUND_WITH_ID + id));
    }

    public void updateArticle(UpdateArticleDTO updateArticleDTO) {
        int id = updateArticleDTO.id();
        boolean isNotUpdated = !articleRepository.modifyArticle(id, updateArticleDTO.title(),
                updateArticleDTO.content());
        if (isNotUpdated) {
            throw new IllegalArgumentException(NO_ARTICLE_FOUND_WITH_ID + id);
        }
    }

    public void deleteArticle(int id) {
        boolean isNotRemoved = !articleRepository.removeArticleById(id);
        if (isNotRemoved) {
            throw new IllegalArgumentException(NO_ARTICLE_FOUND_WITH_ID + id);
        }
    }

    public Article showDetail(int id) {
        Optional<Article> optionalArticle = articleRepository.findArticleById(id);
        if (optionalArticle.isPresent()) {
            Article article = optionalArticle.get();
            article.increaseViewCount();
            return article;
        }
        throw new IllegalArgumentException(NO_ARTICLE_FOUND_WITH_ID + id);
    }
}
