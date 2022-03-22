package view;

import java.util.concurrent.Semaphore;

import controller.ThreadOperacoes;

public class Main {
    public static void main(String[] args) {
        int permissoes = 1;
        Semaphore semaforo = new Semaphore(permissoes);
        for (int i = 1; i < 22; i++) {
            Thread operacao = new ThreadOperacoes(i, semaforo);
            operacao.start();
        }
    } 
}