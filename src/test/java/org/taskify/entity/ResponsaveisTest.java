package org.taskify.entity;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class ResponsaveisTest {

    @Test
    void testResponsaveisGettersAndSetters() {
        Responsaveis responsavel = new Responsaveis();
        responsavel.setIdResponsaveis(1);
        responsavel.setNome("Nome do Responsável");

        assertEquals(1, responsavel.getIdResponsaveis());
        assertEquals("Nome do Responsável", responsavel.getNome());
    }
}