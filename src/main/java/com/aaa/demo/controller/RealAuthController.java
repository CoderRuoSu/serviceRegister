package com.aaa.demo.controller;

import com.aaa.demo.service.RealAuthService;
import com.aaa.demo.util.AutoDispatch;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Map;

/**
 * Created by ruosu on 2018/4/26.
 */
@RestController
@AutoDispatch(serviceInterface= {RealAuthService.class})
@RequestMapping("demo")
public class RealAuthController extends WelcomeController {

    @RequestMapping(value="/{functionName:[a-zA-Z-]+}Auto", method= RequestMethod.POST)
    public @ResponseBody
    Object dipatch(HttpServletRequest request, HttpServletResponse response, @PathVariable("functionName") String name, @RequestBody Map<String, Object> model) {
        return autoDispatch(request, response, name, model);
    }
    @RequestMapping(value="/{functionName:[a-zA-Z-]+}Auto", method=RequestMethod.GET)
    public @ResponseBody Object dipatch2(HttpServletRequest request, HttpServletResponse response, @PathVariable("functionName") String name, @RequestParam Map<String, Object> model) {
        return autoDispatch(request, response, name, model);
    }

}
