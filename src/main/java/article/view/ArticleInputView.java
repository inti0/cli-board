package article.view;

import article.domain.Article;
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

    public UpdateArticleDTO readUpdateArticle(UpdateArticleDTO updateArticleDTO) {
        int id = updateArticleDTO.id();
        String oldTitle = updateArticleDTO.title();
        String oldContent = updateArticleDTO.content();

        System.out.printf("제목 (현재: %s): ", oldTitle);
        String newTitle = scanner.nextLine();
        System.out.printf("내용 (현재: %s): ", oldContent);
        String newContent = scanner.nextLine();

        return new UpdateArticleDTO(id, newTitle, newContent);
    }
}
