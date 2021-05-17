package com.tw.userapp.web.rest.feignClients;

import com.tw.userapp.client.AuthorizedFeignClient;
import com.tw.userapp.service.models.Skill;
import com.tw.userapp.service.models.UserSkills;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@AuthorizedFeignClient(name = "skillapp")
@RequestMapping("/api")
public interface SkillService {

    @RequestMapping(value = "/skills/{id}", method = RequestMethod.GET)
    ResponseEntity<Skill> getSkill(@RequestParam("id") Long id );
}
