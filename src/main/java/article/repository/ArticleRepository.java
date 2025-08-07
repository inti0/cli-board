package article.repository;

import article.domain.Article;
import java.util.ArrayList;
import java.util.Comparator;
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

    //저장소에 해당 Id를 갖는 게시글이 없을 경우 -1 반환
    private int findIndexById(int id) {
        for (int i = 0; i < articles.size(); i++) {
            if (articles.get(i).getId() == id) {
                return i;
            }
        }
        return -1;
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

    public Optional<Article> findArticleById(int id) {
        int index = findIndexById(id);
        return index == -1 ? Optional.empty() : Optional.of(articles.get(index));
    }

    //생성일 기준 오름차순 정렬로 반환
    public List<Article> getArticles() {
        return articles.stream()
                .sorted(Comparator.comparing(Article::getRegDate).reversed())
                .toList();
    }

    //정렬방법에 따라 정렬하여 반환
    public List<Article> getArticles(Comparator<Article> comparator) {
        return articles.stream()
                .sorted(comparator)
                .toList();
    }

    public List<Article> findArticlesByKeyword(String keyword) {
        return articles.stream()
                .filter(article -> article.getContent().contains(keyword) || article.getTitle().contains(keyword))
                .toList();
    }
}
