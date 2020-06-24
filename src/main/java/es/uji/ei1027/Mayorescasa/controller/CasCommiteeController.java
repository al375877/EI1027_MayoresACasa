package es.uji.ei1027.Mayorescasa.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/cascommitee")
public class CasCommiteeController {

    @RequestMapping("/index")
    public String index(Model model) {
        return "cascommitee/index";
    }
}
