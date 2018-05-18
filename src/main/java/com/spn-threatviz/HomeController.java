@Controller
public class HomeController {
 
    @RequestMapping(value = "/")
    public String index() {
        return "index.html";
    }
}