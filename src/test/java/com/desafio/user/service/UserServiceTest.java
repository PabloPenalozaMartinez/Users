package com.desafio.user.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.desafio.user.entity.Phone;
import com.desafio.user.entity.User;
import com.desafio.user.exception.ValidationDataException;
import com.desafio.user.repository.UserRepository;
import com.desafio.user.security.JWTUtil;

@ExtendWith(MockitoExtension.class)
class UserServiceTest {

    @Mock
    private UserRepository usuarioRepository;
    
    @Mock
	private JWTUtil jWTUtil;

    @InjectMocks
    private UserServiceImpl usuarioServiceImpl;

    private static Phone telefonoBD;
    private static List<Phone> telefonosBD;
    private static User usuarioBD;
    private static List<User> usuariosBD;
    private static User usuarioNuevo;
    private static User usuarioActualizacion;
    
    @BeforeAll
    public static void init() throws NoSuchFieldException, SecurityException, IllegalArgumentException, IllegalAccessException {
    	telefonoBD = new Phone();
    	telefonoBD.setId(1L);
    	telefonoBD.setNumber(321654L);
    	telefonoBD.setCitycode(3L);
    	telefonoBD.setCountrycode(54L);
    	telefonosBD = new ArrayList<Phone>();
    	telefonosBD.add(telefonoBD);
    	usuarioBD = new User();
    	usuarioBD.setName("Juan Perez");
    	usuarioBD.setEmail("correo@emp.com");
    	usuarioBD.setPassword("usernamed$omain1com");
    	usuarioBD.setId(UUID.fromString("72db8605-0950-45c3-a450-beee27433cdc"));
    	usuarioBD.setToken("eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJKdWFuIFJvZHJpZ3VleiJ9.9OIEaF80tLW9r6_urril5-Q2VitpTjb9d6545Als0dM");
    	usuarioBD.setActive(true);
    	usuarioBD.setPhones(telefonosBD);
    	usuariosBD = new ArrayList<User>();
    	usuariosBD.add(usuarioBD);
    	usuarioNuevo = new User();
    	usuarioNuevo.setEmail("ppenaloza@gmail.com");
    	usuarioNuevo.setPassword("usernomain1com");
    	
    	usuarioActualizacion = new User();
    	usuarioActualizacion.setName("Juan Poul");
    	usuarioActualizacion.setEmail("correo@pol.com");
    	usuarioActualizacion.setPassword("usernomain1com");
    	usuarioActualizacion.setId(UUID.fromString("72db8605-0950-45c3-a450-beee27433cdc"));
    	
    }
    
    @Test
    void shouldGuardarUsuarioOk() throws NoSuchFieldException, SecurityException, IllegalArgumentException, IllegalAccessException {
    	
    	Field regexMail = usuarioServiceImpl.getClass().getDeclaredField("regexMail");
    	regexMail.setAccessible(true);
    	regexMail.set(usuarioServiceImpl, "^\\S+@\\S+\\.\\S+$");
    	Field regexPassword = usuarioServiceImpl.getClass().getDeclaredField("regexPassword");
    	regexPassword.setAccessible(true);
    	regexPassword.set(usuarioServiceImpl, "^(.{0,7}|[^0-9]*|[^A-Z]*|[^a-z]*|[a-zA-Z0-9]*)$");
    	Field jwt = usuarioServiceImpl.getClass().getDeclaredField("jWTUtil");
    	jwt.setAccessible(true);
    	jwt.set(usuarioServiceImpl, jWTUtil);
    	
        when(usuarioRepository.save(any(User.class))).thenAnswer(invocation -> {
        	User savedUser = invocation.getArgument(0);
            savedUser.setId(UUID.fromString("72db8605-0950-45c3-a450-beee27433cdc"));
            return savedUser;
        });

        User user = usuarioServiceImpl.saveUser(usuarioNuevo);

        assertNotNull(user);
        assertEquals(UUID.fromString("72db8605-0950-45c3-a450-beee27433cdc"), user.getId());
        assertEquals("ppenaloza@gmail.com", user.getEmail());
        assertEquals("usernomain1com", user.getPassword());
        verify(usuarioRepository).save(any(User.class));
    }

