package article.view.inputview;

import article.domain.Article;
import article.view.UpdateArticleDTO;
import article.view.WriteArticleDTO;
import article.view.ListArticlesDto;
import java.util.Scanner;

public class ArticleInputView {

    private final Scanner scanner;

    public ArticleInputView(Scanner scanner) {
        this.scanner = scanner;
    }

    public String readCommand() {
        System.out.print("명령어:");
        return scanner.nextLine();
    }

    public WriteArticleDTO readWriteArticle() {
        System.out.print("제목: ");
        String title = scanner.nextLine();
        System.out.print("내용: ");
        String content = scanner.nextLine();

        return new WriteArticleDTO(title, content);
    }

    public UpdateArticleDTO readUpdateArticle(Article targetArticle) {
        int id = targetArticle.getId();
        String oldTitle = targetArticle.getTitle();
        String oldContent = targetArticle.getContent();

        System.out.printf("제목 (현재: %s): ", oldTitle);
        String newTitle = scanner.nextLine();
        System.out.printf("내용 (현재: %s): ", oldContent);
        String newContent = scanner.nextLine();

        return new UpdateArticleDTO(id, newTitle, newContent);
    }

    public ListArticlesDto readListArticles() {
        System.out.println("기본 정렬(최신순)으로 나타냅니까? (Y/N)");

        String yesOrNo = scanner.nextLine().toUpperCase();
        if (yesOrNo.equalsIgnoreCase("Y")) {
            return new ListArticlesDto(true, "", "");
        }

        System.out.println("시간순 번호순 중 하나를 입력하세요.");
        String orderName = scanner.nextLine();
        System.out.println("오름차순 내림차순 중 하나를 입력하세요.");
        String direction = scanner.nextLine();

        return new ListArticlesDto(false, orderName, direction);
    }

    public String readSearchKeyword() {
        System.out.println("검색할 문자열을 입력하세요.(제목 + 내용으로 검색합니다)");
        return scanner.nextLine();
    }
}
