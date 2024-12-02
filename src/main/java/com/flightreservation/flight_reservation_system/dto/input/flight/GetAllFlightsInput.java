package com.flightreservation.flight_reservation_system.dto.input.flight;

import com.flightreservation.flight_reservation_system.dto.input.common.PageableInput;

public class GetAllFlightsInput {

    private PageableInput pageableInput;

    public PageableInput getPageableInput() {
        return pageableInput;
    }

    public void setPageableInput(PageableInput pageableInput) {
        this.pageableInput = pageableInput;
    }
}
