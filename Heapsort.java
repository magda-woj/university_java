import java.util.Random;

public class Heapsort {
    int[] A;
    int n;

    public Heapsort(int size, int bound) {
        n = size;
        A = new int[size];
        Random rand = new Random();
        for(int i = 0; i < n; i++)
        {
            A[i] = rand.nextInt(bound);
        }
    }

    public void display() {
        for(int i = 0; i < n; i++)
        {
            System.out.print(A[i] + " ");
        }
        System.out.println();
    }

    public void swap(int i, int j)
    {
        int bufor = A[i]; 
        A[i] = A[j]; 
        A[j] = bufor; 
    }

    public void downheap(int k, int size) //uzywamy kopca typu min, poniewaz sortujemy malejaco - chcemy zeby w korzeniu byl najmniejszy
    //element, bo wtedy mozemy go dac na koniec kopca (tablicy) i zapomniec o nim - "zmniejszyc" rozwazany kopiec o 1
    //to jest funkcja ktora sprawia ze poddrzewo o korzeniu w indeksie k staje sie min-kopcem
    {
        int j, tmp = A[k];
        while(k < size/2) 
        {
            j = 2*k+1; // indeks lewego następnika A[k]
            if (j < size-1 && A[j] > A[j+1] ) 
            {
                j++;
            }
            // A[j]– mniejszy z następników węzła A[k] zmiana wzgledem wykladu - wybieramy mniejszy zamiast wiekszego!
            if (tmp <= A[j])//spr warunku kopca - inny niz na wykladzie bo to min-kopiec!
            {
                break;
            }
            // jesli warunek kopca nie zachodzi, przesuwamy aktualny element do góry
            A[k] = A[j];
            k = j;
        } // po zakończeniu pętli:
        A[k] = tmp; //wstawiamy element ktory byl w korzeniu na poczatku na wlasciwe miejsce
    }
    

    public void Sort(int size)
    {
        for(int i = size/2 - 1; i >= 0; i--) //zaczynamy od najwiekszego indeksu ktory nie jest lisciem - dla lisci warunek kopca
        //jest pustospelniony
        {
            downheap(i, size);
        }

        while(size > 0) 
        {
            swap(0, size-1); // dajemy element z korzenia (najmniejszy w kopcu) na ostatnie miejsce - sortowanie malejace
            size-- ; //zmniejszamy kopiec
            downheap (0, size); //zmieniamy zmniejszony kopiec w min-kopiec
        }
    }

    public static void main(String[] args){
        Heapsort heap = new Heapsort(30,100);
        heap.display();
        System.out.println();
        heap.Sort(heap.n);
        heap.display();
    }
}
