package kas.concurrente.modelos;
import java.util.Random;

import kas.concurrente.candado.Filter;

/**
 * Clase que modela un Lugar
 * El lugar consta de un id
 * un booleano que nos dice si esta dispoible
 * y un objeto del tipo Semaphore (El semaforo)
 * @author Kassandra Mirael
 * @version 1.0
 */
public class Lugar {
    private Integer id;
    private boolean disponible;
    private int vecesEstacionado;
    Filter filtroModificado;

    /**
     * Metodo constructor
     * El lugar por defecto esta disponible
     * Pueden llegar un numero n de carros en el peor de los casos
     * veces estacionado sera el numero de veces que se han estacianado en el lugar
     * Si llegan 2 carros y ambos se estacionan, entonces, su valor sera de 2
     * @param id El id del Lugar
     */
    public Lugar(int id){
        this.id = id;
        this.disponible = true;
        this.vecesEstacionado = 0;
        filtroModificado = new Filter(5);
    }

    public Integer getId() {
        return this.id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public boolean isDisponible() {
        return this.disponible;
    }

    public void setDisponible(boolean disponible) {
        this.disponible = disponible;
    }

    public boolean getDisponible() {
        return this.disponible;
    }

    /**
     * En este metodo se simula que se estaciona
     * PELIGRO: ESTAS ENTRANDO A LA 2da SECCION CRITICA
     * Cambia el valor de disponible a false
     * Y se simula que vas pastel de cumple :D
     * Al final, imprime un texto color ROJO diciendo que va salir (Esperen instrucciones para esto)
     * @throws InterruptedException Si algo falla
     */
    public void estaciona() throws InterruptedException{
        filtroModificado.lock();
        disponible = false; 
        vePorPastel();
        String RED = "\u001B[31m";
        String RESET = "\u001B[0m";
        System.out.println(RED + "El carro " + id + " va a salir" + RESET);
        vecesEstacionado++;
        filtroModificado.unlock();
        disponible = true;
    }

    /**
     * En este metodo se genera la sumulación de espera
     * Se genera un tiempo entre 1 y 5 segundos
     * Es pseudo aleatorio
     * @throws InterruptedException En caso de que falle
     */
    public void vePorPastel() throws InterruptedException{
        // tomamos un numero aleatorio entre 1 y 5
        int tiempo = new java.util.Random().nextInt(5) + 1;
        tiempo = tiempo * 1000;
        // dormimos el hilo por ese tiempo
        Thread.sleep(tiempo);
        
    }
    
    public int getVecesEstacionado(){
        return vecesEstacionado;
    }

    public void setVecesEstacionado(int vecesEstacionado){
        this.vecesEstacionado = vecesEstacionado;
    }

    public Filter getFiltroModificado() {
        return filtroModificado;
    }
}
