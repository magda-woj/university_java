// Magda Wojtowicz - 1
/*idea dzialania programu: dana jest tablica nxm, jej zawartosc wczytywana z klawiatury. 
Program wyszukuje ciagla podtablice tej tablicy o maksymalnej sumie elementow, przy czym jesli jest ich kilka 
to wyszukuje podtablice o najmniejszej liczbie elementow, ktorej indeksy i, j, k, l tworza ciag leksykograficznie najmniejszy.

Program wykorzystuje algorytm Kadane wyszukujacy podtablice tablicy 1d, o maksymalnej sumie elementow.
Algorytm Kadane: na poczatku maks suma, tymczasowa suma, poczatek podtablicy i altualny poczatek podtablicy sa rowne 0
idziemy po kolei po elementach tablicy, dodajemy jej elementy do tymczasowej sumy. jesli ktorys element zmniejsza sume do <=0,
to znaczy ze mozemy wszystkie elementy az do niego "odciac", bo na pewno nie zwieksza sumy
jesli natrafimy na tymczasowa sume wieksza od maks sumy, to maks suma = tymczasowa suma

Glowna metoda sumuje dodaje do jednego wiersza kazda mozliwa liczbe wierszy lezacych bezposrednio, ciagle, nad nim
(wtedy suma tego wiersza jest rowna sumie ciaglej prostokatnej podtablicy) i wywoluje na nim algotytm Kadane 1d 
- w ten sposob znajduje ograniczajace podtablice o maks sumie kolumny
*/
import java.util.Scanner;
public class Baca1 {
    public static Scanner scan = new Scanner(System.in); 
    private int[][] A;
    private int _n; // l wierszy
    private int _m; // l kolumn

    public Baca1(int n, int m) //konstruktor tablicy z tresci zadania
    {
        _n = n;
        _m = m;
        A = new int[n][m];
        for(int i = 0; i < n; i++)
        {
            for(int j = 0; j < m; j++)
            {
                A[i][j] = scan.nextInt();
            }
        }
    }

    public long[] kadane(long[] K) // algorytm Kadane dla tablicy jednowymiarowej b - poczatek maks podtablicy e - koniec maks podtablicy
    {
        int max = 0; //to bedzie suma maksymalnej podtablicy
        int b = 0; // to bedzie poczatek maks podtablicy
        int e = 0; // -,,- koniec -,,-
        int currmax = 0; // tymczasowa maks wartosc sumy el, zalezna od tego gdzie jest aktualny poczatek -
        int currb = 0; // aktualny poczatek s
        int isEmpty = 1; // czy maks podtablica jest pusta - na poczatku tak
        for(int i = 0; i < K.length; i++) // jedna petla po tabl - zlozonosc liniowa
        {
            currmax += K[i]; // dodajemy kolejne el tablicy do sumy tymczasowej
            if(currmax == 0 && isEmpty == 1) // test na tablice z samymi ujemnymi i zerami currmax = 0 tylko, kiedy el na ktorym aktualnie jestesmy to 0; isEmpty = 1 bo chcemy znalezc pierwsze takie wystapienie - test na tablice z samymi ujemnymi i zerami
            {
                isEmpty = 0; // w tablicy jest l nieujemna - 0, wiec podtablica nie bedzie pusta
                b = i; // chcemy miec jednoelementowa podtablice z tym zerem na ktorym teraz jestesmy (bo chcemy jak najmniejsza podtablice)
                e = i;
            }
            if(currmax <= 0) // jesli po dodaniu nastepnego elementu suma jest niedodatnia, to znaczy ze mozemy "odciac" el wchodzace w jej sklad bo na pewno nie zwieksza dalszej sumy, chcemy odciac tez = 0, bo chcemy jak najmniejsza podtablice
            {
                currmax = 0; // zerujemy tymczasowa maks wartosc sumy
                currb = i+1; // przestawiamy aktualny poczatek na nastepny indeks tablicy
            }
            else if(currmax > max) // jesli tymczasowa maks wartosc jest wieksza niz dotychczasowo maks wartosc
            {
                max = currmax; // maks wartosc sumy = tymczasowa maks wartosc sumy
                b = currb; // poczatek maks podtablicy = aktualny poczatek
                e = i; // koniec maks podtablicy - indeks na ktorym aktualnie jestesmy
                isEmpty = 0; // tymczasowa maks wartosc jest wieksza niz ogolna, wiec maks podtablica na pewno nie jest pusta
            }
            else if((currmax == max) && (i - currb < e - b)) // chcemy jak najkrotsza tablice
            {
                max = currmax; // maks wartosc sumy = tymczasowa maks wartosc sumy
                b = currb; // poczatek maks podtablicy = aktualny poczatek
                e = i; // koniec maks podtablicy - indeks na ktorym aktualnie jestesmy
                isEmpty = 0; // tymczasowa maks wartosc jest rowna ogolnej, wiec maks podtablica na pewno nie jest pusta
            }
        }
        long[]R = new long[4];
        R[0] = max;
        R[1] = b;
        R[2] = e;
        R[3] = isEmpty;
        if(isEmpty == 1) // chwilowo przy pustej tablicy zwracamy ujemna sume, zeby potem w glownej sytuacji podtablica z jednym zerem wygrywala z pusta podtablica
        {
            R[0] = -1;
        }
        return R; //zwracamy maks wartosc sumy po przejsciu calej tablicy, poczatek i koniec podtablicy oraz czy podtablica jest pusta
    }

