package article.controller;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

class ParsedCommandTest {

    // 2. @ParameterizedTest를 사용한 테스트 (권장)
    @ParameterizedTest
    @DisplayName("유효한 형식의 명령어는 정상적으로 파싱된다.")
    @CsvSource({
            "write, write, 0",
            "list, list, 0",
            "detail 123, detail, 123",
            "update 456, update, 456",
            "delete 789, delete, 789"
    })
    void test_valid_commands(String input, String expectedCommand, int expectedId) {
        // given
        // @CsvSource가 테스트 데이터를 제공

        // when
        ParsedCommand parsedCommand = ParsedCommand.of(input);

        // then
        assertThat(parsedCommand.getCommandName()).isEqualTo(expectedCommand);
        assertThat(parsedCommand.getId()).isEqualTo(expectedId);
    }

    @ParameterizedTest
    @DisplayName("유효하지 않은 형식의 명령어는 예외를 던진다.")
    @CsvSource({
            "write 123 456", // ID가 두 개
            "invalid-command", // 공백이 아닌 다른 구분자 사용
            "''",             // 빈 문자열
            "123 456"       // 숫자만 사용
    })
    void test_invalid_commands_should_throw_exception(String input) {
        // when & then
        assertThatThrownBy(() -> ParsedCommand.of(input))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("패턴 매칭 오류");
    }
}