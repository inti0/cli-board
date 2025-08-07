package article.controller;

import article.domain.Article;
import article.service.ArticleService;
import article.view.UpdateArticleDTO;
import article.view.WriteArticleDTO;
import article.view.inputview.ArticleInputView;
import article.view.ListArticlesDto;
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
        while (true) {
            ParsedCommand parsedCommand = readCommand();
            //입력 오류로 인한 파싱 실패
            if (parsedCommand == null) {
                continue;
            }

            //종료 조건
            if (parsedCommand.getCommandName().equals("exit")) {
                break;
            }

            //command 입력에 따라 동작 수행
            try {
                dispatchCommand(parsedCommand);
            } catch (IllegalArgumentException e) {
                printErrorMessage(e);
            }
        }
    }

    private ParsedCommand readCommand() {
        String command = inputView.readCommand();
        ParsedCommand parsedCommand;

        try {
            parsedCommand = ParsedCommand.of(command);
        } catch (IllegalArgumentException e) {
            printErrorMessage(e);
            printCommandList();
            return null;
        }

        return parsedCommand;
    }

    private void printErrorMessage(IllegalArgumentException e) {
        outputView.printErrorMessage(e);
    }

    private void printCommandList() {
        outputView.printCommandList();
    }

    private void dispatchCommand(ParsedCommand parsedCommand) {
        String commandName = parsedCommand.getCommandName();
        int id = parsedCommand.getId();
        switch (commandName) {
            case "write" -> writeArticle();
            case "list" -> listArticles();
            case "detail" -> showDetail(id);
            case "update" -> updateArticle(id);
            case "delete" -> deleteArticle(id);
            case "search" -> searchArticles();
            default -> printCommandList();
        }
    }

    private void writeArticle() {
        WriteArticleDTO writeArticleDTO = inputView.readWriteArticle();
        articleService.writeArticle(writeArticleDTO);
        outputView.printWriteSuccessMessage();
    }

    private void listArticles() {
        ListArticlesDto listArticlesDto = inputView.readListArticles();
        List<Article> articles = articleService.listArticles(listArticlesDto);
        outputView.printArticles(articles);
    }

    private void showDetail(int id) {
        Article article = articleService.showDetail(id);
        outputView.printArticleDetail(article);
    }

    private void updateArticle(int id) {
        Article article = articleService.findArticleWithId(id);
        UpdateArticleDTO updateArticleDTO = inputView.readUpdateArticle(article);
        articleService.updateArticle(updateArticleDTO);
        outputView.printUpdateSuccessMessage();
    }

    private void deleteArticle(int id) {
        articleService.deleteArticle(id);
        outputView.printDeleteSuccessMessage();
    }

    private void searchArticles() {
        String keyword = inputView.readSearchKeyword();
        List<Article> articles = articleService.searchArticlesByKeyword(keyword);
        outputView.printArticles(articles);
    }
}
