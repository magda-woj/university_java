//Magda Wojtowicz - 1
/* 
idea dzialania programu:
program tasuje piosenki z dwoch plyt: na poczatku w tablicy podane sa tytuly piosenek najpierw z pierwszej tablicy, nastepnie z drugiej.
Po zakonczeniu dzialania programu w tablicy wystepuja na zmiane piosenki z obu plyt.
Algorytm dziala w oparciu o rekurencyjna metode "dziel i zwyciezaj" - w jednym wywolaniu zmienia tablice tak by:
w pierwszej czwartej pozostaly ulozone w dobrej kolejnosci piosenki z pierwszej polowy plyty 1;
w drugiej czwartej znalazly sie ulozone piosenki z pierwszej polowy plyty 2;
w trzeciej czwartej znalazly sie ulozone piosenki z drugiej polowy plyty 1;
w czwartej czwartej pozostaly ulozone piosenki z drugiej polowy plyty 2.
Nastepnie wywolujemy funkcje osobno dla pierwszej i drugiej polowy tablicy - ktore sa teraz takiej postaci jak tablica wejsciowa,
tzn w pierwszej polowie znajduja sie piosenki z plyty 1, a w drugiej piosenki z plyty 2;
Tablica jest uznawana za ulozona jesli ma dlugosc 1 lub 2 - konczymy dzialanie funkcji.

zlozonosc pamieciowa jest stala - wszystkie operacje wykonywane sa na oryginalnej tablicy;
zlozonosc czasowa - O(nlog_2(n))
dowodzi tego rownanie rekurencyje:
T(1) = 0; //tablica jednoelementowa jest ulozona
T(n) = O(n) + 2*T(n/2) // ulozenie tablicy dlugosci n to przejscie po 1/4 tablicy swapujac elementy + czas potrzebny na ulozenie dwoch o polowe krotszych tablic
z powyzszego rownania wynika zlozonosc O(nlog_2(n)) (zrodla: wyklad lub https://en.wikipedia.org/wiki/Master_theorem_(analysis_of_algorithms)#Generic_form)

niezmodyfikowana idea dziala jednak jedynie przy dlugosci tablicy podzielnej przez 4 - w przypadkach gdy dlugosc nie jest podzielna przez 4
dzielenie w taki sposob mogloby skutkowac tablicami innymi niz wejsciowe (np byloby wiecej piosenek z plyty drugiej niz z plyty pierwszej)
dlatego w takich przypadkach dajemy koncowe piosenki na swoje miejsca i wywolujemy funkcje dla tablicy podzielnej przez 4 
w zlozonosci zmienia to tyle ze przed "wlasciwym" wywolaniem dajemy elementy wystajace poza dlugosc podzielna przez 4 na dobre miejsca
- dodaje to jedynie stala przy mnozeniu razy n, nie zmienia wiec zlozonosci w notacji O
*/
import java.util.Scanner;


public class Baca6 {
    public StringBuilder sB = new StringBuilder(); //StringBuilder do outputu
    public static Scanner scan = new Scanner(System.in);

    private String[] Songs; //tablica z piosenkiami
    private int n; //liczba piosenek
    String prefix; //najdluzszy wspolny prefix

    Baca6(int size, String s) //konstruktor
    {
        Songs = new String[size];
        n = size;
        Songs = s.split(" ");
        prefix = new String();
        prefix = Songs[0].substring(0);
    }

    void display() //daje output do stringbuildera
    {
        for(int i = 0; i < n; i++)
        {
            sB.append(Songs[i] + " ");
        }
        sB.append("\n");
        sB.append(prefix);
        sB.append("\n");

    }

    void swap(int i, int j) //zamienia miejscami piosenki o indeksach i, j
    {
        String bufor = Songs[i]; 
        Songs[i] = Songs[j]; 
        Songs[j] = bufor; 
    }

    void checkPref(String s) //znajduje wspolny prefix dotychczasowego wspolnego prefixa i danego stringa
    {
        int i;
        for(i = 0; i < s.length() && i < prefix.length() && prefix.charAt(i) == s.charAt(i); i++); //znajdujemy wspolny prefix dotychczasowego prefixu i elementu naszej jednoelementowej tablicy
        prefix = prefix.substring(0, i);
    }

    void Shuffle(int l, int r) //metoda do tasowania piosenek
    {
        if(r - l <= 1) //jesli tablica jest jedno lub dwuelementowa 
        {
            checkPref(Songs[l]);
            checkPref(Songs[r]); //sprawdzamy prefix piosenek znajdujacych sie na dobrych miejscach
            return; //koniec
        }
        if((r-l+1)%4==2) //jesli dlugosc przystaje do 2 modulo 4
        {
            for(int i = l + (r-l)/2; i < r-1; i++) //dajemy ostatnia piosenke z pierwszej plyty na przedostatnie miejsce
            {
                swap(i, i+1);
            }
            checkPref(Songs[r-1]);
            checkPref(Songs[r]); //sprawdzamy prefix piosenek znajdujacych sie na dobrych miejscach
            Shuffle(l, r-2); //"wlasciwe wywolanie" dla tablicy bez dwoch ostatnich elementow
        }
        else if ((r-l+1)%2==1) //jesli dlugosc tablicy jest nieparzysta
        {
            for(int i = l + (r-l)/2; i < r; i++) //dajemy ostatnia piosenke z pierwszej plyty na koniec
            {
                swap(i, i+1);
            } 
            checkPref(Songs[r]); //sprawdzamy prefix piosenek znajdujacych sie na dobrych miejscach
            Shuffle(l, r-1); //"wlasciwe wywolanie" dla tablicy bez ostatniego elementu
        }
        else{
            for(int i = 0; i < (r-l+1)/4; i++) //dla 1/4 dlugosci tablicy
            {
                swap(l + (r-l+1)/4 + i, l + (r-l+1)/2 + i); //zamieniamy piosenki z drugiej polowy pierwszej plyty z tymi z pierszej polowy drugiej plyty
            }
            Shuffle(l, l + (r-l+1)/2 - 1); //tasujemy pierwsza polowe tablicy
            Shuffle(l + (r-l+1)/2, r); //tasujemy druga polowe tablicy
        }
    }

