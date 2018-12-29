
public interface RAIDInterface2 {
	/**
	 * Dodaje dyski "fizyczne" do macirzy RAID
	 * Metoda na pewno zostanie wykonana przed innymi metodami tego
	 * samego interfejsu. Jednego z dyskow moze brakowac
	 * (null) - oznacza to, ze ulegl on wymontowaniu z macierzy dyskowej.
	 * 
	 * @param array tablica z dyskami 
	 */
	public void addDisks(DiskInterface2[] array);

	/**
	 * Zlecenie zapisu danej value do sektora sector
	 * dysku logicznego. Zlecenie nie jest natychmiast realizowane
	 * lecz buforowane do czasu fizycznego zmodyfikowania danych.
	 * @param sector sektor dysku logicznego, do ktorego ma nastapic zapis
	 * @param value wartosc do zapisu
	 */
	public void write(int sector, int value);

	/**
	 * Zlecenie odczytu z sektora sector dysku logicznego
	 * @param sector sektor dysku logicznego, ktory ma zostac odczytany
	 * @return wartosc odczytana z sektora
	 */
	public int read(int sector);

	/**
	 * Rozmiar dysku logicznego, ktory powstaje po utworzeniu z dysków 
	 * "fizycznych" jednego dysku logicznego.
	 * @return rozmiar w sektorach dysku logicznego
	 */
	public int size();

	/** 
	 * Zlecenie zakonczenia operacji na dyskach.
	 * Z chwila zakocznia tej metody zadna operacja na powierzonych
	 * dyskach "fizycznych" nie moze juz zostac przeprowadzona 
	 * przez dana instacje obiektu klasy implementujacej usluge.
	 * Po zakonczeniu metody shutdown wszystkie kolejne zlecenia nadchodzace
	 * od uzytkownika maja byc ignorowane i nie powodowac zmian na dysku.
	 * Oznacza to, ze zmiany stanu dyskow fizycznych moga byc realizowane wylacznie
	 * przez inne istancje klasy, ktora zaimplementuje ten interfejs.
	 * 
	 */
	public void shutdown();
	
	/**
	 * Metoda pozwalajaca na sprawdzenie czy dane dla danego
	 * sektora dysku logicznego znajduja sie jeszcze w buforze.
	 * 
	 * @param sector numer sektora na dysku logicznym
	 * @return true - dane dla tego sektora sa w buforze, zawartosc 
	 * sektora nie zostala jeszcze utrwalona na dyskach fizycznych
	 * (zapis danej i zapis sumy kontrolnej). false - nie prowadzone
	 * sa zadne operacje zapisu dotyczace wskazanego sektora dysku logicznego.
	 */
	public boolean sectorInUse( int sector );
	
}
