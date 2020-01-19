package SystemManage.DictionaryManage.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("dictionary")
public class DictionaryController {

    /**
     * 待开发页
      * @return
     */
    @RequestMapping("/manager")
    public String index(){
        return "dictionary/dictionary" ;
    }

}
