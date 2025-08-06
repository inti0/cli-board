import article.controller.ArticleController;
import article.repository.ArticleRepository;
import article.service.ArticleService;
import article.view.inputview.ArticleInputView;
import article.view.outputview.ArticleOutputView;
import java.util.Scanner;

//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.
public class Main {
    public static void main(String[] args) {
        ArticleRepository articleRepository = new ArticleRepository();
        ArticleService articleService = new ArticleService(articleRepository);
        ArticleOutputView articleOutputView = new ArticleOutputView();
        ArticleInputView articleInputView = new ArticleInputView(new Scanner(System.in));
        ArticleController articleController = new ArticleController(articleService, articleInputView, articleOutputView);
        App app = new App(articleController);

        app.run();
    }
}