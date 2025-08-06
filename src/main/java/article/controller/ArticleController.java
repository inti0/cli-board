package article.controller;

import article.domain.Article;
import article.service.ArticleService;
import article.view.UpdateArticleDTO;
import article.view.WriteArticleDTO;
import article.view.inputview.ArticleInputView;
import article.view.outputview.ArticleOutputView;
import java.util.List;

public class ArticleController {

    private final ArticleService articleService;
    private final ArticleInputView inputView;
    private final ArticleOutputView outputView;

    public ArticleController(ArticleService articleService, ArticleInputView inputView, ArticleOutputView outputView) {
        this.articleService = articleService;
        this.inputView = inputView;
        this.outputView = outputView;
    }

    public void execute() {
        boolean exitFlag = true;
        while (exitFlag) {
            String command = inputView.readCommand();
            ParsedCommand parsedCommand;
            try {
                parsedCommand = ParsedCommand.of(command);
            } catch (IllegalArgumentException e) {
                System.out.println(e.getMessage());
                continue;
            }

            String commandName = parsedCommand.getCommandName();
            int id = parsedCommand.getId();
            switch (commandName) {
                case "write" -> writeArticle();
                case "list" -> showArticles();
                case "detail" -> showDetail(id);
                case "update" -> updateArticle(id);
                case "delete" -> deleteArticle(id);
                case "exit" -> exitFlag = false;
            }
        }
    }

    private void writeArticle() {
        WriteArticleDTO writeArticleDTO = inputView.readWriteArticle();
        articleService.writeArticle(writeArticleDTO);
        outputView.printWriteSuccessMessage();
    }

    private void showArticles() {
        List<Article> articles = articleService.listArticles();
        outputView.printArticles(articles);
    }

    private void showDetail(int id) {
        Article article = findArticleWithId(id);
        if (article == null) {
            return;
        }
        outputView.printArticleDetail(article);
    }

    private Article findArticleWithId(int id) {
        try {
            return articleService.findArticleWithId(id);
        } catch (IllegalArgumentException e) {
            System.out.println(e.getMessage());
        }
        return null;
    }

    private void updateArticle(int id) {
        Article article = findArticleWithId(id);
        if (article == null) {
            return;
        }
        UpdateArticleDTO updateArticleDTO = inputView.readUpdateArticle(article);
        articleService.updateArticle(updateArticleDTO);
        outputView.printUpdateSuccesMessage();
    }

    private void deleteArticle(int id) {
        Article articleWithId = findArticleWithId(id);
        if (articleWithId == null) {
            return;
        }
        articleService.deleteArticle(id);
        outputView.printDeleteArticleMessage();
    }
}
