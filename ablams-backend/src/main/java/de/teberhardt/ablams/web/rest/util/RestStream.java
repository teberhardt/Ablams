package de.teberhardt.ablams.web.rest.util;

import org.apache.commons.io.IOUtils;

import javax.ws.rs.core.StreamingOutput;
import java.io.InputStream;
import java.net.FileNameMap;
import java.net.URLConnection;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class RestStream {
    private final String mimetype;
    private final StreamingOutput streamingOutput;

    public RestStream(String mimetype, StreamingOutput streamingOutput) {
        this.mimetype = mimetype;
        this.streamingOutput = streamingOutput;
    }

    public String getMimetype() {
        return mimetype;
    }

    public StreamingOutput getStreamingOutput() {
        return streamingOutput;
    }

   private static final FileNameMap fileNameMap = URLConnection.getFileNameMap();

    public static RestStream of(Path file){

        if(Files.isRegularFile(file)) {

            String mimeType = fileNameMap.getContentTypeFor(file.getFileName().toString());

            StreamingOutput streamingOutput = output -> {
                try (InputStream input = Files.newInputStream(file)) {
                    IOUtils.copy(input, output);
                }};

            return new RestStream(mimeType, streamingOutput);
        }
        return null;
    }
}
