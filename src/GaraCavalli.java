import javax.swing.*; // Importa le librerie per l'interfaccia grafica
import java.util.ArrayList; // Importa la classe ArrayList per gestire una lista di cavalli
import java.util.List; // Importa l'interfaccia List
import java.util.Random; // Importa la classe Random per generare numeri casuali

// Classe che rappresenta un cavallo, estende la classe Thread
class Cavallo extends Thread {
    private String nome; // Nome del cavallo
    private int lunghezzaPercorso; // Lunghezza totale del percorso
    private int metriPercorsi = 0; // Metri percorsi finora
    private static final Random random = new Random(); // Generatore di numeri casuali

    // Costruttore della classe Cavallo
    public Cavallo(String nome, int lunghezzaPercorso) {
        this.nome = nome; // Assegna il nome del cavallo
        this.lunghezzaPercorso = lunghezzaPercorso; // Assegna la lunghezza del percorso
    }

    // Metodo eseguito quando il thread è avviato
    @Override
    public void run() {
        // Continua a correre fino a raggiungere la lunghezza del percorso
        while (metriPercorsi < lunghezzaPercorso) {
            // Genera un numero casuale tra 1 e 10 per simulare il passo del cavallo
            int passo = random.nextInt(10) + 1; 
            metriPercorsi += passo; // Aggiorna i metri percorsi

            // Assicurati di non superare la lunghezza del percorso
            if (metriPercorsi > lunghezzaPercorso) {
                metriPercorsi = lunghezzaPercorso; // Imposta a lunghezza massima se supera
            }

            // Stampa i progressi del cavallo sulla console
            System.out.println(nome + " ha percorso " + metriPercorsi + " metri.");
            try {
                Thread.sleep(500); // Aspetta mezzo secondo prima del prossimo passo
            } catch (InterruptedException e) {
                // Se il thread è interrotto, ripristina lo stato di interruzione
                Thread.currentThread().interrupt();
            }
        }
    }
}

// Classe principale per gestire la gara di cavalli
public class GaraCavalli {
    public static void main(String[] args) {
        // Chiede all'utente la lunghezza del percorso
        int lunghezzaPercorso = Integer.parseInt(JOptionPane.showInputDialog("Inserisci la lunghezza del percorso in metri:"));
        
        // Chiede all'utente il numero di cavalli
        int numeroCavalli = Integer.parseInt(JOptionPane.showInputDialog("Inserisci il numero di cavalli:"));

        // Crea una lista per memorizzare i cavalli
        List<Cavallo> cavalli = new ArrayList<>();

        // Ciclo per creare e avviare i cavalli
        for (int i = 0; i < numeroCavalli; i++) {
            // Chiede all'utente di inserire il nome del cavallo
            String nome = JOptionPane.showInputDialog("Inserisci il nome del cavallo " + (i + 1) + ":");
            // Crea un nuovo oggetto Cavallo
            Cavallo cavallo = new Cavallo(nome, lunghezzaPercorso);
            // Aggiunge il cavallo alla lista
            cavalli.add(cavallo);
            // Avvia il thread del cavallo
            cavallo.start(); 
        }

        // Aspetta che tutti i cavalli finiscano la gara
        for (Cavallo cavallo : cavalli) {
            try {
                // Aspetta che il thread del cavallo termini
                cavallo.join();
            } catch (InterruptedException e) {
                // Se l'attesa è interrotta, ripristina lo stato di interruzione
                Thread.currentThread().interrupt();
            }
        }

        // Mostra un messaggio finale quando la gara è finita
        JOptionPane.showMessageDialog(null, "La gara è finita!");
    }
}