    @Test
    void shouldFailGuardarUsuarioCorreoExiste() throws NoSuchFieldException, SecurityException, IllegalArgumentException, IllegalAccessException  {
    	
    	usuarioNuevo.setEmail("correo@emp.com");
    	
    	Field regexMail = usuarioServiceImpl.getClass().getDeclaredField("regexMail");
    	regexMail.setAccessible(true);
    	regexMail.set(usuarioServiceImpl, "^\\S+@\\S+\\.\\S+$");
    	Field regexPassword = usuarioServiceImpl.getClass().getDeclaredField("regexPassword");
    	regexPassword.setAccessible(true);
    	regexPassword.set(usuarioServiceImpl, "^(.{0,7}|[^0-9]*|[^A-Z]*|[^a-z]*|[a-zA-Z0-9]*)$");
    	Field jwt = usuarioServiceImpl.getClass().getDeclaredField("jWTUtil");
    	jwt.setAccessible(true);
    	jwt.set(usuarioServiceImpl, jWTUtil);
    	
        when(usuarioRepository.findByEmail(usuarioNuevo.getEmail())).thenReturn(usuariosBD);

	    assertThrows(ValidationDataException.class, () -> {
	    	usuarioServiceImpl.saveUser(usuarioNuevo);
	    });
    }
    
    @Test
    void shouldFailGuardarUsuarioCorreoFormatoIncorrecto() throws NoSuchFieldException, SecurityException, IllegalArgumentException, IllegalAccessException  {
    	Field regexMail = usuarioServiceImpl.getClass().getDeclaredField("regexMail");
    	regexMail.setAccessible(true);
    	regexMail.set(usuarioServiceImpl, "^[A-Z0-9._%+-]+@[A-Z0-9.-]+\\\\.[A-Z]{2,6}$");
    	Field regexPassword = usuarioServiceImpl.getClass().getDeclaredField("regexPassword");
    	regexPassword.setAccessible(true);
    	regexPassword.set(usuarioServiceImpl, "^(.{0,7}|[^0-9]*|[^A-Z]*|[^a-z]*|[a-zA-Z0-9]*)$");
    	Field jwt = usuarioServiceImpl.getClass().getDeclaredField("jWTUtil");
    	jwt.setAccessible(true);
    	jwt.set(usuarioServiceImpl, jWTUtil);
    	
	    assertThrows(ValidationDataException.class, () -> {
	    	usuarioServiceImpl.saveUser(usuarioNuevo);
	    });
    }
    
    @Test
    void shouldFailGuardarUsuarioContrasenaFormatoIncorrecto() throws NoSuchFieldException, SecurityException, IllegalArgumentException, IllegalAccessException  {
    	Field regexMail = usuarioServiceImpl.getClass().getDeclaredField("regexMail");
    	regexMail.setAccessible(true);
    	regexMail.set(usuarioServiceImpl, "^\\\\S+@\\\\S+\\\\.\\\\S+$");
    	Field regexPassword = usuarioServiceImpl.getClass().getDeclaredField("regexPassword");
    	regexPassword.setAccessible(true);
    	regexPassword.set(usuarioServiceImpl, "^(?=.*[A-Za-z])(?=.*\\\\d)(?=.*[@$!%*#?&])[A-Za-z\\\\d@$!%*#?&]{8,}$");
    	Field jwt = usuarioServiceImpl.getClass().getDeclaredField("jWTUtil");
    	jwt.setAccessible(true);
    	jwt.set(usuarioServiceImpl, jWTUtil);
    	
    	assertThrows(ValidationDataException.class, () -> {
    		usuarioServiceImpl.saveUser(usuarioNuevo);
    	});
    }
    