    public long[] baca() //glowna metoda
    {
        long i = 0;
        long j = 0; 
        long k = 0; 
        long l = 0; // indeksy ogr maks podtablice - jak w tresci zadania
        long max = -1; // maks suma
        long isEmpty = 1;

        for(int r1 = 0; r1 < _n; r1++) // idziemy po wierszach zmienna r1
        {
            long[] K = new long[_m]; // tworzymy pomocnicza tabl jednowymiarowa o dlugosci rownej liczbie kolumn
            for(int r2 = r1; r2 < _n; r2++) // idziemy po wierszach zmienna r2 - czyli zlozonosc jak na razie O(n^2)
            {
                for(int c = 0; c < _m; c++) // idziemy po kolumnach - zlozonosc O(m)
                {
                    K[c] += A[r2][c]; //do pomocniczej tabl sumujemy kolumny w rzedach miedzy r1 a r2 
                }
                long [] Currmax = new long[4];
                Currmax = kadane(K); // kadane - zlozonosc O(m)
                if(isEmpty == 1) //jezeli Currmax[3] kiedykolwiek jest 0 to chcemy zmienic isEmpty na 0 i wiecej juz nie zminiac
                {
                    isEmpty = Currmax[3];
                }
                if(Currmax[0] > max) //jezeli maks suma podtablicy tablicy przekazanej do kadane - rowna maks sumie podtablicy tablicy skladajacej sie z rzedow miedzy r1 a r2 wlacznie, jest wieksza od dotychczasowej maks sumy, to zmieniamy dotychczasowa maks sume oraz i, j, k, l
                {
                    max = Currmax[0];
                    i = r1;
                    j = r2;
                    k = Currmax[1];
                    l = Currmax[2];
                }
                else if((Currmax[0] == max) && (r2-r1+1)*(Currmax[2]-Currmax[1]+1) < (j-i+1)*(l-k+1))//jezeli odpowiednia suma jest rowna, a tablica ma mniej elementow to zamieniamy jw
                {
                    max = Currmax[0];
                    i = r1;
                    j = r2;
                    k = Currmax[1];
                    l = Currmax[2];
                }
                else if((Currmax[0] == max) && (r2-r1+1)*(Currmax[2]-Currmax[1]+1) == (j-i+1)*(l-k+1))
                {
                    if((r1 < i) || ((r1 == i) && (r2 < j)) || ((r1 == i) && (r2 == j) && (Currmax[1] < k)) || ((r1 == i) && (r2 == j) && (Currmax[1] == k) && (Currmax[2] < l)))
                    {
                        i = r1;
                        j = r2;
                        k = Currmax[1];
                        l = Currmax[2];
                    }
                }
            } // jezeli odp suma jest rowna, tabl maja tyle samo elementow, ale wierzcholki nowej twarza leksykograficznie mniejszy ciag, to zamieniamy odpowiednio
        }

        long[] R = new long[6];
        R[0] = max;
        R[1] = i;
        R[2] = j;
        R[3] = k;
        R[4] = l;
        R[5] = isEmpty;
        return R; //zwracamy maksymalna sume podtablicy, jej wspolrzedne i czy jest pusta
    }

    
    public static void main( String [] args ) {
        int howMany = scan.nextInt();
        for(int i = 0; i < howMany; i++)
        {
            scan.next();
            scan.next();
            int n = scan.nextInt();
            int m = scan.nextInt(); //wczytujemy wielkosc tablicy
    
            Baca1 so = new Baca1(n, m);
            long[]R = new long[6];
            R = so.baca();
            if(R[5]==1)
            {
                System.out.println((i+1) + ": " + "n = " + n + " m = " + m + ", s = 0"  + ", mst is empty");
            }
            else
            {
                System.out.println((i+1) + ": " + "n = " + n + " m = " + m + ", s = " + R[0] + ", mst = a[" + R[1] + ".." + R[2] + "][" + R[3] + ".." + R[4] +"]");
            }
        }

    }
}
/*
testy:
wejscie:
5
1 : 4 4
1 1 -1 -1
-1 -1 0 0
2 0 0 0
0 0 0 0
2 : 3 3
-1 -2 -3
-4 -5 -100
-2 -3 -4
3 : 5 5
1 1 1 1 1
1 1 1 1 1
0 0 0 0 0
-10 -1 -10 -4 -9   
10 0 0 0 10
4 : 5 5
1 1 1 1 1
1 1 1 1 1
0 0 0 0 0
-9 -9 -9 -9 -9
10 -10 -10 0 10
5 : 3 3
-4 -5 -8
-1 -1 -1
-4 -6 0

4
1 : 1 6
0 0 0 0 0 0
2 : 1 6
-1 -2 -3 -4 -5 -6
3 : 1 6
-1 -2 -3 -4 -5 0
4 : 1 6
1 1 -1 -1 4 2

3
1 : 2 1
-1
0
2 : 2 1
-1 
-100
3 : 2 1
1
1

poprawne wyjscie:
1: n = 4 m = 4, s = 2, mst = a[2..2][0..0]
2: n = 3 m = 3, s = 0, mst is empty
3: n = 5 m = 5, s = 20, mst = a[4..4][0..4]
4: n = 5 m = 5, s = 10, mst = a[4..4][0..0]
5: n = 3 m = 3, s = 0, mst = a[2..2][2..2]

1: n = 1 m = 6, s = 0, mst = a[0..0][0..0]
2: n = 1 m = 6, s = 0, mst is empty
3: n = 1 m = 6, s = 0, mst = a[0..0][5..5]
4: n = 1 m = 6, s = 6, mst = a[0..0][4..5]

1: n = 2 m = 1, s = 0, mst = a[1..1][0..0]
2: n = 2 m = 1, s = 0, mst is empty
3: n = 2 m = 1, s = 2, mst = a[0..1][0..0]
*/