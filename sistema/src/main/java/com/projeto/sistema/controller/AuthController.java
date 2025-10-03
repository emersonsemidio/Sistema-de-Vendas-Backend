package com.projeto.sistema.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.projeto.sistema.dto.AuthenticationDto;
import com.projeto.sistema.dto.ClienteRegisterDto;
import com.projeto.sistema.dto.LoginResponseDto;
import com.projeto.sistema.dto.MensagemResponseDto;
import com.projeto.sistema.model.Cliente;
import com.projeto.sistema.service.ServiceCliente;
import com.projeto.sistema.service.JwtService; // ✅ Import correto

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import jakarta.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@RestController
@RequestMapping("/auth")
public class AuthController {

  @Autowired
  private ServiceCliente serviceCliente;

  @Autowired
  private AuthenticationManager authenticationManager;

  @Autowired
  private JwtService jwtService; // ✅ Nome correto - JwtService

  @PostMapping("/login")
  @Operation(summary = "Autenticar cliente")
  @ApiResponse(responseCode = "200", description = "Cliente autenticado com sucesso")
  public ResponseEntity login(@RequestBody @Valid AuthenticationDto authenticationDto) {
    var usernamePassword = new UsernamePasswordAuthenticationToken(authenticationDto.email(), authenticationDto.senha());
    var auth = this.authenticationManager.authenticate(usernamePassword);

    var token = jwtService.generateToken((Cliente) auth.getPrincipal()); // ✅ generateToken() não gerarToken()
    return ResponseEntity.ok(new LoginResponseDto(token));
  }

  @PostMapping("/register")
  @Operation(summary = "Criar novo cliente")
  @ApiResponse(responseCode = "201", description = "Cliente criado com sucesso")
  public ResponseEntity<MensagemResponseDto> salvar(@RequestBody @Valid ClienteRegisterDto clienteDto) {
      try {
          Cliente cliente = serviceCliente.convertRegisterDtoToEntity(clienteDto);
          Cliente salvo = serviceCliente.salvar(cliente);
          MensagemResponseDto mensagem = new MensagemResponseDto("Cliente criado com sucesso", "201", salvo);
          return ResponseEntity.status(HttpStatus.CREATED).body(mensagem);

      } catch (Exception e) {
          MensagemResponseDto errorResponse = new MensagemResponseDto(
              "Erro ao criar cliente", "400", e.getMessage()
          );
          return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);
      }
  }
}