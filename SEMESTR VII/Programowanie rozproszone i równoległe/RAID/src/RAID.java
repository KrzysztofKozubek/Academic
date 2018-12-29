import java.util.concurrent.atomic.AtomicInteger;

/**
 * Created by Kriss on 23.11.2016.
 */

public class RAID implements RAIDInterface {

    private DiskInterface[] raid;

    private int nDisksRW;
    private int sizeLogicDisks;
    private int hardwareSize;

    private volatile int breakDisk = -1;
    private volatile boolean turnOn = true;

    private volatile AtomicInteger nOperation = new AtomicInteger(0);

    int[] localSector;
    int[] usingDisk;

    @Override
    public void addDisks(DiskInterface[] array) {

        nDisksRW = array.length - 1;
        hardwareSize = array[0].size();
        sizeLogicDisks = hardwareSize * nDisksRW;
        raid = new DiskInterface[nDisksRW + 1];
        for (int i = 0; i <= nDisksRW; i++) raid[i] = array[i];

        localSector = new int[hardwareSize * (nDisksRW + 1)];
        usingDisk = new int[hardwareSize * (nDisksRW + 1)];
        for (int i = 0; i < hardwareSize * (nDisksRW + 1); i++) {
            localSector[i] = getLocalSector2(i);
            usingDisk[i] = getUseDisk2(i);
        }
    }

    @Override
    public void write(int sector, int value) {
        if (!turnOn) return;
        increamentOperation();

        int usingDisk = getUseDisk(sector);
        int localSector = getLocalSector(sector);

        int oldValue = 0;
        int sumControlValue = 0;

//        oldValue = read(sector);
//        sumControlValue = read(sizeLogicDisks + localSector);
//        write2(usingDisk, localSector, value);
//        write2(nDisksRW, localSector, sumControlValue + value - oldValue);
        if (breakDisk != nDisksRW) {

            AtomicInteger AISUMDISK = new AtomicInteger(0);
            Thread t1 = new Thread(new Runnable() {
                @Override
                public void run() {
                    AISUMDISK.set(read(sector));
                }
            });
            t1.start();

            AtomicInteger AISUMCONTROL = new AtomicInteger(read(sizeLogicDisks + localSector));

            if (breakDisk != nDisksRW) {

                try {
                    t1.join();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

                Thread t2 = new Thread(new Runnable() {
                    @Override
                    public void run() {
                        write2(nDisksRW, localSector, AISUMCONTROL.get() + value - AISUMDISK.get());
                    }
                });

                t2.start();

                if (breakDisk != usingDisk)
                    write2(usingDisk, localSector, value);

                try {
                    t2.join();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

            } else {
                if (breakDisk != usingDisk)
                    write2(usingDisk, localSector, value);
            }
        } else {
            if (breakDisk != usingDisk)
                write2(usingDisk, localSector, value);
        }

        decrementperation();
    }

    @Override
    public int read(int sector) {
        if (!turnOn) return -1;
        increamentOperation();

        int value = 0;

        int usingDisk = getUseDisk(sector);
        int localSector = getLocalSector(sector);

        if (breakDisk == usingDisk)
            value = readUseSumControl(sector);
        else {
            value = read2(usingDisk, localSector);
            if (value == -1)
                value = readUseSumControl(sector);
        }

        decrementperation();
        return value;
    }

    public int readUseSumControl(int sector) {

//        int localSector = getLocalSector(sector);
//        int[] sum = new int[nDisksRW + 1];
//        int result = 0;
//
//        ExecutorService executor = Executors.newFixedThreadPool(nDisksRW + 1);
//        Future<Integer>[] future = new Future[nDisksRW + 1];
//        Callable<Integer>[] task = new Callable[nDisksRW + 1];
//        for (int i = 0; i <= nDisksRW; i++) {
//            final int tmp = i;
//            task[i] = () -> {
//                if (breakDisk != tmp) {
//                    int TaskResult = read2(tmp, localSector);
//                    if (TaskResult == -1)
//                        return 0;
//                    else return TaskResult;
//                } else return 0;
//            };
//            future[i] = executor.submit(task[i]);
//        }
//
//        for (int i = 0; i <= nDisksRW; i++)
//            try {
//                sum[i] = future[i].get();
//            } catch (InterruptedException e) {
//                e.printStackTrace();
//            } catch (ExecutionException e) {
//                e.printStackTrace();
//            }
//
//        for (int i = 0; i <= nDisksRW; i++) result += sum[i];
//        if (breakDisk != nDisksRW) result = sum[nDisksRW] * 2 - result;
//        return result;
        int localSector = getLocalSector(sector);

        AtomicInteger[] values = new AtomicInteger[nDisksRW + 1];
        Thread[] t = new Thread[nDisksRW + 1];

        for (int i = 0; i <= nDisksRW; i++) {
            AtomicInteger AII = new AtomicInteger(i);

            t[i] = new Thread(new Runnable() {
                public void run() {

                    int i = AII.get();
                    if (breakDisk != i) {
                        values[i] = new AtomicInteger(read2(i, localSector));
                        if (values[i].get() == -1)
                            values[i] = new AtomicInteger(0);
                    } else {
                        values[i] = new AtomicInteger(0);
                    }
                }
            });
            t[i].start();
        }

        int sum = 0;
        for (int i = 0; i <= nDisksRW; i++) {
            try {
                t[i].join();
                sum += values[i].get();
            } catch (InterruptedException e) {
            }
        }

        if (breakDisk != nDisksRW) sum = values[nDisksRW].get() * 2 - sum;

        return sum;
    }

    @Override
    public int size() {
        if (!turnOn) return -1;
        return sizeLogicDisks;
    }

    @Override
    public void shutdown() {
        if (!turnOn) return;
        while (nOperation.get() > 0) {
            try {
                Thread.currentThread().sleep(800);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        turnOn = false;
    }


    private void increamentOperation() {
        nOperation.incrementAndGet();
        //changeOperation(0);
    }

    private void decrementperation() {
        nOperation.decrementAndGet();
        //changeOperation(1);
    }


    public int read2(int disk, int localSector) {

        if (disk == breakDisk) return -1;
        synchronized (raid[disk]) {
            if (disk == breakDisk) {
                raid[disk].notify();
                return -1;
            }
            try {
                int result = raid[disk].read(localSector);
                raid[disk].notify();
                return result;
            } catch (DiskInterface.DiskError diskError) {
                raid[disk].notify();
                breakDisk = disk;
                return -1;
            }
        }
    }

    public void write2(int disk, int localSector, int value) {

        if (disk == breakDisk) return;
        synchronized (raid[disk]) {
            if (disk == breakDisk) {
                raid[disk].notify();
                return;
            }
            try {
                raid[disk].write(localSector, value);
                raid[disk].notify();
            } catch (DiskInterface.DiskError diskError) {
                raid[disk].notify();
                breakDisk = disk;
            }
        }
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
    private int getLocalSector(int sector) {
        return localSector[sector];

//        if (sector >= sizeLogicDisks) {
//            return sector - sizeLogicDisks;
//        }
//        return sector / nDisksRW;
    }

    private int getUseDisk(int sector) {
        return usingDisk[sector];

//        if (sector >= sizeLogicDisks) {
//            return nDisksRW;
//        }
//        return sector % nDisksRW;
    }

    private int getLocalSector2(int sector) {

        if (sector >= sizeLogicDisks) {
            return sector - sizeLogicDisks;
        }
        return sector / nDisksRW;
    }

    private int getUseDisk2(int sector) {

        if (sector >= sizeLogicDisks) {
            return nDisksRW;
        }
        return sector % nDisksRW;
    }
}