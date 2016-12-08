package ac.moim.common.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.social.connect.Connection;
import org.springframework.social.google.api.Google;
import org.springframework.social.google.api.impl.GoogleTemplate;
import org.springframework.social.google.api.plus.Person;
import org.springframework.social.google.api.plus.PlusOperations;
import org.springframework.social.google.connect.GoogleConnectionFactory;
import org.springframework.social.oauth2.AccessGrant;
import org.springframework.social.oauth2.GrantType;
import org.springframework.social.oauth2.OAuth2Operations;
import org.springframework.social.oauth2.OAuth2Parameters;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.view.RedirectView;

/**
 * Created by SONG_HOHOON on 2016-12-06.
 */
@Slf4j
@Controller
public class LoginController {

	@Autowired
	private GoogleConnectionFactory googleConnectionFactory;

	@Autowired
	private OAuth2Parameters googleOAuth2Parameters;

	@RequestMapping(value = "/login", method = RequestMethod.GET)
	public RedirectView login() {

		OAuth2Operations oauthOperations = googleConnectionFactory.getOAuthOperations();
		String redirectedUrl = oauthOperations.buildAuthorizeUrl(GrantType.AUTHORIZATION_CODE, googleOAuth2Parameters);

		return new RedirectView(redirectedUrl);
	}

	@RequestMapping(value = "/oauth2callback")
	public String oauth2Callback(@RequestParam(value = "code")String code) {

		OAuth2Operations oAuth2Operations = googleConnectionFactory.getOAuthOperations();
		AccessGrant accessGrant = oAuth2Operations.exchangeForAccess(code, googleOAuth2Parameters.getRedirectUri(), null);
		String accessToken = accessGrant.getAccessToken();
		Long expireTime =  accessGrant.getExpireTime();
		if (expireTime != null && expireTime < System.currentTimeMillis()) {
			accessToken = accessGrant.getRefreshToken();
			log.info("accessToken is expired. refresh token = {}" , accessToken);
		}

		Connection<Google> connection = googleConnectionFactory.createConnection(accessGrant);
		Google google = connection == null ? new GoogleTemplate(accessToken) : connection.getApi();

		PlusOperations plusOperations = google.plusOperations();
		Person person = plusOperations.getGoogleProfile();

		return "hello";
	}
}
