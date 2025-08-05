package article.view.outputview;

import article.domain.Article;
import java.time.format.DateTimeFormatter;
import java.util.List;

public class ArticleOutputView {

    public void printWriteArticleMessage() {
        System.out.println("=> 게시글이 등록되었습니다.");
    }

    public void printArticles(List<Article> articles) {
        StringBuilder stringBuilder = new StringBuilder();

        stringBuilder.append("번호 | 제목       | 등록일\n")
                .append("-----------------------------\n");

        articles.forEach(article -> stringBuilder.append("%d    | %s  | %s\n".formatted(
                article.getId(),
                article.getTitle(),
                article.getContent())));

        System.out.println(stringBuilder.toString());
    }

    public void printArticleDetail(Article article) {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("명령어: %s\n".formatted(article.getTitle()))
                .append("번호: %d\n".formatted(article.getId()))
                .append("제목: %s\n".formatted(article.getTitle()))
                .append("내용: %s\n".formatted(article.getContent()));

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        String dateFormat = formatter.format(article.getRegDate());
        stringBuilder.append("등록일: %s\n".formatted(dateFormat));

        System.out.println(stringBuilder.toString());
    }

    public void printDeleteArticleMessage() {
        System.out.println("=> 게시글이 삭제되었습니다.");
    }
}
