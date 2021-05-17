package com.tw.userapp.web.rest;


import com.tw.userapp.config.Constants;
import com.tw.userapp.domain.User;
import com.tw.userapp.security.AuthoritiesConstants;
import com.tw.userapp.service.CandidateService;
import com.tw.userapp.service.EmployeeService;
import com.tw.userapp.service.UserService;
import com.tw.userapp.service.dto.AddressDTO;
import com.tw.userapp.service.dto.CandidateDTO;
import com.tw.userapp.service.dto.EmployeeDTO;
import com.tw.userapp.service.dto.UserDTO;
import com.tw.userapp.service.models.CVUserDetails;
import com.tw.userapp.service.models.InfoPerso;
import com.tw.userapp.web.rest.errors.BadRequestAlertException;
import com.tw.userapp.web.rest.vm.ManagedUserVM;
import io.github.jhipster.web.util.HeaderUtil;
import io.github.jhipster.web.util.PaginationUtil;
import io.github.jhipster.web.util.ResponseUtil;
import org.jboss.resteasy.client.jaxrs.ResteasyClientBuilder;
import org.keycloak.OAuth2Constants;
import org.keycloak.admin.client.Keycloak;
import org.keycloak.admin.client.KeycloakBuilder;
import org.keycloak.admin.client.resource.RealmResource;
import org.keycloak.admin.client.resource.UsersResource;
import org.keycloak.representations.idm.CredentialRepresentation;
import org.keycloak.representations.idm.RoleRepresentation;
import org.keycloak.representations.idm.UserRepresentation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.env.Environment;
import org.springframework.core.io.FileSystemResource;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.*;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.ws.rs.core.Response;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.URISyntaxException;
import java.util.Arrays;
import java.util.List;

/**
 * REST controller for managing users.
 * <p>
 * This class accesses the {@link com.tw.userapp.domain.User} entity, and needs to fetch its collection of authorities.
 * <p>
 * For a normal use-case, it would be better to have an eager relationship between User and Authority,
 * and send everything to the client side: there would be no View Model and DTO, a lot less code, and an outer-join
 * which would be good for performance.
 * <p>
 * We use a View Model and a DTO for 3 reasons:
 * <ul>
 * <li>We want to keep a lazy association between the user and the authorities, because people will
 * quite often do relationships with the user, and we don't want them to get the authorities all
 * the time for nothing (for performance reasons). This is the #1 goal: we should not impact our users'
 * application because of this use-case.</li>
 * <li> Not having an outer join causes n+1 requests to the database. This is not a real issue as
 * we have by default a second-level cache. This means on the first HTTP call we do the n+1 requests,
 * but then all authorities come from the cache, so in fact it's much better than doing an outer join
 * (which will get lots of data from the database, for each HTTP call).</li>
 * <li> As this manages users, for security reasons, we'd rather have a DTO layer.</li>
 * </ul>
 * <p>
 * Another option would be to have a specific JPA entity graph to handle this case.
 */
@RestController
@RequestMapping("/api")
public class UserResource {

    private final Logger log = LoggerFactory.getLogger(UserResource.class);

    private static final String ENTITY_NAME = "userappUser";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    @Autowired
    private Environment env;

    private final UserService userService;

    private final CandidateService candidateService;

    private final EmployeeService employeeService;



    public UserResource(UserService userService, CandidateService candidateService, EmployeeService employeeService) {
        this.userService = userService;
        this.candidateService = candidateService;
        this.employeeService = employeeService;
    }

    /**
     * {@code GET /users} : get all users.
     *
     * @param pageable the pagination information.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body all users.
     */
    @GetMapping("/users")
    public ResponseEntity<List<UserDTO>> getAllUsers(Pageable pageable) {

        final Page<UserDTO> page = userService.getAllManagedUsers(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }


    /**
     * Gets a list of all roles.
     * @return a string list of all roles.
     */
    @GetMapping("/users/authorities")
    @PreAuthorize("hasAuthority(\"" + AuthoritiesConstants.ADMIN + "\")")
    public List<String> getAuthorities() {
        return userService.getAuthorities();
    }

    /**
     * {@code GET /users/:login} : get the "login" user.
     *
     * @param login the login of the user to find.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the "login" user, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/users/{login:" + Constants.LOGIN_REGEX + "}")
    public ResponseEntity<UserDTO> getUser(@PathVariable String login) {
        log.debug("REST request to get User : {}", login);
        return ResponseUtil.wrapOrNotFound(
            userService.getUserWithAuthoritiesByLogin(login)
                .map(UserDTO::new));
    }

    @GetMapping("/users/{login}")
    public ResponseEntity<UserDTO> getUserByLogin(@PathVariable String login) {
        log.debug("REST request to get User : {}", login);

        return ResponseUtil.wrapOrNotFound(
            userService.getUserWithAuthoritiesByLogin(login)
                .map(UserDTO::new));
    }
    @GetMapping("/users/id/{id}")
    public ResponseEntity<User> getUserById(@PathVariable String id) {
        log.debug("REST request to get User with id : {}", id);

        return ResponseUtil.wrapOrNotFound(
            userService.getUserById(id));

    }

    @PutMapping("/users")
    public ResponseEntity<UserDTO> updateUser(@RequestBody UserDTO userDTO) throws URISyntaxException {
        log.debug("REST request to update user : {}", userDTO);
        if (userDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        userService.updateUser(userDTO.getFirstName(), userDTO.getLastName(), userDTO.getEmail(), userDTO.getLangKey(), userDTO.getImageUrl());
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, userDTO.getId()))
            .body(userDTO);

    }

    @PostMapping("/users/detailsOfCV")
    public ResponseEntity<CVUserDetails>  extractCVDetails(@RequestParam("file") MultipartFile cv) throws URISyntaxException {


        RestTemplate restTemplate = new RestTemplate();
        /**
         * get userdetails from extraction api
          */

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.MULTIPART_FORM_DATA);

