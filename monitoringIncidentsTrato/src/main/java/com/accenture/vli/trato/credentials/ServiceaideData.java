package com.accenture.vli.trato.credentials;

public class ServiceaideData {
    private static final String CSM_APP_URL = "https://csm3.serviceaide.com";
    private static final String API_URL_PATH = "https://csm3.serviceaide.com/csmconnector/Ticket";
    private static final String FILTER = "?filter=((NonTranslatedTicketStatus ne 'Archive' " +
            "and NonTranslatedTicketStatus ne 'Request - Delete' " +
            "and NonTranslatedTicketStatus ne 'Closed' or (NonTranslatedTicketStatus eq 'Closed' " +
            "and LastModTimestamp gt 1716433200)) " +
            "and (OrgStatus eq 0) " +
            "and (((AssignedGroupID eq '37' or AssignedGroupID eq '38') " +
            "and (NonTranslatedTicketStatus eq 'New' or NonTranslatedTicketStatus eq 'Active' or NonTranslatedTicketStatus eq 'Queued') " +
            "and (TypeName eq 'incident') and (TicketAgingRange eq '0-2 days'))))";
    private static final String API_URL = API_URL_PATH + FILTER;

    public String getCSM_APP_URL() {
        return CSM_APP_URL;
    }

    public String getAPI_URL() {
        return API_URL;
    }
}