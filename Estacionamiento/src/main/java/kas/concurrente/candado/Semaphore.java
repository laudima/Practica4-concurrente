package kas.concurrente.candado;

import kas.concurrente.Main;
/**
 * Interfaz que modela un semaforo
 * @version 1.0
 * @author Kassandra Mirael
 */
public class Semaphore {

    private static int count;
    
    /**
     * Metodo que nos retorna el numero de hilos permitidos
     * dentro de la seccion critica.
     * @return El numero de hilos permitido
     */
    public int getPermitsOnCriticalSection(){
        return count;
    }

    public Semaphore(int n){
        this.count  = n;
    }
    public synchronized void acquire(){
        while(isAvailable()){
            try {
                this.wait();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        count += 1;
        this.notifyAll();
    }

    public synchronized void release(){
        while(!isAvailable()){
            try {
                this.wait();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        count -= 1;

        this.notifyAll();
    }

    public synchronized final static boolean isAvailable(){
        return count == 1 ? true : false;
    }
}
