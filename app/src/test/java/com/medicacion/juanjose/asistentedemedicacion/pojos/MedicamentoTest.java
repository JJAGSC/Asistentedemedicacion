package com.medicacion.juanjose.asistentedemedicacion.pojos;

import com.medicacion.juanjose.asistentedemedicacion.RegisterActivity;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import static org.junit.Assert.*;

/**
 * Created by Juanjo on 13/03/2018.
 */

@RunWith(MockitoJUnitRunner.class)
public class MedicamentoTest {

    @Mock
    Medicamento medicamento;

    @Before
    public void setUp() throws Exception{
        medicamento = new Medicamento("Ibuprofeno","22:00", "Antonio");
    }

    // Comprobamos si devuelve correctamente el nombre del medicamento
    @Test
    public void getNombre() throws Exception {
        assertEquals("Ibuprofeno", medicamento.getNombre());
    }

    // Comprobamos si devuelve correctamente la hora de la alarma
    @Test
    public void getHora() throws Exception {
        assertEquals("22:00", medicamento.getHora());
    }

    // Comprobamos si devuelve correctamente el nombre del usuario de la alarma
    @Test
    public void getUsuarioAlarma() throws Exception {
        assertEquals("Antonio", medicamento.getUsuarioAlarma());
    }

}