package it.ServerHttps.main;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

@org.springframework.stereotype.Controller
public class Controller {

	
	@RequestMapping(value="/video", method=RequestMethod.GET)
    public @ResponseBody String getVideoList() {
		return "qualcosa";}
}
