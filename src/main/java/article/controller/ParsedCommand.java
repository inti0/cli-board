package article.controller;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ParsedCommand {

    private static final Pattern pattern = Pattern.compile("^([a-zA-Z]+)( \\d+)?");
    private final String commandName;
    private final int id;

    private ParsedCommand(String commandName, int id) {
        this.commandName = commandName;
        this.id = id;
    }

    public static ParsedCommand of(String command) {
        Matcher matcher = pattern.matcher(command.toLowerCase().trim());
        if (!matcher.matches()) {
            throw new IllegalArgumentException("패턴 매칭 오류 : " + command);
        }
        return new ParsedCommand(matcher.group(1), matcher.group(2) == null ? 0 : Integer.parseInt(matcher.group(2).trim()));
    }

    public String getCommandName() {
        return commandName;
    }

    public int getId() {
        return id;
    }
}
