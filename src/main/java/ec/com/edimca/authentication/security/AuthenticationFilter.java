package ec.com.edimca.authentication.security;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.Gson;

import ec.com.edimca.authentication.model.User;
import ec.com.edimca.authentication.util.ResponseAuth;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import static ec.com.edimca.authentication.constants.SecurityConstants.*;

public class AuthenticationFilter extends UsernamePasswordAuthenticationFilter {

	private static Logger logger = LogManager.getLogger();

	private AuthenticationManager authenticationManager;

	private Gson gson = new Gson();

	public AuthenticationFilter(AuthenticationManager authenticationManager) {
		this.authenticationManager = authenticationManager;
		setFilterProcessesUrl(SIGN_IN_URL);
	}

	@Override
	public Authentication attemptAuthentication(HttpServletRequest req, HttpServletResponse res)
			throws AuthenticationException {

		try {
			User applicationUser = new ObjectMapper().readValue(req.getInputStream(), User.class);
			return authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(
					applicationUser.getEmail(), applicationUser.getPassword(), new ArrayList<>()));
		} catch (Exception e) {
			try {
				res.setStatus(401);
				res.getWriter().print(e.getMessage());
				res.getWriter().flush();
			} catch (Exception e2) {
				logger.error(e2.getMessage());
			}
			logger.error(e.getMessage());
			return null;
		}
	}

	@Override
	protected void successfulAuthentication(HttpServletRequest req, HttpServletResponse res, FilterChain chain,
			Authentication auth) throws IOException, ServletException {

		List<String> rolesList = new ArrayList<String>();

		auth.getAuthorities().forEach(role -> {
			rolesList.add(role.toString());
		});

		String[] roles = rolesList.toArray(new String[0]);

		Date expiresIn = new Date(System.currentTimeMillis() + EXPIRATION_TIME);

		String email = ((org.springframework.security.core.userdetails.User) auth.getPrincipal()).getUsername();

		String accessToken = JWT.create()
				.withSubject(email)
				.withExpiresAt(expiresIn).withArrayClaim("roles", roles).sign(Algorithm.HMAC512(KEY.getBytes()));

		ResponseAuth response = new ResponseAuth(accessToken, expiresIn);
		String responseJson = this.gson.toJson(response);

		res.setStatus(200);
		res.setContentType("application/json");
		res.getWriter().print(responseJson);
		res.getWriter().flush();
	}

}
