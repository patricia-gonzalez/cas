package org.apereo.cas.support.oauth.web.audit;

import lombok.val;

import org.apereo.cas.services.RegisteredServiceTestUtils;
import org.apereo.cas.support.oauth.OAuth20GrantTypes;
import org.apereo.cas.support.oauth.services.OAuthRegisteredService;
import org.apereo.cas.support.oauth.web.response.accesstoken.ext.AccessTokenRequestDataHolder;
import org.apereo.cas.ticket.OAuthToken;
import org.apereo.cas.util.CollectionUtils;
import org.aspectj.lang.JoinPoint;
import org.junit.Test;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

/**
 * This is {@link AccessTokenGrantRequestAuditResourceResolverTests}.
 *
 * @author Misagh Moayyed
 * @since 5.3.0
 */
public class AccessTokenGrantRequestAuditResourceResolverTests {
    @Test
    public void verifyAction() {
        val r = new AccessTokenGrantRequestAuditResourceResolver();
        val token = mock(OAuthToken.class);
        when(token.getId()).thenReturn("CODE");
        when(token.getService()).thenReturn(RegisteredServiceTestUtils.getService());

        val service = new OAuthRegisteredService();
        service.setClientId("CLIENTID");
        service.setName("OAUTH");
        service.setId(123);

        val holder = AccessTokenRequestDataHolder.builder()
            .scopes(CollectionUtils.wrapSet("email"))
            .service(token.getService())
            .authentication(token.getAuthentication())
            .registeredService(service)
            .grantType(OAuth20GrantTypes.AUTHORIZATION_CODE)
            .token(token)
            .ticketGrantingTicket(token != null ? token.getTicketGrantingTicket() : null)
            .build();
        assertTrue(r.resolveFrom(mock(JoinPoint.class), holder).length > 0);
    }
}
