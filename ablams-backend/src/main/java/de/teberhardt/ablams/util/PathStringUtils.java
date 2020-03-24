package de.teberhardt.ablams.util;

import java.nio.file.Path;
import java.nio.file.Paths;

public class PathStringUtils {

    private final Path folderPath;

    public PathStringUtils(Path folderPath) {
        this.folderPath = folderPath;
    }

    public String getRelativeString(String parentString) {
        return getRelativeString(Paths.get(parentString));
    }

    public String getRelativeString(Path parentPath)
    {
        return parentPath.relativize(folderPath).toString();
    }

    public String getFileName() {
        return folderPath.getFileName().toString();
    }
}
