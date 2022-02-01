package com.uk.companieshouse.controller;

import com.uk.companieshouse.model.CompaniesHouseResponse;
import com.uk.companieshouse.service.CompaniesHouseService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static com.uk.companieshouse.utils.TestHelper.TESTCRN;
import static com.uk.companieshouse.utils.TestHelper.getCompaniesHouseResponseList;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class CompaniesHouseControllerTest {
    private CompaniesHouseController companiesHouseController;
    private CompaniesHouseService mockCompaniesHouseService;
    private final List<CompaniesHouseResponse> expectedCompaniesHouseResponse = getCompaniesHouseResponseList();

    @BeforeEach
    void setUp() {
        mockCompaniesHouseService = mock(CompaniesHouseService.class);
        companiesHouseController = new CompaniesHouseController(mockCompaniesHouseService);
    }

    @Test
    void getCompaniesHouse_whenCRNIsPassed_shouldReturnCompaniesHouseResponse() {
        when(mockCompaniesHouseService.getCompaniesHouseResponse(TESTCRN)).
                thenReturn(expectedCompaniesHouseResponse);

        List actualCompaniesHouseResponseList = companiesHouseController.getCompaniesHouseResponse(TESTCRN);

        assertEquals(expectedCompaniesHouseResponse, actualCompaniesHouseResponseList);
    }

    @Test
    void getCompaniesHouse_whenCRNWithSpecialCharactersIsPassed_shouldThrowBadRequestError() {
        try {
            companiesHouseController.getCompaniesHouseResponse("msf@£@$SDFSDFSDF12313");
        } catch (Exception exception) {
            assertEquals("400 CRN should only contain alphanumeric characters", exception.getMessage());
        }
    }
}
