package br.com.cod3r.cm.modelo;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class CampoTest {

    private Campo campo = new Campo(3,3);

    @Test
    void testeVizinhoRealDistancia1(){

        Campo vizinho = new Campo(3, 2);
        boolean resultado = campo.adicionarVizinho(vizinho);

        Assertions.assertTrue(resultado);
    }
}
