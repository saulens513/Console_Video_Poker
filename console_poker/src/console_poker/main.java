package console_poker;

import java.util.ArrayList;
import java.util.Collections;
import java.util.InputMismatchException;
import java.util.Scanner;

class korta implements Comparable<korta> {

	private String zenklas;
	private String skaicius;
	private int verte;

	public korta(String kZenklas, String kSkaicius, int kVerte) {
		zenklas = kZenklas;
		skaicius = kSkaicius;
		verte = kVerte;
	}

	public int getVerte() {
		return verte;
	}

	public String getZenklas() {
		return zenklas;
	}

	public String getSkaicius() {
		return skaicius;
	}

//reikia pasidaryti lyginima kad galeciau surusiuot kortas rankoje
	@Override
	public int compareTo(korta o) {
		return this.getVerte() - o.getVerte();
	}

}

public class main {
	public static void main(String[] args) {
		Scanner pNuskaitymas = new Scanner(System.in);
		int pasirinkimas;
		int kIsmesta = 10;
		Boolean bMeniu = true;
		do {
			System.out.println("Paspausk 1 kad zaisti, bet ka, kad iseiti.");
			// gaunam pasirinkima
			try {
				pasirinkimas = pNuskaitymas.nextInt();
			} catch (InputMismatchException e) {// kad nenuluztu jei ne skaiciu paspaudziau
				pasirinkimas = 0;
			}
			switch (pasirinkimas) {
			case 1:
				// kalade ir zaidejo kortos
				ArrayList<korta> kalade = new ArrayList<korta>();
				ArrayList<korta> kRankose = new ArrayList<korta>();
				kalade = nKalade();
				// duodamos kortos, isimamos is kalades
				for (int i = 0; i < 5; i++) {
					kRankose.add(kalade.get(0));
					kalade.remove(0);
				}
				rodyt_kortas(kRankose);
				System.out.println("\nKa noretumete daryti:\n" + "1.Zaisti \n2.Ismesti korta(-as)");
				pasirinkimas = pNuskaitymas.nextInt();
				if (pasirinkimas == 1) {// suskaiciuojami taskai
					skaiciuoti_taskus(kRankose);
					break;
				} else if (pasirinkimas == 2) {
					System.out.println("\nKiek kortu noretumete ismesti? (1-5), 0-atsaukti");
					kIsmesta = pNuskaitymas.nextInt();
					if (kIsmesta == 5) {// jeigu pasirinkau ismest 5 kortas, meta visas automatiskai
						System.out.println("Ismetamos visos kortos...");
						for (int i = 0; i < 5; i++) {
							kRankose.set(i, new korta("-", "-", 0));// dedu tuscias kortas, jas poto uzpildysiu naujomis
							System.out.println("Ismeta korta");
						}
						rodyt_kortas(kRankose);
					} else if (kIsmesta == 0) {
						skaiciuoti_taskus(kRankose);// skaiciuojam rezultata, jeigu persigalvojo ismest kortas
						break;
					} else {
						while (kIsmesta < 1 || kIsmesta > 5) {// kol neivesiu teisingo skaiciaus prasys ivest teisinga
							System.out.println("Ivedete netinkama skaiciu.Bandykite is naujo.");
							System.out.println("\nKiek kortu noretumete ismesti? (1-5), 0-atsaukti");
							kIsmesta = pNuskaitymas.nextInt();
							if (kIsmesta == 0) {
								skaiciuoti_taskus(kRankose);// skaiciuojam rezultata, jeigu persigalvojo ismest kortas
								break;
							}

						}
						for (int i = 0; i < kIsmesta; i++) {
							System.out.println("\nKuria korta norite ismesti?, 0-atsaukti");
							pasirinkimas = pNuskaitymas.nextInt();
							if (pasirinkimas == 0) {
								break;
							}
							if (kRankose.get(pasirinkimas - 1).getVerte() == 0) {
								System.out.println("Si korta jau yra ismesta, pasirinkite kita, 0-atsaukti");
								i--;// kad neeitu tolyn ciklas kol neivesiu tinkamo skaiciaus
							} else {
								// ikisu tuscia korta kad nepajudetu pozicija ir neklaidintu norint ismest
								// kelias kortas
								kRankose.set(pasirinkimas - 1, new korta("-", "-", 0));
								rodyt_kortas(kRankose);
							}
						}
					}
					// dabar isvalome tuscias vietas ir uzpildome naujomis kortomis
					for (int i = 0; i < 5; i++) {
						if (kRankose.get(i).getVerte() == 0) {// jei tuscia korta (nes ismeciau)
							System.out.println("\nPaemete korta: " + kalade.get(0).getZenklas() + " "
									+ kalade.get(0).getSkaicius());
							kRankose.set(i, kalade.get(0));// paimama korta nuo kalades virsaus
							kalade.remove(0);
						}
					}
					skaiciuoti_taskus(kRankose);// skaiciuojam rezultata
					break;
				}

			default:
				bMeniu = false;// paspaudus ne vieneta uzdarys meniu
			}
		} while (bMeniu == true);// kai paspausiu ne 1, man baigsis meniu ir uzdarys zaidima
		pNuskaitymas.close();
		System.out.println("Aciu uz zaidima!");
	}