    public static void main( String [] args ) {
        
        int howMany = scan.nextInt();
        for(int i = 0; i < howMany; i++)
        {
            int n = scan.nextInt();
            String s = scan.nextLine();
            s = scan.nextLine();
            Baca6 sou = new Baca6(n, s);
            sou.Shuffle(0, n-1);
            sou.display();
            s = sou.sB.toString();
            System.out.print(s);
        }
        
    }
}
/* testy:
3
4
FleetwoodMacGypsy FleetwoodMacDreams FlorenceSweetNothing FlorenceNoLight
5
Piosenka11 piosenka12 piosenka13 piosenka21 piosenka22
15
p1 p2 p3 p4 p5 p6 p7 p8 p9 p10 p11 p12 p13 p14 p15

poprawne wyjscie:
FleetwoodMacGypsy FlorenceSweetNothing FleetwoodMacDreams FlorenceNoLight
Fl
Piosenka11 piosenka21 piosenka12 piosenka22 piosenka13
p1 p9 p2 p10 p3 p11 p4 p12 p5 p13 p6 p14 p7 p15 p8
p
--------------------------------------------------------------------------

wejscie:
4
1
BamBam
3
KochamMame KochamTate KochamBabcie
5
tak tak tak tak tak
2
tak nie

poprawne wyjscie:
tak nie
BamBam
BamBam
KochamMame KochamBabcie KochamTate
Kocham
tak tak tak tak tak
tak
tak nie 
---------------------------------------------------------------------------

wejscie:
20
1
a1 

2
a1 b1 
3
a1 a2 b1 
4
a1 a2 b1 b2 
5
a1 a2 a3 b1 b2 
6
a1 a2 a3 b1 b2 b3 
7
a1 a2 a3 a4 b1 b2 b3 
8
a1 a2 a3 a4 b1 b2 b3 b4 
9
a1 a2 a3 a4 a5 b1 b2 b3 b4 
10
a1 a2 a3 a4 a5 b1 b2 b3 b4 b5 
11
a1 a2 a3 a4 a5 a6 b1 b2 b3 b4 b5 
12
a1 a2 a3 a4 a5 a6 b1 b2 b3 b4 b5 b6 
13
a1 a2 a3 a4 a5 a6 a7 b1 b2 b3 b4 b5 b6 
14
a1 a2 a3 a4 a5 a6 a7 b1 b2 b3 b4 b5 b6 b7 
15
a1 a2 a3 a4 a5 a6 a7 a8 b1 b2 b3 b4 b5 b6 b7 
16
a1 a2 a3 a4 a5 a6 a7 a8 b1 b2 b3 b4 b5 b6 b7 b8 
17
a1 a2 a3 a4 a5 a6 a7 a8 a9 b1 b2 b3 b4 b5 b6 b7 b8 
18
a1 a2 a3 a4 a5 a6 a7 a8 a9 b1 b2 b3 b4 b5 b6 b7 b8 b9 
19
a1 a2 a3 a4 a5 a6 a7 a8 a9 a10 b1 b2 b3 b4 b5 b6 b7 b8 b9 
20
a1 a2 a3 a4 a5 a6 a7 a8 a9 a10 b1 b2 b3 b4 b5 b6 b7 b8 b9 b10

poprawne wyjscie:
a1 
a1
a1 b1 

a1 b1 a2 

a1 b1 a2 b2 

a1 b1 a2 b2 a3 

a1 b1 a2 b2 a3 b3 

a1 b1 a2 b2 a3 b3 a4 

a1 b1 a2 b2 a3 b3 a4 b4 

a1 b1 a2 b2 a3 b3 a4 b4 a5 

a1 b1 a2 b2 a3 b3 a4 b4 a5 b5 

a1 b1 a2 b2 a3 b3 a4 b4 a5 b5 a6 

a1 b1 a2 b2 a3 b3 a4 b4 a5 b5 a6 b6 

a1 b1 a2 b2 a3 b3 a4 b4 a5 b5 a6 b6 a7 

a1 b1 a2 b2 a3 b3 a4 b4 a5 b5 a6 b6 a7 b7 

a1 b1 a2 b2 a3 b3 a4 b4 a5 b5 a6 b6 a7 b7 a8 

a1 b1 a2 b2 a3 b3 a4 b4 a5 b5 a6 b6 a7 b7 a8 b8 

a1 b1 a2 b2 a3 b3 a4 b4 a5 b5 a6 b6 a7 b7 a8 b8 a9 

a1 b1 a2 b2 a3 b3 a4 b4 a5 b5 a6 b6 a7 b7 a8 b8 a9 b9 

a1 b1 a2 b2 a3 b3 a4 b4 a5 b5 a6 b6 a7 b7 a8 b8 a9 b9 a10 

a1 b1 a2 b2 a3 b3 a4 b4 a5 b5 a6 b6 a7 b7 a8 b8 a9 b9 a10 b10 

*/