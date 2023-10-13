package br.com.moisas.tdolist.user;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import at.favre.lib.crypto.bcrypt.BCrypt;

/**
 * Modificadores
 * public
 * private
 * protected
 */

/**
 * class
 * interface
 * enum
 */
@RestController
@RequestMapping("/users")
public class UserController {
    // /**
    // * String (text)
    // * Integer (int) numero inteiros
    // * Doble (double) numeros 0.0000
    // * Float (float) Numeros 0.0000
    // * Char (A C) caracteres
    // * Date (data)
    // * void
    // */

    // /**
    // * Body
    // *
    // * @param userModel
    // * @return
    // */

    @Autowired
    private IUserRepository userRepository;

    @PostMapping("/")
    public ResponseEntity create(@RequestBody UserModel userModel) {
        var user = this.userRepository.findByUsername(userModel.getUsername());

        if (user != null) {
            // Mensagens de erro
            // Stattus Code
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Usuário já existe");
        }

        var passwordHashred = BCrypt.withDefaults().hashToString(12, userModel.getPassword().toCharArray());

        userModel.setPassword(passwordHashred);

        var userCreated = this.userRepository.save(userModel);
        return ResponseEntity.status(HttpStatus.CREATED).body(userCreated);
    }
}
