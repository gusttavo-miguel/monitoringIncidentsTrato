package com.example.botTelegram.utis.serviceaideData;

import lombok.Getter;

@Getter
public class Credentials {
    private static final String CSM_APP_URL = "https://csm3.serviceaide.com";
    private static final String USER_AUTH_TOKEN = "apiusertokenslicetoken40285";
    private static final String SLICE_TOKEN = "VbxiZZ1IpsGe5T7L_l.t-Ho68629aCgt";
    private static final String WEBSERVICE_USER_NAME = "wsIncidentes@serviceaide.trato";
    private static final String WEBSERVICE_USER_PASSWORD = "vD7@6!Hcx1";
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

    public String getUSER_AUTH_TOKEN() {
        return USER_AUTH_TOKEN;
    }

    public String getSLICE_TOKEN() {
        return SLICE_TOKEN;
    }

    public String getWEBSERVICE_USER_NAME() {
        return WEBSERVICE_USER_NAME;
    }

    public String getWEBSERVICE_USER_PASSWORD() {
        return WEBSERVICE_USER_PASSWORD;
    }

    public String getAPI_URL() {
        return API_URL;
    }
}