import java.util.*;
import java.util.concurrent.*;
import java.util.concurrent.atomic.AtomicBoolean;

import static java.util.concurrent.TimeUnit.MINUTES;

public class RAID2 implements RAIDInterface2 {

    private int PhisicalSizeDisks   = 0;
    private int diskSize            = 0;
    private int diskControl         = 0;

    private final List<DiskInterface2>                  raid        = new ArrayList<>();
    private final ExecutorService                       exService   = Executors.newCachedThreadPool();
    private final AtomicBoolean                         turnOff     = new AtomicBoolean(false);
    private final ConcurrentHashMap<Integer, Integer>   buffer      = new ConcurrentHashMap<>();

    @Override
    public void addDisks(DiskInterface2[] array) {
        for(int i = 0; i < array.length; i++) {
            raid.add(array[i]);
            if(array[i] != null) diskSize = array[i].size();
        }

        diskControl = array.length - 1;
        PhisicalSizeDisks = (raid.size() - 1) * diskSize;
    }
    private synchronized Future<Integer> exServiceRead(int sector){
        Read read = new Read(sector);
        if (checkIsTurnOff()) return null;
        return exService.submit(read);
    }

    private synchronized boolean exServiceWrite(int sector, int value){
        Write write = new Write(sector, value);
        if (checkIsTurnOff()) return false;
        exService.submit(write);
        return true;
    }

    @Override
    public int read(int sector) {

        Future<Integer> result = exServiceRead(sector);
        if(result == null) return -1;

        try { return result.get(); }
        catch (Exception e) {}
        return -1;
    }

    @Override
    public void write(int sector, int value) {

        addToBuffer(sector, value);
        if(exServiceWrite(sector, value) == false) return;
    }

    @Override
    public int size() { return PhisicalSizeDisks; }

    @Override
    public void shutdown() {

        turnOffRAID();
        exService.shutdown();
//        try { exService.awaitTermination(5, MINUTES); }
//        catch (Exception e) {}
    }

    @Override
    public boolean sectorInUse(int sector) { synchronized (buffer) { return buffer.containsKey(sector); }}

    private synchronized void addToBuffer(int sector, int value){ buffer.put(sector, value); }

    private synchronized boolean checkIsTurnOff(){ return turnOff.get(); }

    private synchronized void turnOffRAID(){ turnOff.set(true); }

    private class Read implements Callable<Integer> {

        private final int sector;

        private Read(int sector) { this.sector = sector; }

        /**
         * Split sector RAID            Split sector Local Disk
         * d:0  d:1  d:2  d:3  d:S      d:0  d:1  d:2  d:3  d:S
         * |---||---||---||---||---|    |---||---||---||---||---|
         * | 0 || 1 || 2 || 3 || 8 |    | 0 || 0 || 0 || 0 || 0 |
         * |---||---||---||---||---|    |---||---||---||---||---|
         * | 4 || 5 || 6 || 7 || 9 |    | 1 || 1 || 1 || 1 || 1 |
         * |---||---||---||---||---|    |---||---||---||---||---|
         */
        private int getLocalSector(int sector) { return sector % diskSize; }

        private int getUseDisk(int sector) { return sector / diskSize; }

        private int readAllDisk(int useDisk, int useSector) {

            int result = 0;

            int i = 0;
            while (i < raid.size() - 1) {

                if (i != useDisk) {

                    int sectorNum = (i * diskSize) + useSector;
                    if (sectorInUse(sectorNum)) {
                        synchronized (buffer) {
                            result += buffer.get(sectorNum);
                        }
                        i++;
                        continue;
                    }

                    synchronized (raid.get(i)) {
                        result += raid.get(i).read(useSector);
                    }
                    i++;
                } else {
                    i++;
                }
            }
            return result;
        }

        @Override
        public Integer call() throws Exception {

            final int useSector = getLocalSector(sector);
            final int useDisk   = getUseDisk(sector);

            if (!sectorInUse(sector)) {

                if (raid.get(useDisk) == null) {

                    int sum = readAllDisk(useDisk, useSector);

                    if (raid.get(diskControl) == null) {
                        return -1;
                    }
                    synchronized (raid.get(diskControl)) {
                        return raid.get(diskControl).read(useSector) - sum;
                    }
                } else {
                    synchronized (raid.get(useDisk)) {
                        return raid.get(useDisk).read(useSector);
                    }
                }
            } else {
                synchronized (buffer) {
                    return buffer.get(sector);
                }
            }
        }
    }

    private class Write implements Callable<Boolean> {

        private final int sector;
        private final int value;

        private Write(int sector, int value) {

            this.sector = sector;
            this.value = value;
        }

        /**
         * Split sector RAID            Split sector Local Disk
         * d:0  d:1  d:2  d:3  d:S      d:0  d:1  d:2  d:3  d:S
         * |---||---||---||---||---|    |---||---||---||---||---|
         * | 0 || 1 || 2 || 3 || 8 |    | 0 || 0 || 0 || 0 || 0 |
         * |---||---||---||---||---|    |---||---||---||---||---|
         * | 4 || 5 || 6 || 7 || 9 |    | 1 || 1 || 1 || 1 || 1 |
         * |---||---||---||---||---|    |---||---||---||---||---|
         */
        private int getLocalSector(int sector) { return sector % diskSize; }

        private int getUseDisk(int sector) { return sector / diskSize; }

        @Override
        public Boolean call() throws Exception {

            final int useSector = getLocalSector(sector);
            final int useDisk   = getUseDisk(sector);

            int controlSum = value;
            int i = 0;
            while (i < diskControl) {

                if (i == useDisk) {
                    i++;
                    continue;
                }

                final int localSectorNum = useSector + (i * diskSize);

                if (!sectorInUse(localSectorNum)) { } else {
                    synchronized (buffer) {
                        controlSum += buffer.get(localSectorNum);
                        i++;
                        continue;
                    }
                }

                if (raid.get(i) == null) { } else {
                    synchronized (raid.get(i)) {
                        controlSum += raid.get(i).read(useSector);
                    }
                }
                i++;
            }
            if (raid.get(raid.size() - 1) == null) {
            } else {
                synchronized (raid.get(diskControl)) {
                    raid.get(diskControl).write(useSector, controlSum);
                }
            }
            if (raid.get(useDisk) == null) {
            } else {
                synchronized (raid.get(useDisk)) {
                    raid.get(useDisk).write(useSector, value);
                }
            }

            synchronized (buffer) { buffer.remove(sector); }
            return true;
        }
    }
}
