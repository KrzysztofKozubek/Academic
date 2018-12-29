public class WatchDir {
private final WatchService watcher;
private final Path dir = Paths.get(".");
public WatchDir() throws IOException {
    this.watcher = FileSystems.getDefault().newWatchService();
    dir.register(watcher, ENTRY_CREATE);
}

public static void main(String[] args) throws IOException {
    // oczekujemy na zdarzenia
    new WatchDir().processEvents();
}

void processEvents() {
    while(true){
        // wait for key to be signalled
        WatchKey key;
        try {
            key = watcher.take();
        } catch (InterruptedException x) { return; }
        for (WatchEvent<?> event: key.pollEvents()) {
            WatchEvent<Path> ev = (WatchEvent<Path>) event;
            Path name = ev.context();
            Path child = this.dir.resolve(name);
            System.out.format("%s: %s\n", event.kind().name(), child);
        }
        key.reset();
    }
}
}
