package soa.web;

import java.util.HashMap;
import java.util.Map;

import org.apache.camel.ProducerTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;


@Controller
public class SearchController {

	@Autowired
	  private ProducerTemplate producerTemplate;

	@RequestMapping("/")
    public String index() {
        return "index";
    }


    @RequestMapping(value="/search")
    @ResponseBody
    public Object search(@RequestParam("q") String q) {
        
        Map<String, Object> headers = new HashMap<String, Object>();
        
        String[] query = q.split("max:");
        
        headers.put("CamelTwitterKeywords", query[0]);
        
        if(query.length > 1){
            headers.put("CamelTwitterCount", query[1]);
        }
        
        return producerTemplate.requestBodyAndHeaders("direct:search", "", headers);
    }
}