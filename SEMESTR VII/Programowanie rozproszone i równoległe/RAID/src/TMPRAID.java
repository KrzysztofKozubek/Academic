//import java.util.Arrays;
//import java.util.Vector;
//import java.util.concurrent.*;
//
///**
// * Created by Kriss on 15.11.2016.
// */
//public class TMPRAID implements RAIDInterface {
//
//    private volatile Vector<Boolean>         blockDisk  = new Vector<>();
//    private volatile Vector<DiskInterface>   disks      = new Vector<>();;
//
//    private int BREAK_DOWN_SUMCONTROL_DISK = 0;
//    private int operations = 0;
//
//    /**
//     * Dodaje dyski "fizyczne" do macirzy RAID
//     * Metoda na pewno zostanie wykonana przed innymi metodami tego
//     * samego interfejsu.
//     *
//     * @param array tablica z dyskami
//     */
//    @Override
//    public void addDisks(DiskInterface[] array) {
//        if (!turnOn) return;
//
////        for (int i = 0; i < array.length; i++) {
////
////            blockDisk.add(new Boolean(Boolean.FALSE));
////            disks.add(array[i]);
////
////            nDiskRW++;
////            sizeLogicDisks += array[i].size();
////        }
////
////        /* One disk is disk designed to holding sum control */
////        nDiskRW--;
////        sizeOneDisk     = array[0].size();
////        sizeLogicDisks -= sizeOneDisk;
//    }
//
//    /**
//     * Zlecenie zapisu danej value do sektora sector
//     * dysku logicznego
//     *
//     * @param sector sektor dysku logicznego, do ktorego ma nastapic zapis
//     * @param value wartosc do zapisu
//     */
//    int breakDisk = -1;
//
//    @Override
//    public void write(int sector, int value) {
//        if (!turnOn) return;
//
//        int useDisk = getUseDisk(nDiskRW, sizeLogicDisks, sector);
//
//        Write write = new Write(useDisk, sector, value, this);
//        Thread thread = new Thread(write);
//        thread.start();
//    }
//
//    /**
//     * Zlecenie odczytu z sektora sector dysku logicznego
//     *
//     * @param sector sektor dysku logicznego, ktory ma zostac odczytany
//     * @return wartosc odczytana z sektora
//     */
//    @Override
//    public int read(int sector) {
//        if (!turnOn) return -1;
//
//        int result = 0;
//        int useDisk = getUseDisk(nDiskRW, sizeLogicDisks, sector);
//
//        accessToDisk(useDisk);
//        try {
//            result = disks.get(useDisk).read(getLocalSector(nDiskRW, sizeLogicDisks, sector));
//            blockDisk.set(useDisk, false);
//        } catch (DiskInterface.DiskError diskError) {
//            diskError.printStackTrace();
//            blockDisk.set(useDisk, false);
//            /*
//            sum = 1+2+3+4 | S = sum
//            |---||---||---||---||-X-|
//            | 1 || 2 || 3 || 4 || S |
//            |---||---||---||---||-X-|
//             */
//           /* case: when break down last disk (sum control disk) */
//            breakDisk = useDisk;
//            Integer[] valueDisk = readAllDisk(sector);
//            System.out.println(Arrays.toString(valueDisk));
//            int sum = 0;
//            for (int i = 0; i < valueDisk.length - 1; i++) sum += valueDisk[i];
//            if (breakDisk == nDiskRW) return sum;
//
//            /*
//            sum = 1+2+4+S | 3 = sum - S
//            |---||---||-X-||---||---|
//            | 1 || 2 || 3 || 4 || S |
//            |---||---||-X-||---||---|
//             */
//            /* case: when break down other disk (not sum control disk) */
//            decreamantOperations();
//            return sum - valueDisk[valueDisk.length - 1];
//        }
//        decreamantOperations();
//        return result;
//    }
//
//    /**
//     * Rozmiar dysku logicznego, ktory powstaje po utworzeniu z dysków
//     * "fizycznych" jednego dysku logicznego.
//     *
//     * @return rozmiar w sektorach dysku logicznego
//     */
//    int nDiskRW         = 0;
//    int sizeLogicDisks  = 0;
//    int sizeOneDisk     = 0;
//
//    @Override
//    public int size() {
//        if (!turnOn) return -1;
//        return sizeLogicDisks;
//    }
//
//    /**
//     * Zlecenie zakonczenia operacji na dyskach.
//     * Z chwila zakocznia tej metody zadna operacja na powierzonych
//     * dyskach "fizycznych" nie moze juz zostac przeprowadzona
//     * przez dana instacje obiektu klasy implementujacej usluge.
//     * Po zakonczeniu metody shutdown wszystkie kolejne zlecenia nadchodzace
//     * od uzytkownika maja byc ignorowane i nie powodowac zmian na dysku.
//     * Oznacza to, ze zmiany stanu dyskow fizycznych moga byc realizowane wylacznie
//     * przez inne istancje klasy, ktora zaimplementuje ten interfejs.
//     */
//
//    boolean turnOn = true;
//
//    @Override
//    public void shutdown() {
//        while(operations != 0){}
//        turnOn = false;
//    }
//
//    private Integer[] readAllDisk(int sector) {
//
//        Integer[] valueDisk = new Integer[nDiskRW];
////        int startSector = sector - getUseDisk(nDiskRW, sizeLogicDisks, sector);
////
////        ExecutorService es = Executors.newFixedThreadPool(nDiskRW);
////        Vector<Future<Integer>> f = new Vector<>();
////
////        int j = 0;
////        for (int i = startSector; i < startSector + nDiskRW; i++, j++) {
////            if (breakDisk != j) {
////                f.add(es.submit(new Read(this, j, i)));
////            }
////        }
////        if (breakDisk != j) {
////            f.add(es.submit(new Read(this, nDiskRW, getLocalSector(nDiskRW, sizeLogicDisks, sector) + sizeLogicDisks)));
////        }
////        j = 0;
////        for (Future<Integer> var : f) {
////            try {
////                valueDisk[j] = var.get();
////                j++;
////            } catch (InterruptedException e) {
////                e.printStackTrace();
////            } catch (ExecutionException e) {
////                e.printStackTrace();
////            }
////        }
////        es.shutdown();
//
//        return valueDisk;
//    }
//
//    public void accessToDisk(int useDisk) {
//        synchronized (this) {
//            while (blockDisk.get(useDisk)) {
//            }
//            blockDisk.set(useDisk, true);
//        }
//        increamantOperations();
//    }
//
//    public Vector<Boolean> getBlockDisk() {
//        return blockDisk;
//    }
//
//    public Vector<DiskInterface> getDisks() {
//        return disks;
//    }
//
//
//    /**
//     * Split sector RAID            Split sector Local Disk
//     * d:0  d:1  d:2  d:3  d:S      d:0  d:1  d:2  d:3  d:S
//     * |---||---||---||---||---|    |---||---||---||---||---|
//     * | 0 || 1 || 2 || 3 || 8 |    | 0 || 0 || 0 || 0 || 0 |
//     * |---||---||---||---||---|    |---||---||---||---||---|
//     * | 4 || 5 || 6 || 7 || 9 |    | 1 || 1 || 1 || 1 || 1 |
//     * |---||---||---||---||---|    |---||---||---||---||---|
//     */
//    public static int getLocalSector(int nDisksRW, int sizeLogicDisks, int sector) {
//
////        if (sector >= sizeLogicDisks) {
////            return sector - sizeLogicDisks;
////        }
////        return sector / nDisksRW;
//    }
//
//    public static int getUseDisk(int nDisksRW, int sizeLogicDisks, int sector) {
//
////        if (sector >= sizeLogicDisks) {
////            return nDisksRW;
////        }
////        return sector % nDisksRW;
//    }
//
//    synchronized public void increamantOperations(){
//        operations++;
//    }
//    synchronized public void decreamantOperations(){
//        operations--;
//    }
//}
//
//class Write implements Runnable {
//
//    final int CORRECT_SAVE = 1;
//
//    int useDisk;
//    int sector;
//    int value;
//
//    RAID raid;
//
//    public Write(int useDisk, int sector, int value, RAID raid) {
//        this.useDisk = useDisk;
//        this.sector = sector;
//        this.value = value;
//        this.raid = raid;
//    }
//
//    /**
//     * run write value to sector of disk
//     *
//     * @return value CORRECT_SAVE if process end correct or null if disk is break down
//     */
//    @Override
//    public void run() {
//
//        raid.accessToDisk(useDisk);
//        try {
//            raid.getDisks().get(useDisk).write(raid.getLocalSector(raid.nDiskRW, raid.sizeLogicDisks, sector), value);
//            raid.getBlockDisk().set(useDisk, false);
//            raid.read(sector);
//
//            raid.decreamantOperations();
//        } catch (DiskInterface.DiskError diskError) {
//            diskError.printStackTrace();
//            /**
//             * raid have to do nothing
//             * |---||---||---||---||-X-|
//             * | 1 || 2 || 3 || 4 || S |
//             * |---||---||---||---||-X-|
//             *
//             */
//            /* case: when break down last disk (sum control disk) */
//            raid.breakDisk = useDisk;
//            if (useDisk != raid.nDiskRW) {
//
//            /**
//             * sum = 1+2+4+S | 3 = sum - S
//             * |---||---||-X-||---||---|
//             * | 1 || 2 || 3 || 4 || S |
//             * |---||---||-X-||---||---|
//             */
//             /* case: when break down other disk (not sum control disk) */
//                int valueSumControlDisk = raid.read(raid.sizeLogicDisks + raid.getLocalSector(raid.nDiskRW, raid.sizeLogicDisks, sector));
//                System.out.println(valueSumControlDisk);
//                try {
//                    raid.getDisks().get(useDisk).write(raid.sizeLogicDisks + raid.getLocalSector(raid.nDiskRW, raid.sizeLogicDisks, sector), valueSumControlDisk + value);
//                } catch (DiskInterface.DiskError diskError1) {
//                    diskError1.printStackTrace();
//                }
//            }
//            raid.getBlockDisk().set(useDisk, false);
//            raid.decreamantOperations();
//        }
//    }
//}
//
//class Read implements Callable<Integer> {
//
//    RAID raid;
//    int useDisk;
//    int sector;
//
//    public Read(RAID raid, int useDisk, int sector) {
//        this.useDisk = useDisk;
//        this.raid = raid;
//        this.sector = sector;
//    }
//
//    /**
//     * Call read value from sector of disk
//     *
//     * @return value sector of disk or null if disk is break down
//     */
//    public Integer call() throws Exception {
//
//        raid.accessToDisk(useDisk);
//        int valueSector = 0;
////        try {
////            valueSector = raid.getDisks().get(useDisk).read(raid.getLocalSector(raid.nDiskRW, raid.sizeLogicDisks, sector));
////            raid.getBlockDisk().set(useDisk, false);
////        } catch (DiskInterface.DiskError diskError) {
////            diskError.printStackTrace();
////            raid.getBlockDisk().set(useDisk, false);
////            return null;
////        }
//        return valueSector;
//    }
//}
//












