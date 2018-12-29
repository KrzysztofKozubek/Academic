import java.util.Random;
import java.util.Vector;

/**
 * Created by Kriss on 12.12.2016.
 */

interface AdPacks {

    int getGivesRevenuesUpto();

    double getCost();

    boolean getWorking();

    double getGeneration();

    double getMaxGeneration();

    void work(double percent);

    double getValue();

    double takeValue(double value);
}

class Voyage implements AdPacks {

    double cost = 55;
    boolean working = true;
    double generated = 0;
    double value = 0;
    double maxGeneration = 0;
    public int GivesRevenuesUpto = 135;

    public Voyage() {
        maxGeneration = GivesRevenuesUpto * cost / 100;
    }

    @Override
    public int getGivesRevenuesUpto() {
        return GivesRevenuesUpto;
    }

    @Override
    public double getCost() {
        return cost;
    }

    @Override
    public boolean getWorking() {
        return working;
    }

    @Override
    public double getGeneration() {
        return generated;
    }

    @Override
    public double getMaxGeneration() {
        return maxGeneration;
    }

    @Override
    public void work(double percent) {
        if (generated >= maxGeneration) return;
        generated += cost * percent / 100;
        value += cost * percent / 100;
        if (generated > maxGeneration) generated = maxGeneration;
        if (generated >= maxGeneration) working = false;
    }

    @Override
    public double getValue() {
        return value;
    }

    @Override
    public double takeValue(double value) {
        if (value > this.value) {
            double result = this.value;
            this.value = 0;
            return result;
        }
        this.value -= value;
        return value;
    }
}

class LongJourney implements AdPacks {

    double cost = 25;
    boolean working = true;
    double generated = 0;
    double value = 0;
    double maxGeneration = 0;
    public int GivesRevenuesUpto = 120;

    public LongJourney() {
        maxGeneration = GivesRevenuesUpto * cost / 100;
    }

    @Override
    public int getGivesRevenuesUpto() {
        return GivesRevenuesUpto;

    }

    @Override
    public double takeValue(double value) {
        if (value > this.value) {
            double result = this.value;
            this.value = 0;
            return result;
        }
        this.value -= value;
        return value;
    }

    @Override
    public double getCost() {
        return cost;
    }

    @Override
    public boolean getWorking() {
        return working;
    }

    @Override
    public double getGeneration() {
        return generated;
    }

    @Override
    public double getMaxGeneration() {
        return maxGeneration;
    }

    @Override
    public void work(double percent) {
        if (generated >= maxGeneration) return;
        generated += cost * percent / 100;
        value += cost * percent / 100;
        if (generated > maxGeneration) generated = maxGeneration;
        if (generated >= maxGeneration) working = false;
    }

    @Override
    public double getValue() {
        return value;
    }

}

class JourneyStarter implements AdPacks {

    double cost = 5;
    boolean working = true;
    double generated = 0;
    double value = 0;
    double maxGeneration = 0;
    public int GivesRevenuesUpto = 110;

    public JourneyStarter() {
        maxGeneration = GivesRevenuesUpto * cost / 100;
    }

    @Override
    public int getGivesRevenuesUpto() {
        return GivesRevenuesUpto;

    }

    @Override
    public double takeValue(double value) {
        if (value > this.value) {
            double result = this.value;
            this.value = 0;
            return result;
        }
        this.value -= value;
        return value;
    }

    @Override
    public double getCost() {
        return cost;
    }

    @Override
    public boolean getWorking() {
        return working;
    }

    @Override
    public double getGeneration() {
        return generated;
    }

    @Override
    public double getMaxGeneration() {
        return maxGeneration;
    }

    @Override
    public void work(double percent) {
        if (generated >= maxGeneration) return;
        generated += cost * percent / 100;
        value += cost * percent / 100;
        if (generated > maxGeneration) generated = maxGeneration;
        if (generated >= maxGeneration) working = false;
    }

    @Override
    public double getValue() {
        return value;
    }

}

public class ZFShare {

    double StartUpCapital = 0; //In $
    int numberDats = 0;
    int periodShow = 0; // In day

    int minDaysEarning = 2;
    int maxDaysEarning = 4;

    int costAdPacks = 0;

    double wallet = 0;
    /*
    version =
        1 = PL
        2 = ENG
     */
    int version = 1;


