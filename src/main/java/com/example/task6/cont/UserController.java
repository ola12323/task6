package com.example.task6.cont;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import com.example.task6.model.ParamDto;
import com.example.task6.serv.UserService;

@Controller
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @ModelAttribute("paramDto")
    public ParamDto registerDto() {
        return new ParamDto();
    }

}
