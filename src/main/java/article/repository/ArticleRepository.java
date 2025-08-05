package article.repository;

import article.domain.Article;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class ArticleRepository {
    private final List<Article> articles = new ArrayList<>();
    private int id = 1;

    public void addArticle(String title, String content) {
        Article article = new Article(id++, title, content);
        articles.add(article);
    }

    public boolean removeArticleById(int id) {
        int index = findIndexById(id);
        if (index == -1) {
            return false;
        }
        articles.remove(index);
        return true;
    }

    public int findIndexById(int id) {
        Optional<Article> optionalArticle = articles.stream()
                .filter(article -> article.getId() == id)
                .findFirst();

        return optionalArticle.map(Article::getId).orElse(-1);
    }

    public boolean modifyArticle(int id, String title, String content) {
        int index = findIndexById(id);
        if (index == -1) {
            return false;
        }
        Article article = new Article(id, title, content);
        articles.set(index, article);
        return true;
    }

    public List<Article> getArticles() {
        return new ArrayList<>(articles);
    }
}
