package com.flightreservation.flight_reservation_system.dto.input.common;

import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

public class PageableInput {

    private int size;
    private int number;
    private String sortBy;
    private Sort.Direction sortOrder;

    public PageableInput(int size, int number, String sortBy, Sort.Direction sortOrder) {
        this.size = size;
        this.number = number;
        this.sortBy = sortBy;
        this.sortOrder = sortOrder;
    }

    public int getSize() {
        return size;
    }

    public void setSize(int size) {
        this.size = size;
    }

    public int getNumber() {
        return number;
    }

    public void setNumber(int number) {
        this.number = number;
    }

    public String getSortBy() {
        return sortBy;
    }

    public void setSortBy(String sortBy) {
        this.sortBy = sortBy;
    }

    public Sort.Direction getSortOrder() {
        return sortOrder;
    }

    public void setSortOrder(Sort.Direction sortOrder) {
        this.sortOrder = sortOrder;
    }

    public Pageable toPageable() {
        String adjustedSortBy = "id".equals(sortBy) ? "_id" : sortBy;
        Sort sort = Sort.by(sortOrder, adjustedSortBy);
        return PageRequest.of(number, Math.min(size, 100), sort);
    }
}
