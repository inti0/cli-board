package article.domain;

import java.time.LocalDateTime;

public class Article {

    private final int id;
    private final String title;
    private final String content;
    private final LocalDateTime regDate;
    private int viewCount = 0;

    public Article(int id, String title, String content) {
        this.id = id;
        this.title = title;
        this.content = content;
        this.regDate = LocalDateTime.now();
    }

    public int getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public String getContent() {
        return content;
    }

    public LocalDateTime getRegDate() {
        return regDate;
    }

    public int getViewCount() {
        return viewCount;
    }

    public void increaseViewCount() {
        this.viewCount++;
    }
}
