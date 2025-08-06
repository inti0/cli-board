import article.controller.ArticleController;

public class App {

    private final ArticleController articleController;

    public App(ArticleController articleController) {
        this.articleController = articleController;
    }

    void run() {
        System.out.println("=== CLI 게시판 구동 ===");
        articleController.execute();
    }
}
