package com.tw.userapp.service.impl;

import com.tw.userapp.domain.*;
import com.tw.userapp.repository.*;
import com.tw.userapp.service.CandidateService;
import com.tw.userapp.service.dto.AcademicExperienceDTO;
import com.tw.userapp.service.dto.CandidateDTO;
import com.tw.userapp.service.dto.ProfessionalExperienceDTO;
import com.tw.userapp.service.mapper.*;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;
import java.util.stream.Collectors;

/**
 * Service Implementation for managing {@link Candidate}.
 */
@Service
@Transactional
public class CandidateServiceImpl implements CandidateService {

    private final Logger log = LoggerFactory.getLogger(CandidateServiceImpl.class);

    private final CandidateRepository candidateRepository;

    private final AddressRepository addressRepository;

    private final ProfessionalExperienceRepository professionalExperienceRepository;

    private final AcademicExperienceRepository academicExperienceRepository;
    private final CandidateMapper candidateMapper;

    private final AddressMapper addressMapper;



    private final CountryMapper countryMapper;

    private final ProfessionalExperienceMapper professionalExperienceMapper;

    private final AcademicExperienceMapper academicExperienceMapper;

    private final UserRepository userRepository;





	public CandidateServiceImpl(CandidateRepository candidateRepository, AddressRepository addressRepository,
                                ProfessionalExperienceRepository professionalExperienceRepository, AcademicExperienceRepository academicExperienceRepository, CandidateMapper candidateMapper,
                                AddressMapper addressMapper, CountryMapper countryMapper,
                                ProfessionalExperienceMapper professionalExperienceMapper, AcademicExperienceMapper academicExperienceMapper, UserRepository userRepository) {
		super();
		this.candidateRepository = candidateRepository;
		this.addressRepository = addressRepository;
		this.professionalExperienceRepository = professionalExperienceRepository;
        this.academicExperienceRepository = academicExperienceRepository;
        this.candidateMapper = candidateMapper;
		this.addressMapper = addressMapper;
		this.countryMapper = countryMapper;
		this.professionalExperienceMapper = professionalExperienceMapper;
        this.academicExperienceMapper = academicExperienceMapper;
        this.userRepository = userRepository;
	}

	@Override
    public CandidateDTO save(CandidateDTO candidateDTO) {
	    // TODO: Only use login if id isn't already defined
        log.debug("Request to save Candidate : {}", candidateDTO);

        Address address = addressMapper.toEntity(candidateDTO.getAddress());
        List<AcademicExperience> academicExperiences = academicExperienceMapper.toEntity(candidateDTO.getAcademicExperience());
        List<ProfessionalExperience> profExp = professionalExperienceMapper.toEntity(candidateDTO.getProfessionalExperience());
        Candidate candidate = candidateMapper.toEntity(candidateDTO);
        Optional<User> user = userRepository.findById(candidateDTO.getId());
        if(user.isPresent()) {
            user.get().setEmail(candidateDTO.getEmail());
            user.get().setLastName(candidateDTO.getLastName());
            user.get().setFirstName(candidateDTO.getFirstName());
            userRepository.save(user.get());
        }
        log.debug("Request to get user : {}", address);
        candidate.setUser(user.get());
        candidate.setId(user.get().getId());

        candidate.setAddress(address);


        candidate = candidateRepository.save(candidate);

        log.debug("candidate added : {}", candidate);


     //   address = addressRepository.save(address);

        log.debug("Request to save address : {}", address);

        if ( profExp != null) {
            for(ProfessionalExperience exp : profExp) {
                exp.candidate(candidate);
            }


            profExp = professionalExperienceRepository.saveAll(profExp);
        }
        log.debug("Adding academicalExperiences : {}", candidate);
        if ( academicExperiences != null) {
            for (AcademicExperience acad : academicExperiences) {
                acad.candidate(candidate);
            }
            academicExperiences = academicExperienceRepository.saveAll(academicExperiences);
        }
    /*    if(!profExp.isEmpty()) {
        		//TODO: remove experiences having that CandidateId which are not in the array
        	for(ProfessionalExperience exp: profExp) {

        		if(exp != null)
        			professionalExperienceRepository.save(exp);
            }
        } */

        return candidateMapper.toDto(candidate);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<CandidateDTO> findAll(Pageable pageable) {
        log.debug("Request to get all Candidates");
        return candidateRepository.findAll(pageable)
            .map(candidateMapper::toDto);
    }


    @Override
    @Transactional(readOnly = true)
    public Optional<CandidateDTO> findOne(String id) {
        log.debug("Request to get Candidate : {}", id);

          Optional<Candidate> candidate = candidateRepository.findById(id);
          Optional<CandidateDTO> candidateDTO = candidateRepository.findById(id)
          .map(candidateMapper::toDto);
          if(candidate.isPresent()) {

        	  candidateDTO.get().setAddress(addressMapper.toDto(candidate.get().getAddress()));
        	 List<ProfessionalExperience> professionalExp = candidate.get().
        			 								getProfessionalExperiences()
        			 								.stream()
        			 								.collect(Collectors.toList());

              List<AcademicExperience> academicExperiences = new ArrayList<>(candidate.get().
                  getAcademicExperiences());

        	 candidateDTO.get().setCountry(countryMapper.toDto(candidate.get().getCountry()));

        	 // we have a set of an object
        	 candidateDTO.get().setProfessionalExperience(professionalExperienceMapper.toDto(professionalExp));
              candidateDTO.get().setAcademicExperience(academicExperienceMapper.toDto(academicExperiences));
          }

        return candidateDTO;
    }

    @Override
    public void delete(String id) {
        log.debug("Request to delete Candidate : {}", id);
        candidateRepository.deleteById(id);
    }

    @Override
    public Optional<CandidateDTO> findOneByUserLogin(String login) {


	    //getting user instance  by login
	    Optional<User> user = userRepository.findOneByLogin(login);
        Optional<CandidateDTO> candidateDTO = Optional.of(new CandidateDTO());

	    if(user.isPresent()) {
	         Optional<Candidate> candidate = candidateRepository.findById(user.get().getId());
             candidateDTO =  candidate.map(candidateMapper::toDto);


             if(candidateDTO.isPresent() && !candidate.get().getProfessionalExperiences().isEmpty()) {
                 candidateDTO.get().setEmail(user.get().getEmail());
                 candidateDTO.get().setLastName(user.get().getLastName());
                 candidateDTO.get().setFirstName(user.get().getFirstName());
                 List<ProfessionalExperienceDTO> exps = professionalExperienceMapper
                                                .toDto(new ArrayList<>(candidate.get().getProfessionalExperiences()));

                 candidateDTO.get()
                     .setProfessionalExperience(exps);

                 List<AcademicExperienceDTO> acads = academicExperienceMapper
                     .toDto(new ArrayList<>(candidate.get().getAcademicExperiences()));
                 candidateDTO.get()
                             .setAcademicExperience(acads);

             }

        }
	    return candidateDTO;
    }
}
