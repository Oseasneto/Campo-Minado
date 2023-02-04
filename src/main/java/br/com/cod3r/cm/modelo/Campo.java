package br.com.cod3r.cm.modelo;

import br.com.cod3r.cm.excecao.ExplosaoException;

import java.util.ArrayList;
import java.util.List;

public class Campo {
    private final int coluna;
    private final int linha;

    private boolean aberto = false;
    private boolean minado = false;
    private boolean marcado = false;

    private List<Campo> vizinhos = new ArrayList<>();

    Campo(int linha, int coluna) {
        this.coluna = coluna;
        this.linha = linha;
    }

    boolean adicionarVizinho(Campo vizinho) {
        boolean linhaDiferente = linha != vizinho.linha;
        boolean colunaDiferente = coluna != vizinho.coluna;
        boolean diagonal = linhaDiferente && colunaDiferente;

        //calcular a diferença
        int diferencaLinha = Math.abs(linha - vizinho.linha);
        int diferencaColuna = Math.abs(coluna - vizinho.coluna);
        int diferencaGeral = diferencaColuna + diferencaLinha;

        if(diferencaGeral == 1 && !diagonal){
            vizinhos.add(vizinho);
            return true;
        } else if (diferencaGeral ==2 && diagonal) {
            vizinhos.add(vizinho);
            return true;
        } else {
            return false;
        }
    }

    void alternarMarcacao(){
        if (!aberto) {
            marcado = !marcado;
        }
    }

    boolean abrir(){
        if (!aberto && !marcado) {
            aberto = true;
            if (minado){
                throw new ExplosaoException();
            }

            if(vizinhacaSegura()) {
                vizinhos.forEach(v -> v.abrir());
            }

            return true;
        } else {
            return false;
        }
    }

    boolean vizinhacaSegura(){

        return vizinhos.stream().noneMatch(v -> v.minado);
    }
    void minar () {
        minado = true;
    }

    public boolean isMarcado(){
        return marcado;
    }

    public boolean isAberto(){
        return aberto;
    }
    public boolean isFechado(){
        return !isAberto();
    }
}
