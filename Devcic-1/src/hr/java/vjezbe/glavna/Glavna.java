package hr.java.vjezbe.glavna;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Scanner;

import hr.java.vjezbe.entitet.Artikl;
import hr.java.vjezbe.entitet.Kategorija;
import hr.java.vjezbe.entitet.Korisnik;
import hr.java.vjezbe.entitet.Prodaja;

public class Glavna {
    private static final String FORMAT_DATUMA = "dd.MM.yyyy.";

    public static void main(String[] args) {

	Scanner ucitavac = new Scanner(System.in);

	System.out.print("Unesite broj korisnika koje želite unijeti: ");
	int brojKorisnika = ucitavac.nextInt();
	ucitavac.nextLine();
	Korisnik[] korisnici = new Korisnik[brojKorisnika];
	for (int i = 0; i < brojKorisnika; i++) {
	    System.out.println("Unos " + (i + 1) + ". korisnika");
	    korisnici[i] = unesiKorisnika(ucitavac, i + 1);
	}

	System.out.print("Unesite broj kategorija koje želite unijeti: ");
	int brojKategorija = ucitavac.nextInt();
	ucitavac.nextLine();
	Kategorija[] kategorije = new Kategorija[brojKategorija];

	for (int k = 0; k < kategorije.length; k++) {
	    kategorije[k] = unesiKategoriju(ucitavac, k);

	}
	System.out.print("Unesite broj artikala koji su aktivno na prodaju: ");
	int brojAktivnihOglasa = ucitavac.nextInt();
	ucitavac.nextLine();
	Prodaja[] aktivneProdaje = new Prodaja[brojAktivnihOglasa];

	for (int i = 0; i < aktivneProdaje.length; i++) {
	    aktivneProdaje[i] = unesiProdaju(ucitavac, i, korisnici, kategorije);
	}

	ucitavac.close();

	System.out.println("Trenutno su oglasi na prodaju:");
	for (Prodaja aktivnaprodaja : aktivneProdaje) {
	    System.out.println("Naslov: " + aktivnaprodaja.getArtikl().getNaslov());
	    System.out.println("Opis: " + aktivnaprodaja.getArtikl().getOpis());
	    System.out.println("Cijena: " + aktivnaprodaja.getArtikl().getCijena());
	    LocalDate datumObjave = aktivnaprodaja.getDatumObjave();
	    String datumObjaveString = datumObjave.format(DateTimeFormatter.ofPattern(FORMAT_DATUMA));
	    System.out.println("Datum objave: " + datumObjaveString);
	    System.out.println("Kontakt podaci: " + aktivnaprodaja.getKorisnik().getIme() + " "
		    + aktivnaprodaja.getKorisnik().getPrezime() + ", mail: " + aktivnaprodaja.getKorisnik().getEmail()
		    + ", tel: " + aktivnaprodaja.getKorisnik().getTelefon());
	}
    }

    private static Prodaja unesiProdaju(Scanner ucitavac, int i, Korisnik[] korisnici, Kategorija[] kategorije) {
	Integer redniBrojKorisnika = 0;
	System.out.println("Odaberite korisnika: ");
	for (int j = 0; j < korisnici.length; j++) {
	    System.out.println((j + 1) + ". " + korisnici[j].getIme() + " " + korisnici[j].getPrezime());
	}
	System.out.print("Odabir korisnika >> ");
	redniBrojKorisnika = ucitavac.nextInt();
	ucitavac.nextLine();
	Korisnik odabraniKorisnik = korisnici[redniBrojKorisnika - 1];

	Integer redniBrojKategorije = 0;
	System.out.println("Odaberite kategoriju: ");
	for (int j = 0; j < kategorije.length; j++) {
	    System.out.println((j + 1) + ". " + kategorije[j].getNaziv());
	}
	System.out.print("Odabir kategorije >> ");
	redniBrojKategorije = ucitavac.nextInt();
	ucitavac.nextLine();
	Kategorija odabranaKategorija = kategorije[redniBrojKategorije - 1];

	Artikl[] artikliKategorije = new Artikl[kategorije.length];
	artikliKategorije = odabranaKategorija.getArtikli();
	Integer redniBrojArtikla = 0;

	System.out.println("Odaberite artikl:");
	for (int j = 0; j < odabranaKategorija.getArtikli().length; j++) {
	    System.out.println((j + 1) + ". " + artikliKategorije[j].getNaslov());
	}
	System.out.print("Odabir artikla >> ");
	redniBrojArtikla = ucitavac.nextInt();
	ucitavac.nextLine();
	Artikl odabraniArtikl = artikliKategorije[redniBrojArtikla - 1];

	LocalDate datumObjave = LocalDate.now();
	return new Prodaja(odabraniArtikl, odabraniKorisnik, datumObjave);
    }

    private static Artikl unesiArtikl(Scanner ucitavac, int j) {
	System.out.print("Unesite naslov " + (j + 1) + ". oglasa artikla: ");
	String naslov = ucitavac.nextLine();
	System.out.print("Unesite opis " + (j + 1) + ". oglasa artikla: ");
	String opis = ucitavac.nextLine();
	System.out.print("Unesite cijenu " + (j + 1) + ". oglasa artikla: ");
	BigDecimal cijena = ucitavac.nextBigDecimal();
	ucitavac.nextLine();
	return new Artikl(naslov, opis, cijena);
    }

    private static Kategorija unesiKategoriju(Scanner ucitavac, int i) {
	System.out.print("Unesite naziv " + (i + 1) + ". kategorije: ");
	String naziv = ucitavac.nextLine();
	naziv = naziv.substring(0, 1).toUpperCase() + naziv.substring(1).toLowerCase();
	System.out.print("Unesite broj artikala koji želite unijeti za unesenu kategoriju: ");
	int brojArtikalaKategorije = ucitavac.nextInt();
	ucitavac.nextLine();
	Artikl[] artikliKategorije = new Artikl[brojArtikalaKategorije];
	for (int j = 0; j < artikliKategorije.length; j++) {
	    artikliKategorije[j] = unesiArtikl(ucitavac, j);
	}
	return new Kategorija(naziv, artikliKategorije);
    }

    private static Korisnik unesiKorisnika(Scanner ucitavac, int brojKorisnika) {
	System.out.print("Unesite ime korisnika: ");
	String ime = ucitavac.nextLine();
	ime = ime.substring(0, 1).toUpperCase() + ime.substring(1).toLowerCase();
	System.out.print("Unesite prezime korisnika: ");
	String prezime = ucitavac.nextLine();
	prezime = prezime.substring(0, 1).toUpperCase() + prezime.substring(1).toLowerCase();
	System.out.print("Unesite email korisnika: ");
	String email = ucitavac.nextLine();
	System.out.print("Unesite telefon korisnika: ");
	String telefon = ucitavac.nextLine();
	return new Korisnik(ime, prezime, email, telefon);
    }

}
