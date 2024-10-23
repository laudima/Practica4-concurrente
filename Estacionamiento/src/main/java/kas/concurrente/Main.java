package kas.concurrente;

import kas.concurrente.candado.Filter;
import kas.concurrente.candado.Peterson;
import kas.concurrente.candado.Semaphore;
import kas.concurrente.modelos.Estacionamiento;

/**
 * Clase principal, la usaran para SUS pruebas
 * Pueden modigicar los valores estaticos para ver como funciona
 * NO USEN VALORES EXTREMEDAMENTE ALTOS, puede alentar mucho su compu
 * AQUI EJECUTAN LA SIMULACION
 * @author Kassandra Mirael
 * @version 1.0
 */
public class Main implements Runnable{

    public final static int numCARROS =5; 
    final static int numCajones = 5; 
    private static Estacionamiento estacionamiento; 
    private static Filter lock;
    private static Peterson peterson; 
    private static Semaphore semaforo;

    // private static WeakSemaphore semaforo;
    
    /**
     * Metodo constructor
     * Se inicializa el Semaforo Modificado con _______
     * Se inicaliza el Estacionamiento con _______
     */
    public Main(){
        estacionamiento = new Estacionamiento(numCajones);
        lock = new Filter(numCARROS);
        // peterson = new Peterson();
        estacionamiento.inicializaLugares();
        semaforo = new Semaphore(numCajones);


    }

    public int getNumCajones(){
        return numCajones;
    }

    /**
     * Una documentacion del main xD, esta bien 
     * Paso 0: Lee estas instrucciones
     * Paso 1: Crea el Objeto de tipo main
     * Paso 2: Crea Una estructura de datos que contenga a nuestros hilos
     * Paso 3: Genera con un ciclo, el cual inialice un numero igual de NUM_CARROS
     * Paso 4: No olvides agregarlos a la estructura e inicializarlos
     * Paso 5: Finalmente has un Join a tus hilos
     * @param args Los Argumentos
     * @throws InterruptedException Por si explota su compu al ponerle medio millon de hilos xD
     */
    public static void main(String[] args) throws InterruptedException{
        //Creamos el objeto main 
        Main m = new Main();
        //Creamos una estructura de datos que contenga a nuestros hilos
        Thread[] hilos = new Thread[numCARROS];
        //Generamos con un ciclo, el cual inialice un numero igual de NUM_CARROS
        //Agregamos los hilos a la estructura e inicializamos
        for(int i = 0; i < numCARROS; i++){
            hilos[i] = new Thread(m);
            hilos[i].start();
        }
        for(int i = 0; i < numCARROS; i++){
            hilos[i].join();
        }
    }

    /**
     * Aqui esta su primer seccion crítica
     * Paso 1: Keep calm and ...
     * Paso 2: Beware with the concurrent code
     * Paso 3: Try to remember some basics of Java and POO
     * Paso 4: Obten el ID de tu hilo
     * Paso 5: TU CARRO (HILO) ENTRARA AL ESTACIONAMIENTO (Los Hilos simulan ser carros, 
     * no es necesario que generes clase Carro (puedes hacerlo si quieres))
     */
    @Override
    public void run(){
        
        int carro = (int) Thread.currentThread().getId() % numCARROS;

        while(true){
            try{
                if(!estacionamiento.estaLleno()){
                    int lugar; 
                    estacionamiento.entraCarro(carro);
                    lugar = estacionamiento.obtenLugar();
                    lock.lock();
                    estacionamiento.asignaLugar(lugar); // se estaciona SECCION CRITICA
                    lock.unlock();
                    break;
                }
            }catch(InterruptedException e){
                e.printStackTrace();
            }
        }
        
    }
}