	// funkcija kuri isveda dabar turimas kortas
	public static void rodyt_kortas(ArrayList<korta> hand) {
		System.out.println("\nJusu kortos: ");
		for (korta k : hand) {
			if (k.getVerte() == 0) {
				System.out.println("-----");
			} else {
				System.out.println(k.getZenklas() + " " + k.getSkaicius() + "(" + k.getVerte() + ")");
				// kad nerodytu kortos skaitines vertes galima naudoti sitaip
				// System.out.println(k.getZenklas() + " " + k.getSkaicius());
			}
		}
	}

	// funkcija kuri sukurs mum nauja kalade
	public static ArrayList<korta> nKalade() {
		ArrayList<korta> kalade = new ArrayList<korta>();
		// spades
		kalade.add(new korta("Spades", "2", 2));
		kalade.add(new korta("Spades", "3", 3));
		kalade.add(new korta("Spades", "4", 4));
		kalade.add(new korta("Spades", "5", 5));
		kalade.add(new korta("Spades", "6", 6));
		kalade.add(new korta("Spades", "7", 7));
		kalade.add(new korta("Spades", "8", 8));
		kalade.add(new korta("Spades", "9", 9));
		kalade.add(new korta("Spades", "10", 10));
		kalade.add(new korta("Spades", "J", 11));
		kalade.add(new korta("Spades", "Q", 12));
		kalade.add(new korta("Spades", "K", 13));
		kalade.add(new korta("Spades", "T", 14));
		// hearts
		kalade.add(new korta("Hearts", "2", 2));
		kalade.add(new korta("Hearts", "3", 3));
		kalade.add(new korta("Hearts", "4", 4));
		kalade.add(new korta("Hearts", "5", 5));
		kalade.add(new korta("Hearts", "6", 6));
		kalade.add(new korta("Hearts", "7", 7));
		kalade.add(new korta("Hearts", "8", 8));
		kalade.add(new korta("Hearts", "9", 9));
		kalade.add(new korta("Hearts", "10", 10));
		kalade.add(new korta("Hearts", "J", 11));
		kalade.add(new korta("Hearts", "Q", 12));
		kalade.add(new korta("Hearts", "K", 13));
		kalade.add(new korta("Hearts", "T", 14));
		// clubs
		kalade.add(new korta("Clubs", "2", 2));
		kalade.add(new korta("Clubs", "3", 3));
		kalade.add(new korta("Clubs", "4", 4));
		kalade.add(new korta("Clubs", "5", 5));
		kalade.add(new korta("Clubs", "6", 6));
		kalade.add(new korta("Clubs", "7", 7));
		kalade.add(new korta("Clubs", "8", 8));
		kalade.add(new korta("Clubs", "9", 9));
		kalade.add(new korta("Clubs", "10", 10));
		kalade.add(new korta("Clubs", "J", 11));
		kalade.add(new korta("Clubs", "Q", 12));
		kalade.add(new korta("Clubs", "K", 13));
		kalade.add(new korta("Clubs", "T", 14));
		// diamonds
		kalade.add(new korta("Diamonds", "2", 2));
		kalade.add(new korta("Diamonds", "3", 3));
		kalade.add(new korta("Diamonds", "4", 4));
		kalade.add(new korta("Diamonds", "5", 5));
		kalade.add(new korta("Diamonds", "6", 6));
		kalade.add(new korta("Diamonds", "7", 7));
		kalade.add(new korta("Diamonds", "8", 8));
		kalade.add(new korta("Diamonds", "9", 9));
		kalade.add(new korta("Diamonds", "10", 10));
		kalade.add(new korta("Diamonds", "J", 11));
		kalade.add(new korta("Diamonds", "Q", 12));
		kalade.add(new korta("Diamonds", "K", 13));
		kalade.add(new korta("Diamonds", "T", 14));
		// maisoma ir grazinama kalade
		Collections.shuffle(kalade);
		return kalade;
	}

