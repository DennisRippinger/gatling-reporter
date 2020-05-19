package de.drippinger.gatling;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static de.drippinger.gatling.GitRevisionChecker.GIT_ABBREVIATION_LENGTH;
import static org.assertj.core.api.Assertions.assertThat;

class GitRevisionCheckerTest {

    @Test
    @DisplayName("Provides a current revision")
    void provide_current_revision() {
        assertThat(GitRevisionChecker.currentRevision())
            .hasSize(GIT_ABBREVIATION_LENGTH);
    }

}