    @Test
    void shouldActualizarUsuarioOk() throws NoSuchFieldException, SecurityException, IllegalArgumentException, IllegalAccessException  {
    	
    	usuarioActualizacion.setPassword("usernomain1com");
    	
    	Field regexMail = usuarioServiceImpl.getClass().getDeclaredField("regexMail");
    	regexMail.setAccessible(true);
    	regexMail.set(usuarioServiceImpl, "^\\S+@\\S+\\.\\S+$");
    	Field regexPassword = usuarioServiceImpl.getClass().getDeclaredField("regexPassword");
    	regexPassword.setAccessible(true);
    	regexPassword.set(usuarioServiceImpl, "^(.{0,7}|[^0-9]*|[^A-Z]*|[^a-z]*|[a-zA-Z0-9]*)$");
    	Field jwt = usuarioServiceImpl.getClass().getDeclaredField("jWTUtil");
    	jwt.setAccessible(true);
    	jwt.set(usuarioServiceImpl, jWTUtil);
    	
        when(usuarioRepository.save(any(User.class))).thenAnswer(invocation -> {
        	User savedUser = invocation.getArgument(0);
            savedUser.setId(UUID.fromString("72db8605-0950-45c3-a450-beee27433cdc"));
            return savedUser;
        });

        User user = usuarioServiceImpl.updateUser(usuarioActualizacion);

        assertNotNull(user);
        assertEquals(UUID.fromString("72db8605-0950-45c3-a450-beee27433cdc"), user.getId());
        assertEquals("correo@pol.com", user.getEmail());
        assertEquals("usernomain1com", user.getPassword());
        verify(usuarioRepository).save(any(User.class));
    }
    
    @Test
    void shouldFailActualizarUsuarioContarsenaFormatoIncorrecto() throws NoSuchFieldException, SecurityException, IllegalArgumentException, IllegalAccessException  {
    	
    	usuarioActualizacion.setPassword("userno$m@ain1com");
    	
    	Field regexMail = usuarioServiceImpl.getClass().getDeclaredField("regexMail");
    	regexMail.setAccessible(true);
    	regexMail.set(usuarioServiceImpl, "^\\S+@\\S+\\.\\S+$");
    	Field regexPassword = usuarioServiceImpl.getClass().getDeclaredField("regexPassword");
    	regexPassword.setAccessible(true);
    	regexPassword.set(usuarioServiceImpl, "^(.{0,7}|[^0-9]*|[^A-Z]*|[^a-z]*|[a-zA-Z0-9]*)$");
    	Field jwt = usuarioServiceImpl.getClass().getDeclaredField("jWTUtil");
    	jwt.setAccessible(true);
    	jwt.set(usuarioServiceImpl, jWTUtil);
    	
    	assertThrows(ValidationDataException.class, () -> {
    		usuarioServiceImpl.updateUser(usuarioActualizacion);
    	});
    }
    
    
    @Test
    void shouldActualizarTelefonosOk() throws NoSuchFieldException, SecurityException, IllegalArgumentException, IllegalAccessException  {
    	
    	Field regexMail = usuarioServiceImpl.getClass().getDeclaredField("regexMail");
    	regexMail.setAccessible(true);
    	regexMail.set(usuarioServiceImpl, "^\\S+@\\S+\\.\\S+$");
    	Field regexPassword = usuarioServiceImpl.getClass().getDeclaredField("regexPassword");
    	regexPassword.setAccessible(true);
    	regexPassword.set(usuarioServiceImpl, "^(.{0,7}|[^0-9]*|[^A-Z]*|[^a-z]*|[a-zA-Z0-9]*)$");
    	Field jwt = usuarioServiceImpl.getClass().getDeclaredField("jWTUtil");
    	jwt.setAccessible(true);
    	jwt.set(usuarioServiceImpl, jWTUtil);
    	
        when(usuarioRepository.save(any(User.class))).thenAnswer(invocation -> {
        	User savedUser = invocation.getArgument(0);
            savedUser.setId(UUID.fromString("72db8605-0950-45c3-a450-beee27433cdc"));
            return savedUser;
        });

        User user = usuarioServiceImpl.updatePhones(usuarioBD);

        assertNotNull(user);
        assertEquals(UUID.fromString("72db8605-0950-45c3-a450-beee27433cdc"), user.getId());
        assertEquals("correo@emp.com", user.getEmail());
        assertEquals("usernamed$omain1com", user.getPassword());
        verify(usuarioRepository).save(any(User.class));
    }

}
