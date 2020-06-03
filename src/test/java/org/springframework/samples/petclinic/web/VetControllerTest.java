package org.springframework.samples.petclinic.web;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.samples.petclinic.model.Vet;
import org.springframework.samples.petclinic.model.Vets;
import org.springframework.samples.petclinic.service.ClinicService;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.then;

@ExtendWith(MockitoExtension.class)
class VetControllerTest {
    @Mock
    private ClinicService clinicService;

    @Mock
    private Map<String, Object> model;

    @InjectMocks
    private VetController controller;

    @Captor
    ArgumentCaptor<Vets> vetsCaptor;

    @DisplayName("Test model properly updated when no vets found")
    @Test
    public void testShowVetListWithNoVets() {
        // when
        String view = controller.showVetList(model);

        // then
        then(model).should().put(eq("vets"), vetsCaptor.capture());
        assertEquals("vets/vetList", view);
        assertTrue(vetsCaptor.getValue().getVetList().isEmpty());
    }

    @DisplayName("Test model properly updated when vets found")
    @Test
    public void testShowVetListWithVets() {
        // given
        List<Vet> vets = Arrays.asList(new Vet(), new Vet());
        given(clinicService.findVets()).willReturn(vets);

        // when
        String view = controller.showVetList(model);

        // then
        then(model).should().put(eq("vets"), vetsCaptor.capture());
        assertEquals("vets/vetList", view);
        assertEquals(2, vetsCaptor.getValue().getVetList().size());
        assertTrue(vetsCaptor.getValue().getVetList().containsAll(vets));
    }


    @DisplayName("Test show resources when no vets found")
    @Test
    public void testShowResourcesVetListWithNoVets() {
        // when
        Vets foundVets = controller.showResourcesVetList();

        // then
        assertTrue(foundVets.getVetList().isEmpty());
    }

    @DisplayName("Test show resources when vets found")
    @Test
    public void testShowResourcesVetListWithVets() {
        // given
        List<Vet> vets = Arrays.asList(new Vet(), new Vet());
        given(clinicService.findVets()).willReturn(vets);

        // when
        Vets foundVets = controller.showResourcesVetList();

        // then
        assertEquals(2, foundVets.getVetList().size());
        assertTrue(foundVets.getVetList().containsAll(vets));
    }
}