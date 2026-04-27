package com.ccsw.Tutorial.prestamo.model;

import com.ccsw.Tutorial.common.pagination.PageableRequest;

import java.time.LocalDate;

public class PrestamoSearchDto {

    private PageableRequest pageable;

    private String title;

    private String clientName;

    /**
     * @return searchDate
     */
    public LocalDate getSearchDate() {
        return this.searchDate;
    }

    /**
     * @param searchDate new value of {@link #searchDate}.
     */
    public void setSearchDate(LocalDate searchDate) {
        this.searchDate = searchDate;
    }

    private LocalDate searchDate;

    /**
     * @return pageable
     */
    public PageableRequest getPageable() {

        return this.pageable;
    }

    /**
     * @param pageable new value of {@link #getPageable}.
     */
    public void setPageable(PageableRequest pageable) {

        this.pageable = pageable;
    }

    /**
     * @return title
     */
    public String getTitle() {

        return this.title;
    }

    /**
     * @param title new value of {@link #getTitle}.
     */
    public void setTitle(String title) {

        this.title = title;
    }

    /**
     * @return clientName
     */
    public String getClientName() {

        return this.clientName;
    }

    /**
     * @param clientName new value of {@link #getClientName}.
     */
    public void setClientName(String clientName) {

        this.clientName = clientName;
    }

}
