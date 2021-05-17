package com.tw.userapp.web.rest;


import com.tw.userapp.security.SecurityUtils;
import com.tw.userapp.service.CandidateService;
import com.tw.userapp.service.dto.CandidateDTO;
import com.tw.userapp.service.models.*;
import com.tw.userapp.web.rest.errors.BadRequestAlertException;
import com.tw.userapp.web.rest.feignClients.DataappService;
import com.tw.userapp.web.rest.feignClients.SkillService;

import io.github.jhipster.web.util.HeaderUtil;
import io.github.jhipster.web.util.PaginationUtil;
import io.github.jhipster.web.util.ResponseUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.env.Environment;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

/**
 * REST controller for managing {@link com.tw.userapp.domain.Candidate}.
 */
@RestController
@RequestMapping("/api")
public class CandidateResource {

    private final Logger log = LoggerFactory.getLogger(CandidateResource.class);

    private static final String ENTITY_NAME = "userappCandidate";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final CandidateService candidateService;

    private DataappService dataappService;

    private SkillService skillService;

    @Autowired
    private Environment env;

    @Autowired
    public CandidateResource(CandidateService candidateService, DataappService dataappService,SkillService skillService) {
        this.candidateService = candidateService;
        this.dataappService = dataappService;
        this.skillService = skillService;
    }

