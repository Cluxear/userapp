package com.tw.userapp.web.rest.feignClients;

import com.tw.userapp.client.AuthorizedFeignClient;
import com.tw.userapp.service.models.UserSkillDTO;
import com.tw.userapp.service.models.UserSkills;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.User;
import org.springframework.web.bind.annotation.*;

@AuthorizedFeignClient(name = "dataapp")
@RequestMapping(value = "/api")
public interface DataappService {

    @RequestMapping (value = "/user-skills/userId/{user_id}", method = RequestMethod.GET)
    UserSkills getListOfUserSkills(@RequestParam("user_id") String user_id);

    @RequestMapping (value = "/user-skills/", method = RequestMethod.POST)
    ResponseEntity<UserSkillDTO> createUserSkill(@RequestBody UserSkillDTO userSkillDTO);

    @RequestMapping (value = "/user-skills/", method = RequestMethod.PUT)
    ResponseEntity<UserSkillDTO> updateUserSkill(@RequestBody UserSkillDTO userSkillDTO);


}
