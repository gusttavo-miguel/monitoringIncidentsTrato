package com.example.botTelegram.utis.serviceaideData;

import lombok.Data;

@Data
public class Credentials {
    private final String CSM_APP_URL = "https://csm3.serviceaide.com";
    private final String USER_AUTH_TOKEN = "apiusertokenslicetoken40285";
    private final String SLICE_TOKEN = "VbxiZZ1IpsGe5T7L_l.t-Ho68629aCgt";
    private final String WEBSERVICE_USER_NAME = "wsIncidentes@serviceaide.trato";
    private final String WEBSERVICE_USER_PASSWORD = "vD7@6!Hcx1";
    private final String API_URL_PATH = "https://csm3.serviceaide.com/csmconnector/Ticket";
    private final String FILTER = "?filter=((NonTranslatedTicketStatus ne 'Archive' " +
            "and NonTranslatedTicketStatus ne 'Request - Delete' " +
            "and NonTranslatedTicketStatus ne 'Closed' or (NonTranslatedTicketStatus eq 'Closed' " +
            "and LastModTimestamp gt 1716433200)) " +
            "and (OrgStatus eq 0) " +
            "and (((AssignedGroupID eq '37' or AssignedGroupID eq '38') " +
            "and (NonTranslatedTicketStatus eq 'New' or NonTranslatedTicketStatus eq 'Active' or NonTranslatedTicketStatus eq 'Queued') " +
            "and (TypeName eq 'incident') and (TicketAgingRange eq '0-2 days'))))";
    private final String API_URL = API_URL_PATH + FILTER;
}