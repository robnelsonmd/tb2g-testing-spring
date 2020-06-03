package org.springframework.samples.petclinic.service;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.samples.petclinic.model.PetType;
import org.springframework.samples.petclinic.repository.PetRepository;

import java.util.Arrays;
import java.util.Collection;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.then;

@ExtendWith(MockitoExtension.class)
class ClinicServiceImplTest {
    @Mock
    private PetRepository petRepository;

    @InjectMocks
    private ClinicServiceImpl service;

    @DisplayName("Test finding pet types when no types returned from repository")
    @Test
    void findPetTypesWithNoTypes() {
        // when
        Collection<PetType> foundPetTypes = service.findPetTypes();

        //then
        then(petRepository).should().findPetTypes();
        assertTrue(foundPetTypes.isEmpty());
    }

    @DisplayName("Test finding pet types when types returned from repository")
    @Test
    void findPetTypesWithTypes() {
        // given
        List<PetType> petTypes = Arrays.asList(new PetType(), new PetType());
        given(petRepository.findPetTypes()).willReturn(petTypes);

        // when
        Collection<PetType> foundPetTypes = service.findPetTypes();

        //then
        then(petRepository).should().findPetTypes();
        assertEquals(2, foundPetTypes.size());
        assertTrue(foundPetTypes.containsAll(petTypes));
    }
}