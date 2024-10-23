package kas.concurrente.modelos;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class EstacionamientoTest {
    Estacionamiento es;
    final static int NUMLUGARES = 10;
    List<Thread> hilos;

    @BeforeEach
    void setUp(){
        es = new Estacionamiento(NUMLUGARES);
        initHilos();
    }

    /**
     * Teste que revisa si tiene todos los lugares disponibles al iniciar
     */
    @Test
    void lugaresDisponiblesITest(){
        assertEquals(NUMLUGARES,es.getLugaresDisponibles());
    }

    /**
     * Test que comprueba que el numero de veces que se estaciona sea correcto
     * @throws InterruptedException
     */
    @Test
    void conteoVecesEstacionado() throws InterruptedException{
        for(int i = 0; i < NUMLUGARES; i++){
            es.getLugares()[i].estaciona();
        }
        assertEquals(NUMLUGARES, verificaVecesEstacionado());
    }

    @Test
    void conteoGlobalVecesEstacionado() throws InterruptedException{
        for(Thread t : hilos){
            t.start();
        }

        for(Thread t : hilos){
            t.join();
        }

        assertEquals(NUMLUGARES,verificaVecesEstacionado());
    }

    int verificaVecesEstacionado(){
        int res = 0;
        for(int i = 0; i < es.getLugares().length; ++i){
            res += es.getLugares()[i].getVecesEstacionado();
        }

        return res;
    }

    /**
     * AGREGA 2 TEST MAS
     * TEST bien hechos
     */

    @Test
    void obtenLugarTest() throws InterruptedException{
        int lugar[] = new int[NUMLUGARES]; 
        for(int i = 0; i < NUMLUGARES; i++){
            lugar[i] = es.obtenLugar();
            es.getLugares()[i].estaciona();
        }
        
        // verifica que los lugares obtenidos esten en el rango correcto
        for(int i = 0; i < NUMLUGARES; i++){
            //checamos que lugar[i] este entre 0 y NUMLUGARES
            assertEquals(true, lugar[i] >= 0 && lugar[i] < NUMLUGARES);
        }
    
    }

    @Test
    void estaLlenoTest() throws InterruptedException{
        for(int i = 0; i < NUMLUGARES; i++){
            int lugar = es.obtenLugar();
            es.asignaLugar(lugar);
        }
        assertEquals(true, es.estaLleno());
    }
    
    void initHilos(){
        hilos = new ArrayList<>();

        for(int i=0; i < NUMLUGARES*2; ++i){
            Thread t = new Thread(this::simulaCS,""+i);
            hilos.add(t);
        }
    }

    void simulaCS() {
        try{
            int id = Integer.parseInt(Thread.currentThread().getName());
            es.entraCarro(id);
            es.asignaLugar(id);
        }catch(InterruptedException e){
            e.printStackTrace();
        }
        
    }
}
