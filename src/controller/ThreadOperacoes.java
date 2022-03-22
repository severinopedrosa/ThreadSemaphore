package controller;

import java.util.concurrent.Semaphore;

public class ThreadOperacoes extends Thread {
    private int threadID;
    private int tipoOperacao;
    private int tempoMilis;
    private int ciclo;
    private Semaphore semaforoBD;

    public ThreadOperacoes(int threadID, Semaphore semaforoBD) {
        this.threadID = threadID;
        this.semaforoBD = semaforoBD;
    }

    public void run() {
        tipoOperacao = threadID % 3;
        if (tipoOperacao == 1) {
            ciclo = 2;
        } else if (tipoOperacao == 2 || tipoOperacao == 0) {
            ciclo = 3;
        }
        for (int i = 0; i < ciclo; i++) {
            executarCalculo(tipoOperacao);
        
            try {
                semaforoBD.acquire();
                executaTransacaoBD(tipoOperacao);
            } catch (InterruptedException e) {
                e.printStackTrace();
            } finally {
                semaforoBD.release();
            }
        
        }
    }

    private void executarCalculo(int op) {
        if (op == 1) {
            tempoMilis = (int) (Math.random() * 801) + 200;
           
            System.out.println("Thread #" + threadID + " realizando calculo de 0,2 a 1,0 segundos.");
        } else if (op == 2) {
            tempoMilis = (int) (Math.random() * 1001) + 500;
           
            System.out.println("Thread #" + threadID + " realizando calculo de 0,5 a 1,5 segundos.");
        } else if (op == 0) {
            tempoMilis = (int) (Math.random() * 1001) + 1000;
            
            System.out.println("Thread #" + threadID + " realizando calculo de 1,0 a 2,0 segundos.");
        }
        try {
            Thread.sleep(tempoMilis);
            System.out.println("Thread #" + threadID + " calculo final em " + tempoMilis + " milisegundos.");
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    private void executaTransacaoBD(int op) {
        if (op == 1) {
            tempoMilis = 1000;
            System.out.println("Thread #" + threadID + " realizando transacao de BD de 1,0 segundos.");
        } else if (op == 2 || op == 0) {
            tempoMilis = 1500;
            System.out.println("Thread #" + threadID + " realizando transacao de BD de 1,5 segundos.");
        }
        try {
            Thread.sleep(tempoMilis);
            System.out.println("Thread #" + threadID + " transacao de BD finalizada em " + tempoMilis + " milisegundos.");
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}