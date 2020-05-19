package de.drippinger.gatling;

import org.eclipse.jgit.lib.ObjectId;
import org.eclipse.jgit.lib.Repository;
import org.eclipse.jgit.storage.file.FileRepositoryBuilder;

import java.io.IOException;

public final class GitRevisionChecker {

    protected static final int GIT_ABBREVIATION_LENGTH = 8;

    private GitRevisionChecker() {
        // NOP
    }

    public static String currentRevision() {
        try {
            Repository repository = new FileRepositoryBuilder()
                .findGitDir()
                .build();

            ObjectId head = repository.resolve("HEAD");
            return head.abbreviate(GIT_ABBREVIATION_LENGTH).name();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

}