    /**
     * {@code POST  /candidates} : Create a new candidate.
     *
     * @param candidateDTO the candidateDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new candidateDTO, or with status {@code 400 (Bad Request)} if the candidate has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/candidates")
    public ResponseEntity<CandidateDTO> createCandidate(@RequestBody CandidateDTO candidateDTO) throws URISyntaxException {
        log.debug("REST request to save Candidate : {}", candidateDTO);
        if (candidateDTO.getId() != null) {
            throw new BadRequestAlertException("A new candidate cannot already have an ID", ENTITY_NAME, "idexists");
        }
        if (Objects.isNull(candidateDTO.getLogin())) {
            throw new BadRequestAlertException("Invalid association value provided", ENTITY_NAME, "null");
        }
        // updating the user Skills list
        List<Skill> newUserSkills = candidateDTO.getSkills();

        // get old userSkills
        /*
                we have a new list of userSkills with id and name,
         */
        for(Skill skill: newUserSkills) {
            // check if userSkill with skill Id exists

        }
        CandidateDTO result = candidateService.save(candidateDTO);
        return ResponseEntity.created(new URI("/api/candidates/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId()))
            .body(result);
    }

    /**
     * {@code PUT  /candidates} : Updates an existing candidate.
     *
     * @param candidateDTO the candidateDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated candidateDTO,
     * or with status {@code 400 (Bad Request)} if the candidateDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the candidateDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/candidates")
    public ResponseEntity<CandidateDTO> updateCandidate(@RequestBody CandidateDTO candidateDTO) throws URISyntaxException {
        log.debug("REST request to update Candidate : {}", candidateDTO);
        if (candidateDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        CandidateDTO result = candidateService.save(candidateDTO);
        // updating the user Skills list
        List<Skill> newUserSkills = candidateDTO.getSkills();


        List<UserSkillDTO> oldUserSkills = dataappService.getListOfUserSkills(candidateDTO.getId()).getUserSkills();

       // check if newUserSkill id exists in oldUserSkills


        for(Skill skill: newUserSkills) {

            // check if the skill is present
            Optional<UserSkillDTO> currentUserSkill = oldUserSkills
                .stream()
                .filter(e -> e.getSkillId().equals(skill.getId()))
                .findFirst();

            // update userSkill with new SkillLevel value.
            if(currentUserSkill.isPresent()){

                currentUserSkill.get().setSkillLevel(skill.getSkillLevel());
                dataappService.updateUserSkill(currentUserSkill.get());
            }
            // create new userSkill
            else {
                UserSkillDTO newUserSkill = new UserSkillDTO();
                newUserSkill.setSkillId(skill.getId());
                newUserSkill.setUserId(result.getId());
                newUserSkill.setSkillLevel(skill.getSkillLevel());

                dataappService.createUserSkill(newUserSkill);
                // create new userSkill with new skillId and skillLevel.
            }
        }
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, candidateDTO.getId()))
            .body(result);
    }

    /**
     * {@code GET  /candidates} : get all the candidates.
     *
     * @param pageable the pagination information.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of candidates in body.
     */
    @GetMapping("/candidates")
    public ResponseEntity<List<CandidateDTO>> getAllCandidates(Pageable pageable)  {

        // Create the API client with the main uri and the version of the API
   //     ClientApi clientApi = new ClientApi("https://tst018.sxondemand.fr/tst18/rest/", "v2");
// Set the api key
   //     clientApi.setApiKey("4c8660faaae0584bfd544c2e3f82a897");



        log.debug("REST request to get a page of Candidates");
        Page<CandidateDTO> page = candidateService.findAll(pageable);

  /*      try {
            CandidatesService  candidateService = new CandidatesService(clientApi);
            CandidateList candidateList = candidateService.query();
            for(Candidate cand : candidateList.getItems() ) {
                    CandidateDTO newCand = new CandidateDTO();
                    newCand.setFirstName(cand.getFirstName());
                    newCand.setLastName(cand.getLastName());
            }
        }
        catch (Exception e) {
            e.printStackTrace(System.err);
        }*/

        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /candidates/:id} : get the "id" candidate.
     *
     * @param id the id of the candidateDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the candidateDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/candidates/{id}")
    public ResponseEntity<CandidateDTO> getCandidate(@PathVariable String id) {
        log.debug("REST request to get Candidate : {}", id);

        Optional<CandidateDTO> candidateDTO = candidateService.findOne(id);
        RestTemplate restTemplate = new RestTemplate();
        // get the list of user skills for dataService
        UserSkills userSkills = dataappService.getListOfUserSkills(id);
        Skills skills = new Skills();
        for( UserSkillDTO userSkill: userSkills.getUserSkills()) {

            ResponseEntity<Skill> skill = skillService.getSkill(userSkill.getSkillId());
            skill.getBody().setSkillLevel(userSkill.getSkillLevel());
            skills.addSkill(skill.getBody());
        }
        // get the skills info from the skillService
        candidateDTO.get().setSkills(skills.getSkillList());


        return ResponseUtil.wrapOrNotFound(candidateDTO);
    }

    /**
     * {@code POST  /candidateScore} :
     *
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the candidateDTO, or with status {@code 404 (Not Found)}.
     */
    @PostMapping("/candidateScore/")
    public List<Integer> getCandidateScoreJobPost(@RequestBody CandidateDTO candidate, @RequestBody JobPost jobPost) {
        log.debug("REST request to get Candidate {} score for job post {}", candidate.getId(), jobPost.getId() );

       // Set the years of experience for candidate based on jobpost in question

        // You check the professionalExperiences for a position


        return null;
    }

    @GetMapping("/candidates/login/{login}")
    public ResponseEntity<CandidateDTO> getCandidateByLogin(@PathVariable String login) {
        log.debug("REST request to get Candidate : {}", login);

        Optional<CandidateDTO> candidateDTO = candidateService.findOneByUserLogin(login);
        RestTemplate restTemplate = new RestTemplate();
        // get the list of user skills for dataService
        if(candidateDTO.isPresent()){
            log.debug("Candidate ID HERRRRRRRRRRRRRRRRRRRRRE: {}", candidateDTO.get().getId());
            UserSkills userSkills = dataappService.getListOfUserSkills(candidateDTO.get().getId());
            Skills skills = new Skills();
            for( UserSkillDTO userSkill: userSkills.getUserSkills()) {

                ResponseEntity<Skill> skill = skillService.getSkill(userSkill.getSkillId());
                if(skill.hasBody()) {

                    skill.getBody().setSkillLevel(userSkill.getSkillLevel());
                    skills.addSkill(skill.getBody());
                }
                candidateDTO.get().setSkills(skills.getSkillList());
            }


        }

        return ResponseUtil.wrapOrNotFound(candidateDTO);
    }
    @GetMapping("/candidates/skills/")
    public ResponseEntity<List<Skill>> getCandidateSkills() {
        log.debug("REST request to get current user List of skills");
        Optional<CandidateDTO> candidateDTO = candidateService.findOneByUserLogin(SecurityUtils.getCurrentUserLogin().get());
        Skills skills = new Skills();
        if(candidateDTO.isPresent()) {
            UserSkills userSkills = dataappService.getListOfUserSkills(candidateDTO.get().getId());

            for( UserSkillDTO userSkill: userSkills.getUserSkills()) {

                ResponseEntity<Skill> skill = skillService.getSkill(userSkill.getSkillId());
                skills.addSkill(skill.getBody());
            }
        }

        return ResponseEntity.ok(skills.getSkillList());
    }
    @GetMapping("/candidate/info")
    public CandidateDTO getCandidateInfoFromExtractionService() {


        return null;
    }
    /**
     * {@code DELETE  /candidates/:id} : delete the "id" candidate.
     *
     * @param id the id of the candidateDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/candidates/{id}")
    public ResponseEntity<Void> deleteCandidate(@PathVariable String id) {

        log.debug("REST request to delete Candidate : {}", id);
        candidateService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id)).build();
    }


}
