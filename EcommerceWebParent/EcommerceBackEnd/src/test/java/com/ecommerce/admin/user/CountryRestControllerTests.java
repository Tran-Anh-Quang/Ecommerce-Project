package com.ecommerce.admin.user;

import com.ecommerce.admin.setting.country.repository.CountryRepository;
import com.ecommerce.common.entity.Country;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
public class CountryRestControllerTests {

    @Autowired
    MockMvc mockMvc;

    @Autowired
    ObjectMapper objectMapper;

    @Autowired
    CountryRepository countryRepository;

    @Test
    @WithMockUser(username = "dinosaurous9x@gmail.com", password = "12345678", roles = "ADMIN")
    public void testListCountries() throws Exception{
        String url = "/countries/list";
        MvcResult mvcResult = mockMvc.perform(MockMvcRequestBuilders.get(url))
                .andExpect(status().isOk())
                .andDo(print())
                .andReturn();

        String jsonResponse = mvcResult.getResponse().getContentAsString();
        Country[] countries = objectMapper.readValue(jsonResponse, Country[].class);

        assertThat(countries).hasSizeGreaterThan(0);
    }


    @Test
    @WithMockUser(username = "nam@codejava.net", password = "something", roles = "ADMIN") public void testCreateCountry() throws JsonProcessingException, Exception {
        String url="/countries/save";
        String countryName = "Germany";
        String countryCode = "DE";
        Country country = new Country(countryName, countryCode);
        MvcResult result = mockMvc.perform(post(url).contentType("application/json")
                        .content(objectMapper.writeValueAsString(country))
                        .with(csrf()))
                . andDo (print())
                .andExpect(status().isOk())
                . andReturn();
        String response = result.getResponse().getContentAsString();
        Integer countryId = Integer.parseInt(response);

        Optional<Country> findById = countryRepository.findById(countryId);
        assertThat(findById.isPresent());

        Country savedCountry = findById.get();
        assertThat(savedCountry.getName()).isEqualTo(countryName);
    }

}
