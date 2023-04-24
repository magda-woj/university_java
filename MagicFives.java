//Magda Wojtowicz - 1
/*
idea dzialania programu: program znajduje k-ty najmniejszy element w tablicy (liczac od 1). Wykorzystuje do tego algorytm magicznych piatek
(mediane median) oraz algorytm quickselect z funkcja partition.
Partition - mamy element dzielacy (pivot). Porownujemy z nim elementy i w pierwszej czesci tablicy dajemy elementy mniejsze od pivota,
nastepnie pivot a potem od niego wieksze. Zwracamy indeks pivota.
Quickselect - jesli indeks z partition to k-1 to zwracamy go. jesli jest za duzy, to wywolujemy select na pierwszej czesci tablicy,
jesli za maly to na drugiej (zmniejszajac k o (dlugosc pierwszej tablicy+1))
Magiczne piatki - pomaga wybrac pivot. Dzielimy tablice na 5-cio elementowe kawalki, z kazdego wybieramy mediane. nastepnie wywolujemy
select na tych medianach, zeby dostac mediane median. Mediana median to nasz pivot.

Zlozonosc pamieciowa - stala (oprocz stosu wywolan rekurencyjnych funkcji). Dzialamy na jednej tablicy, jedynie zamieniajac elementy.
Nie ma dodatkowych tablic, stosow, list ani nic podobnego.

Zlozonosc czasowa - liniowa
Rownanie dla zlozonosci pesymistycznej:
Dla malych podzadan n < p: T(n) <= c1*n, c1 - stala
dla n >= p: T(n) <= T(n/5) + T((3n)/4) + c2*n, c2 - stala, bo: wyznaczenie mediany median to zadanie wielkosci n/5,
rekurencyjne wywolanie funkcji select na zbiorze wielkosci nie wiekszej niz 3/4*n, poniewaz
co najmniej 1/4 elementow tablicy jest mniejsza lub rowna od pivota oraz co najmniej 1/4 elementow jest od niego wieksza lr - gwarantuje to wybor 
pivota jako mediany median - pivot jest wiekszy lr od polowy median, a kazda mediana wieksza lr od polowy "swoich" elementow - wiec pivot jest wiekszy lr od co najmniej 1/4 elementow
analogicznie jest mniejszy lr od 1/4 elementow
partition i sortowanie piatek ma zlozonosc liniowa dla stalej c2.
*/

import java.util.Scanner;
public class MagicFives {
    public static Scanner scan = new Scanner(System.in);

    private int[] A; // tablica
    private int n; // rozmiar tablicy

    public MagicFives(int size) //konstruktor
    {
            n = size;
            A = new int[size];
            for(int j = 0; j < n; j++)
            {
                A[j] = scan.nextInt();
            }
    }

    void swap(int i, int j) //zamienia elementy tablicy o indeksach i oraz j
    {
        int bufor = A[i]; 
        A[i] = A[j]; 
        A[j] = bufor; 
    }

    void Insertion(int l, int r) //insertion sort - argumenty to poczatek i koniec czesci tablicy ktora sortujemy
    {
        for(int i = l+1; i <= r; i++) //idziemy po tablicy
        {
            int k = A[i], j = i-1; //bierzemy k
            while(j >= l && A[j] > k)  //szukamy miejsca w ktorym powinnismy wsadzic k
            {
                A[j+1] = A[j]; //przesuwamy wszystkie el w prawo
                j--;
            }
            A[j+1] = k; //dajemy k na miejsce
        }
    }

    int InsertionMedian(int l, int r)//insertion sort + zwraca mediane sortowanego kawalka tablicy
    {
        for(int i = l+1; i <= r; i++) //idziemy po tablicy
        {
            int k = A[i], j = i-1; //bierzemy k
            while(j >= l && A[j] > k)  //szukamy miejsca w ktorym powinnismy wsadzic k
            {
                A[j+1] = A[j]; //przesuwamy wszystkie el w prawo
                j--;
            }
            A[j+1] = k; //dajemy k na miejsce
        }
        return  (l+r)/2; //zwroc mediane
    }

