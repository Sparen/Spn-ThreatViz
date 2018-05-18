package spnthreatviz;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@Controller
public class HomeController {
 
    @RequestMapping(value = "/")
    public String index() {
        return "index.html";
    }
}