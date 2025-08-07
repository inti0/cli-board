package article.view.outputview;

import article.domain.Article;
import java.time.format.DateTimeFormatter;
import java.util.List;

public class ArticleOutputView {

    private static final DateTimeFormatter DATE_TIME_FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd");

    public void printWriteSuccessMessage() {
        System.out.println("=> 게시글이 등록되었습니다.");
    }

    public void printArticles(List<Article> articles) {
        StringBuilder stringBuilder = new StringBuilder();

        stringBuilder.append("번호 | 제목       | 등록일\n")
                .append("-----------------------------\n");

        articles.forEach(article -> stringBuilder.append("%d    | %s  | %s\n".formatted(
                article.getId(),
                article.getTitle(),
                DATE_TIME_FORMATTER.format(article.getRegDate()))));

        System.out.println(stringBuilder.toString());
    }

    public void printArticleDetail(Article article) {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("번호: %d\n".formatted(article.getId()))
                .append("제목: %s\n".formatted(article.getTitle()))
                .append("내용: %s\n".formatted(article.getContent()))
                .append("조회수: %d\n".formatted(article.getViewCount()));

        String dateFormat = DATE_TIME_FORMATTER.format(article.getRegDate());
        stringBuilder.append("등록일: %s\n".formatted(dateFormat));

        System.out.println(stringBuilder.toString());
    }

    public void printDeleteSuccessMessage() {
        System.out.println("=> 게시글이 삭제되었습니다.");
    }

    public void printUpdateSuccessMessage() {
        System.out.println("=> 게시글이 수정되었습니다.");
    }

    public void printCommandList() {
        System.out.println("명령어를 다시 입력해주세요.\n"
                + "명령어 리스트) write, list, detail [id], update [id], delete [id], search");
    }

    public void printErrorMessage(IllegalArgumentException e) {
        System.out.println("[ERROR] : " + e.getMessage());
    }
}
