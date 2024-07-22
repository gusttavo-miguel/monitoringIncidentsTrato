package com.example.botTelegram.service;

import com.example.botTelegram.newPojo.Return;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.google.gson.Gson;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;

public class ServiceaidService {

//    private final String apiUrl = "https://csm3.serviceaide.com/csmconnector/Ticket"; // Substitua pela URL da API
    private final String apiUrl = "https://csm3.serviceaide.com/csmconnector/Ticket/recent?duration=180&created=true";
//    private final String apiUrl = "https://csm3.serviceaide.com/csmconnector/Ticket?filter=((NonTranslatedTicketStatus%20ne%20'Archive'%20and%20NonTranslatedTicketStatus%20ne%20'Request%20-%20Delete'%20and%20NonTranslatedTicketStatus%20ne%20'Closed'%20or%20(NonTranslatedTicketStatus%20eq%20'Closed'%20and%20LastModTimestamp%20gt%201716001201))%20and%20(OrgStatus%20eq%200)%20and%20(((NonTranslatedTicketStatus%20eq%20'New'%20or%20NonTranslatedTicketStatus%20eq%20'Active'%20or%20NonTranslatedTicketStatus%20eq%20'Escalated'%20or%20NonTranslatedTicketStatus%20eq%20'Queued')%20and%20(TypeName%20eq%20'incident'))))%26INClause%3DAssignedGroupID~~-1%2C44%2C42%2C38%2C37%2C2%26%24inlinecount%3Dallpages%26%24select%3DAssignedCaseID%2CAssignedGroup%2CAssignedID%2CAssignedIndividual%2CCategorizationCategory%2CCategorizationClass%2CCategorizationItem%2CCategorizationType%2CCreationTimestamp%2CDescription%2CHasAttachments%2CLastModTimestamp%2CLockedByExternalUserId%2CMdrElementID%2CPriority%2CPriorityCode%2CReasonCode%2CRequesterOrgHierarchicalPath%2CRequesterOrgName%2CRequesterRootOrgName%2CRequesterUserName%2CRequesterVIPFlag%2CSLAComplianceStatus%2CSLAResolveByTimestamp%2CTicketStatus%2CTranslatedSLAComplianceStatus%2CTranslatedTypeName%2CTypeName%2CRequestedForOrgName%2CRequestedForUserName%26categorytype%3Dlist%26CTGREQUEST%3DTRUE%26_dc%3D1721256720470%26%24format%3Djson%26%24skip%3D0%26%24top%3D25%26%24orderby%3DCreationTimestamp%20desc%26%24inlinecount%3Dallpages";

    public String getIncidents() throws JsonProcessingException {
        RestTemplate restTemplate = new RestTemplate();

        HttpHeaders headers = new HttpHeaders();
        headers.set("csm_app_url", "https://csm3.serviceaide.com");
        headers.set("user_auth_token", "apiusertokenslicetoken40285");
        headers.set("slice_token", "VbxiZZ1IpsGe5T7L_l.t-Ho68629aCgt");
        headers.set("webservice_user_name", "wsIncidentes@serviceaide.trato");
        headers.set("webservice_user_password", "vD7@6!Hcx1");

        HttpEntity<String> entity = new HttpEntity<>(headers);

        ResponseEntity<String> response = restTemplate.exchange(
                apiUrl,
                HttpMethod.GET,
                entity,
                String.class
        );

        String json = response.getBody();

        Gson gson = new Gson();
        Return returno = gson.fromJson(json, Return.class);

        StringBuilder temp = new StringBuilder();
        ArrayList<String> tickets = new ArrayList<>();

        for (int i = 0; i <= returno.data().totalCount(); i++) {

            if (returno.data().items().get(i).TicketStatus().equals("Active") || returno.data().items().get(i).TicketStatus().equals("Queued") && returno.data().items().get(i).TranslatedTypeName().equals("Incident")) {

            var ticketIdentifier = "Ticket: ".concat(returno.data().items().get(i).TicketIdentifier().concat("\n"));
            var ticketStatus = "Status: ".concat(returno.data().items().get(i).TicketStatus().concat("\n"));
            var description = "Descrição: ".concat(returno.data().items().get(i).Description().concat("\n"));
//                var totalTickets =  returno.data().totalCount();

            temp.append(ticketIdentifier).append(ticketStatus).append(description);
            tickets.add(String.valueOf(temp));


//                Index 0 out of bounds for length 0
            }
        }
        System.out.println(tickets);

        return String.valueOf(tickets);
    }
}