	// funkcija kuri paskaiciuos musu score
	public static void skaiciuoti_taskus(ArrayList<korta> hand) {
		int score = 0;
		Collections.sort(hand);
		System.out.println("\nRikiuojamos kortos...");
		rodyt_kortas(hand);
		if (hand.get(0).getVerte() == 10 && hand.get(1).getVerte() == 11 && hand.get(2).getVerte() == 12
				&& hand.get(3).getVerte() == 13 && hand.get(4).getVerte() == 14
				&& hand.get(0).getZenklas().equals(hand.get(1).getZenklas())
				&& hand.get(1).getZenklas().equals(hand.get(2).getZenklas())
				&& hand.get(2).getZenklas().equals(hand.get(3).getZenklas())
				&& hand.get(3).getZenklas().equals(hand.get(4).getZenklas())) {
			System.out.println("\nRoyal flush found");
			score = 800;// royal flush

		} else if (hand.get(0).getZenklas().equals(hand.get(1).getZenklas())
				&& hand.get(1).getZenklas().equals(hand.get(2).getZenklas())
				&& hand.get(2).getZenklas().equals(hand.get(3).getZenklas())
				&& hand.get(3).getZenklas().equals(hand.get(4).getZenklas())
				&& hand.get(0).getVerte() == hand.get(1).getVerte() - 1
				&& hand.get(1).getVerte() == hand.get(2).getVerte() - 1
				&& hand.get(2).getVerte() == hand.get(3).getVerte() - 1
				&& hand.get(3).getVerte() == hand.get(4).getVerte() - 1) {
			System.out.println("\nStraight flush found");
			score = 50;// straight flush

		} else if (hand.get(0).getVerte() == hand.get(1).getVerte() && hand.get(1).getVerte() == hand.get(2).getVerte()
				&& hand.get(2).getVerte() == hand.get(3).getVerte() || // X X X X Y
				hand.get(1).getVerte() == hand.get(2).getVerte() && hand.get(2).getVerte() == hand.get(3).getVerte()
						&& hand.get(3).getVerte() == hand.get(4).getVerte()) { // Y X X X X
			System.out.println("\nFour of a kind found");
			score = 25;// four of a kind, gali buti pvz X X X X Y arba Y X X X X, kai isrikiavau
			// pvz 4 4 4 4 T arba 6 8 8 8 8

		} else if (hand.get(0).getVerte() == hand.get(1).getVerte() && hand.get(1).getVerte() == hand.get(2).getVerte()
				&& hand.get(3).getVerte() == hand.get(4).getVerte() || // X X X Y Y
				hand.get(0).getVerte() == hand.get(1).getVerte() && hand.get(2).getVerte() == hand.get(3).getVerte()
						&& hand.get(3).getVerte() == hand.get(4).getVerte()) { // X X Y Y Y
			System.out.println("\nFull house found");
			score = 9;// full house, isrikiavus didejimo seka pora ir trejetukas bus pirmas arba pora
			// pvz 2 2 2 J J arba Q Q T T T

		} else if (hand.get(0).getZenklas().equals(hand.get(1).getZenklas())
				&& hand.get(1).getZenklas().equals(hand.get(2).getZenklas())
				&& hand.get(2).getZenklas().equals(hand.get(3).getZenklas())
				&& hand.get(3).getZenklas().equals(hand.get(4).getZenklas())) {
			System.out.println("\nFlush found");
			score = 6;// flush tikrina ar visu kortu zenklai vienodi

		} else if (hand.get(0).getVerte() == hand.get(1).getVerte() - 1
				&& hand.get(1).getVerte() == hand.get(2).getVerte() - 1
				&& hand.get(2).getVerte() == hand.get(3).getVerte() - 1
				&& hand.get(3).getVerte() == hand.get(4).getVerte() - 1) {
			System.out.println("\nStraight found");
			score = 4;// straight, jeigu pirmo verte yra lygi antro vertei - 1, ir t.t., iseina kad
						// dideja po viena

			// X X X Y Z
		} else if (hand.get(0).getVerte() == hand.get(1).getVerte() && hand.get(1).getVerte() == hand.get(2).getVerte()
				// Y X X X Z
				|| hand.get(1).getVerte() == hand.get(2).getVerte() && hand.get(2).getVerte() == hand.get(3).getVerte()
				// Y Z X X X
				|| hand.get(2).getVerte() == hand.get(3).getVerte()
						&& hand.get(3).getVerte() == hand.get(4).getVerte()) {
			score = 3;// three of a kind, pvz 2 2 2 5 7, 2 5 5 5 7, 2 5 7 7 7

			// X X Y Y -
		} else if (hand.get(0).getVerte() == hand.get(1).getVerte() && hand.get(2).getVerte() == hand.get(3).getVerte()
				// X X - Y Y
				|| hand.get(0).getVerte() == hand.get(1).getVerte() && hand.get(3).getVerte() == hand.get(4).getVerte()
				// - X X Y Y
				|| hand.get(1).getVerte() == hand.get(2).getVerte()
						&& hand.get(3).getVerte() == hand.get(4).getVerte()) {
			System.out.println("\nTwo pair found");
			score = 2;// two pair, pvz 2 2 5 5 7, 2 2 5 7 7, 2 5 5 7 7
		} else if ((hand.get(0).getVerte() == hand.get(1).getVerte() && hand.get(0).getVerte() >= 11) || // X X - - -
				(hand.get(1).getVerte() == hand.get(2).getVerte() && hand.get(1).getVerte() >= 11) || // - X X - -
				(hand.get(2).getVerte() == hand.get(3).getVerte() && hand.get(2).getVerte() >= 11) || // - - X X -
				(hand.get(3).getVerte() == hand.get(4).getVerte() && hand.get(3).getVerte() >= 11)) { // - - - X X
			System.out.println("\nJacks of better found");
			score = 1;// Jacks or better, pvz J J Q K T, 10 J J K T, 10 J Q Q T, 7 8 10 T T
		} else
			score = 0;// nerA laimejimu
		System.out.println("\nJus surinkote: " + score + " tasku. Sekmes kita kart!\n");
	}

}
