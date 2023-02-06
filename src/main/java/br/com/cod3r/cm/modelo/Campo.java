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

    public boolean isMinado(){
        return minado;
    }

    void setAberto(boolean aberto) {
        this.aberto = aberto;
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

    public int getColuna() {
        return coluna;
    }

    public int getLinha() {
        return linha;
    }
    boolean objetivoAlcancado(){
        boolean desvendado = !minado && aberto;
        boolean protegido = minado && marcado;
        return  desvendado || protegido;
    }
    long minasNaVizinhanca(){
        return vizinhos.stream().filter(v -> v.minado).count();
    }

    void reiniciar(){
        aberto = false;
        minado = false;
        marcado = false;
    }

    public String toString(){
        if (marcado) {
            return "x";
        } else if (aberto && minado) {
            return "*";
        } else if (aberto && minasNaVizinhanca() >0) {
            return Long.toString(minasNaVizinhanca());
        } else if (aberto) {
            return " ";
        } else {
            return "?";
        }
    }
}
