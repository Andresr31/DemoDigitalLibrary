
package co.edu.autonoma.digital_library.controllers;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping("/api/v1/test")
public class TestController {
    
    @CrossOrigin
    @GetMapping("/hello-world")
    public String getHelloWorld(){
        return "Hello World Java Spring Boot";
    }
    
    @CrossOrigin
    @GetMapping("/saludo/{nombre}")
    public String saludar(@PathVariable String nombre){
        return "Hola "+nombre+" soy Andrés";
    }
    
    
}
