package com.medicacion.juanjose.asistentedemedicacion;

import android.widget.EditText;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.internal.matchers.Null;
import org.mockito.runners.MockitoJUnitRunner;

import static org.junit.Assert.*;

/**
 * Created by Juanjo on 13/03/2018.
 */

@RunWith(MockitoJUnitRunner.class)
public class RegisterActivityTest {

    @Mock
    RegisterActivity registerActivity;

    @Before
    public void setUp() throws Exception{
        registerActivity = new RegisterActivity();
    }

    @Test
    public void validarContraseña(){

        // Contraseñas iguales, debe devolver true
        assertEquals(true,registerActivity.validarCampos("Antonio",
                "1234", "1234"));

    }
}