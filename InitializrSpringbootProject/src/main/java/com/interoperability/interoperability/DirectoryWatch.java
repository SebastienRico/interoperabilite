package com.interoperability.interoperability;

import com.interoperability.interoperability.utilities.Util;
import java.io.File;
import static java.nio.file.StandardWatchEventKinds.ENTRY_CREATE;
import static java.nio.file.StandardWatchEventKinds.OVERFLOW;
import static java.nio.file.StandardWatchEventKinds.ENTRY_DELETE;
import static java.nio.file.StandardWatchEventKinds.ENTRY_MODIFY;

import java.io.IOException;
import java.nio.file.FileSystem;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.WatchEvent;
import java.nio.file.WatchEvent.Kind;
import java.nio.file.WatchKey;
import java.nio.file.WatchService;
import java.util.concurrent.TimeUnit;
import java.util.logging.Level;
import java.util.logging.Logger;

public class DirectoryWatch extends Thread {

    @Override
    public void run() {
        watchDirectoryPath(Util.getProperty("path_to_watch"));
    }

    public void watchDirectoryPath(String pathDirectory) {

        Path path = Paths.get(pathDirectory);
        Logger.getLogger(DirectoryWatch.class.getName()).log(Level.INFO, "Watching path : {0}", path);
        FileSystem fs = path.getFileSystem();

        try (WatchService service = fs.newWatchService()) {
            path.register(service, ENTRY_CREATE, ENTRY_MODIFY, ENTRY_DELETE);
            // Start the infinite polling loop
            WatchKey key = null;
            while (true) {
                key = service.take();
                // Dequeueing events
                Kind<?> kind = null;
                for (WatchEvent<?> watchEvent : key.pollEvents()) {
                    // Get the type of the event
                    kind = watchEvent.kind();
                    if (OVERFLOW == kind) {
                        continue; // loop
                    } else if (ENTRY_CREATE == kind) {
                        Path newPath = ((WatchEvent<Path>) watchEvent).context();
                        
                        Logger.getLogger(DirectoryWatch.class.getName()).log(Level.INFO, "New path created : {0}", newPath);
                        TimeUnit.SECONDS.sleep(1);
                        
                        ParserCSV parser = new ParserCSV();
                        parser.parsingFichier(pathDirectory + "\\" + newPath);
                        
                        File file = new File(pathDirectory + "\\" + newPath);
                        if (file.delete()) {
                            Logger.getLogger(DirectoryWatch.class.getName()).log(Level.INFO, "File deleted : {0}", newPath);
                        } else {
                            Logger.getLogger(DirectoryWatch.class.getName()).log(Level.SEVERE, "Cannot delete file : {0}", newPath);
                        }
                    } else if (ENTRY_MODIFY == kind) {
                        Path newPath = ((WatchEvent<Path>) watchEvent).context();
                        Logger.getLogger(DirectoryWatch.class.getName()).log(Level.INFO, "File modified : {0}", newPath);
                    }
                }
                if (!key.reset()) {
                    break; // loop
                }
            }
        } catch (IOException | InterruptedException ioe) {
        }
    }
}
