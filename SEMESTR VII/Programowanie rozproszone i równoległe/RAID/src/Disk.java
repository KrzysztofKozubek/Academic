/**
 * Created by Kriss on 14.11.2016.
 */
public class Disk implements DiskInterface {
    @Override
    public void write(int sector, int value) throws DiskError {

    }

    @Override
    public int read(int sector) throws DiskError {
        return 0;
    }

    @Override
    public int size() {
        return 0;
    }
}