    public int partition(int l, int r, int pivind) //l - poczatek tab, r - koniec tab, pivotind - indeks elementu dzielacego
    {
        int pivot = A[pivind];
        swap(pivind, r); //najpierw dajemy el dzielacy na koniec
        int j = l;
        for (int i = l; i <= r; i++) //idziemy po tablicy
        {
            if (A[i] < pivot) //jesli mamy element mniejszy od dzielacego
            {
                swap(i, j); //to zamieniamy z wiekszym od dzielacego
                j++; 
            }
        }
        swap(r, j); //dajemy element dzielacy na swoje miejsce
        return j; //i zwracamy jego indeks
    }

    public int pivot(int l, int r, int p) //funkcja wyznaczajaca indeks elementu dzielacego w partition (pivot)
    {
        for(int i = l; i <= r; i = i+5) //na kawalkach tablicy o dlugosci 5
        {
            int curr = i + 4; //koniec kawalka ktorym sie zajmujemy
            if(curr > r) //jesli ostatni kawalek jest krotszy niz 5, to musimy zmienic jego koniec na dobry
                curr = r;
            int median = InsertionMedian(i, curr); //sortujemy kawalek i zwracamy jego mediane
            swap(median, l + (i-l)/5); //dajemy mediany na poczatek tablicy (mediane n-tego kawalka na n-te miejsce w tablicy)
        }
        int k = (r-l)/10 + 1; // wyznaczamy ktorym elementem w tablicy bedzie mediana median (liczba median na pol liczac od aktualnego l+1)
        int curr = l + (r-l)/5; //wyznaczamy gdzie koncza sie mediany
        return Select(l, curr, k, p);//szukamy k-tego elementu w poczatkowej czesci tablicy (wsrod median) - mediany median - zwracamy ja
    }

    public int Select(int l, int r, int k, int p)//glowna funkcja zalezna od l - poczatku tablicy w ktorej szukamy, r - konca, k - ktorego el szukamy, p - od jakiej wielkosci po prostu sortujemy podzadanie
    {
        if(r-l+1 < p)//jesli mamy male podzadanie
        {
            Insertion(l, r); //sortujemy
            return l+k-1; //zwracamy k-ty element
        }
        int m = pivot(l, r, p); //wyznaczamy indeks elementu dzielacego (mediany median)
        m = partition(l, r, m); //wywolujemy partition i wyznaczamy indeks elementu dzielacego w podzielonej tablcicy
 
        if (m-l == k - 1) //jesli indeks el dzielacego jest k-1 (-1 wynika z roznicy ze tablice indeksujemy od 0, a szukamy k-tego elementu liczac od 1)
            return m; //to go zwracamy
 
        else if (m-l < k - 1) //jesli indeks jest za maly
        {
            k =  k-(m-l+1);
            return Select(m + 1, r, k, p); //to szukamy w prawej czesci tablicy k - (dlugosc lewej czesci tablicy wlacznie z indeksem m)-tego elementu
        }
 
        else //jesli za duzy
            return Select(l, m - 1, k, p); //to szukamy k-tego elementu w prawej czesci tablicy
    }

   public static void main( String [] args ) {
   
        int howMany = scan.nextInt();

        for(int i = 0; i < howMany; i++)
        {
            int size = scan.nextInt();
            MagicFives sou = new MagicFives(size);

            int q = scan.nextInt();
            for(int j = 0; j < q; j++)
            {
                int k = scan.nextInt();
                if(k < 1 || k > sou.n)
                {
                    System.out.println(k + " brak");
                }
                else{
                    int index = sou.Select(0, size-1, k, 10);
                    System.out.println(k + " " + sou.A[index]);
                }
            }
        }
    }
}

/*
testy:
wejscie:
1
16
16 15 14 13 12 11 10 9 8 7 6 5 4 3 2 1
5
1 5 10 15 17

poprawne wyjscie:
1 1
5 5
10 10
15 15
17 brak

wejscie:
3
5
1 2 3 4 5
3
1 2 3
5
5 3 4 4 3
5
2 5 1 3 4
10
1 1 1 1 1 1 1 1 1 1
5
1 10 0 20 11

poprawne wyjscie:
1 1
2 2
3 3
2 3
5 5
1 3
3 4
4 4
1 1
10 1
0 brak
20 brak
11 brak

wejscie:
1
26
8 3 1 10 43 13 14 9 4 27 25 19 43 34 18 6 21 29 14 23 22 5 8 11 25 2
5
1 26 3 24 25
poprawne wyjscie:
1 1
26 43
3 3
24 34
25 43

*/