        MultiValueMap<String, Object> bodyMap = new LinkedMultiValueMap<>();
         bodyMap.add("file",  new FileSystemResource(convert(cv)));

        HttpEntity<MultiValueMap<String, Object>> requestEntity
            = new HttpEntity<>(bodyMap, headers);

        restTemplate.postForLocation("http://localhost:5000/uploads", requestEntity);


        /** Extract perso info from cv **/
        log.debug("Extracting data from cv {} ", cv.getOriginalFilename());


        CVUserDetails userDetails = new CVUserDetails();
        InfoPerso infoPerso = new InfoPerso();
        userDetails =   restTemplate.getForEntity("http://localhost:5000/info_perso/"+ cv.getOriginalFilename(), CVUserDetails.class).getBody();

        log.debug("REST request to update user : {}", userDetails.toString());

        return ResponseEntity.ok(userDetails);

    }
    @PostMapping("/users/register")
    public ResponseEntity<ManagedUserVM> registerUser(@RequestBody ManagedUserVM userDTO) {

            log.debug(" Trying to register new user {}:", userDTO);
        // getting access token from keycloak server
        Keycloak keycloak = KeycloakBuilder.builder().serverUrl(env.getProperty("keycloak.auth-server-url"))
            .grantType(OAuth2Constants.CLIENT_CREDENTIALS).realm("jhipster").clientId("user_app")
            .clientSecret("671a12a0-7fc3-4d28-bd5b-395cc2fbba0d")
            .resteasyClient(new ResteasyClientBuilder().connectionPoolSize(10).build()).build();
        keycloak.tokenManager().getAccessToken();

        // creating a user instance
        UserRepresentation user = new UserRepresentation();
        user.setEnabled(true);
        user.setUsername(userDTO.getLogin());
        user.setFirstName(userDTO.getFirstName());
        user.setLastName(userDTO.getLastName());
        user.setEmail(userDTO.getEmail());

        // Get realm
        RealmResource realmResource = keycloak.realm("jhipster");
        UsersResource usersRessource = realmResource.users();

        Response response = usersRessource.create(user);
        log.debug("REST request to add user to keycloak with status : {} and response body: {}", response.getStatus(), response.getHeaders());
//        userDTO.setStatusCode(response.getStatus());
//        userDTO.setStatus(response.getStatusInfo().toString());

        if (response.getStatus() == 201 || response.getStatus() == 409) {

           // String userId = CreatedResponseUtil.getCreatedId(response);

            String userId = response.getLocation().getPath().replaceAll(".*/([^/]+)$", "$1");
            log.info("Created userId {}", userId);


            // create password credential
            CredentialRepresentation passwordCred = new CredentialRepresentation();
            passwordCred.setTemporary(false);
            passwordCred.setType(CredentialRepresentation.PASSWORD);
            passwordCred.setValue(userDTO.getPassword());

            org.keycloak.admin.client.resource.UserResource userResource = usersRessource.get(userId);

            // Set password credential
            userResource.resetPassword(passwordCred);

            // Get realm role candidate
            RoleRepresentation realmRoleCandidate = realmResource.roles().get("ROLE_CANDIDATE").toRepresentation();
            RoleRepresentation realmRoleUser = realmResource.roles().get("ROLE_USER").toRepresentation();
            RoleRepresentation realmRoleEmployee = realmResource.roles().get("ROLE_USER").toRepresentation();

            // Assign realm role candidate to user
            if(userDTO.getAuthorities().stream().anyMatch(val -> val.equals("ROLE_CANDIDATE") ))
                userResource.roles().realmLevel().add(Arrays.asList(realmRoleUser, realmRoleCandidate ));
            else userResource.roles().realmLevel().add(Arrays.asList(realmRoleUser, realmRoleEmployee ));

            // creating an instance of user
            User newUser = userService.registerUser(userDTO);
            // creating an instance of employee or candidate based on authority selected.
            if(userDTO.getAuthorities().stream().anyMatch(val -> val.equals("ROLE_CANDIDATE") )){
                CandidateDTO candidateDTO = new CandidateDTO();
                candidateDTO.setId(newUser.getId());
                AddressDTO address = new AddressDTO();
                address.setCity("");
                address.setStreet("");
                address.setState("");

                candidateDTO.setAddress(address);
                candidateService.save(candidateDTO);
            }
            else if( userDTO.getAuthorities().stream().anyMatch(val -> val.equals("ROLE_EMPLOYEE") )) {

                EmployeeDTO employeeDTO = new EmployeeDTO();
                employeeDTO.setUserId(newUser.getId());
                employeeDTO.setDegreeId(userDTO.getDegreeId());
                employeeDTO.setPositionId(userDTO.getPositionId());
                employeeDTO.setSeniorityLevelId(userDTO.getSeniorityLevelId());
                employeeDTO.setSalary(userDTO.getSalary());
                employeeService.save(employeeDTO);

            }
        }
        return ResponseEntity.ok(userDTO);

    }
    public static File convert(MultipartFile file)
    {
        File convFile = new File(file.getOriginalFilename());
        try {
            convFile.createNewFile();
            FileOutputStream fos = new FileOutputStream(convFile);
            fos.write(file.getBytes());
            fos.close();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

        return convFile;
    }
}