    Vector<AdPacks> adPackses = new Vector<>();

    public ZFShare(double wallet) {
        this.wallet = wallet;
    }

    void addToWallet(double cash) {
        wallet += cash;
    }

    void takeAll() {
        double result = 0;
        for (int i = 0; i < adPackses.size(); i++)
            result += adPackses.get(i).takeValue(999);

        wallet += result;
    }

    void takeMoneyFromZFShare(double howMany) {

        if (getAllCash() < howMany) return;
        double result = 0;
        int i = 0;
        while (howMany >= result)
            result += adPackses.get(i++).takeValue(howMany - result);

        wallet += result;
    }

    int getNumberJurnerStarter() {
        int result = 0;
        for (int i = 0; i < adPackses.size(); i++) if (adPackses.get(i).getGivesRevenuesUpto() == 110) result++;
        return result;
    }

    int getNumberLongJourney() {
        int result = 0;
        for (int i = 0; i < adPackses.size(); i++) if (adPackses.get(i).getGivesRevenuesUpto() == 120) result++;
        return result;
    }

    int getNumberVoyage() {
        int result = 0;
        for (int i = 0; i < adPackses.size(); i++) if (adPackses.get(i).getGivesRevenuesUpto() == 135) result++;
        return result;
    }

    int getWorkingAdPack() {
        int result = 0;
        for (int i = 0; i < adPackses.size(); i++) {
            if(adPackses.get(i).getWorking()) result++;
        }
        return result;
    }

    double getAllCash() {

        double result = 0;
        for (int i = 0; i < adPackses.size(); i++)
            result += adPackses.get(i).getValue();

        return result;
    }

    double getAllHistory() {

        double result = 0;
        for (int i = 0; i < adPackses.size(); i++)
            result += adPackses.get(i).getGeneration();

        return result;
    }

    void buyAdpack() {

        while (wallet >= 5) {

            if (getNumberJurnerStarter() >= 100 && wallet >= 25) {
                if (getNumberLongJourney() >= 100 && wallet >= 55) {
                    adPackses.add(new Voyage());
                    wallet -= 55;
                    costAdPacks += 55;
                } else {
                    adPackses.add(new LongJourney());
                    wallet -= 25;
                    costAdPacks += 25;
                }
            } else {
                adPackses.add(new JourneyStarter());
                wallet -= 5;
                costAdPacks += 5;
            }
        }
    }

    void work(double numberDays, double periodShow) {

        for (double day = 0; day < numberDays; day++) {

            Random dailyEarning = new Random();
            double percent = 4;//dailyEarning.nextDouble() % (maxDaysEarning - minDaysEarning) + minDaysEarning;
            for (int i = 0; i < adPackses.size(); i++) {
                adPackses.get(i).work(percent);
            }

            if (day % periodShow == 0 && version == 1) {
                System.out.println("Dzień:" + (1 + day) + " Procent dzienny:" + percent + " wallet: " + wallet + " All Cash:" + getAllHistory());
            }
            if (day % periodShow == 0 && version == 2) {
                System.out.println("Days:" + (1 + day) + " All Cash:" + getAllHistory());
            }

            takeAll();
            buyAdpack();
        }
    }

    void work() {


        double tmp, tmp2, dayEarning=0;
        for (double day = 0; day < 60; day++) {
            Random dailyEarning = new Random();
            double percent = 2;//dailyEarning.nextDouble() % (maxDaysEarning - minDaysEarning) + minDaysEarning;
            for (int i = 0; i < adPackses.size(); i++) {
                tmp = adPackses.get(i).getValue();
                adPackses.get(i).work(percent);
                tmp2 = adPackses.get(i).getValue();
                if(adPackses.get(i).getWorking())
                    dayEarning += (tmp2 - tmp);
            }
            //System.out.println("DAYS EARNING:" + dayEarning);
            takeAll();
        }
    }

    void podsumowanie() {

        System.out.println("wallet:" + wallet);
        System.out.println("All cash:" + getAllHistory());
        System.out.println("working adpack:" + getWorkingAdPack());
    }

    public static void main(String[] args) {

        ZFShare zfShare = new ZFShare(50);
        zfShare.buyAdpack();
        zfShare.work(30, 30);
        zfShare.work();
        System.out.println("WALLET:" + zfShare.wallet);
    }
}
