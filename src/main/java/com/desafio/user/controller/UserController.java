package com.desafio.user.controller;

import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.desafio.user.dto.ErrorResponse;
import com.desafio.user.entity.User;
import com.desafio.user.exception.ObjectNotFoundException;
import com.desafio.user.exception.ValidationDataException;
import com.desafio.user.service.UserService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;

@RestController
public class UserController {

	@Autowired 
	private UserService userService;
	
    @Operation(
        summary = "Obtener todos los usuarios",
        description = "Devuelve una lista con todos los usuarios registrados.",
        responses = {
                @ApiResponse(responseCode = "200", description = "Lista de usuarios",
                        content = @Content(mediaType = "application/json", schema = @Schema(implementation = User.class)))
        }
    )
	@GetMapping("/users")
    public ResponseEntity<List<User>> findAll(){
    	return ResponseEntity.ok(userService.findAll());
    }
	
    @Operation(
        summary = "Registra un usuario nuevo",
        description = "Crea un usuario nuevo y devuelve sus datos. Retorna código 201 si se crea correctamente.",
        responses = {
                @ApiResponse(responseCode = "201", description = "Usuario creado exitosamente",
                        content = @Content(mediaType = "application/json", schema = @Schema(implementation = User.class))),
                @ApiResponse(responseCode = "400", description = "Datos inválidos", content = @Content),
                @ApiResponse(responseCode = "500", description = "Error servidor", content = @Content)
        }
    )
	@PostMapping("/users")
    public ResponseEntity<User> saveUser(@RequestBody User user){
		return ResponseEntity.status(HttpStatus.CREATED).body(userService.saveUser(user));
    }
	
    @Operation(
        summary = "Modifica un usuario",
        description = "Modifica un usuario y devuelve sus datos. Retorna código 201 si se modifica correctamente.",
        responses = {
                @ApiResponse(responseCode = "201", description = "Usuario modificado exitosamente",
                        content = @Content(mediaType = "application/json", schema = @Schema(implementation = User.class))),
                @ApiResponse(responseCode = "400", description = "Datos inválidos", content = @Content),
                @ApiResponse(responseCode = "404", description = "Objeto no encontrado", content = @Content),
                @ApiResponse(responseCode = "500", description = "Error servidor", content = @Content)
        }
    )
	@PutMapping("/users/{uuid}")
	public ResponseEntity<User> updateUser(@RequestBody User newUser, @PathVariable String uuid){
		User user = userService.findByUUID(UUID.fromString(uuid))
	              .map(usuario -> {
	            	  usuario.setName(newUser.getName());
	            	  usuario.setEmail(newUser.getEmail());
	            	  usuario.setPhones(newUser.getPhones());
	                  return userService.updateUser(usuario);
	              })
	              .orElseGet(() -> {
	            	  throw new ObjectNotFoundException("Objeto no encontrado");
	              });
	      return ResponseEntity.status(HttpStatus.CREATED).body(user);
	}

    @Operation(
        summary = "Modifica parcialmente un usuario",
        description = "Modifica parcialmente un usuario y devuelve sus datos. Retorna código 201 si se modifica correctamente.",
        responses = {
                @ApiResponse(responseCode = "201", description = "Usuario modificado exitosamente",
                        content = @Content(mediaType = "application/json", schema = @Schema(implementation = User.class))),
                @ApiResponse(responseCode = "404", description = "Objeto no encontrado", content = @Content),
                @ApiResponse(responseCode = "500", description = "Error servidor", content = @Content)
        }
    )
	@PatchMapping("/users/{uuid}")
	public ResponseEntity<User> updatePhones(@RequestBody User newUser, @PathVariable String uuid){
		User user = userService.findByUUID(UUID.fromString(uuid))
	              .map(usuario -> {
	            	  usuario.setPhones(newUser.getPhones());
	                  return userService.updatePhones(usuario);
	              })
	              .orElseGet(() -> {
	            	  throw new ObjectNotFoundException("Objeto no encontrado");
	              });
	      return ResponseEntity.status(HttpStatus.CREATED).body(user);
	}
	
    @Operation(
        summary = "Desactiva un usuario",
        description = "Desactiva un usuario y devuelve sus datos. Retorna código 201 si se desactiva correctamente.",
        responses = {
                @ApiResponse(responseCode = "201", description = "Usuario desactivado exitosamente",
                        content = @Content(mediaType = "application/json", schema = @Schema(implementation = User.class))),
                @ApiResponse(responseCode = "404", description = "Objeto no encontrado", content = @Content),
                @ApiResponse(responseCode = "500", description = "Error servidor", content = @Content)
        }
    )
	@DeleteMapping("/users/{uuid}")
	public ResponseEntity<User> deleteUser(@PathVariable String uuid){
		User user = userService.findByUUID(UUID.fromString(uuid))
	              .map(usuario -> {
	            	  usuario.setActive(false);
	                  return userService.updateUser(usuario);
	              })
	              .orElseGet(() -> {
	            	  throw new ObjectNotFoundException("Objeto no encontrado");
	              });
		return ResponseEntity.status(HttpStatus.CREATED).body(user);
	}
    

    @ExceptionHandler(ValidationDataException.class)
    public ResponseEntity<ErrorResponse> handleValidationDataException(ValidationDataException ex) {
    	return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ErrorResponse(ex.getMessage()));
    }

    @ExceptionHandler(ObjectNotFoundException.class)
    public ResponseEntity<ErrorResponse> handleObjectNotFoundException(ObjectNotFoundException ex) {
    	return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ErrorResponse(ex.getMessage()));
    }
    
    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorResponse> handleException(Exception ex) {
    	return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new ErrorResponse(ex.getMessage()));
    }
    
}
