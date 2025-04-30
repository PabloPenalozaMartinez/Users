package com.desafio.user.repository;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.desafio.user.entity.User;

@ExtendWith(MockitoExtension.class)
public class UserRepositoryTest {

    @Mock
    private UserRepository usuarioRepository;
    
    private static User usuario;
    private static List<User> usuarios;
    
    @BeforeAll
    public static void init() throws NoSuchFieldException, SecurityException, IllegalArgumentException, IllegalAccessException {
    	usuario = new User();
    	usuario.setName("Juan Perez");
    	usuario.setEmail("correo@emp.com");
    	usuario.setPassword("usernamed$omain1com");
    	usuario.setId(UUID.fromString("72db8605-0950-45c3-a450-beee27433cdc"));
    	usuario.setToken("eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJKdWFuIFJvZHJpZ3VleiJ9.9OIEaF80tLW9r6_urril5-Q2VitpTjb9d6545Als0dM");
    	usuario.setActive(true);
    	usuarios = new ArrayList<User>();
    	usuarios.add(usuario);
    }
    
    @Test
    void shouldBuscarPorNombreOk() throws NoSuchFieldException, SecurityException, IllegalArgumentException, IllegalAccessException {
        when(usuarioRepository.findByName("Paul")).thenReturn(usuarios);
        List<User> users = usuarioRepository.findByName("Paul");
        assertNotNull(users);
    }
}