//
//import java.util.Vector;
//import java.util.concurrent.atomic.AtomicInteger;
//
///**
// * Created by Kriss on 23.11.2016.
// */
//
//
//import java.util.Vector;
//import java.util.concurrent.atomic.AtomicInteger;
//
//public class RAID implements RAIDInterface {
//
//    private volatile Vector<DiskInterface> matrix = new Vector<>();
//    private volatile Vector<BridgeRAIDDisk> raid = new Vector<>();
//
//    private volatile int nDisksRW;
//    private volatile int sizeLogicDisks;
//    private volatile int hardwareSize;
//
//    private volatile int[] arrayMatrix;
//    private volatile int breakDisk = -1;
//    private volatile boolean turnOn = true;
//
//    private volatile int nOperation = 0;
//
//
//    @Override
//    public void addDisks(DiskInterface[] array) {
//
//        nDisksRW = array.length - 1;
//        hardwareSize = array[0].size();
//        sizeLogicDisks = hardwareSize * nDisksRW;
//        arrayMatrix = new int[array.length * hardwareSize];
//
//        for (int i = 0; i <= nDisksRW; i++) matrix.add(array[i]);
//        for (int i = 0; i <= nDisksRW; i++) raid.add(new BridgeRAIDDisk(array[i]));
//
////        for(int i = 0; i <= nDisksRW; i++){
////            AtomicInteger var = new AtomicInteger(hardwareSize);
////            AtomicInteger index = new AtomicInteger(i);
////
////            Thread t = new Thread(new Runnable() {
////                public void run() {
////                    int usingDisk = 0;
////                    synchronized (this) {
////                        usingDisk = index.get();
////                        index.set(usingDisk + 1);
////                    }
////                    for(int i = 0; i < var.get(); i++){
////                        try {
////                            arrayMatrix[getLogicSector(usingDisk, i)] = raid.get(usingDisk).read(i);
////                            //System.out.println("disk: " + getLogicSector(usingDisk, i) +  " " + arrayMatrix[getLogicSector(usingDisk, i)] );
////                        } catch (DiskInterface.DiskError diskError) {
////                            diskError.printStackTrace();
////                            arrayMatrix[getLogicSector(usingDisk, i)] = -1;
////                        }
////                    }
////                }
////            });
////            t.start();
////            try {
////                t.join();
////            } catch (InterruptedException e) {
////                e.printStackTrace();
////            }
////        }
//    }
//
//    @Override
//    public void write(int sector, int value) {
//        if (!turnOn) return;
//
//        increamentOperation();
//        int usingDisk = getUseDisk(sector);
//        int localSector = getLocalSector(sector);
//
//        int oldValue = 0;
//        int sumControlValue = 0;
//
//        oldValue = read(sector);
//        sumControlValue = read(sizeLogicDisks + localSector);
//
//        if (breakDisk != usingDisk) {
//            try {
//                raid.get(usingDisk).write(localSector, value);
//            } catch (DiskInterface.DiskError diskError) {
//                //diskError.printStackTrace();
//                breakDisk = usingDisk;
//            }
//        }
//        if (breakDisk != nDisksRW) {
//            try {
//                raid.get(nDisksRW).write(localSector, sumControlValue + value - oldValue);
//            } catch (DiskInterface.DiskError diskError) {
//                //diskError.printStackTrace();
//                breakDisk = usingDisk;
//            }
//        }
//        decrementperation();
//    }
//
//    @Override
//    public int read(int sector) {
//        if (!turnOn) return -1;
//
//        increamentOperation();
//        int value = 0;
//
//        int usingDisk = getUseDisk(sector);
//        int localSector = getLocalSector(sector);
//
//        try {
//            if (breakDisk == usingDisk)
//                throw new DiskInterface.DiskError();
//            value = raid.get(usingDisk).read(localSector);
//        } catch (DiskInterface.DiskError diskError) {
//            //diskError.printStackTrace();
//            breakDisk = usingDisk;
//
//            AtomicInteger sectors = new AtomicInteger(localSector);
//            AtomicInteger[] values = new AtomicInteger[nDisksRW + 1];
//            Thread[] t = new Thread[nDisksRW + 1];
//
//            for (int i = 0; i <= nDisksRW; i++) {
//
//                AtomicInteger p = new AtomicInteger(i);
//                t[i] = new Thread(new Runnable() {
//                    public void run() {
//
//                        int j = p.get();
//                        int s = sectors.get();
//
//                        if (breakDisk != j) {
//                            try {
//                                values[j] = new AtomicInteger(raid.get(j).read(s));
//                            } catch (DiskInterface.DiskError diskError1) {
//                                //diskError1.printStackTrace();
//                                values[j] = new AtomicInteger(0);
//                            }
//                        } else values[j] = new AtomicInteger(0);
//                    }
//                });
//                t[i].start();
//            }
//
//            for (int i = 0; i <= nDisksRW; i++) {
//                try {
//                    t[i].join();
//                } catch (InterruptedException e) {
//                    //e.printStackTrace();
//                }
//            }
//
//            int sum = 0;
//            for (int i = 0; i < values.length; i++) {
//                if (values[i] != null) {
//                    sum += values[i].get();
//                }
//            }
//
//            if (breakDisk != nDisksRW)
//                sum = values[nDisksRW].get() * 2 - sum;
//
//            decrementperation();
//            return sum;
//        }
//
//        decrementperation();
//        return value;
//    }
//
//    @Override
//    public int size() {
//        if (!turnOn) return -1;
//        return sizeLogicDisks;
//    }
//
//    @Override
//    public void shutdown() {
//        if (!turnOn) return;
//        while (nOperation != 0) {
//        }
//        turnOn = false;
//    }
//
//    private synchronized int doSth(int sector, int value, int operation) {
//        if (operation == 0) {
//            /* read */
//            return arrayMatrix[sector];
//        } else {
//            /* write */
//            arrayMatrix[sector] = value;
//        }
//        return -1;
//    }
//
//    private synchronized int readValue(int sector) {
//        return doSth(sector, -1, 0);
//    }
//
//    private synchronized void writeValue(int sector, int value) {
//        doSth(sector, value, 1);
//    }
//
//    private synchronized void increamentOperation() {
//        nOperation++;
//    }
//
//    private synchronized void decrementperation() {
//        nOperation--;
//    }
//
//
//    /**
//     * Split sector RAID            Split sector Local Disk
//     * d:0  d:1  d:2  d:3  d:S      d:0  d:1  d:2  d:3  d:S
//     * |---||---||---||---||---|    |---||---||---||---||---|
//     * | 0 || 1 || 2 || 3 || 8 |    | 0 || 0 || 0 || 0 || 0 |
//     * |---||---||---||---||---|    |---||---||---||---||---|
//     * | 4 || 5 || 6 || 7 || 9 |    | 1 || 1 || 1 || 1 || 1 |
//     * |---||---||---||---||---|    |---||---||---||---||---|
//     */
//    private int getLocalSector(int sector) {
//
//        if (sector >= sizeLogicDisks) {
//            return sector - sizeLogicDisks;
//        }
//        return sector / nDisksRW;
//    }
//
//    private int getLogicSector(int disk, int localSector) {
//
//        if (disk <= nDisksRW) {
//            return disk + nDisksRW * localSector;
//        }
//        return sizeLogicDisks + localSector;
//    }
//
//    private int getUseDisk(int sector) {
//
//        if (sector >= sizeLogicDisks) {
//            return nDisksRW;
//        }
//        return sector % nDisksRW;
//    }
//}
//
//class BridgeRAIDDisk {
//
//    DiskInterface disk;
//
//    public BridgeRAIDDisk(DiskInterface disk) {
//        this.disk = disk;
//    }
//
//    private synchronized int doSth(int sector, int value, int operation) throws DiskInterface.DiskError {
//
//        if (operation == 0) {
//            /* read */
//            return disk.read(sector);
//        } else {
//            /* write */
//            disk.write(sector, value);
//        }
//        return -1;
//    }
//
//    public int read(int sector) throws DiskInterface.DiskError {
//        return doSth(sector, -1, 0);
//    }
//
//    public void write(int sector, int value) throws DiskInterface.DiskError {
//        doSth(sector, value, 1);
//    }
//}
//